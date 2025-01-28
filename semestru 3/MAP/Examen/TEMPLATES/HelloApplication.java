package ubb.scs.map.avioane;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ubb.scs.map.avioane.Domain.Validators.MenuItemValidator;
import ubb.scs.map.avioane.Domain.Validators.TableValidator;
import ubb.scs.map.avioane.Repository.MenuItemRepository;
import ubb.scs.map.avioane.Repository.TableRepository;
import ubb.scs.map.avioane.Service.ScreenService;
import ubb.scs.map.avioane.Service.Service;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        TableRepository tableRepository = new TableRepository(
                "jdbc:postgresql://localhost:5432/FapteBune",
                "postgres",
                "12345678",
                new TableValidator()
                );
        MenuItemRepository menuRepository = new MenuItemRepository(
                "jdbc:postgresql://localhost:5432/FapteBune",
                "postgres",
                "12345678",
                new MenuItemValidator()
        );
        Service service = new Service(menuRepository, tableRepository);
        ScreenService screenService = new ScreenService(service);
        screenService.createStage("main","Main.fxml");
        screenService.showStage("main");
    }

    public static void main(String[] args) {
        launch();
    }
}