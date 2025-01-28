package org.example.lab6.service;

import org.example.lab6.domain.Friendship;
import org.example.lab6.domain.Tuple;
import org.example.lab6.domain.User;
import org.example.lab6.repository.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.lang.Math.abs;

public class UserService implements Service<User> {
    private final Repository<Long, User> userRepository;
    private final Repository<Tuple<Long, Long>, Friendship> friendshipRepository;

    public UserService(Repository<Long, User> repository, Repository<Tuple<Long, Long>, Friendship> friendshipFileRepository) {
        this.userRepository = repository;
        this.friendshipRepository = friendshipFileRepository;
    }

    public String getEntities() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(this::userToString)
                .collect(Collectors.joining("\n"));
    }

    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    public List<User> getFriendsForUser(Long userId) {
        List<User> friends = new ArrayList<>();
        Iterable<Friendship> allFriendships = friendshipRepository.findAll();
        for (Friendship friendship : allFriendships) {
            Tuple<Long, Long> friendshipId = friendship.getId();
            Long friendId = null;
            if (friendshipId.getLeft().equals(userId)) {
                friendId = friendshipId.getRight();
            } else if (friendshipId.getRight().equals(userId)) {
                friendId = friendshipId.getLeft();
            }
            if (friendId != null) {
                userRepository.findOne(friendId).ifPresent(friends::add);
            }
        }
        return friends;
    }


    public Optional<User> findUser(String username) {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .filter(user -> username.equals(user.getUsername()))
                .findFirst();
    }


    private String userToString(User user) {
        return user.toString() + " ID : " + user.getId();
    }

    public User removeEntity(Long id) {
        Optional<User> deletedUser = userRepository.delete(id);
        if (deletedUser.isPresent()) {
            cleanupFriendships(id);
            removeUserFromFriends(deletedUser.get());
        } else
            throw new ServiceException("That user doesn't exist!");
        return deletedUser.orElse(null);
    }

    private void cleanupFriendships(Long userId) {
        friendshipRepository.findAll().forEach(friendship -> {
            if (friendship.getId().getLeft().equals(userId) || friendship.getId().getRight().equals(userId)) {
                friendshipRepository.delete(friendship.getId());
            }
        });
    }

    private void removeUserFromFriends(User deletedUser) {
        userRepository.findAll().forEach(user -> user.removeFriend(deletedUser));
    }

    public void addUser(User user) {
        user.setId(generateId());
        userRepository.save(user);
    }

    private Long generateId() {
        return abs(RandomGenerator.getDefault().nextLong());
    }

    public String getFriendships() {
        Map<Long, List<User>> userFriendsMap = mapUserFriends();
        return userFriendsMap.entrySet()
                .stream()
                .map(this::friendsToString)
                .collect(Collectors.joining("\n"));
    }

    public Map<Long, List<User>> mapUserFriends() {
        Map<Long, List<User>> userFriendsMap = new HashMap<>();
        friendshipRepository.findAll().forEach(friendship -> {
            addFriend(userFriendsMap, friendship.getId().getLeft(), friendship.getId().getRight());
            addFriend(userFriendsMap, friendship.getId().getRight(), friendship.getId().getLeft());
        });
        return userFriendsMap;
    }

    private void addFriend(Map<Long, List<User>> userFriendsMap, Long userId, Long friendId) {
        userRepository.findOne(friendId).ifPresent(friend -> userFriendsMap.computeIfAbsent(userId, k -> new ArrayList<>()).add(friend));
    }


    private String friendsToString(Map.Entry<Long, List<User>> entry) {
        User user = userRepository.findOne(entry.getKey()).orElse(null);
        if (user != null) {
            String friendsString = entry.getValue().stream()
                    .map(User::toString)
                    .collect(Collectors.joining(", "));
            return user + ": [" + friendsString + "]";
        }
        return "";
    }

    public User userLogin(String username, String password) {
        return findUser(username).orElse(null);
    };
}