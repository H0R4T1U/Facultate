package ubb.scs.map.Domain;

public class UserInstance {
    private static UserInstance instance;
    private Long id;
    private String username;
    public UserInstance() {

    }

    public static UserInstance getInstance() {
        if (instance == null) {
            instance = new UserInstance();
            instance.setId(-1L);
        }
        return instance;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
