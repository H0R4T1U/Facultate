package ubb.scs.map;

import javafx.application.Application;
import javafx.stage.Stage;
import ubb.scs.map.Domain.*;
import ubb.scs.map.Facades.RepositoryFacade;
import ubb.scs.map.Facades.ServiceFacade;
import ubb.scs.map.Services.*;



public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        RepositoryFacade.getInstance();
        UserInstance.getInstance();
        ServiceFacade.getInstance();

        ScreenService screenService = new ScreenService(stage);
        ServiceFacade.getInstance().setScreenService(screenService);

        screenService.addScene("login","Views/login.fxml");
        screenService.addScene("main","Views/mainWindow.fxml");
        screenService.addScene("friendshipRequests","Views/friendRequestWindow.fxml");
        screenService.addScene("messages","Views/Messages.fxml");
        screenService.addScene("register","Views/register.fxml");
        screenService.addScene("profile","Views/profilePage.fxml");
        screenService.switchScene("login");


        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}