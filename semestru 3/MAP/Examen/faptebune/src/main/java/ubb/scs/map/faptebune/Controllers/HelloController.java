package ubb.scs.map.faptebune.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.springframework.security.core.parameters.P;
import ubb.scs.map.faptebune.Domain.Orase;
import ubb.scs.map.faptebune.Domain.Persoana;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.StreamSupport;

public class HelloController extends ControllerSuperclass{

    public TextField prenume;
    public TextField username;
    public TextField parola;
    public ChoiceBox<String> oras;
    public TextField strada;
    public TextField numarStrada;
    public TextField telefon;
    @FXML
    private TextField nume;
    public ChoiceBox<Persoana> users;
    public void initialize() {
        // Populate the ChoiceBox with enum values
        oras.setItems(FXCollections.observableArrayList(
                        Arrays.stream(Orase.values())
                                .map(Enum::name)
                                .toList()
                )
        );
        users.setItems(FXCollections.observableArrayList(
                StreamSupport.stream(getPersoanaService().getAll().spliterator(), false)
                        .toList()));
    }
    public void register(ActionEvent actionEvent) {
        String Nume = nume.getText();
        String Prenume = prenume.getText();
        String Username = username.getText();
        String Parola = parola.getText();
        String Oras = oras.getValue();
        String Strada = strada.getText();
        String NumarStrada = nume.getText();
        String Telefon = telefon.getText();
        Persoana pers = new Persoana(Nume, Prenume, Username, Parola, Oras, Strada, NumarStrada, Telefon);
        try {
            Optional<Persoana> Persoana = getPersoanaService().create(pers);
            if (Persoana.isPresent()) {
                getScreenService().setUser(Persoana.get());
                nume.clear();
                prenume.clear();
                username.clear();
                parola.clear();
                oras.getItems().clear();
                strada.clear();
                numarStrada.clear();
                telefon.clear();
                getScreenService().switchScene("main");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void login(ActionEvent actionEvent) {
        getScreenService().setUser(users.getValue());

        getScreenService().switchScene("main");
    }
}
