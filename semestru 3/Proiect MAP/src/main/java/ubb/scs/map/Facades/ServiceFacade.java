package ubb.scs.map.Facades;

import ubb.scs.map.Domain.*;
import ubb.scs.map.Services.*;
import ubb.scs.map.Utils.Paging.Page;
import ubb.scs.map.Utils.Paging.Pageable;
import ubb.scs.map.Utils.observer.ObservableType;
import ubb.scs.map.Utils.observer.Observer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ServiceFacade {
    private final UserService userService;
    private final FriendshipService friendshipService;
    private final FriendshipRequestService friendshipRequestService;
    private ScreenService screenService = null    ;
    private final NetworkService networkService;
    private final MessageService messageService;

    private static ServiceFacade instance = null;
    private ServiceFacade() {
        RepositoryFacade rf = RepositoryFacade.getInstance();
        this.userService = new UserService(rf.getUserRepository());
        this.friendshipService = new FriendshipService(rf.getFriendshipRepository());
        this.friendshipRequestService = new FriendshipRequestService(rf.getFriendshipRequestRepository(),friendshipService);
        this.networkService = new NetworkService(friendshipService,userService);
        this.messageService = new MessageService(rf.getMessageRepository());

    }
    public static ServiceFacade getInstance(){
        if(instance == null){
            instance = new ServiceFacade();
        }
        return instance;
    }

    public void setScreenService(ScreenService screenService) {
        this.screenService = screenService;
    }

    public void switchScene(String name) {
        this.screenService.switchScene(name);
    }

    public Page<User> getUsersPaged(Pageable page){
        return userService.findAllOnPage(page);
    }
    public Optional<User> getUserById(Long id) {
        return userService.getById(id);
    }

    public Optional<User> getUserByName(String name) {
        return userService.getByUsername(name);
    }

    public boolean login(String username, String password) {
        return userService.login(username, password);
    }
    public Optional<User> createUser(User user) {
        return userService.create(user);
    }

    public Optional<Friendship> getFriendshipById(Tuple<Long,Long> id){
        return friendshipService.getById(id);
    }

    public List<Friendship> getUserFriends(Long id) {
        return friendshipService.getUserFriends(id);
    }

    public Optional<Friendship> createFriendship(Friendship fr){
        return friendshipService.create(fr);
    }

    public Optional<Friendship> deleteFriendship(Tuple<Long,Long> id){
        return friendshipService.delete(id);
    }
    public Page<Friendship> getUserFriendsPaged(Pageable page){
        return friendshipService.findAllOnPage(page);
    }
    public List<User> getUsersNotFriends(Long id){
        List<Friendship> friends = getUserFriends(id);
        Set<Long> friendshipIds = friends.stream()
                .flatMap(friendship -> Stream.of(friendship.getId().getE1(), friendship.getId().getE2()))
                .collect(Collectors.toSet());
        friendshipIds.add(id); // Add Current user id to the list of ids to be filtered
        return StreamSupport.stream(userService.getAll().spliterator(), false)
                .filter(user -> !friendshipIds.contains(user.getId()))
                .toList();

    }
    public Iterable<FriendshipRequest> getRequestsForUser(User user){
     return friendshipRequestService.getRequestsForUser(user);
    }

    public void removeFriendshipRequest(FriendshipRequest fr){
        friendshipRequestService.removeRequest(fr.getSender(), fr.getReceiver());
    }
    public void createFriendshipRequest(User snd,User rcv){
        friendshipRequestService.addRequest(snd, rcv);
    }

    public List<Message> getMessages(Long sender,Long receiver){
        return messageService.getMessagesForConversation(sender, receiver);
    }
    public void addMessage(Long currentUser, Long chatterId, String message, Long reply) {
        Message msg = new Message(currentUser,chatterId,message,reply, LocalDateTime.now());
        messageService.addMessage(msg);
    }
    public Optional<Message> getMessageById(Long id){
        return messageService.getMessageById(id);
    }
    public void addObserver(Observer observer, ObservableType type) {
        if(type == ObservableType.REQUEST){
            friendshipRequestService.addObserver(observer);
        }
        if(type == ObservableType.USER){
            userService.addObserver(observer);
        }
        if(type == ObservableType.FRIENDSHIP){
            friendshipService.addObserver(observer);
        }
        if(type == ObservableType.MESSAGE){
            messageService.addObserver(observer);
        }
    }


}
