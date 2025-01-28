package org.example.lab6.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.lab6.domain.User;
import org.example.lab6.service.*;

public class AddFriendPageController extends ControllerSuperclass {

    @FXML
    private Button buttonRequest;

    @FXML
    private TableView<User> tableViewUsers;

    @FXML
    private TableColumn<User, String> tableColumnUsername;

    @FXML
    private TableColumn<User, String> tableColumnFullName;


    @Override
    public void init() {
        tableViewUsers.getItems().clear();
        tableColumnFullName.setCellValueFactory(new PropertyValueFactory<>("FullName"));
        tableColumnUsername.setCellValueFactory(new PropertyValueFactory<>("Username"));
        super.getUserService().getUsers().forEach(user ->
                tableViewUsers.getItems().add(user));
    }

    @FXML
    private void handleButtonRequestClicked() {
        var selectedUsers = tableViewUsers.getSelectionModel().getSelectedItems();
        super.getRequestService().addRequest(super.getSceneService().getCurrentUser(), selectedUsers.getFirst());
        super.getSceneService().switchScene("friends");
    }

}
