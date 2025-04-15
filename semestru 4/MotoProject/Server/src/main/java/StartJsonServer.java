import Services.IService;
import network.utils.AbstractServer;
import network.utils.ChatJsonConcurrentServer;
import network.utils.ServerException;

import repository.JDBCImplementation.PlayerDBRepository;
import repository.JDBCImplementation.RaceDBRepository;
import repository.JDBCImplementation.TeamDBRepository;
import repository.JDBCImplementation.UserDBRepository;
import repository.PlayerRepository;
import repository.RaceRepository;
import repository.TeamRepository;
import repository.UserRepository;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import server.Service;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class StartJsonServer {
    private static int defaultPort=55555;
    private static Logger logger = LogManager.getLogger(StartJsonServer.class);
    public static void main(String[] args) {
        Properties serverProps=new Properties();
        try {
            serverProps.load(StartJsonServer.class.getResourceAsStream("/chatserver.properties"));
           logger.info("Server properties set. {} ", serverProps);
            //serverProps.list(System.out);
        } catch (IOException e) {
            logger.error("Cannot find chatserver.properties "+e);
            logger.debug("Looking for file in "+(new File(".")).getAbsolutePath());
            return;
        }
        UserRepository userRepo=new UserDBRepository(serverProps);
        PlayerRepository playerRepo=new PlayerDBRepository(serverProps);
        TeamRepository teamRepo=new TeamDBRepository(serverProps);
        RaceRepository raceRepo=new RaceDBRepository(serverProps,playerRepo);
        Service ServerImpl=new Service(playerRepo,userRepo,raceRepo,teamRepo);

        int chatServerPort=defaultPort;
        try {
            chatServerPort = Integer.parseInt(serverProps.getProperty("chat.server.port"));
        }catch (NumberFormatException nef){
            logger.error("Wrong  Port Number"+nef.getMessage());
            logger.debug("Using default port "+defaultPort);
        }
        logger.debug("Starting server on port: "+chatServerPort);
        AbstractServer server = new ChatJsonConcurrentServer(chatServerPort, ServerImpl);
        try {
            server.start();
        } catch (ServerException e) {
            logger.error("Error starting the server" + e.getMessage());
        }
    }
}
