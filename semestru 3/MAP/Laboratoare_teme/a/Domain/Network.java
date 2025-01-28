package scs.map.Domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Network extends Entity<Long> {
    List<User> users;

    public Network() {
        this.users = new ArrayList<>();
    }
}
