package ubb.scs.map.Repository.database;


import ubb.scs.map.Domain.Friendship;
import ubb.scs.map.Domain.Tuple;
import ubb.scs.map.Domain.User;
import ubb.scs.map.Domain.UserInstance;
import ubb.scs.map.Domain.validators.Validator;
import ubb.scs.map.Utils.Paging.Page;
import ubb.scs.map.Utils.Paging.Pageable;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class FriendshipDatabaseRepository  extends DatabaseRepository<Tuple<Long, Long>, Friendship> implements PagingRepository<Tuple<Long,Long>, Friendship>  {

    public FriendshipDatabaseRepository(String url, String username, String password, Validator<Friendship> validator) {
        super(url, username, password, validator);
    }

    @Override
    public Optional<Friendship> findOne(Tuple<Long, Long> longLongTuple) {
        String FIND_ONE_QUERY = "SELECT * FROM \"Friendship\" WHERE \"ID1\" = ? AND \"ID2\" = ?";
        try (Connection connection = prepareConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ONE_QUERY)) {
            preparedStatement.setLong(1, longLongTuple.getE1());
            preparedStatement.setLong(2, longLongTuple.getE2());
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapToFriendship(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Friendship> findAll() {
        List<Friendship> friendships = new ArrayList<>();
        String FIND_ALL_QUERY = "SELECT * FROM \"Friendship\"";
        try (Connection connection = prepareConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_QUERY)) {
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                friendships.add(mapToFriendship(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return friendships;
    }

    @Override
    public Optional<Friendship> save(Friendship friendship) {
        validator.validate(friendship);

        String SAVE_QUERY = "INSERT INTO \"Friendship\" (\"ID1\",\"ID2\",\"Date\",\"User1\",\"User2\") VALUES(?,?,?,?,?)";
        try (Connection connection = prepareConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_QUERY)) {
            preparedStatement.setLong(1, friendship.getId().getE1());
            preparedStatement.setLong(2, friendship.getId().getE2());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(friendship.getDate()));
            preparedStatement.setString(4, friendship.getUser1());
            preparedStatement.setString(5, friendship.getUser2());
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

        String DELETE_QUERY = "DELETE FROM \"Friendship\" WHERE \"ID1\" = ? AND \"ID2\" = ?";
        try (Connection connection = prepareConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
            preparedStatement.setLong(1, longLongTuple.getE1());
            preparedStatement.setLong(2, longLongTuple.getE2());
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


    private Friendship mapToFriendship(ResultSet resultSet) throws SQLException {
        Tuple<Long, Long> id = new Tuple<>(resultSet.getLong("ID1"), resultSet.getLong("ID2"));
        LocalDateTime requestDate = resultSet.getTimestamp("Date").toLocalDateTime();
        String user1 = resultSet.getString("User1");
        String user2 = resultSet.getString("User2");
        Friendship friendship = new Friendship(requestDate, user1, user2);
        friendship.setId(id);


        return friendship;
    }

    @Override
    public Page<Friendship> findAllOnPage(Pageable pageable) {
        List<Friendship> entities = new ArrayList<>();
        Long id = UserInstance.getInstance().getId();
        String FIND_FRIENDS_QUERY =
                "SELECT * FROM \"Friendship\" WHERE \"ID1\" = " + id + " OR \"ID2\" = " + id +
                        " LIMIT " + pageable.getPageSize() +
                        " OFFSET " + (pageable.getPageNumber() - 1) * pageable.getPageSize();
        try (Connection connection = prepareConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_FRIENDS_QUERY);
             ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next())
                entities.add(mapToFriendship(resultSet));
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
        return new Page<>(entities);
    }
}