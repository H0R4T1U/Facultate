package ubb.scs.map.avioane.Repository;

import ubb.scs.map.avioane.Domain.MenuItem;
import ubb.scs.map.avioane.Domain.Order;
import ubb.scs.map.avioane.Domain.OrderItem;
import ubb.scs.map.avioane.Domain.Validators.Validator;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderItemsRepository extends DatabaseRepository<OrderItem> {

    public OrderItemsRepository(String url, String username, String password, Validator<OrderItem> validator) {
        super(url, username, password, validator);
    }
    private OrderItem createOrder(ResultSet resultSet) throws SQLException {
        OrderItem item = new OrderItem(
                resultSet.getInt(1),
                resultSet.getInt(2)
        );
        return item;
    }

    public Optional<OrderItem> save(OrderItem item) {
        validator.validate(item);
        String SAVE_QUERY = "insert into \"OrderItems\" (\"orderId\",\"menuItemId\")  values(?,?)";
        try (Connection connection = prepareConnection();
             PreparedStatement ps = connection.prepareStatement(SAVE_QUERY)) {
            ps.setInt(1, item.getOrderId());
            ps.setInt(2, item.getMenuItem());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Inserting user failed, no rows affected.");
            }
            return Optional.of(item);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Iterable<OrderItem> findAll() {
        List<OrderItem> menu = new ArrayList<>();
        String FIND_ALL_QUERY = "SELECT * FROM \"OrderItems\"";
        try (Connection connection = prepareConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_QUERY)) {
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                menu.add(createOrder(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return menu;
    }
}
