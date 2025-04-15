package gui;

import Services.IService;
import Services.MotoException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginController{

    private IService server;
    private MainController chatCtrl;
    private User crtUser;

    private static Logger logger = LogManager.getLogger(LoginController.class);
    @FXML
    TextField user;
    @FXML
    TextField password;

    Parent mainChatParent;

    public void setServer(IService s){
        server=s;
    }


    public void setParent(Parent p){
        mainChatParent=p;
    }

    public void pressLogin(ActionEvent actionEvent) {
        //Parent root;
        String nume = user.getText();
        String passwd = password.getText();
        crtUser = new User(nume, passwd);


        try{
            server.login(crtUser, chatCtrl);
            Stage stage=new Stage();
            stage.setTitle("Chat Window for " +crtUser.getUsername());
            stage.setScene(new Scene(mainChatParent));

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    chatCtrl.logout();
                    logger.debug("Closing application");
                    System.exit(0);
                }
            });

            stage.show();
            chatCtrl.setUser(crtUser);
            chatCtrl.loadRaces();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

        }   catch (MotoException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("MPP chat");
                alert.setHeaderText("Authentication failure");
                alert.setContentText("Wrong username or password");
                alert.showAndWait();
            }


    }





    public void pressCancel(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void setUser(User user) {
        this.crtUser = user;
    }

    public void setChatController(MainController mainController) {
        this.chatCtrl = mainController;
    }


}
