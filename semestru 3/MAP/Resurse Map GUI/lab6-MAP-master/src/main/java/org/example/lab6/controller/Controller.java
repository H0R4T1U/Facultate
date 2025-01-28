package org.example.lab6.controller;

import org.example.lab6.domain.User;
import org.example.lab6.service.*;

public interface Controller {
    void setUserService(UserService userService);
    void setSceneService(SceneService sceneService);
    void setRequestService(RequestService requestService);
    void setFriendshipService(FriendshipService friendshipService);
    void init();
}
