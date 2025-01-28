package ubb.scs.map.Domain;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MessageAlert {

    public static void showInfo(String message) {
        showAlertAsync(AlertType.INFORMATION, "Information", null, message);
    }

    public static void showWarning(String message) {
        showAlertAsync(AlertType.WARNING, "Warning", null, message);
    }

    public static void showError(String message) {
        showAlert(message);
    }

    private static void showAlertAsync(AlertType alertType, String title, String header, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }


    private static void showAlert(String message) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("An error occurred");
            alert.setContentText(message);
            alert.showAndWait();
        }
}
