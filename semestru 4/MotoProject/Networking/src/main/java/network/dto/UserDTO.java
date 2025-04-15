package network.dto;

import java.io.Serializable;


public class UserDTO implements Serializable{
    private String username;
    private String passwd;

    public UserDTO(String id) {
        this(id,"");
    }

    public UserDTO(String id, String passwd) {
        this.username = id;
        this.passwd = passwd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String id) {
        this.username = id;
    }

    public String getPasswd() {
        return passwd;
    }

    @Override
    public String toString(){
        return "UserDTO["+username+' '+passwd+"]";
    }
}
