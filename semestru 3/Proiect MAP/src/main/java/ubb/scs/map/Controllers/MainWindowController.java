package ubb.scs.map.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ubb.scs.map.Domain.*;
import ubb.scs.map.Utils.Paging.Page;
import ubb.scs.map.Utils.Paging.Pageable;
import ubb.scs.map.Utils.observer.ObservableType;
import ubb.scs.map.Utils.observer.Observer;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

public class MainWindowController extends ControllerSuperclass implements Observer{
    private final ObservableList<Friendship> model = FXCollections.observableArrayList();
    public Button buttonPrevious;
    public Label labelPageNumber;
    public Button buttonNext;
    private ObservableList<User> userModel = FXCollections.observableArrayList();
    private Integer currentPageNumber;
    private Integer maximumPageNumber;
    @FXML
    private ListView<User> userList;

    @FXML
    private TextField searchBar;
    @FXML
    private TableView<Friendship> tableView;
    @FXML
    private TableColumn<Friendship, String> tableColumnName;

    public void init() {
        service.addObserver(this, ObservableType.FRIENDSHIP);
        service.addObserver(this, ObservableType.USER);
        currentPageNumber = 1;
        maximumPageNumber = ((int) Math.ceil((double) service.getUserFriends(UserInstance.getInstance().getId()).size() / 3));

        initModel();
        Optional<User> user = service.getUserById(UserInstance.getInstance().getId());
        // Alerta Friend Request
        if(user.isPresent()){
            Iterable<FriendshipRequest> requests = service.getRequestsForUser(user.get());
            boolean hasRequests = requests.iterator().hasNext();
            if (hasRequests) {
                new Thread(() -> {
                    MessageAlert.showInfo("You have pending friendship requests!");
                }).start();
            }
        }
        tableColumnName.setCellValueFactory(cellData -> {
            Friendship f = cellData.getValue();
            if(f.getId().getE1().equals(UserInstance.getInstance().getId())){
                return new SimpleStringProperty(f.getUser2());
            }else{
                return new SimpleStringProperty(f.getUser1());
            }
        });

        tableView.setItems(model);

        userList.setCellFactory(param -> new ListCell<User>() {
            @Override
            protected void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setText(item.toString());
                    setWrapText(true);
                    setPrefWidth(param.getWidth() - 20);
                    setMinHeight(USE_COMPUTED_SIZE);
                    setMaxHeight(USE_PREF_SIZE);
                }
            }
        });


        userList.setItems(userModel);
    }
    private void reloadTable() {
        Pageable pageable = new Pageable(3, currentPageNumber);
        Page<Friendship> currentPage = new Page<>(service.getUserFriendsPaged(pageable).getElementsOnPage());
        model.clear();
        ObservableList<Friendship> friendships = FXCollections.observableList(
                StreamSupport.stream(currentPage.getElementsOnPage().spliterator(), false)
                        .toList());
        model.addAll(friendships);
        labelPageNumber.setText("Page " + currentPageNumber + " of "+ maximumPageNumber);
        buttonPrevious.setDisable(currentPageNumber == 1);
        buttonNext.setDisable(currentPageNumber>= maximumPageNumber);
    }

    private void initModel() {
        List<User> users = service.getUsersNotFriends(UserInstance.getInstance().getId());
        userModel.clear();
        userModel.addAll(users);
        reloadTable();
    }

    @Override
    public void update() {
        initModel();
    }

    @FXML
    protected void logout() throws IOException {
        UserInstance.getInstance().setId((long)-1);
        service.switchScene("login");
    }
    @FXML
    protected void removeFriend() {
        Friendship friendship=(Friendship) tableView.getSelectionModel().getSelectedItem();
        service.deleteFriendship(friendship.getId());

    }
    @FXML
    protected void addFriend() throws IOException {
        searchBar.clear();
        Optional<User> snd = service.getUserById(UserInstance.getInstance().getId());
        Optional<User> rcv = Optional.ofNullable(userList.getSelectionModel().getSelectedItem());
        try {
            if (rcv.isPresent() && snd.isPresent()) {
                service.createFriendshipRequest(snd.get(), rcv.get());
            }
        }catch (Exception e){
            MessageAlert.showWarning(e.getMessage());
        }
    }
    @FXML
    protected void toRequests() throws IOException {
        service.switchScene("friendshipRequests");
    }
    @FXML
    protected void toMessages() throws IOException {
        service.switchScene("messages");
    }

    public void handleButtonPreviousClicked(ActionEvent actionEvent) {
        currentPageNumber--;
        reloadTable();
    }

    public void handleButtonNextClicked(ActionEvent actionEvent) {
        currentPageNumber++;
        reloadTable();
    }
    @FXML
    protected void toProfile() {
        service.switchScene("profile");
    }
}
