package ubb.scs.map.Services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ubb.scs.map.Controllers.ControllerSuperclass;
import ubb.scs.map.HelloApplication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ScreenService  {
    private final Stage primaryStage;
    private final Map<String, Scene> scenes = new HashMap<>();
    private final Map<String, ControllerSuperclass> controllers = new HashMap<>();


    public ScreenService(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void addScene(String name, String fxmlFile) {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(fxmlFile));
        try {
            Parent root = loader.load();
            ControllerSuperclass controller = loader.getController();
            scenes.put(name, new Scene(root));
            controllers.put(name, controller);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchScene(String name) {
        Scene scene = scenes.get(name);
        ControllerSuperclass controller = controllers.get(name);
        if (scene != null) {
            controller.init();
            primaryStage.setScene(scene);
        } else {
            System.out.println("Scene not found: " + name);
        }
    }
}
