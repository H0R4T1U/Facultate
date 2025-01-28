package ubb.scs.map.faptebune.Repository;



import ubb.scs.map.faptebune.Domain.Persoana;
import ubb.scs.map.faptebune.Domain.Validators.Validator;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersoanaDatabaseRepository extends DatabaseRepository<Long, Persoana>  {

    public PersoanaDatabaseRepository(String url, String username, String password, Validator<Persoana> validator) {
        super(url, username, password, validator);
    }

    @Override
    public Optional<Persoana> findOne(Long id) {
        String FIND_ONE_QUERY = "select * from \"Persoane\" where id=?";
        try (Connection connection = prepareConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ONE_QUERY)) {
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next())
                return Optional.of(createPersoana(resultSet));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Persoana> findAll() {
        List<Persoana> users = new ArrayList<>();
        String FIND_ALL_QUERY = "select * from \"Persoane\"";
        try (Connection connection = prepareConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_QUERY);
             ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next())
                users.add(createPersoana(resultSet));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public Optional<Persoana> save(Persoana user) {
        validator.validate(user);
        String SAVE_QUERY = "insert into \"Persoane\" (\"nume\",\"prenume\",\"username\",\"parola\",\"oras\",\"strada\",\"numarStrada\",\"telefon\" )  values(?,?,?,?,?,?,?,?)";
        try (Connection connection = prepareConnection();
             PreparedStatement ps = connection.prepareStatement(SAVE_QUERY,Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getNume());
            ps.setString(2, user.getPrenume());
            ps.setString(3, user.getUsername());
            ps.setString(4, user.getParola());
            ps.setString(5, user.getOras());
            ps.setString(6, user.getStrada());
            ps.setString(7, user.getNumarStrada());
            ps.setString(8, user.getTelefon());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Inserting user failed, no rows affected.");
            }
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long generatedId = generatedKeys.getLong(1);
                    user.setId(generatedId); // Assuming Message class has a setId method
                } else {
                    throw new SQLException("Inserting user failed, no ID obtained.");
                }
            }
            return Optional.of(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Persoana> delete(Long id) {
        Optional<Persoana> user = findOne(id);
        String DELETE_QUERY = "delete from \"Persoane\" where id=?";
        try (Connection connection = prepareConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_QUERY)) {
            ps.setLong(1, id);
            ps.execute();
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Persoana> update(Persoana entity) {
        return Optional.empty();
    }



    private Persoana createPersoana(ResultSet resultSet) throws SQLException {
        Persoana user =  new Persoana(
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4),
                resultSet.getString(5),
                resultSet.getString(6),
                resultSet.getString(7),
                resultSet.getString(8),
                resultSet.getString(9)
                );
        user.setId(resultSet.getLong(1));
        return user;
    }


}
