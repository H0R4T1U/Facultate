package ubb.scs.map.clinica.Repository;

import ubb.scs.map.clinica.Domain.Medic;
import ubb.scs.map.clinica.Domain.Sectie;
import ubb.scs.map.clinica.Domain.Validators.Validator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MedicRepository extends DatabaseRepository<Medic> {
    public MedicRepository(String url, String username, String password, Validator<Medic> validator) {
        super(url, username, password, validator);
    }
    private Medic createMedic(ResultSet resultSet) throws SQLException {
        Medic o = new Medic(
                resultSet.getLong(2),
                resultSet.getString(3),
                resultSet.getInt(4),
                resultSet.getBoolean(5)

        );
        o.setId(resultSet.getLong(1));
        return o;
    }
    public Iterable<Medic> findAll() {
        List<Medic> medic = new ArrayList<>();
        String FIND_ALL_QUERY = "SELECT * FROM \"Medic\"";
        try (Connection connection = prepareConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_QUERY)) {
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                medic.add(createMedic(resultSet));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return medic;
    }
}
