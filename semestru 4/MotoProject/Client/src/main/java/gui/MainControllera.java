//package gui;
//
//import Services.IObserver;
//import Services.IService;
//import Services.MotoException;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.Node;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import model.Player;
//import model.Race;
//import model.Team;
//import model.User;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.net.URL;
//import java.util.List;
//import java.util.Optional;
//import java.util.ResourceBundle;
//
//public class MainControllera implements Initializable, IObserver {
//    private IService service;
//
//    @FXML
//    private TextField teamSearch;
//
//    @FXML
//    private TableView<Player> playerTable;
//
//    @FXML
//    private TableColumn<Player, Integer> playerIdColumn;
//
//    @FXML
//    private TableColumn<Player, String> playerNameColumn;
//
//    @FXML
//    private TableColumn<Player, Integer> playerTeamColumn;
//
//    @FXML
//    private TableView<Race> raceTable;
//
//    @FXML
//    private TableColumn<Race, Integer> raceIdColumn;
//
//    @FXML
//    private TableColumn<Race, Integer> racePlayersColumn;
//
//    @FXML
//    private TableColumn<Race, String> raceEngineColumn;
//
//    @FXML
//    private TextField playerNameAdd;
//
//    @FXML
//    private TextField playerCodeAdd;
//
//    @FXML
//    private TextField playerTeamAdd;
//
//    private static Logger logger = LogManager.getLogger(MainControllera.class);
//
//    private User curentUser;
//
//    public void setCurentUser(User curentUser) {
//        this.curentUser = curentUser;
//    }
//
//    public void setService(IService service) {
//        this.service = service;
//    }
//
//    private final ObservableList<Race> raceData = FXCollections.observableArrayList();
//    private final ObservableList<Player> playerData = FXCollections.observableArrayList();
//
//    public MainControllera(IService service, User currentUser) {
//        this.service = service;
//        this.curentUser = currentUser;
//    }
//
//    @FXML
//    public void initialize() {
////        playerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
////        playerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
////        playerTeamColumn.setCellValueFactory(new PropertyValueFactory<>("team"));
////        playerTable.setItems(playerData);
////        raceIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
////        racePlayersColumn.setCellValueFactory(new PropertyValueFactory<>("NoPlayers"));
////        raceEngineColumn.setCellValueFactory(new PropertyValueFactory<>("engineType"));
////        raceTable.setItems(raceData);
//
//        //loadRaces();
//    }
//
//    private void loadRaces() {
//        raceData.clear();
//        try {
//            raceData.addAll(service.getAllRaces().values());
//        } catch (MotoException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void searchPlayers() {
//        Race selectedRace = raceTable.getSelectionModel().getSelectedItem();
//        if (selectedRace == null) {
//            System.out.println("No race selected");
//            return;
//        }
//
//        String teamName = teamSearch.getText();
//        Optional<Team> team = null;
//        try {
//            team = service.getTeamByName(teamName);
//        } catch (MotoException e) {
//            throw new RuntimeException(e);
//        }
//        if (team.isPresent()) {
//            List<Player> players = null;
//            try {
//                players = service.getRacePlayersOfTeam(selectedRace.getId(), team.get().getId());
//            } catch (MotoException e) {
//                throw new RuntimeException(e);
//            }
//            playerData.clear();
//            playerData.addAll(players);
//            System.out.println("Team and players found");
//        } else {
//            System.out.println("Team not found");
//        }
//    }
//
//    public void addPlayerToRace() {
//        Race selectedRace = raceTable.getSelectionModel().getSelectedItem();
//        if (selectedRace == null) {
//            System.out.println("No race selected");
//            return;
//        }
//
//        String playerName = playerNameAdd.getText();
//        String playerCode = playerCodeAdd.getText();
//        String teamName = playerTeamAdd.getText();
//
//        Optional<Team> team = null;
//        try {
//            team = service.getTeamByName(teamName);
//        } catch (MotoException e) {
//            throw new RuntimeException(e);
//        }
//        if (team.isPresent()) {
//            try {
//                service.add(playerName,playerCode,team.get().getId(),selectedRace.getId());
//            } catch (MotoException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println("Player added to race");
//        } else {
//            System.out.println("Team not found");
//        }
//    }
//    void logout() {
//        try {
//            service.logout(curentUser, this);
//        } catch (MotoException e) {
//            logger.error("Logout error " + e);
//        }
//    }
//    public void handleLogout(ActionEvent actionEvent) {
//        logout();
//        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
//    }
//
//    @Override
//    public void playerAdded(Player player) throws MotoException {
//
//    }
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//
//    }
//}