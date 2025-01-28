package ubb.scs.map.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ubb.scs.map.Domain.User;
import ubb.scs.map.Domain.UserInstance;
import ubb.scs.map.Utils.Constants;

import java.util.Optional;

public class ProfilePageController extends ControllerSuperclass {
    @FXML
    public Label name;
    @FXML
    public Label age;
    @FXML
    public Label joined;
    public void init(){
        Optional<User> user = service.getUserById(UserInstance.getInstance().getId());
        if (user.isPresent()) {
            name.setText(user.get().getUsername());
            age.setText("Age:"+ user.get().getAge());
            joined.setText("Joined:"+user.get().getJoinDate().format(Constants.DATE_FORMATTER));
        }
    }

    @FXML
    protected void backToMainMenu() {
        service.switchScene("main");
    }

}
