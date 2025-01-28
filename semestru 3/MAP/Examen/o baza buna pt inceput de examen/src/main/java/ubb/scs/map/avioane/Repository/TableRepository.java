package ubb.scs.map.avioane.Repository;

import ubb.scs.map.avioane.Domain.Table;
import ubb.scs.map.avioane.Domain.Validators.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableRepository extends DatabaseRepository<Table> {
    public TableRepository(String url, String username, String password, Validator<Table> validator) {
        super(url, username, password, validator);
    }
    public Iterable<Table> findAll() {
        List<Table> tables = new ArrayList<>();
        String FIND_ALL_QUERY = "SELECT * FROM \"Tables\"";
        try (Connection connection = prepareConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_QUERY)) {
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tables.add(createTable(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tables;
    }
    private Table createTable(ResultSet resultSet) throws SQLException {
        Table table =  new Table();
        table.setId(resultSet.getInt(1));
        return table;
    }

}
