package ubb.scs.map.avioane.Repository;


import ubb.scs.map.avioane.Domain.Validators.Validator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public class DatabaseRepository<E>{
    private final String url;
    private final String username;
    private final String password;
    protected final Validator<E> validator;

    public DatabaseRepository(String url, String username, String password, Validator<E> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }
    Connection prepareConnection() throws SQLException {
        return DriverManager.getConnection(getUrl(), getUsername(), getPassword());
    }
    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
