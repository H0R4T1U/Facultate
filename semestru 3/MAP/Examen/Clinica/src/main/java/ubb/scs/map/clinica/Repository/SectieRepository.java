package ubb.scs.map.clinica.Repository;

import ubb.scs.map.clinica.Domain.Sectie;
import ubb.scs.map.clinica.Domain.Validators.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SectieRepository extends DatabaseRepository<Sectie> {
    public SectieRepository(String url, String username, String password, Validator<Sectie> validator) {
        super(url, username, password, validator);
    }
    private Sectie createSectie(ResultSet resultSet) throws SQLException {
        Sectie o = new Sectie(
                resultSet.getString(2),
                resultSet.getLong(3),
                resultSet.getInt(4),
                resultSet.getInt(5)

        );
        o.setId(resultSet.getLong(1));
        return o;
    }
    public Iterable<Sectie> findAll() {
        List<Sectie> order = new ArrayList<>();
        String FIND_ALL_QUERY = "SELECT * FROM \"Sectie\"";
        try (Connection connection = prepareConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_QUERY)) {
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                order.add(createSectie(resultSet));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return order;
    }
}
