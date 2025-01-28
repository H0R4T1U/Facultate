package ubb.scs.map.avioane.Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ubb.scs.map.avioane.Domain.Order;
import ubb.scs.map.avioane.Domain.Tables;
import ubb.scs.map.avioane.Observers.Observer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MainController extends ControllerSuperclass implements Observer {
    @FXML
    private TableView<Order> ordersTable; // TableView to display orders
    @FXML
    private TableColumn<Order, Integer> orderIdColumn;
    @FXML
    private TableColumn<Order, Integer> tableIdColumn;
    @FXML
    private TableColumn<Order, String> dateColumn;
    @FXML
    private TableColumn<Order, String> statusColumn;
    @FXML
    TableColumn<Order, String> produseColumn ;
    @Override
    public void init() {
        // Fetching and sorting orders based on date
        List<Order> orders = StreamSupport.stream(getService().getOrders().spliterator(), false)
                .sorted(Comparator.comparing(Order::getDate)) // Sorting by date
                .toList();

        ObservableList<Order> observableOrders = FXCollections.observableArrayList(orders);
        ordersTable.setItems(observableOrders);

        // Set the cell value factories for the columns
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableIdColumn.setCellValueFactory(new PropertyValueFactory<>("table"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        produseColumn.setCellValueFactory(cellData -> {
            Order order = cellData.getValue();
            // Here, you can calculate or fetch a custom value for the new column
            List<String> Produse = getService().getOrderItemsNames(order.getId());
            String produseStr = String.join(",", Produse);
            return new SimpleStringProperty(produseStr);
        });
        ordersTable.getColumns().add(produseColumn);
        // Initialize tables
        StreamSupport.stream(getService().getAllTables().spliterator(), false)
                .forEach(table -> {
                    getScreenService().showStage("Table.fxml", table.getId());
                });

        getService().addObserver(this);
    }

    @Override
    public void update() {
        List<Order> orders = StreamSupport.stream(getService().getOrders().spliterator(), false)
                .sorted(Comparator.comparing(Order::getDate)) // Sorting by date
                .toList();

        ObservableList<Order> observableOrders = FXCollections.observableArrayList(orders);
        ordersTable.setItems(observableOrders);
    }
}
