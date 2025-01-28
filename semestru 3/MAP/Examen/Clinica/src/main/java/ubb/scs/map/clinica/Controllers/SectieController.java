package ubb.scs.map.clinica.Controllers;

import javafx.css.converter.StringConverter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ubb.scs.map.clinica.Domain.Consultatie;
import ubb.scs.map.clinica.Domain.Medic;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SectieController extends ControllerSuperclass {



    public DatePicker DateField;
    public TextField NumeField;
    public TextField CnpField;
    public ComboBox<Medic> medic;

    @Override
    public void init() {
        List<Medic> medics = getService().getMedics().stream()
                .filter(medic -> Objects.equals(medic.getIdSectie(), getSectieId()))
                .toList();
        medic.getItems().addAll(medics);

    }

    public void createConsultatie(ActionEvent actionEvent) {
        LocalDate date = DateField.getValue();
        LocalTime time = LocalTime.now();
        String nume = NumeField.getText();
        String cnp = CnpField.getText();
        Long idMedic = medic.getSelectionModel().getSelectedItem().getId();
        Consultatie consultatie = new Consultatie(idMedic,cnp,nume,date,time);
        getService().createConsultatie(consultatie);
    }
}
