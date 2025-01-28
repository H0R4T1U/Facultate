package org.example.lab6.repository;

import org.example.lab6.domain.Entity;
import org.example.lab6.domain.validators.Validator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

public abstract class AbstractDBRepo<ID, E extends Entity<ID>> implements Repository<ID, E> {
    private final String url;
    private final String username;
    private final String password;
    protected final Validator<E> validator;

    public AbstractDBRepo(String url, String username, String password, Validator<E> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }

    @Override
    public abstract Optional<E> findOne(ID id);

    @Override
    public abstract Iterable<E> findAll();

    @Override
    public abstract Optional<E> save(E entity);

    @Override
    public abstract Optional<E> delete(ID id);

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    protected Connection prepareConnection() throws SQLException {
        return DriverManager.getConnection(getUrl(), getUsername(), getPassword());
    }
}
