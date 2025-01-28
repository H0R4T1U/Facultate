package ubb.scs.map.examen.Controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class InitController extends ControllerSuperclass {

    public void createClient(ActionEvent actionEvent) {
        this.getScreenService().showStage("Client.fxml");
    }
}
