package ubb.scs.map.Services;

import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NetworkService {
    private final UserService userService;
    private final FriendshipService friendshipService;
    public NetworkService(FriendshipService friendshipService,UserService userService) {
        this.friendshipService = friendshipService;
        this.userService = userService;
    }
    public List<Set<Long>> getCommunityGraph() {
        Graph<Long, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
        userService.getAll().forEach(user -> graph.addVertex(user.getId()));
        friendshipService.getAll()
                .forEach(friendship -> graph
                        .addEdge(friendship.getId().getE1(),friendship.getId().getE2()));
        return new ConnectivityInspector<>(graph).connectedSets();
    }
    public Set<Long> biggestCommunity() {
        List<Set<Long>> communityGraph = getCommunityGraph();
        return communityGraph.stream()
                .max(Comparator.comparingInt(Set::size))
                .orElse(new HashSet<>());
    }
}
