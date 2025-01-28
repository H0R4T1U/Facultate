package org.example.lab6.controller;

import javafx.fxml.FXML;
import org.example.lab6.service.FriendshipService;
import org.example.lab6.service.RequestService;
import org.example.lab6.service.SceneService;
import org.example.lab6.service.UserService;

public class ControllerSuperclass implements Controller{
    private UserService userService;
    private SceneService sceneService;
    private RequestService requestService;
    private FriendshipService friendshipService;

    public SceneService getSceneService() {
        return sceneService;
    }

    public UserService getUserService() {
        return userService;
    }

    public FriendshipService getFriendshipService() {
        return friendshipService;
    }

    public RequestService getRequestService() {
        return requestService;
    }

    public void setFriendshipService(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    public void setSceneService(SceneService sceneService) {
        this.sceneService = sceneService;
    }

    public void setRequestService(RequestService requestService) {
        this.requestService = requestService;
    }

    public void init() {

    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @FXML
    public void handleButtonProfileClicked() {
        sceneService.switchScene("profile");
    }

    @FXML
    public void handleButtonTasksClicked() {
        sceneService.switchScene("tasks");
    }

    @FXML
    public void handleButtonFriendsClicked() {
        sceneService.switchScene("friends");
    }

    @FXML
    public void handleButtonRegisterClicked() {
        sceneService.switchScene("tasks");
    }
}
