package org.example.lab6.service;

public interface Service<E> {
    String getEntities();

    E removeEntity(Long id);
}

