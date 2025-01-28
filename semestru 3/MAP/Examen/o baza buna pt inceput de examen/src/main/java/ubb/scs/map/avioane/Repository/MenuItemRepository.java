package ubb.scs.map.avioane.Repository;

import ubb.scs.map.avioane.Domain.MenuItem;
import ubb.scs.map.avioane.Domain.Table;
import ubb.scs.map.avioane.Domain.Validators.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuItemRepository extends DatabaseRepository<MenuItem> {

    public MenuItemRepository(String url, String username, String password, Validator<MenuItem> validator) {
        super(url, username, password, validator);
    }
    public Iterable<MenuItem> findAll() {
        List<MenuItem> menu = new ArrayList<>();
        String FIND_ALL_QUERY = "SELECT * FROM \"Menu\"";
        try (Connection connection = prepareConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_QUERY)) {
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                menu.add(createMenu(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return menu;
    }
    private MenuItem createMenu(ResultSet resultSet) throws SQLException {
        MenuItem item =  new MenuItem(
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getFloat(4),
                resultSet.getString(5)
        );
        item.setId(resultSet.getInt(1));
        return item;
    }
}
