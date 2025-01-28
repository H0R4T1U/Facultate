module ubb.scs.map.faptebune {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires spring.security.core;

    opens ubb.scs.map.faptebune to javafx.fxml;
    exports ubb.scs.map.faptebune;
    exports ubb.scs.map.faptebune.Controllers;
    opens ubb.scs.map.faptebune.Controllers to javafx.fxml;
}