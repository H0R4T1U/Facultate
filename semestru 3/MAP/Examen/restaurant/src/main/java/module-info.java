module ubb.scs.map.avioane {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens ubb.scs.map.avioane to javafx.fxml;
    exports ubb.scs.map.avioane;
    exports ubb.scs.map.avioane.Controllers;
    opens ubb.scs.map.avioane.Controllers to javafx.fxml;
}