import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.HibernateImplementation.PlayerHibernateRepository;
import repository.HibernateImplementation.RaceHibernateRepository;
import repository.HibernateImplementation.TeamHibernateRepository;
import repository.HibernateImplementation.UserHibernateRepository;
import repository.JDBCImplementation.PlayerDBRepository;
import repository.JDBCImplementation.RaceDBRepository;
import repository.JDBCImplementation.TeamDBRepository;
import repository.JDBCImplementation.UserDBRepository;
import repository.PlayerRepository;
import repository.RaceRepository;
import repository.TeamRepository;
import repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class GrpcServer {
    private static Logger logger = LogManager.getLogger(GrpcServer.class);
    public static void main(String[] args) throws IOException, InterruptedException {
            Properties serverProps=new Properties();
            try {
                serverProps.load(GrpcServer.class.getResourceAsStream("/chatserver.properties"));
                logger.info("Server properties set. {} ", serverProps);
            } catch (IOException e) {
                logger.error("Cannot find chatserver.properties "+e);
                logger.debug("Looking for file in "+(new File(".")).getAbsolutePath());
                return;
            }
            UserRepository userRepo=new UserHibernateRepository();
            PlayerRepository playerRepo=new PlayerHibernateRepository();
            TeamRepository teamRepo=new TeamHibernateRepository();
            RaceRepository raceRepo=new RaceHibernateRepository();
        Server server = ServerBuilder.forPort(9090)
                .addService(new MotoServiceImpl(playerRepo,userRepo,raceRepo,teamRepo))
                .build()
                .start();

        System.out.println("Server started on port 9090");
        server.awaitTermination();
    }
}
