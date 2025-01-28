package org.example.lab6.service;

import org.example.lab6.domain.Request;
import org.example.lab6.domain.Tuple;
import org.example.lab6.domain.User;
import org.example.lab6.repository.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RequestService {
    private final Repository<Tuple<Long, Long>, Request> requestRepository;

    public RequestService(Repository<Tuple<Long, Long>, Request> requestRepository) {
        this.requestRepository = requestRepository;
    }

    public Iterable<Request> getRequestsForUser(User user) {
        List<Request> userRequests = new ArrayList<>();
        for (Request request : requestRepository.findAll())
            if (request.getId().getLeft().equals(user.getId()) || request.getId().getRight().equals(user.getId()))
                userRequests.add(request);
        return userRequests;
    }

    public void addRequest(User sender, User receiver) {
        Request newRequest = new Request(
                sender.getId(),
                receiver.getId(),
                LocalDateTime.now(),
                "pending"
        );
        requestRepository.save(newRequest);
    }

    public void removeRequest(Long senderID, Long receiverID) {
        requestRepository.delete(new Tuple<>(senderID, receiverID));
    }
}
