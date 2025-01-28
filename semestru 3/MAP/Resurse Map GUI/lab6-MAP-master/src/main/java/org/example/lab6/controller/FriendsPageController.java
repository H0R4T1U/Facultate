package org.example.lab6.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import org.example.lab6.domain.Friendship;
import org.example.lab6.domain.Request;
import org.example.lab6.service.FriendshipService;
import org.example.lab6.service.RequestService;
import org.example.lab6.service.SceneService;
import org.example.lab6.service.UserService;

import java.util.StringTokenizer;

public class FriendsPageController extends ControllerSuperclass {

    @FXML
    private ListView<String> listViewFriends, listViewRequests;

    @FXML
    private Button buttonTasks, buttonProfile;

    private ObservableList<String> requestList;

    public void init() {
        listViewRequests.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setText(item);
                    setWrapText(true);
                    setPrefWidth(param.getWidth() - 20);
                    setMinHeight(USE_COMPUTED_SIZE);
                    setMaxHeight(USE_PREF_SIZE);
                }
            }
        });
        listViewFriends.getItems().clear();
        UserService userService = super.getUserService();
        SceneService sceneService = super.getSceneService();
        RequestService requestService = super.getRequestService();
        userService.getFriendsForUser(sceneService.getCurrentUser().getId())
                .forEach(f -> listViewFriends.getItems().add(f.toString()));
        requestList = FXCollections.observableArrayList();
        listViewRequests.setItems(requestList);
        requestList.clear();
        requestService.getRequestsForUser(sceneService.getCurrentUser())
                .forEach(r -> requestList.add(r.toString()));
    }

    @FXML
    private void handleButtonAddFriendClicked() {
        super.getSceneService().switchScene("add-friend");
    }

    @FXML
    private void handleButtonDeleteFriendClicked(ActionEvent actionEvent) {
        var selectedItems = listViewFriends.getSelectionModel().getSelectedItems();
        StringTokenizer str = new StringTokenizer(selectedItems.getFirst());
        Friendship f = super.getFriendshipService().findFriendshipByUsername(str.nextToken());
        super.getFriendshipService().removeFriendship(f.getId().getLeft(), f.getId().getRight());
        listViewFriends.getItems().remove(listViewFriends.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleButtonAcceptRequestClicked(ActionEvent actionEvent) {
        var selectedItems = listViewRequests.getSelectionModel().getSelectedItems();
        StringTokenizer str = new StringTokenizer(selectedItems.getFirst());
        str.nextToken();
        Long user1ID = Long.parseLong(str.nextToken());
        str.nextToken();
        Long user2ID = Long.parseLong(str.nextToken());
        super.getFriendshipService().addFriendship(user1ID, user2ID);
        super.getRequestService().removeRequest(user1ID, user2ID);
        listViewRequests.getItems().remove(listViewRequests.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleButtonDeleteRequestClicked() {
        var selectedItems = listViewRequests.getSelectionModel().getSelectedItems();
        StringTokenizer str = new StringTokenizer(selectedItems.getFirst());
        str.nextToken();
        Long senderID = Long.parseLong(str.nextToken());
        str.nextToken();
        Long receiverID = Long.parseLong(str.nextToken());
        super.getRequestService().removeRequest(senderID, receiverID);
        requestList.remove(listViewRequests.getSelectionModel().getSelectedItem());
    }
}
