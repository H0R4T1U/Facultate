package ubb.scs.map.examen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ubb.scs.map.examen.Repository.CityRepository;
import ubb.scs.map.examen.Repository.TrainStationsRepository;
import ubb.scs.map.examen.Service.ScreenService;
import ubb.scs.map.examen.Service.Service;
import ubb.scs.map.examen.domain.Validators.CityValidator;
import ubb.scs.map.examen.domain.Validators.TrainStationsValidator;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        CityRepository cityRepository = new CityRepository(
                "jdbc:postgresql://localhost:5432/Examen",
                "postgres",
                "12345678",
                new CityValidator()
        );
        TrainStationsRepository trainStationsRepository = new TrainStationsRepository(
                "jdbc:postgresql://localhost:5432/Examen",
                "postgres",
                "12345678",
                new TrainStationsValidator()
        );
        Service service = new Service(cityRepository,trainStationsRepository);
        ScreenService screenService = new ScreenService(service);
        screenService.showStage("init.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}