package ubb.scs.map.faptebune.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import ubb.scs.map.faptebune.Domain.Nevoie;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.StreamSupport;

public class MainpageController extends ControllerSuperclass {
    public TextField descriere;
    public TextField titlu;
    public DatePicker deadline;

    ObservableList<Nevoie> nevoi = FXCollections.observableArrayList();

    @FXML
    private TableView<Nevoie> tabelNevoi;

    @FXML
    private TableColumn<Nevoie, String> colTitlu;

    @FXML
    private TableColumn<Nevoie, String> colDescriere;

    @FXML
    private TableColumn<Nevoie, LocalDateTime> colDeadline;

    @FXML
    private TableColumn<Nevoie, Long> colOmInNevoie;

    @FXML
    private TableColumn<Nevoie, Long> colOmSalvator;

    @FXML
    private TableColumn<Nevoie, String> colStatus;

    ObservableList<Nevoie> nevoi2 = FXCollections.observableArrayList();

    @FXML
    private TableView<Nevoie> faptebune;

    @FXML
    private TableColumn<Nevoie, String> colTitlu1;

    @FXML
    private TableColumn<Nevoie, String> colDescriere1;

    @FXML
    private TableColumn<Nevoie, LocalDateTime> colDeadline1;

    @FXML
    private TableColumn<Nevoie, Long> colOmInNevoie1;

    @FXML
    private TableColumn<Nevoie, Long> colOmSalvator1;

    @FXML
    private TableColumn<Nevoie, String> colStatus1;


    public void init() {
        // Initialize table columns
        colTitlu.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDescriere.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        colDeadline.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        colOmInNevoie.setCellValueFactory(new PropertyValueFactory<>("omInNevoie"));
        colOmSalvator.setCellValueFactory(new PropertyValueFactory<>("omSalvator"));
        colOmSalvator.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Long item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else if (item == -1) {
                    setText("null"); // Show null instead of -1
                } else {
                    setText(String.valueOf(item));
                }
            }
        });
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Add a listener for row selection
        tabelNevoi.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                handleRowClick(newSelection);
            }
        });

        // Populate table items
        ObservableList<Nevoie> nevoi = FXCollections.observableArrayList(
                StreamSupport.stream(getNevoiService().getAll().spliterator(), false)
                        .filter(nevoie -> !nevoie.getOmInNevoie().equals(getScreenService().getUser().getId()))
                        .toList()
        );
        tabelNevoi.setItems(nevoi);

        // Lock rows based on a condition
        lockRowsBasedOnCondition();

        colTitlu1.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDescriere1.setCellValueFactory(new PropertyValueFactory<>("descriere"));
        colDeadline1.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        colOmInNevoie1.setCellValueFactory(new PropertyValueFactory<>("omInNevoie"));
        colOmSalvator1.setCellValueFactory(new PropertyValueFactory<>("omSalvator"));
        colStatus1.setCellValueFactory(new PropertyValueFactory<>("status"));
        ObservableList<Nevoie> nevoi2 = FXCollections.observableArrayList(
                StreamSupport.stream(getNevoiService().getAll().spliterator(), false)
                        .filter(nevoie -> nevoie.getOmSalvator().equals(getScreenService().getUser().getId()))
                        .toList()
        );
        faptebune.setItems(nevoi2);
    }

    private void handleRowClick(Nevoie selectedNevoie) {
        selectedNevoie.setStatus("Erou gasit!");
        selectedNevoie.setOmSalvator(getScreenService().getUser().getId());
        getNevoiService().update(selectedNevoie);
        tabelNevoi.refresh();

    }

    private void lockRowsBasedOnCondition() {
        tabelNevoi.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Nevoie item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle(""); // Reset style for empty rows
                } else if (item.getOmSalvator() != -1) {
                    setDisable(true); // Disable interaction
                    setStyle("-fx-background-color: lightgray;"); // Apply locked styling
                } else {
                    setDisable(false); // Enable interaction
                    setStyle(""); // Reset style for unlocked rows
                }
            }
        });
    }


    public void submit(ActionEvent actionEvent) {
        String title = titlu.getText();
        String descriere = this.descriere.getText();
        LocalDate localDate = deadline.getValue();
        if (localDate != null) {
            LocalDateTime localDateTime = LocalDateTime.of(localDate, LocalTime.now());
            Long userId = getScreenService().getUser().getId();
            Nevoie nevoie = new Nevoie(title,descriere,localDateTime,userId, (long) -1,"Caut erou!");
            getNevoiService().create(nevoie);
            titlu.clear();
            this.descriere.clear();

        }
    }
}

