package scs.map.Services;

import scs.map.Domain.Friendship;
import scs.map.Domain.Tuple;
import scs.map.Repository.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FriendshipService extends EntityService<Tuple<Long,Long>, Friendship> {
    UserService userService;
    public FriendshipService(Repository<Tuple<Long,Long>, Friendship> repository,UserService userService) {
        super(repository);
        this.userService = userService;
    }
    public void deletedUser(Long id){
        List<Friendship> friendships = new ArrayList<>((Collection<Friendship>) getAll());
        friendships.forEach(friendship -> {
            if(friendship.getId().getE1().equals(id) || friendship.getId().getE2().equals(id)){
                delete(friendship.getId());
            }
        });
    }
    private static void createGraph(List<Friendship> friendships, Map<Long, List<Long>> graph) {
        if (friendships == null || friendships.isEmpty()) return;
        friendships.forEach(friendship -> {
            Long person1 = friendship.getId().getE1();
            Long person2 = friendship.getId().getE2();
            addEdge(person1, person2, graph);
            addEdge(person2, person1, graph);
        });
    }

    private static void addEdge(Long source, Long destination, Map<Long, List<Long>> graph) {
        graph.computeIfAbsent(source, _ -> new ArrayList<>()).add(destination);
    }

    private static void dfs(Long node, Set<Long> visited, Map<Long, List<Long>> graph, boolean saveComponent, List<Long> currentComponent) {
        visited.add(node);
        if (graph.containsKey(node))
            for (Long neighbor : graph.get(node))
                if (!visited.contains(neighbor))
                    dfs(neighbor, visited, graph, saveComponent, currentComponent);
        if (saveComponent)
            currentComponent.add(node);
    }
    public int getNumberOfCommunities() {
        List<Friendship> friendships = new ArrayList<>((Collection<Friendship>) getAll());
        friendships.removeIf(friendship ->
                userService.getById(friendship.getId().getE1()).isEmpty() ||
                userService.getById(friendship.getId().getE2()).isEmpty()
        );
        return noConnectedComponents(friendships);
    }

    public String getMostSocialCommunity() {
        Map<Long, List<Long>> graph = new HashMap<>();
        StringBuilder stringBuilder = new StringBuilder();
        List<Friendship> friendships = new ArrayList<>((Collection<Friendship>) getAll());
        createGraph(friendships, graph);
        List<Long> mostSocialCommunity = findLongestPath(graph);

        mostSocialCommunity.stream().map(id -> userService.getById(id))
                .filter(Optional::isPresent).
                forEach(usr -> stringBuilder.append(usr).append(", "));
//        mostSocialCommunity.forEach(id ->{
//            Optional<User> usr = userService.getById(id);
//            if(usr.isPresent()){
//                stringBuilder.append(usr).append(", ");
//            }
//        });
        return stringBuilder.toString();
    }
    private int noConnectedComponents(List<Friendship> friendships) {
        Map<Long, List<Long>> graph = new HashMap<>();
        createGraph(friendships, graph);
        Set<Long> visited = new HashSet<>();
        AtomicInteger components = new AtomicInteger();
        userService.getAll().forEach(user ->{
            if(!graph.containsKey(user.getId())){
                components.getAndIncrement();
            }
        });
        graph.keySet().stream().filter(node -> !visited.contains(node)).forEach(node -> {
            components.getAndIncrement();
            dfs(node, visited, graph, false, new ArrayList<>());
        });
        return components.get();
    }

    private List<Long> findLongestPath(Map<Long, List<Long>> graph) {
        List<Long> longestPath = new ArrayList<>();
        Set<Long> visited = new HashSet<>();
        graph.keySet().forEach(node ->{
            List<Long> currentPath = new ArrayList<>();
            dfsForLongestPath(node, visited, graph, currentPath, longestPath);
        });

        return longestPath;
    }

    private void dfsForLongestPath(Long node, Set<Long> visited, Map<Long, List<Long>> graph, List<Long> currentPath, List<Long> longestPath) {
        visited.add(node);
        currentPath.add(node);
        if (graph.containsKey(node))
            graph.get(node).stream()
                    .filter(neighbor -> !visited.contains(neighbor))
                    .forEach(neighbor -> dfsForLongestPath(neighbor, visited, graph, currentPath, longestPath));
        if (currentPath.size() > longestPath.size()) {
            longestPath.clear();
            longestPath.addAll(currentPath);
        }
        currentPath.remove(node);
    }
}
