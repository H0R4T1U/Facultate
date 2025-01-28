package ubb.scs.map.avioane.Repository;

import ubb.scs.map.avioane.Domain.MenuItem;
import ubb.scs.map.avioane.Domain.Order;
import ubb.scs.map.avioane.Domain.OrderStatus;
import ubb.scs.map.avioane.Domain.Validators.Validator;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepository extends DatabaseRepository<Order> {


    public OrderRepository(String url, String username, String password, Validator<Order> validator) {
        super(url, username, password, validator);
    }
    private Order createOrder(ResultSet resultSet) throws SQLException {
       Order o = new Order(
                resultSet.getInt(2),
                LocalDateTime.parse(resultSet.getString(3)),
                OrderStatus.valueOf(resultSet.getString(4).toUpperCase())

        );
       o.setId(resultSet.getInt(1));
       return o;
    }

    public Optional<Order> save(Order order) {
        validator.validate(order);
        String SAVE_QUERY = "insert into \"Order\" (\"table\",\"date\",\"status\")  values(?,?,?)";
        try (Connection connection = prepareConnection();
             PreparedStatement ps = connection.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, order.getTable());
            ps.setString(2, order.getDate().toString());
            ps.setString(3, order.getStatus().toString());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Inserting user failed, no rows affected.");
            }
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Integer generatedId = generatedKeys.getInt(1);
                    order.setId(generatedId); // Assuming Message class has a setId method
                } else {
                    throw new SQLException("Inserting user failed, no ID obtained.");
                }
            }
            return Optional.of(order);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Iterable<Order> findAll() {
        List<Order> order = new ArrayList<>();
        String FIND_ALL_QUERY = "SELECT * FROM \"Order\"";
        try (Connection connection = prepareConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_QUERY)) {
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                order.add(createOrder(resultSet));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return order;
    }
}
