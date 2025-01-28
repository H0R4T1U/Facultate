package org.example.lab6.service;

import org.example.lab6.domain.Friendship;
import org.example.lab6.domain.Tuple;
import org.example.lab6.domain.User;
import org.example.lab6.repository.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FriendshipService {
    private final Repository<Tuple<Long, Long>, Friendship> friendshipRepository;
    private final Repository<Long, User> userRepository;

    public FriendshipService(Repository<Tuple<Long, Long>, Friendship> friendshipRepository, Repository<Long, User> userRepository) {
        this.friendshipRepository = friendshipRepository;
        this.userRepository = userRepository;
    }

    public void addFriendship(Long id1, Long id2) {
        validateUsersExist(id1, id2);
        validateFriendshipNotExists(id1, id2);
        User user1 = userRepository.findOne(id1).get();
        User user2 = userRepository.findOne(id2).get();
        user1.addFriend(user2);
        user2.addFriend(user1);
        saveFriendship(user1, user2);
    }

    private void validateUsersExist(Long id1, Long id2) {
        if (Objects.equals(id1, id2))
            throw new ServiceException("You can't befriend yourself!");
        if (userRepository.findOne(id1).isEmpty() || userRepository.findOne(id2).isEmpty())
            throw new ServiceException("One or both users do not exist.");
    }

    private void validateFriendshipNotExists(Long id1, Long id2) {
        if (friendshipRepository.findOne(new Tuple<>(id1, id2)).isPresent() ||
                friendshipRepository.findOne(new Tuple<>(id2, id1)).isPresent())
            throw new ServiceException("Those users are already friends!");
    }

    private void saveFriendship(User user1, User user2) {
        Friendship friendship = new Friendship();
        friendship.setDateSent(LocalDateTime.now());
        friendship.setId(new Tuple<>(user1.getId(), user2.getId()));
        friendshipRepository.save(friendship);
    }

    public void removeFriendship(Long id1, Long id2) {
        Optional<User> user1Opt = userRepository.findOne(id1);
        Optional<User> user2Opt = userRepository.findOne(id2);
        if (user1Opt.isPresent() && user2Opt.isPresent()) {
            User user1 = user1Opt.get();
            User user2 = user2Opt.get();
            removeFriends(user1, user2);
            if (friendshipRepository.delete(new Tuple<>(user1.getId(), user2.getId())).isEmpty())
                throw new ServiceException("Those users aren't friends!");
        } else
            throw new ServiceException("That friendship doesn't exist!");
    }

    private void removeFriends(User user1, User user2) {
        user1.removeFriend(user2);
        user2.removeFriend(user1);
    }

    public List<Friendship> getFriendshipList() {
        return (List<Friendship>) friendshipRepository.findAll();
    }

    public String getMostSocialCommunity() {
        Map<Long, List<Long>> graph = createGraph(getFriendshipList());
        List<Long> mostSocialCommunity = findLongestPath(graph);
        return formatCommunity(mostSocialCommunity);
    }

    private Map<Long, List<Long>> createGraph(List<Friendship> friendships) {
        Map<Long, List<Long>> graph = new HashMap<>();
        if (friendships != null && !friendships.isEmpty()) {
            friendships.forEach(friendship -> {
                Long person1 = friendship.getId().getLeft();
                Long person2 = friendship.getId().getRight();
                addEdge(graph, person1, person2);
                addEdge(graph, person2, person1);
            });
        }
        return graph;
    }

    private void addEdge(Map<Long, List<Long>> graph, Long source, Long destination) {
        graph.computeIfAbsent(source, k -> new ArrayList<>()).add(destination);
    }

    private String formatCommunity(List<Long> community) {
        StringBuilder stringBuilder = new StringBuilder();
        community.forEach(id -> userRepository.findOne(id).ifPresent(user -> stringBuilder.append(user).append(", ")));
        if (!stringBuilder.isEmpty())
            stringBuilder.setLength(stringBuilder.length() - 2);
        return stringBuilder.toString();
    }

    private List<Long> findLongestPath(Map<Long, List<Long>> graph) {
        List<Long> longestPath = new ArrayList<>();
        Set<Long> visited = new HashSet<>();
        graph.keySet().forEach(node -> {
            List<Long> currentPath = new ArrayList<>();
            dfsForLongestPath(node, visited, graph, currentPath, longestPath);
            visited.clear();
        });
        return longestPath;
    }

    private void dfsForLongestPath(Long node, Set<Long> visited, Map<Long, List<Long>> graph, List<Long> currentPath, List<Long> longestPath) {
        visited.add(node);
        currentPath.add(node);
        if (graph.containsKey(node))
            graph.get(node).forEach(neighbor -> {
                if (!visited.contains(neighbor))
                    dfsForLongestPath(neighbor, visited, graph, currentPath, longestPath);
            });
        updateLongestPath(currentPath, longestPath);
        currentPath.removeLast();
        visited.remove(node);
    }

    private void updateLongestPath(List<Long> currentPath, List<Long> longestPath) {
        if (currentPath.size() > longestPath.size()) {
            longestPath.clear();
            longestPath.addAll(currentPath);
        }
    }

    public int getNumberOfCommunities() {
        Map<Long, List<Long>> graph = createGraph(getFriendshipList());
        Set<Long> visited = new HashSet<>();
        AtomicInteger componentCount = new AtomicInteger();
        userRepository.findAll().forEach(user -> {
            if (!graph.containsKey(user.getId()))
                componentCount.getAndIncrement();
        });
        graph.keySet().forEach(node -> {
            if (!visited.contains(node)) {
                componentCount.getAndIncrement();
                depthFirstSearch(node, visited, graph);
            }
        });
        return componentCount.get();
    }

    private void depthFirstSearch(Long node, Set<Long> visited, Map<Long, List<Long>> graph) {
        visited.add(node);
        if (graph.containsKey(node))
            graph.get(node).forEach(neighbor -> {
                if (!visited.contains(neighbor))
                    depthFirstSearch(neighbor, visited, graph);
            });
    }

    public Friendship findFriendshipByUsername(String username) {
        Iterable<Friendship> list = friendshipRepository.findAll();
        for (Friendship friendship : list) {
            Optional<User> user1 = userRepository.findOne(friendship.getId().getLeft());
            Optional<User> user2 = userRepository.findOne(friendship.getId().getRight());
            if (friendship.getId().getLeft().equals(user1.get().getId()) && friendship.getId().getRight().equals(user2.get().getId()) ||
                    friendship.getId().getRight().equals(user1.get().getId()) && friendship.getId().getLeft().equals(user2.get().getId()))
                return friendship;
        }
        return null;
    }
}