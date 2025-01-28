package ubb.scs.map.avioane.Service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ubb.scs.map.avioane.Controllers.ControllerSuperclass;
import ubb.scs.map.avioane.HelloApplication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ScreenService {
    private final Map<String, Stage> stages = new HashMap<>();
    private final Map<String, ControllerSuperclass> controllers = new HashMap<>();
    private final Service service;

    public ScreenService(Service service) {
        this.service = service;
    }

    public void createStage(String name, String fxmlFile) {
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(fxmlFile));
        try {
            Parent root = loader.load();
            ControllerSuperclass controller = loader.getController();
            controller.setService(service);
            controller.setScreenService(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stages.put(name, stage);
            controllers.put(name, controller);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage(String name) {
        Stage stage = stages.get(name);
        ControllerSuperclass controller = controllers.get(name);
        if (stage != null) {
            controller.init();
            stage.show();
        } else {
            System.out.println("Stage not found: " + name);
        }
    }

    public void closeStage(String name) {
        Stage stage = stages.get(name);
        if (stage != null) {
            stage.close();
            stages.remove(name);
            controllers.remove(name);
        } else {
            System.out.println("Stage not found: " + name);
        }
    }
}
