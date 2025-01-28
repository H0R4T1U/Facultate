module ubb.scs.map.examen {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens ubb.scs.map.examen to javafx.fxml;
    exports ubb.scs.map.examen;
    exports ubb.scs.map.examen.Controllers;
    opens ubb.scs.map.examen.Controllers to javafx.fxml;
    exports ubb.scs.map.examen.Service;

}