using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text;
using System.Text.Json;
using moto.model;
using moto.networking.dto;
using moto.services;
using log4net;
using moto.networking.jsonprotocol;

namespace chat.networking.jsonprotocol;

public class ChatServerJsonProxy: IService
{
		 private string host;
		private int port;

		private IObserver client;
		private NetworkStream stream;
		private TcpClient connection;
		private Queue<Response> responses;
		private volatile bool finished;
        private EventWaitHandle _waitHandle;
        private static readonly ILog log = LogManager.GetLogger(typeof(ChatServerJsonProxy));
		public ChatServerJsonProxy(string host, int port)
		{
			this.host = host;
			this.port = port;
			responses=new Queue<Response>();
		}

		public User FindByUsername(string username)
		{
			throw new NotImplementedException();
		}

		public virtual void Login(User user, IObserver client)
		{
			initializeConnection();
			user.Password=TextUtils.SimpleEncode(user.Password);
			sendRequest(JsonProtocolUtils.CreateLoginRequest(user));
			Response response =readResponse();
			if (response.Type==ResponseType.OK)
			{
				this.client=client;
				return;
			}
			if (response.Type==ResponseType.ERROR)
			{
				String err =response.ErrorMessage;
				closeConnection();
				throw new MotoException(err);
			}
		}
		

	public virtual void Logout(User user, IObserver client)
		{
			sendRequest(JsonProtocolUtils.CreateLogoutRequest(user));
			Response response =readResponse();
			closeConnection();
			if (response.Type==ResponseType.ERROR)
			{
				throw new MotoException(response.ErrorMessage);
			}
		}

	public void Add(Player p, int raceId, IObserver client)
	{
		try
		{
			initializeConnection();
			Request req = JsonProtocolUtils.CreateAddPlayerRequest(p, raceId.ToString());
			sendRequest(req);
			Response response =readResponse();
			if (response.Type == ResponseType.ERROR)
			{
				string err = response.ErrorMessage;
				closeConnection();
				throw new MotoException(err);
			} 
		}catch (MotoException e)
		{
			log.Error(e);
			log.Error(e.StackTrace);
		}
	}

	public Team? GetTeamByName(string name)
	{
		try
		{
			initializeConnection();

			Request req = JsonProtocolUtils.CreateGetTeamRequest(name);
			sendRequest(req);
			Response response = readResponse();
			if (response.Type == ResponseType.GET_TEAM)
			{
				Team team = DTOUtils.GetFromDTO(response.Team);
				return team;
			}

			if (response.Type == ResponseType.ERROR)
			{
				string err = response.ErrorMessage;
				closeConnection();
				throw new MotoException(err);
			}
		}
		catch (MotoException e)
		{
			log.Error(e);
			log.Error(e.StackTrace);
		}

		return null;
	}

	public Race[] GetAllRaces()
	{
		initializeConnection();
		Request req = JsonProtocolUtils.CreateGetRacesRequest();
		sendRequest(req);
		Response response = readResponse();
		if (response.Type == ResponseType.GET_RACES)
		{
			return DTOUtils.GetFromDTO(response.Races);
		}

		if (response.Type == ResponseType.ERROR)
		{
			string err = response.ErrorMessage;
			closeConnection();
			throw new MotoException(err);
		}
		return null;
	}

	public Player[] GetRacePlayersOfTeam(int raceId, int teamId)
	{
		try
		{
			initializeConnection();
			string tID = teamId.ToString();
			string rID = raceId.ToString();
			Request req = JsonProtocolUtils.CreateGetTeamPlayersRequest(rID, tID);
			sendRequest(req);
			Response response = readResponse();
			if (response.Type == ResponseType.GET_TEAM_PLAYERS)
			{
				return DTOUtils.GetFromDTO(response.Players);
			}
			if (response.Type == ResponseType.ERROR)
			{
				string err = response.ErrorMessage;
				closeConnection();
				throw new MotoException(err);
			}
		}catch (MotoException e)
		{
			log.Error(e);
			log.Error(e.StackTrace);
		}
		return null;
	}


	private void closeConnection()
		{
			finished=true;
			try
			{
				stream.Close();
				connection.Close();
                _waitHandle.Close();
				client=null;
			}
			catch (Exception e)
			{
				Console.WriteLine(e.StackTrace);
			}
		}

		private void sendRequest(Request request)
		{
			try
			{
				lock (stream)
				{
					
					string jsonRequest = JsonSerializer.Serialize(request);
					log.DebugFormat("Sending request {0}",jsonRequest);
					byte[] data = Encoding.UTF8.GetBytes(jsonRequest + "\n"); // Append newline 
					stream.Write(data, 0, data.Length);
					stream.Flush();
					
				}
			}
			catch (Exception e)
			{
				throw new MotoException("Error sending object "+e);
			}

		}

		private Response readResponse()
		{
			Response response =null;
			try
			{
                _waitHandle.WaitOne();
				lock (responses)
				{
                    response = responses.Dequeue();
                
				}
			}catch (Exception e) {
				Console.WriteLine(e.StackTrace);
			}
			return response;
		}
		private void initializeConnection()
		{
			 try
			 {
				connection=new TcpClient(host,port);
				stream=connection.GetStream();
				finished=false;
                _waitHandle = new AutoResetEvent(false);
				startReader();
			}catch (Exception e){
                Console.WriteLine(e.StackTrace);
			}
		}
		private void startReader()
		{
			Thread tw =new Thread(run);
			tw.Start();
		}


		private void handleUpdate(Response response)
		{
			log.DebugFormat("handleUpdate called with {0}",response);
			if (response.Type == ResponseType.NEW_PLAYER)
			{
				Player player = DTOUtils.GetFromDTO(response.NewPlayer);
				log.DebugFormat("Player Added: {0}",player.Name);
				try
				{
					client.PlayerAdded(player);
				}catch (Exception e)
				{
					log.Error("Error notifying client about new player "+e);
				}
			}
		}

		private bool isUpdate(Response response){
			return response.Type == ResponseType.NEW_PLAYER;
		}

		public virtual void run()
			{
				using StreamReader reader = new StreamReader(stream, Encoding.UTF8);
				while(!finished)
				{
					try
					{
			           string responseJson = reader.ReadLine();
                        if (string.IsNullOrEmpty(responseJson)) 
	                        continue; //nu s-a citit un raspuns valid
                        Response response=JsonSerializer.Deserialize<Response>(responseJson);
						log.Debug("response received "+response);
						if (isUpdate(response))
						{
							 handleUpdate(response);
						}
						else
						{
							lock (responses)
							{
                                responses.Enqueue(response);
							}
                            _waitHandle.Set();
						}
					}
					catch (Exception e)
					{
						log.Error("Reading error "+e);
					}
					
				}
			}
		
}