package ubb.scs.map.clinica.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ubb.scs.map.clinica.Domain.Consultatie;
import ubb.scs.map.clinica.Observers.Observer;

import java.time.LocalDate;
import java.time.LocalTime;

public class DoctorController extends ControllerSuperclass implements Observer {

    public Label Header;
    @FXML
    private TableView<Consultatie> tableViewConsultatii;
    @FXML
    private TableColumn<Consultatie, Long> columnId;
    @FXML
    private TableColumn<Consultatie, String> columnCnp;
    @FXML
    private TableColumn<Consultatie, String> columnNume;
    @FXML
    private TableColumn<Consultatie, LocalDate> columnData;
    @FXML
    private TableColumn<Consultatie, LocalTime> columnOra;
    @Override
    public void init() {
        getService().addObserver(this);
        Header.setText("Doctor: " + getDoctorId());

        // Link table columns to Consultatie properties
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnCnp.setCellValueFactory(new PropertyValueFactory<>("cnp"));
        columnNume.setCellValueFactory(new PropertyValueFactory<>("nume"));
        columnData.setCellValueFactory(new PropertyValueFactory<>("data"));
        columnOra.setCellValueFactory(new PropertyValueFactory<>("ora"));
        ObservableList<Consultatie> consultatii = FXCollections.observableArrayList();
        consultatii.addAll(getService().getConsultatiiDoctor(getDoctorId()));
        tableViewConsultatii.setItems(consultatii);

    }


    @Override
    public void update() {
        ObservableList<Consultatie> consultatii = FXCollections.observableArrayList();
        consultatii.addAll(getService().getConsultatiiDoctor(getDoctorId()));
        tableViewConsultatii.setItems(consultatii);
    }
}



