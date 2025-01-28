package ubb.scs.map.avioane.Controllers;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import ubb.scs.map.avioane.Domain.Order;
import ubb.scs.map.avioane.Domain.OrderItem;
import ubb.scs.map.avioane.Domain.MenuItem;
import ubb.scs.map.avioane.Domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TableController extends ControllerSuperclass {

    public Label header;
    public VBox tablesContainer; // VBox to hold all tables dynamically
    public Button placeOrderButton; // Button to place orders

    private List<MenuItem> selectedItems = new ArrayList<>(); // Store selected items

    @Override
    public void init() {
        header.setText("Table " + getTableId());

        Map<String, List<MenuItem>> menuByCategory = getService().getMenuByCategory();

        // Create a table for each category
        for (Map.Entry<String, List<MenuItem>> entry : menuByCategory.entrySet()) {
            String category = entry.getKey();
            List<MenuItem> items = entry.getValue();

            // Create a label for the category
            Label categoryLabel = new Label(category);
            categoryLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

            // Create a TableView with checkboxes
            TableView<MenuItem> tableView = createTableViewForCategory(items);

            // Add the label and table to the container
            tablesContainer.getChildren().addAll(categoryLabel, tableView);
        }

        // Configure "Place Order" button
        placeOrderButton.setOnAction(event -> placeOrder());
    }

    private TableView<MenuItem> createTableViewForCategory(List<MenuItem> items) {
        TableView<MenuItem> tableView = new TableView<>();

        // Create columns for each property
        TableColumn<MenuItem, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<MenuItem, String> itemColumn = new TableColumn<>("Item");
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("item"));

        TableColumn<MenuItem, Float> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<MenuItem, String> currencyColumn = new TableColumn<>("Currency");
        currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currency"));

        // Add a column for checkboxes
        TableColumn<MenuItem, Boolean> selectColumn = new TableColumn<>("Select");
        selectColumn.setCellValueFactory(param -> {
            MenuItem menuItem = param.getValue();
            // We don't need to return a property, just null, as we will handle the checkbox manually.
            return null;
        });

        // Set the cell factory to create a checkbox for each row
        selectColumn.setCellFactory(param -> {
            TableCell<MenuItem, Boolean> cell = new TableCell<>() {
                private final CheckBox checkBox = new CheckBox();

                {
                    checkBox.setOnAction(event -> {
                        MenuItem menuItem = getTableView().getItems().get(getIndex());
                        if (checkBox.isSelected()) {
                            selectedItems.add(menuItem); // Add to selected items if checked
                        } else {
                            selectedItems.remove(menuItem); // Remove from selected items if unchecked
                        }
                    });
                }

                @Override
                protected void updateItem(Boolean item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(checkBox);
                        checkBox.setSelected(item != null && item); // Set the checkbox state
                    }
                }
            };
            return cell;
        });

        // Add the columns to the table
        tableView.getColumns().addAll(selectColumn, idColumn, itemColumn, priceColumn, currencyColumn);

        // Populate the table with items
        tableView.getItems().addAll(items);

        return tableView;
    }


    private void placeOrder() {
        if (selectedItems.isEmpty()) {
            showAlert("No items selected", "Please select items to place an order.");
            return;
        }

        // Create a new order
        Order order = new Order(getTableId(), LocalDateTime.now(), OrderStatus.PLACED);
        getService().saveOrder(order); // Save order (logic in OrderService)

        Order order_with_id = getService().getTableOrder(getTableId()).get();

        // For each selected menu item, create a corresponding order item
        for (MenuItem item : selectedItems) {
            OrderItem orderItem = new OrderItem(order_with_id.getId(), item.getId());
            getService().saveOrderItem(orderItem);
        }

        showAlert("Order Placed", "Your order has been placed successfully!");
        selectedItems.clear(); // Clear selected items after placing the order
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
