package ubb.scs.map.faptebune;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ubb.scs.map.faptebune.Controllers.HelloController;
import ubb.scs.map.faptebune.Domain.Nevoie;
import ubb.scs.map.faptebune.Domain.Persoana;
import ubb.scs.map.faptebune.Domain.Validators.NevoieValidator;
import ubb.scs.map.faptebune.Domain.Validators.PersoanaValidator;
import ubb.scs.map.faptebune.Repository.NevoieDatabaseRepository;
import ubb.scs.map.faptebune.Repository.PersoanaDatabaseRepository;
import ubb.scs.map.faptebune.Repository.Repository;
import ubb.scs.map.faptebune.Service.EntityService;
import ubb.scs.map.faptebune.Service.NevoiService;
import ubb.scs.map.faptebune.Service.PersoanaService;
import ubb.scs.map.faptebune.Service.ScreenService;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Repository<Long, Persoana> persoaneRepository = new PersoanaDatabaseRepository(
            "jdbc:postgresql://localhost:5432/FapteBune",
            "postgres",
            "12345678",
            new PersoanaValidator()
        );
        Repository<Long, Nevoie> nevoieRepository = new NevoieDatabaseRepository(
                "jdbc:postgresql://localhost:5432/FapteBune",
                "postgres",
                "12345678",
                new NevoieValidator()
        );

        PersoanaService service = new PersoanaService(persoaneRepository);
        NevoiService nevoiService = new NevoiService(nevoieRepository);
        ScreenService sceneService = new ScreenService(stage, nevoiService,service);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        HelloController controller = new HelloController();
        controller.setPerosanaService(service);
        controller.setScreenService(sceneService);
        controller.setNevoiService(nevoiService);
        fxmlLoader.setController(controller);

        sceneService.addScene("main", "mainpage.fxml");

        try {
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException("Error loading FXML", e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}