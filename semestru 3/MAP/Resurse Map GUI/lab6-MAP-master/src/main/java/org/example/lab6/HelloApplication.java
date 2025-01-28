package org.example.lab6;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.lab6.controller.*;
import org.example.lab6.domain.*;
import org.example.lab6.domain.validators.FriendshipValidator;
import org.example.lab6.domain.validators.RequestValidator;
import org.example.lab6.domain.validators.UserValidator;
import org.example.lab6.repository.FriendshipDBRepo;
import org.example.lab6.repository.Repository;
import org.example.lab6.repository.RequestDBRepo;
import org.example.lab6.repository.UserDBRepo;
import org.example.lab6.service.*;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        Repository<Long, User> userRepository = new UserDBRepo("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "postgres", new UserValidator());
        Repository<Tuple<Long, Long>, Friendship> friendshipRepository = new FriendshipDBRepo("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "postgres", new FriendshipValidator());
        UserService userService = new UserService(userRepository, friendshipRepository);
        FriendshipService friendshipService = new FriendshipService(friendshipRepository, userRepository);
        Repository<Tuple<Long, Long>, Request> requestRepository = new RequestDBRepo("jdbc:postgresql://localhost:5432/socialnetwork", "postgres", "postgres", new RequestValidator());
        RequestService requestService = new RequestService(requestRepository);
        SceneService sceneService = new SceneService(stage, userService, requestService, friendshipService);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login-page.fxml"));
        fxmlLoader.setControllerFactory(controllerClass -> {
            try {
                if (controllerClass == LoginPageController.class) {
                    LoginPageController lpc = new LoginPageController();
                    lpc.setUserService(userService);
                    lpc.setSceneService(sceneService);
                    lpc.setRequestService(requestService);
                    return lpc;
                }
                return controllerClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Error creating controller", e);
            }
        });

        try {
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException("Error loading FXML", e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
