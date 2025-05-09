package gui;

import Services.IObserver;

import Services.IService;
import Services.MotoException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Player;
import model.Race;
import model.Team;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.StreamSupport;

public class MainController implements Initializable, IObserver {
    @FXML
    private TextField teamSearch;

    @FXML
    private TableView<Player> playerTable;

    @FXML
    private TableColumn<Player, String> playerNameColumn;

    @FXML
    private TableColumn<Player, Integer> playerTeamColumn;

    @FXML
    private TableView<Race> raceTable;

    @FXML
    private TableColumn<Race, Integer> racePlayersColumn;

    @FXML
    private TableColumn<Race, String> raceEngineColumn;

    @FXML
    private TextField playerNameAdd;

    @FXML
    private TextField playerCodeAdd;

    @FXML
    private TextField playerTeamAdd;

    private final ObservableList<Race> raceData = FXCollections.observableArrayList();
    private final ObservableList<Player> playerData = FXCollections.observableArrayList();


    private ObservableList<String> friends = FXCollections.observableArrayList();

    private IService server;
    private User user;

    private static Logger logger = LogManager.getLogger(MainController.class);
    public MainController() {
        //this.server = server;
        logger.debug("Constructor ChatController");
    }

    public MainController(IService server) {
        this.server = server;
        logger.debug("constructor ChatController cu server param");

    }

    public void setServer(IService s) {
        server = s;
    }

    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        playerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        playerTeamColumn.setCellValueFactory(new PropertyValueFactory<>("team"));
        playerTable.setItems(playerData);
        racePlayersColumn.setCellValueFactory(new PropertyValueFactory<>("noPlayers"));
        raceEngineColumn.setCellValueFactory(new PropertyValueFactory<>("engineType"));
        raceTable.setItems(raceData);

    }

    public void loadRaces(){
        try {
            raceData.clear();
            raceData.addAll(server.getAllRaces());
        }catch (MotoException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("MPP MOTO");
            alert.setHeaderText("GET failure");
            alert.setContentText("Couldn't get Races!");
            alert.showAndWait();
        }
    }


    public void handleLogout(ActionEvent actionEvent) {
        logout();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
    }



     void logout() {
        try {
            server.logout(user, this);

        } catch (MotoException e) {
            logger.error("Logout error " + e);
        }
    }


    public void handleAddPlayer() {
        Race selectedRace = raceTable.getSelectionModel().getSelectedItem();
        if (selectedRace == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("MPP MOTO");
            alert.setHeaderText("Add failure");
            alert.setContentText("Race please select a race");
            alert.showAndWait();
        }
        String playerName = playerNameAdd.getText();
        String playerCode = playerCodeAdd.getText();
        String teamName = playerTeamAdd.getText();
        try {
            Optional<Team> team = server.getTeamByName(teamName);
            if(team.isPresent()) {
                Player player = new Player(playerName, playerCode, team.get().getId());
                player.setId(-1);
                server.add(player,selectedRace.getId(),this);
            }

        } catch (MotoException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("MPP MOTO");
            alert.setHeaderText("Add failure");
            alert.setContentText("Couldn't add player!");
            alert.showAndWait();
        }
    }
    public void setUser(User user) {
        this.user = user;
    }


    public void handleSearchTeam(){
        String teamName = teamSearch.getText();
        Race race = raceTable.getSelectionModel().getSelectedItem();
        if (teamName.isEmpty() || race == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("MPP MOTO");
            alert.setHeaderText("Search failure");
            alert.setContentText("Please fill in the team name field before searching and select a race");
            alert.showAndWait();
        } else {
            try {
                Optional<Team> team = server.getTeamByName(teamName);
                if (team.isPresent()) {
                    playerData.clear();
                    playerData.addAll(server.getRacePlayersOfTeam(race.getId(), team.get().getId()));

                }
            } catch (MotoException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("MPP MOTO");
                alert.setHeaderText("Search failure");
                alert.setContentText("Couldn't get players!");
                alert.showAndWait();
            }
        }
    }

    @Override
    public void playerAdded(Player player)  {
        Platform.runLater(()-> {
            loadRaces();
            if (!playerData.isEmpty()) {
                if (playerData.getFirst().getTeam().equals(player.getTeam())) {
                    playerData.add(player);
                }
            }
        });
    }

}
