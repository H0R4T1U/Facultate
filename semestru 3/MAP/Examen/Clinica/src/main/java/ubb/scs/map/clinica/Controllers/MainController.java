package ubb.scs.map.clinica.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import ubb.scs.map.clinica.Domain.Medic;
import ubb.scs.map.clinica.Domain.Sectie;
import ubb.scs.map.clinica.Service.ScreenService;

import java.util.List;

public class MainController extends ControllerSuperclass {


    public ComboBox<Sectie> Sectii;

    @Override
    public void init() {
        List<Medic> medici = getService().getMedics();
        for (Medic medic : medici) {
            getScreenService().showMedicStage(medic.getId());
        }
        Sectii.getItems().addAll(getService().getSectii());
        Sectii.setOnAction(e -> {
            Long idSectie = Sectii.getSelectionModel().getSelectedItem().getId();
            getScreenService().showSectieStage(idSectie);
        });
    }

}