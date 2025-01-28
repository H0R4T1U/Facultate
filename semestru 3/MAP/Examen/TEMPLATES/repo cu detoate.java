package ubb.scs.map.faptebune.Repository;



import ubb.scs.map.faptebune.Domain.Nevoie;
import ubb.scs.map.faptebune.Domain.Nevoie;
import ubb.scs.map.faptebune.Domain.Validators.Validator;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NevoieDatabaseRepository extends DatabaseRepository<Long, Nevoie>  {

    public NevoieDatabaseRepository(String url, String username, String password, Validator<Nevoie> validator) {
        super(url, username, password, validator);
    }

    @Override
    public Optional<Nevoie> findOne(Long id) {
        String FIND_ONE_QUERY = "select * from \"Nevoi\" where id=?";
        try (Connection connection = prepareConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ONE_QUERY)) {
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next())
                return Optional.of(createNevoie(resultSet));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Nevoie> findAll() {
        List<Nevoie> users = new ArrayList<>();
        String FIND_ALL_QUERY = "select * from \"Nevoi\"";
        try (Connection connection = prepareConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_QUERY);
             ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next())
                users.add(createNevoie(resultSet));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public Optional<Nevoie> save(Nevoie nevoie) {
        validator.validate(nevoie);
        String SAVE_QUERY = "insert into \"Nevoi\" (\"titlu\",\"descriere\",\"deadline\",\"omInNevoie\",\"omSalvator\",\"status\")  values(?,?,?,?,?,?)";
        try (Connection connection = prepareConnection();
             PreparedStatement ps = connection.prepareStatement(SAVE_QUERY,Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nevoie.getTitle());
            ps.setString(2, nevoie.getDescriere());
            ps.setString(3, nevoie.getDeadline().toString());
            ps.setLong(4, nevoie.getOmInNevoie());
            ps.setLong(5, nevoie.getOmSalvator());
            ps.setString(6, nevoie.getStatus());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Inserting user failed, no rows affected.");
            }
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long generatedId = generatedKeys.getLong(1);
                    nevoie.setId(generatedId); // Assuming Message class has a setId method
                } else {
                    throw new SQLException("Inserting user failed, no ID obtained.");
                }
            }
            return Optional.of(nevoie);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Nevoie> delete(Long id) {
        Optional<Nevoie> nevoie = findOne(id);
        String DELETE_QUERY = "delete from \"Persoane\" where id=?";
        try (Connection connection = prepareConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_QUERY)) {
            ps.setLong(1, id);
            ps.execute();
            return nevoie;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Nevoie> update(Nevoie entity) {
        validator.validate(entity);
        String UPDATE_QUERY = "UPDATE \"Nevoi\" SET \"titlu\" = ?, \"descriere\" = ?, \"deadline\" = ?, \"omInNevoie\" = ?, \"omSalvator\" = ?, \"status\" = ? WHERE \"id\" = ?";
        try (Connection connection = prepareConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_QUERY)) {

            ps.setString(1, entity.getTitle());
            ps.setString(2, entity.getDescriere());
            ps.setString(3, entity.getDeadline().toString());
            ps.setLong(4, entity.getOmInNevoie());
            ps.setLong(5, entity.getOmSalvator());
            ps.setString(6, entity.getStatus());
            ps.setLong(7, entity.getId());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                return Optional.empty(); // No rows updated, possibly invalid ID
            }
            return Optional.of(entity); // Return the updated entity
        } catch (SQLException e) {
            throw new RuntimeException("Error updating entity", e);
        }
    }



    private Nevoie createNevoie(ResultSet resultSet) throws SQLException {
        Nevoie nevoie =  new Nevoie(
                resultSet.getString(2),
                resultSet.getString(3),
                LocalDateTime.parse(resultSet.getString(4)),
                resultSet.getLong(5),
                resultSet.getLong(6),
                resultSet.getString(7)
        );
        nevoie.setId(resultSet.getLong(1));
        return nevoie;
    }


}
