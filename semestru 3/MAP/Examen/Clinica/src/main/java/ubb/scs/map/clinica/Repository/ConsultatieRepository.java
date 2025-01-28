package ubb.scs.map.clinica.Repository;

import ubb.scs.map.clinica.Domain.Consultatie;
import ubb.scs.map.clinica.Domain.Medic;
import ubb.scs.map.clinica.Domain.Sectie;
import ubb.scs.map.clinica.Domain.Validators.Validator;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsultatieRepository extends DatabaseRepository<Consultatie> {
    public ConsultatieRepository(String url, String username, String password, Validator<Consultatie> validator) {
        super(url, username, password, validator);
    }
    private Consultatie createConsultatie(ResultSet resultSet) throws SQLException {
        Consultatie o = new Consultatie(
                resultSet.getLong(2),
                resultSet.getString(3),
                resultSet.getString(4),
                LocalDate.parse(resultSet.getString(5)),
                LocalTime.parse(resultSet.getString(6))

        );
        o.setId(resultSet.getLong(1));
        return o;
    }
    public Optional<Consultatie> save(Consultatie consult) {
        validator.validate(consult);
        String SAVE_QUERY = "insert into \"Consultatie\" (\"idMedic\",\"CNP\",\"Nume\",\"Data\",\"Ora\")  values(?,?,?,?,?)";
        try (Connection connection = prepareConnection();
             PreparedStatement ps = connection.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, consult.getIdMedic());
            ps.setString(2, consult.getCnp());
            ps.setString(3, consult.getNume());
            ps.setString(4, consult.getData().toString());
            ps.setString(5, consult.getOra().toString());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Inserting user failed, no rows affected.");
            }
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long generatedId = generatedKeys.getLong(1);
                    consult.setId(generatedId);
                } else {
                    throw new SQLException("Inserting user failed, no ID obtained.");
                }
            }
            return Optional.of(consult);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Iterable<Consultatie> findAll() {
        List<Consultatie> consult = new ArrayList<>();
        String FIND_ALL_QUERY = "SELECT * FROM \"Consultatie\"";
        try (Connection connection = prepareConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_QUERY)) {
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                consult.add(createConsultatie(resultSet));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return consult;
    }
}
