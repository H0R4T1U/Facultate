module ubb.scs.map.clinica {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens ubb.scs.map.clinica to javafx.fxml;
    exports ubb.scs.map.clinica;
    exports ubb.scs.map.clinica.Controllers;
    opens ubb.scs.map.clinica.Controllers to javafx.fxml;
}