package ubb.scs.map.faptebune.Service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ubb.scs.map.faptebune.Controllers.ControllerSuperclass;
import ubb.scs.map.faptebune.Domain.Persoana;
import ubb.scs.map.faptebune.HelloApplication;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ScreenService  {
    private final Stage primaryStage;
    private final Map<String, Scene> scenes = new HashMap<>();
    private final Map<String, ControllerSuperclass> controllers = new HashMap<>();
    private final NevoiService nevoiService;
    private final PersoanaService persoanaService;
    private  Persoana user;
    public ScreenService(Stage primaryStage, NevoiService nevoiService, PersoanaService persoanaService) {
        this.primaryStage = primaryStage;
        this.nevoiService = nevoiService;
        this.persoanaService = persoanaService;

    }

    public Persoana getUser() {
        return user;
    }

    public void setUser(Persoana user) {
        this.user = user;
    }

    public void addScene(String name, String fxmlFile) {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(fxmlFile));
        try {
            Parent root = loader.load();
            ControllerSuperclass controller = loader.getController();
            controller.setNevoiService(nevoiService);
            controller.setPerosanaService(persoanaService);
            controller.setScreenService(this);
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
