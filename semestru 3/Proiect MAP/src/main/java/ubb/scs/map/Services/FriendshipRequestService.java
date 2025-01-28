package ubb.scs.map.Services;


import ubb.scs.map.Domain.FriendshipRequest;
import ubb.scs.map.Domain.Tuple;
import ubb.scs.map.Domain.User;
import ubb.scs.map.Repository.Repository;
import ubb.scs.map.Utils.observer.Observable;
import ubb.scs.map.Utils.observer.Observer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FriendshipRequestService implements Observable {
    private final Repository<Tuple<Long, Long>, FriendshipRequest> requestRepository;
    private final FriendshipService friendshipService;
    private final List<Observer> observers = new ArrayList<>();

    public FriendshipRequestService(Repository<Tuple<Long, Long>, FriendshipRequest> requestRepository, FriendshipService friendshipService) {
        this.requestRepository = requestRepository;
        this.friendshipService = friendshipService;
    }

    public Iterable<FriendshipRequest> getRequestsForUser(User user) {
        List<FriendshipRequest> userRequests = new ArrayList<>();
        for (FriendshipRequest request : requestRepository.findAll())
            if (request.getId().getE2().equals(user.getId()))
                userRequests.add(request);
        return userRequests;
    }

    public void addRequest(User sender, User receiver) {
        FriendshipRequest newRequest = new FriendshipRequest(
                sender.getId(),
                receiver.getId(),
                LocalDateTime.now(),
                "pending"
        );
        if (sender.getId().equals(receiver.getId()))
            throw new ServiceException("You can't send a friend request to yourself!");
        if (friendshipService.getById(new Tuple<>(sender.getId(), receiver.getId())).isPresent() ||
                friendshipService.getById(new Tuple<>(receiver.getId(), sender.getId())).isPresent())
            throw new ServiceException("You can't send a friend request to a friend!");
        if(requestRepository.findOne(new Tuple<>(sender.getId(), receiver.getId())).isPresent()  || requestRepository.findOne(new Tuple<>(receiver.getId(), sender.getId())).isPresent()){
            throw new ServiceException("You can't send a friend request twice!");
        }
        requestRepository.save(newRequest);
        notifyObservers();
    }

    public void removeRequest(Long senderID, Long receiverID) {
        requestRepository.delete(new Tuple<>(senderID, receiverID));
    }

    @Override
    public void addObserver(Observer e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(Observer::update);
    }
}
