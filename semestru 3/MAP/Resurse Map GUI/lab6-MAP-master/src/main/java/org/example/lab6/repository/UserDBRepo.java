package org.example.lab6.repository;

import org.example.lab6.domain.User;
import org.example.lab6.domain.validators.Validator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDBRepo extends AbstractDBRepo<Long, User> {
    private static final String FIND_ONE_QUERY = "select * from \"users\" where id=?";
    private static final String FIND_ALL_QUERY = "select * from \"users\"";
    private static final String SAVE_QUERY = "insert into \"users\" (\"id\", \"first_name\", \"last_name\", \"username\", \"password\")  values(?,?,?,?,?)";
    private static final String DELETE_QUERY = "delete from \"users\" where id=?";

    public UserDBRepo(String url, String username, String password, Validator<User> validator) {
        super(url, username, password, validator);
    }

    @Override
    public Optional<User> findOne(Long id) {
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
        try (Connection connection = prepareConnection()) {
            PreparedStatement ps = connection.prepareStatement(SAVE_QUERY);
            ps.setLong(1, user.getId());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setString(4, user.getUsername());
            ps.setString(5, user.getPassword());
            ps.execute();
            return Optional.of(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> delete(Long id) {
        Optional<User> user = findOne(id);
        try (Connection connection = prepareConnection()) {
            PreparedStatement ps = connection.prepareStatement(DELETE_QUERY);
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
        return new User(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));
    }
}