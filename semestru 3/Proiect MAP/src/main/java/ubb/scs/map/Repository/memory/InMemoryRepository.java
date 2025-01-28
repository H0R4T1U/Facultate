package ubb.scs.map.Repository.memory;

import ubb.scs.map.Domain.Entity;
import ubb.scs.map.Domain.validators.ValidationException;
import ubb.scs.map.Domain.validators.Validator;
import ubb.scs.map.Repository.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID,E> {
    protected final Map<ID, E> entities;
    private final Validator<E> validator;

    public InMemoryRepository(Validator<E> validator) {
        this.entities = new HashMap<>();
        this.validator = validator;
    }

    @Override
    public Optional<E> findOne(ID id) throws IllegalArgumentException{
        if(id == null){
            throw new IllegalArgumentException("ID cannot be null");
        }
        return Optional.ofNullable(entities.get(id));

    }

    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    @Override
    public Optional<E> save(E entity) throws ValidationException {

        if(entity == null) {
            throw new IllegalArgumentException("ENTITY CANNOT BE NULL");
        }
        validator.validate(entity);
        if(entities.containsKey(entity.getId())) {
            return Optional.ofNullable(entities.get(entity.getId()));
        }
        entities.put(entity.getId(), entity);
        return Optional.empty();
    }

    @Override
    public Optional<E> delete(ID id) throws IllegalArgumentException {
        if(id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public Optional<E> update(E entity) throws ValidationException {
        if(entity == null) {
            throw new IllegalArgumentException("ENTITY CANNOT BE NULL");
        }
        validator.validate(entity);
        if(entities.containsKey(entity.getId())) {
            entities.put(entity.getId(), entity);
        }
        return Optional.of(entity);
    }
}
