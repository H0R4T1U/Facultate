package org.example.lab6.repository;

import org.example.lab6.domain.Friendship;
import org.example.lab6.domain.Tuple;
import org.example.lab6.domain.validators.Validator;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FriendshipDBRepo extends AbstractDBRepo<Tuple<Long, Long>, Friendship> {

    public FriendshipDBRepo(String url, String username, String password, Validator<Friendship> validator) {
        super(url, username, password, validator);
    }

    @Override
    public Optional<Friendship> findOne(Tuple<Long, Long> friendship) {
        String sql = "SELECT * FROM friendships WHERE \"ID1\" = ? AND \"ID2\" = ?";
        try (Connection connection = prepareConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, friendship.getLeft());
            preparedStatement.setLong(2, friendship.getRight());
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return Optional.of(mapToFriendship(friendship, resultSet.getTimestamp("FriendsFrom").toLocalDateTime()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Friendship> findAll() {
        List<Friendship> friendships = new ArrayList<>();
        String sql = "SELECT * FROM \"friendships\"";
        try (Connection connection = prepareConnection()) {
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Tuple<Long, Long> id = new Tuple<>(
                        resultSet.getLong("ID1"),
                        resultSet.getLong("ID2")
                );
                LocalDateTime dateSent = resultSet.getTimestamp("FriendsFrom").toLocalDateTime();
                friendships.add(mapToFriendship(id, dateSent));
            }
            return friendships;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Friendship> save(Friendship friendship) {
        validator.validate(friendship);
        String sql = "INSERT INTO \"friendships\" (\"ID1\",\"ID2\",\"FriendsFrom\") VALUES(?,?,?)";
        try (Connection connection = prepareConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, friendship.getId().getLeft());
            preparedStatement.setLong(2, friendship.getId().getRight());
            preparedStatement.setDate(3, Date.valueOf(friendship.getDateSent().toLocalDate()));
            preparedStatement.execute();
            return Optional.of(friendship);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Friendship> delete(Tuple<Long, Long> longLongTuple) {
        Optional<Friendship> friendship = findOne(longLongTuple);
        if (friendship.isEmpty())
            return Optional.empty();
        String sql = "DELETE FROM friendships WHERE \"ID1\" = ? AND \"ID2\" = ?";
        try (Connection connection = prepareConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, longLongTuple.getLeft());
            preparedStatement.setLong(2, longLongTuple.getRight());
            preparedStatement.executeUpdate();
            return friendship;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Friendship> update(Friendship entity) {
        return Optional.empty();
    }

    private Friendship mapToFriendship(Tuple<Long, Long> id, LocalDateTime dateSent) {
        Friendship friendship = new Friendship();
        friendship.setId(id);
        friendship.setDateSent(dateSent);
        return friendship;
    }
}