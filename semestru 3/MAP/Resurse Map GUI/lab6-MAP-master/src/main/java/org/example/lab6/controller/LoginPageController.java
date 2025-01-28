package org.example.lab6.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.lab6.domain.User;
import org.example.lab6.service.*;

public class LoginPageController extends ControllerSuperclass {
    @FXML
    private TextField textFieldUsername;

    @FXML
    private PasswordField passwordFieldPassword;

    @FXML
    private Button buttonLogin, buttonRegister;

    @FXML
    public void handleButtonLoginClicked(ActionEvent event) {
        SceneService sceneService = super.getSceneService();
        UserService userService = super.getUserService();
        String username = textFieldUsername.getText();
        String password = passwordFieldPassword.getText();
        User currentUser = userService.userLogin(username, password);
        sceneService.setCurrentUser(currentUser);
        sceneService.addScene("tasks", "main-page.fxml");
        sceneService.addScene("friends", "friends-page.fxml");
        sceneService.addScene("profile", "profile-page.fxml");
        sceneService.addScene("add-friend", "add-friend-page.fxml");
        sceneService.switchScene("tasks");
    }
}
