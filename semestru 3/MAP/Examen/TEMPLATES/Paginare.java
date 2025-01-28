package ubb.scs.map.demo4.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ubb.scs.map.demo4.domain.Client;
import ubb.scs.map.demo4.domain.Flight;
import ubb.scs.map.demo4.domain.Ticket;
import ubb.scs.map.demo4.service.Service;
import ubb.scs.map.demo4.service.observer.Observer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class ClientController extends ControllerSuperclass implements Observer {
    public TextField textFieldFullName;


    @FXML
    private DatePicker datePicker;

    @FXML
    private TableColumn<Flight, LocalDateTime> departureColoana;

    @FXML
    private ComboBox<String> fromCombo;

    @FXML
    private TableColumn<Flight, LocalDateTime> landingColoana;

    @FXML
    private TableColumn<Flight, Integer> seatsColoana;

    @FXML
    private TableView<Flight> tableView;

    @FXML
    private ComboBox<String> toCombo;

    @FXML
    private Button cautaButton;
    @FXML
    private Button buyButton;
    @FXML
    private TableColumn<Flight,Integer> availableColoana;
    @FXML
    private Button nextButton;

    private Integer index=5;
    ObservableList<Flight> model = FXCollections.observableArrayList();


    public void init(){
        tableView.setItems(model);

        landingColoana.setCellValueFactory(new PropertyValueFactory<Flight, LocalDateTime>("landing"));
        departureColoana.setCellValueFactory(new PropertyValueFactory<Flight, LocalDateTime>("departure"));
        seatsColoana.setCellValueFactory(new PropertyValueFactory<Flight, Integer>("seats"));

        availableColoana.setCellValueFactory(c -> {
            Flight fl = c.getValue();
            Integer cnt = 0;
            for (Ticket t : getService().getTicket()) {
                if (t.getIdFlight().equals(fl.getId()))
                    cnt++;
            }
            return new ReadOnlyObjectWrapper<>(fl.getSeats() - cnt);
        });
        tableView.setItems(model);
        initModel();
        getService().addObserver(this);
    }


    private void initModel() {
          // Assuming the current user is saved in the scene service
        Client client = getSceneService().getCurrentClient();
        if (client != null) {
            // Populate the fields with user data
            textFieldFullName.setText(client.getName());
            //textFieldCommunity.setText(currentUser.getCommunityName());
        } else {
            // Handle case if user is not logged in
            MessageAlert.showErrorMessage(null, "Client is null");
        }
        setCombo();
        handleSearch();
    }
    public void setCombo(){
        Set<String> from = new HashSet<>();
        Set<String> to = new HashSet<>();
        for(Flight flight : getService().getFlight()){
            from.add(flight.getFrom());
            to.add(flight.getTo());
        }
        fromCombo.getItems().addAll(from);
        toCombo.getItems().addAll(to);
    }

    public void handleSearch() {
        LocalDate start = datePicker.getValue();
        String from = fromCombo.getValue();
        String to=toCombo.getValue();
        if(start!=null && from!=null && to!=null) {
            Integer localIndex=0;

            model.clear();
            for (Flight fl : getService().getFlight()) {
                if (fl.getDeparture().toLocalDate().compareTo(start) == 0 && fl.getFrom().equals(from) && fl.getTo().equals(to)) {
                    localIndex++;
                    if(localIndex<=index && localIndex>index-5)
                        model.add(fl);
                }
            }
        }
    }

    public void handleBuy(ActionEvent event) {
        Flight flight= tableView.getSelectionModel().getSelectedItem();
        getService().adaugaTicket(getSceneService().getCurrentClient().getUsername(),flight.getId());

        getService().notifyObservers();
    }

    @Override
    public void update() {
        initModel();
    }

    public void handleNext(ActionEvent event){
        index=index+5;
        handleSearch();
    }

    public void handlePrevious(ActionEvent event){
        index=index-5;
        handleSearch();
    }
}
