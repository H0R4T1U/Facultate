package ubb.scs.map.Repository.database;


import ubb.scs.map.Domain.Message;
import ubb.scs.map.Domain.validators.Validator;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class MessageDatabaseRepository extends DatabaseRepository<Long, Message> {

    public MessageDatabaseRepository(String url, String username, String password, Validator<Message> validator) {
        super(url, username, password, validator);
    }

    @Override
    public Optional<Message> findOne(Long id) {
        String FIND_ONE_QUERY = "SELECT * FROM \"Message\" WHERE \"Id\" = ?";
        try (Connection connection = prepareConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ONE_QUERY)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapToMessage(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Message> findAll() {
        List<Message> messages = new ArrayList<>();
        String FIND_ALL_QUERY = "SELECT * FROM \"Message\"";
        try (Connection connection = prepareConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_QUERY)) {
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                messages.add(mapToMessage(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return messages;
    }

    @Override
    public Optional<Message> save(Message message) {
        validator.validate(message);

        String SAVE_QUERY = "INSERT INTO \"Message\" (\"Sender\", \"Receiver\", \"Reply\", \"Text\", \"Date\") VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = prepareConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS)) {

            // Set parameters for the INSERT query
            preparedStatement.setLong(1, message.getSender());
            preparedStatement.setLong(2, message.getReceiver());
            preparedStatement.setObject(3, message.getReplyMessage(), java.sql.Types.BIGINT); // Handle nullable value
            preparedStatement.setString(4, message.getText());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(message.getDateSent()));

            // Execute the query
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Inserting message failed, no rows affected.");
            }

            // Retrieve the generated ID
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long generatedId = generatedKeys.getLong(1);
                    message.setId(generatedId); // Assuming Message class has a setId method
                } else {
                    throw new SQLException("Inserting message failed, no ID obtained.");
                }
            }

            return Optional.of(message);
        } catch (SQLException e) {
            throw new RuntimeException("Error saving message: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Message> delete(Long id) {
        Optional<Message> message = findOne(id);
        if (message.isEmpty())
            return Optional.empty();

        String DELETE_QUERY = "DELETE FROM \"Message\" WHERE \"Id\" = ?";
        try (Connection connection = prepareConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();
            return message;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Message> update(Message entity) {
        return Optional.empty();
    }


    private Message mapToMessage(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("Id");
        LocalDateTime sentDate = resultSet.getTimestamp("Date").toLocalDateTime();
        Long sender = resultSet.getLong("Sender");
        Long receiver = resultSet.getLong("Receiver");
        String text = resultSet.getString("Text");
        Long reply = Optional.of(resultSet.getLong("Reply")).orElse(null);
        Message message = new Message(sender,receiver,text,reply,sentDate);
        message.setId(id);

        return message;
    }
}