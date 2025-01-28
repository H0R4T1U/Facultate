package ubb.scs.map.clinica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ubb.scs.map.clinica.Domain.Validators.ConsultatieValidator;
import ubb.scs.map.clinica.Domain.Validators.MedicValidator;
import ubb.scs.map.clinica.Domain.Validators.SectieValidator;
import ubb.scs.map.clinica.Repository.ConsultatieRepository;
import ubb.scs.map.clinica.Repository.MedicRepository;
import ubb.scs.map.clinica.Repository.SectieRepository;
import ubb.scs.map.clinica.Service.ScreenService;
import ubb.scs.map.clinica.Service.Service;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        ConsultatieRepository consultatieRepository = new ConsultatieRepository(
                "jdbc:postgresql://localhost:5432/Clinica",
                "postgres",
                "12345678",
                new ConsultatieValidator()
        );
        MedicRepository medicRepository = new MedicRepository(
                "jdbc:postgresql://localhost:5432/Clinica",
                "postgres",
                "12345678",
                new MedicValidator()
        );
        SectieRepository sectieRepository = new SectieRepository(
                "jdbc:postgresql://localhost:5432/Clinica",
                "postgres",
                "12345678",
                new SectieValidator()
        );

        Service service = new Service(consultatieRepository, medicRepository, sectieRepository);
        ScreenService screenService = new ScreenService(service);
        screenService.showStage("main.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}