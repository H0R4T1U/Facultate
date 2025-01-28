package ubb.scs.map.Controllers;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import ubb.scs.map.Domain.MessageAlert;
import ubb.scs.map.Domain.User;
import ubb.scs.map.Domain.UserInstance;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

public class RegisterController extends ControllerSuperclass {
    @FXML
    private TextField ageField;
    @FXML
    private TextField PhoneNumberField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    public void signIn() {
        service.switchScene("login");
    }
    public void signUp(ActionEvent actionEvent) {

        String password = passwordField.getText();
        String name = usernameField.getText();
        Integer age = Integer.parseInt(ageField.getText());
        String phoneNumber = PhoneNumberField.getText();
        passwordField.clear();
        usernameField.clear();
        ageField.clear();
        PhoneNumberField.clear();
        
        Optional<User> created = service.createUser(new User(name, new BCryptPasswordEncoder().encode(password), phoneNumber, LocalDateTime.now() ,age));
        if(created.isPresent()) {
            if(service.login(name,password)){
                service.switchScene("main");
            }else{
                service.switchScene("login");
            }
        }else{
            new Thread(() -> {
                MessageAlert.showWarning("The account was not created!!");
            }).start();
        }

    }
}
