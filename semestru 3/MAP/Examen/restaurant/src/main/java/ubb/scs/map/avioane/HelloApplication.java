package ubb.scs.map.avioane;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ubb.scs.map.avioane.Domain.Validators.MenuItemValidator;
import ubb.scs.map.avioane.Domain.Validators.OrderValidator;
import ubb.scs.map.avioane.Domain.Validators.TableValidator;
import ubb.scs.map.avioane.Domain.Validators.orderItemValidator;
import ubb.scs.map.avioane.Repository.MenuItemRepository;
import ubb.scs.map.avioane.Repository.OrderItemsRepository;
import ubb.scs.map.avioane.Repository.OrderRepository;
import ubb.scs.map.avioane.Repository.TableRepository;
import ubb.scs.map.avioane.Service.ScreenService;
import ubb.scs.map.avioane.Service.Service;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        TableRepository tableRepository = new TableRepository(
                "jdbc:postgresql://localhost:5432/Restaurant",
                "postgres",
                "12345678",
                new TableValidator()
                );
        MenuItemRepository menuRepository = new MenuItemRepository(
                "jdbc:postgresql://localhost:5432/Restaurant",
                "postgres",
                "12345678",
                new MenuItemValidator()
        );
        OrderRepository orderRepository = new OrderRepository(
                "jdbc:postgresql://localhost:5432/Restaurant",
                "postgres",
                "12345678",
                new OrderValidator()
        );
        OrderItemsRepository orderItemsRepository = new OrderItemsRepository(
                "jdbc:postgresql://localhost:5432/Restaurant",
                "postgres",
                "12345678",
                new orderItemValidator()
        );
        Service service = new Service(menuRepository, tableRepository,orderRepository, orderItemsRepository);
        ScreenService screenService = new ScreenService(service);
        screenService.showStage("Main.fxml");

    }

    public static void main(String[] args) {
        launch();
    }
}