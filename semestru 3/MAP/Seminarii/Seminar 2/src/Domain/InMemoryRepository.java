package Domain;

import java.util.HashMap;
import java.util.Map;

public class InMemoryRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {
    private Map<ID, E> entities;
    @Override
    public E findOne(ID id) {
        return null;
    }

    public InMemoryRepository() {
        this.entities = new HashMap<>();
    }

    @Override
    public Iterable<E> findAll() {
        return null;
    }

    @Override
    public E save(E entity) {
        entities.put(entity.getId(), entity);
        return null;
    }

    @Override
    public E delete(ID id) {
        return null;
    }

    @Override
    public E update(E entity) {
        return null;
    }
}
