package org.example.lab6.domain;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MessageAlert {

    public static void showInfo(String message) {
        showAlert(AlertType.INFORMATION, "Information", null, message);
    }

    public static void showWarning(String message) {
        showAlert(AlertType.WARNING, "Warning", null, message);
    }

    public static void showError(String message) {
        showAlert(AlertType.ERROR, "Error", "An error occurred", message);
    }

    private static void showAlert(AlertType alertType, String title, String header, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

