using System.Net.Sockets;
using System.Runtime.Serialization;
using System.Runtime.Serialization.Formatters.Binary;
using System.Text;
using System.Text.Json;
using chat.networking.jsonprotocol;
using moto.model;
using moto.networking.dto;
using moto.services;
using log4net;
using moto.model;
using moto.services;

namespace moto.networking.jsonprotocol;

public class ChatClientJsonWorker:  IObserver 
{
    private IService server;
		private TcpClient connection;

		private NetworkStream stream;
		private volatile bool connected;
		private static readonly ILog log = LogManager.GetLogger(typeof(ChatClientJsonWorker));
		public ChatClientJsonWorker(IService server, TcpClient connection)
		{
			this.server = server;
			this.connection = connection;
			try
			{
				
				stream=connection.GetStream();
				connected=true;
			}
			catch (Exception e)
			{
                log.Error(e.StackTrace);
			}
		}

		public virtual void run()
		{
			using StreamReader reader = new StreamReader(stream, Encoding.UTF8);
			while(connected)
			{
				try
				{
					string requestJson = reader.ReadLine(); // Read JSON line-by-line
					if (string.IsNullOrEmpty(requestJson)) continue;
					log.DebugFormat("Received json request {0}",requestJson);
					Request request = JsonSerializer.Deserialize<Request>(requestJson);
					log.DebugFormat("Deserializaed Request {0} ",request);
                  	Response response =handleRequest(request);
					if (response!=null)
					{
					   sendResponse(response);
					}
				}
				catch (Exception e)
				{
					log.ErrorFormat("run eroare {0}",e.Message);
					if (e.InnerException!=null)
						log.ErrorFormat("run inner error {0}",e.InnerException.Message);
                    log.Error(e.StackTrace);
				}
				
				try
				{
					Thread.Sleep(1000);
				}
				catch (Exception e)
				{
                    log.Error(e.StackTrace);
				}
			}
			try
			{
				stream.Close();
				connection.Close();
			}
			catch (Exception e)
			{
				log.Error("Error "+e);
			}
		}


		
	private static Response okResponse=JsonProtocolUtils.CreateOkResponse();

	private Response handleRequest(Request request)
		{
			Response response =null;
			if (request.Type==RequestType.LOGIN)
			{
				log.Debug("Login request ...");
				User user =DTOUtils.GetFromDTO(request.User);
				user.Password = TextUtils.SimpleDecode(user.Password);
				try
                {
                    lock (server)
                    {
                        server.Login(user, this);
                    }
					return okResponse;
				}
				catch (MotoException e)
				{
					connected=false;
					return JsonProtocolUtils.CreateErrorResponse(e.Message);
				}
			}
			if (request.Type==RequestType.LOGOUT)
			{
				log.Debug("Logout request");
				
				User user =DTOUtils.GetFromDTO(request.User);
				try
				{
                    lock (server)
                    {

                        server.Logout(user, this);
                    }
					connected=false;
					return okResponse;

				}
				catch (MotoException e)
				{
				   return JsonProtocolUtils.CreateErrorResponse(e.Message);
				}
			}

			if (request.Type == RequestType.ADD_PLAYER)
			{
				log.Debug("Add player request");
				Player player =DTOUtils.GetFromDTO(request.Player);
				int raceId = int.Parse(request.RaceId);
				try
				{
					server.Add(player, raceId, this);
					return okResponse;
				}
				catch (MotoException e)
				{
					return JsonProtocolUtils.CreateErrorResponse(e.Message);
				}
			}

			if (request.Type == RequestType.GET_RACES)
			{
				log.Debug("Get races request");
				try
				{
					Race[] races = server.GetAllRaces();
					return JsonProtocolUtils.CreateGetRacesResponse(races);
				}
				catch (MotoException e)
				{
					return JsonProtocolUtils.CreateErrorResponse(e.Message);
				}
			}

			if (request.Type == RequestType.GET_TEAM_PLAYERS)
			{
				log.Debug("Get team players request");
				try
				{
					int raceId = int.Parse(request.RaceId);
					int teamId = int.Parse(request.TeamId);
					Player[] players = server.GetRacePlayersOfTeam(raceId, teamId);
					return JsonProtocolUtils.CreateGetPlayerResponse(players);
				}
				catch (MotoException e)
				{
					return JsonProtocolUtils.CreateErrorResponse(e.Message);
				}
			}

			if (request.Type == RequestType.GET_TEAM)
			{
				log.Debug("Get Team Request");
				try
				{
					Team? team = server.GetTeamByName(request.TeamName);
					if (team != null)
					{
						return JsonProtocolUtils.CreateGetTeamResponse(DTOUtils.GetDTO(team));
					}
					return JsonProtocolUtils.CreateErrorResponse("Team not found");
				}
				catch (MotoException e)
				{
					return JsonProtocolUtils.CreateErrorResponse(e.Message);
				}
			}
			
			return response;
		}

	private void sendResponse(Response response)
		{
			//de modificat pentru json
			String jsonString=JsonSerializer.Serialize(response);
			log.DebugFormat("sending response {0}",jsonString);
			lock (stream)
			{
				byte[] data = Encoding.UTF8.GetBytes(jsonString + "\n"); // Append newline 
				stream.Write(data, 0, data.Length);
				stream.Flush();
			}

		}

	public void PlayerAdded(Player player)
	{
		Response response = JsonProtocolUtils.CreatePlayerAddedResponse(DTOUtils.GetDTO(player));
		try
		{
			sendResponse(response);
		}
		catch (IOException e)
		{
			throw new MotoException(e.Message, e);
		}
	}
}