package ubb.scs.map.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ubb.scs.map.Domain.MessageAlert;


public class LoginController extends ControllerSuperclass {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;





    @FXML
    protected void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        usernameField.clear();
        passwordField.clear();
        if (service.login(username,password)) {
            System.out.println(username + " Logged in!");
            service.switchScene("main");
        } else{
            new Thread(() -> {
                MessageAlert.showWarning("Username or password is incorrect. Please try again.!");
            }).start();
        }

    }
    @FXML
    protected void toRegister() {
        service.switchScene("register");
    }
}
