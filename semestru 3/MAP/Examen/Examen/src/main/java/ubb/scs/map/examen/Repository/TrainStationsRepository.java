package ubb.scs.map.examen.Repository;

import ubb.scs.map.examen.domain.City;
import ubb.scs.map.examen.domain.TrainStations;
import ubb.scs.map.examen.domain.Validators.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TrainStationsRepository extends DatabaseRepository<TrainStations> {
    public TrainStationsRepository(String url, String username, String password, Validator<TrainStations> validator) {
        super(url, username, password, validator);
    }

    public Iterable<TrainStations> findAll() {
        List<TrainStations> trains = new ArrayList<>();
        String FIND_ALL_QUERY = "SELECT * FROM \"TrainStations\"";
        try (Connection connection = prepareConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_QUERY)) {
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                trains.add(createTrainStation(resultSet));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return trains;
    }
    private TrainStations createTrainStation(ResultSet resultSet) throws SQLException {
        TrainStations t = new TrainStations(
                resultSet.getInt(2),
                resultSet.getInt(3)

        );
        t.setId(resultSet.getInt(1));
        return t;
    }
}
