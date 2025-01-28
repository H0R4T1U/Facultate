package org.example.lab6.repository;

import org.example.lab6.domain.Request;
import org.example.lab6.domain.Tuple;
import org.example.lab6.domain.validators.Validator;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RequestDBRepo extends AbstractDBRepo<Tuple<Long, Long>, Request> {

    public RequestDBRepo(String url, String username, String password, Validator<Request> validator) {
        super(url, username, password, validator);
    }

    @Override
    public Optional<Request> findOne(Tuple<Long, Long> requestID) {
        String sql = "SELECT * FROM requests WHERE sender = ? OR receiver = ?";
        try (Connection connection = prepareConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, requestID.getLeft());
            ps.setLong(2, requestID.getRight());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Long userId1 = rs.getLong("sender");
                Long userId2 = rs.getLong("receiver");
                LocalDateTime requestDate = rs.getTimestamp("dateSent").toLocalDateTime();
                String status = rs.getString("status");
                return Optional.of(new Request(userId1, userId2, requestDate, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Request> findAll() {
        List<Request> requests = new ArrayList<>();
        String sql = "SELECT * FROM requests";
        try (Connection connection = prepareConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Long userId1 = rs.getLong("sender");
                Long userId2 = rs.getLong("receiver");
                LocalDateTime requestDate = rs.getTimestamp("dateSent").toLocalDateTime();
                String status = rs.getString("status");
                requests.add(new Request(userId1, userId2, requestDate, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return requests;
    }

    @Override
    public Optional<Request> save(Request entity) {
        String sql = "INSERT INTO requests (status, sender, receiver, \"dateSent\") VALUES (?, ?, ?, ?)";
        try (Connection connection = prepareConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, entity.getStatus());
            ps.setLong(2, entity.getSender());
            ps.setLong(3, entity.getReceiver());
            ps.setTimestamp(4, Timestamp.valueOf(entity.getDateSent()));
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return Optional.of(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Request> delete(Tuple<Long, Long> request) {
        String sql = "DELETE FROM requests WHERE sender = ? AND receiver = ?";
        try (Connection connection = prepareConnection(); PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, request.getLeft());
            ps.setLong(2, request.getRight());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0)
                return Optional.of(new Request(request.getLeft(), request.getRight(), LocalDateTime.now(), "deleted"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<Request> update(Request entity) {
        return Optional.empty();
    }
}
