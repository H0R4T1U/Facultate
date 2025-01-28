package ubb.scs.map.examen.Repository;

import ubb.scs.map.examen.domain.City;
import ubb.scs.map.examen.domain.Validators.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CityRepository extends DatabaseRepository<City> {
    public CityRepository(String url, String username, String password, Validator<City> validator) {
        super(url, username, password, validator);
    }

    public Iterable<City> findAll() {
        List<City> cities = new ArrayList<>();
        String FIND_ALL_QUERY = "SELECT * FROM \"City\"";
        try (Connection connection = prepareConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_QUERY)) {
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                cities.add(createCities(resultSet));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cities;
    }
    private City createCities(ResultSet resultSet) throws SQLException {
        City c = new City(
                resultSet.getString(2)

        );
        c.setId(resultSet.getInt(1));
        return c;
    }

    public Optional<City> findOne(Integer id) {
        String FIND_ONE_QUERY = "select * from \"City\" where id=?";
        try (Connection connection = prepareConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ONE_QUERY)) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next())
                return Optional.of(createCities(resultSet));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}
