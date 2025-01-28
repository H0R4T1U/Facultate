package ubb.scs.map.clinica.Service;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ubb.scs.map.clinica.Controllers.ControllerSuperclass;
import ubb.scs.map.clinica.HelloApplication;
import ubb.scs.map.clinica.Service.Service;

import java.io.IOException;

public class ScreenService {
    private final Service service;

    public ScreenService(Service service) {
        this.service = service;
    }

    public void showStage(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(fxmlFile));
            Parent root = loader.load();

            ControllerSuperclass controller = loader.getController();
            controller.setService(service);
            controller.setScreenService(this);
            controller.init();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showSectieStage(Long id){
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("sectie.fxml"));
            Parent root = loader.load();

            ControllerSuperclass controller = loader.getController();
            controller.setService(service);
            controller.setScreenService(this);
            controller.setSectieId(id);
            controller.init();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showMedicStage(Long id) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("medic.fxml"));
            Parent root = loader.load();

            ControllerSuperclass controller = loader.getController();
            controller.setService(service);
            controller.setScreenService(this);
            controller.setDoctorId(id);
            controller.init();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}