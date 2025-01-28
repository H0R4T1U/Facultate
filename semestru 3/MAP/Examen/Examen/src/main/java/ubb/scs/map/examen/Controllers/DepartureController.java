package ubb.scs.map.examen.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import ubb.scs.map.examen.Observers.Observer;
import ubb.scs.map.examen.domain.City;
import ubb.scs.map.examen.domain.TrainStations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ubb.scs.map.examen.Constants.Constants.PRICE_PER_STATION;

public class DepartureController extends ControllerSuperclass implements Observer {
    public ChoiceBox<City> ArivalField;
    public ChoiceBox<City> DepartureField;
    public CheckBox directField;
    public ListView<String> trainList;
    public Label UsersSearching;
    Integer arivalId = null;
    Integer departureId = null;
    @Override
    public void init() {

        getService().addObserver(this);
        ArivalField.getItems().addAll(getService().getCities());
        DepartureField.getItems().addAll(getService().getCities());

    }

    public void Search(ActionEvent actionEvent) {
        try{
            if(arivalId != null && departureId != null){
                Map<Integer,Integer> old = new HashMap<>();
                old.put(arivalId, departureId);
                getService().decrementSearching(old);
            }
            arivalId = ArivalField.getSelectionModel().getSelectedItem().getId();
            departureId = DepartureField.getSelectionModel().getSelectedItem().getId();
            Map<Integer,Integer> actual = new HashMap<>();
            actual.put(arivalId, departureId);
            getService().setSearching(actual);

            Boolean direct = directField.isSelected();
            if (arivalId != null && departureId != null && !departureId.equals(arivalId)) {
                List<List<TrainStations>> trains = getService().getTrainStations(departureId, arivalId, direct);
                initModel(trains);
            } else {
                showAlert("Warning", "Invalid data!");

            }
        }catch (Exception e){
            showAlert("Warning","Select departure/arrival city");
        }
        }
    private void initModel(List<List<TrainStations>> trains){
        ObservableList<String> observableData = FXCollections.observableArrayList();
        // pentru a nu avea duplicate orasele intermediare
        trains.forEach(list -> {
            int numberOfStations = list.size();
            String result = list.stream()
                    .map(trainStation -> {
                        StringBuilder sb = new StringBuilder();
                        sb.append(getService().getCityById(trainStation.getDepartureId()))
                                .append("-")
                                .append(trainStation.getId());

                        // Check if it's the last element
                        if (list.indexOf(trainStation) == list.size() - 1) {
                            sb.append("->")
                                    .append(getService().getCityById(trainStation.getArrivalId()));
                        }
                        sb.append(",price="+numberOfStations*PRICE_PER_STATION);
                        return sb.toString();
                    })
                    .reduce((a, b) -> a + "->" + b) // Join the elements with a space
                    .orElse(""); // Handle empty lists
            observableData.add(result.trim());
        });

        // Set the items in the ListView
        trainList.setItems(observableData);
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void update() {
        if(arivalId != null && departureId != null){
            Map<Integer,Integer> Data = new HashMap<>();
            Data.put(arivalId, departureId);
            Integer searches = getService().getSearches(Data);
            if(searches >1 ){
                searches -=1;
                UsersSearching.setText(searches.toString() + " other users are searching");
            }else{
                UsersSearching.setText("No one is searching");
            }
        }
    }
}

