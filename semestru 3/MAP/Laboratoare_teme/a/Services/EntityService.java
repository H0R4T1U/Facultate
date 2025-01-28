package scs.map.Services;

import scs.map.Domain.Entity;
import scs.map.Repository.Repository;

import java.util.Optional;

public class EntityService<ID, E extends Entity<ID>> {
    protected Repository<ID, E> repo;

    public EntityService(Repository<ID, E> repository) {
        this.repo = repository;
    }

    public Optional<E> create(E entity) {
        return repo.save(entity);
    }

    public Optional<E> update(E entity) {
        return repo.update(entity);
    }

    public Optional<E> delete(ID entityId) {
        return repo.delete(entityId);
    }

    public Iterable<E> getAll() {
        return repo.findAll();
    }

    public Optional<E> getById(ID entityId) {return repo.findOne(entityId);}
}
