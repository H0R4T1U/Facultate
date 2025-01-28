package ubb.scs.map.Repository.database;

import ubb.scs.map.Domain.User;
import ubb.scs.map.Domain.validators.Validator;
import ubb.scs.map.Utils.Paging.Page;
import ubb.scs.map.Utils.Paging.Pageable;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDatabaseRepository extends DatabaseRepository<Long, User> implements PagingRepository<Long, User> {

    public UserDatabaseRepository(String url, String username, String password, Validator<User> validator) {
        super(url, username, password, validator);
    }

    @Override
    public Optional<User> findOne(Long id) {
        String FIND_ONE_QUERY = "select * from \"User\" where id=?";
        try (Connection connection = prepareConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ONE_QUERY)) {
            ps.setLong(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next())
                return Optional.of(createUser(resultSet));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<User> findAll() {
        List<User> users = new ArrayList<>();
        String FIND_ALL_QUERY = "select * from \"User\"";
        try (Connection connection = prepareConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL_QUERY);
             ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next())
                users.add(createUser(resultSet));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public Optional<User> save(User user) {
        validator.validate(user);
        String SAVE_QUERY = "insert into \"User\" (\"username\",\"password\",\"phone\",\"joindate\",\"age\" )  values(?,?,?,?,?)";
        try (Connection connection = prepareConnection();
             PreparedStatement ps = connection.prepareStatement(SAVE_QUERY,Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getPhoneNumber());
            ps.setString(4, user.getJoinDate().toString());
            ps.setInt(5, user.getAge());
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
    public Optional<User> delete(Long id) {
        Optional<User> user = findOne(id);
        String DELETE_QUERY = "delete from \"User\" where id=?";
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
    public Optional<User> update(User entity) {
        return Optional.empty();
    }



    private User createUser(ResultSet resultSet) throws SQLException {
        User user =  new User(resultSet.getString(2), resultSet.getString(3),resultSet.getString(4), LocalDateTime.parse(resultSet.getString(6)),resultSet.getInt(5));
        user.setId(resultSet.getLong(1));
        return user;
    }

    @Override
    public Page<User> findAllOnPage(Pageable pageable) {
        List<User> entities = new ArrayList<>();
            try (Connection connection = prepareConnection();
                 PreparedStatement ps = connection.prepareStatement("SELECT * FROM \"User\" limit " + pageable.getPageSize() + " OFFSET " + (pageable.getPageNumber() - 1) * pageable.getPageSize());
                 ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next())
                    entities.add(createUser(resultSet));
            } catch (SQLException e) {
                throw new RuntimeException("Database error: " + e.getMessage(), e);
            }
            return new Page<>(entities);
    }
}