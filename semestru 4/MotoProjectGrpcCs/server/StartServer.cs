// See https://aka.ms/new-console-template for more information
using System.Configuration;
using System.Net.Sockets;
using System.Reflection;
using moto.networking;
using moto.networking.jsonprotocol;
using moto.persistence;
using moto.services;
using log4net;
using log4net.Config;
using moto.persistence.database;
using moto.server;

public class StartServer
    {
        private static int DEFAULT_PORT=55556;
        private static String DEFAULT_IP="127.0.0.1";
        private static readonly ILog log = LogManager.GetLogger(typeof(StartServer));
        public static void Main(string[] args)
        {
            
          
            //configurare jurnalizare folosind log4net
            var logRepository = LogManager.GetRepository(Assembly.GetEntryAssembly());
            XmlConfigurator.Configure(logRepository, new FileInfo("log4net.config"));
			
            log.Info("Starting chat server");
            log.Info("Reading properties from app.config ...");
           int port = DEFAULT_PORT;
           String ip = DEFAULT_IP;
           String portS= ConfigurationManager.AppSettings["port"];
           if (portS == null)
           {
               log.Debug("Port property not set. Using default value "+DEFAULT_PORT);
           }
           else
           {
               bool result = Int32.TryParse(portS, out port);
               if (!result)
               {
                   log.Debug("Port property not a number. Using default value "+DEFAULT_PORT);
                   port = DEFAULT_PORT;
                   log.Debug("Portul "+port);
               }
           }
           String ipS=ConfigurationManager.AppSettings["ip"];
           
           if (ipS == null)
           {
               log.Info("Port property not set. Using default value "+DEFAULT_IP);
           }
           log.InfoFormat("Configuration Settings for database {0}",GetConnectionStringByName("chatDB"));
           IDictionary<String, string> props = new SortedList<String, String>();
           props.Add("ConnectionString", GetConnectionStringByName("chatDB"));
           IUserRepository userRepo=new UserDbRepository(props["ConnectionString"]);
           IPlayerRepository playerRepo=new PlayerDbRepository(props["ConnectionString"]);
           ITeamRepository teamRepo=new TeamDbRepository(props["ConnectionString"]);
           IRaceRepository raceRepo=new RaceDbRepository(props["ConnectionString"],playerRepo);
            IService serviceImpl = new Service(playerRepo,userRepo,raceRepo,teamRepo);

         
           log.DebugFormat("Starting server on IP {0} and port {1}", ip, port);
			//SerialChatServer server = new SerialChatServer(ip,port, serviceImpl);
            JsonChatServer server = new JsonChatServer(ip,port, serviceImpl);
            server.Start();
            log.Debug("Server started ...");
            //Console.WriteLine("Press <enter> to exit...");
            Console.ReadLine();
            
        }
        
        
		
        static string GetConnectionStringByName(string name)
        {
            // Assume failure.
            string returnValue = null;

            // Look for the name in the connectionStrings section.
            ConnectionStringSettings settings =ConfigurationManager.ConnectionStrings[name];

            // If found, return the connection string.
            if (settings != null)
                returnValue = settings.ConnectionString;

            return returnValue;
        }
    }

   public class SerialChatServer: ConcurrentServer 
    {
        private IService server;
        private ClientJsonWorker worker;
        public SerialChatServer(string host, int port, IService server) : base(host, port)
            {
                this.server = server;
                Console.WriteLine("SerialChatServer...");
        }
        protected override Thread createWorker(TcpClient client)
        {
            worker = new ClientJsonWorker(server, client);
            return new Thread(new ThreadStart(worker.run));
        }
    }
    
   public class JsonChatServer: ConcurrentServer 
   {
       private IService server;
       private ClientJsonWorker worker;
       private static readonly ILog log = LogManager.GetLogger(typeof(JsonChatServer));
       public JsonChatServer(string host, int port, IService server) : base(host, port)
       {
           this.server = server;
           log.Debug("Creating JsonChatServer...");
       }
       protected override Thread createWorker(TcpClient client)
       {
           worker = new ClientJsonWorker(server, client);
           return new Thread(worker.run);
       }
   }
    