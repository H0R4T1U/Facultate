package ubb.scs.map.faptebune.Service;

import org.w3c.dom.Entity;
import ubb.scs.map.faptebune.Domain.Nevoie;
import ubb.scs.map.faptebune.Domain.Persoana;
import ubb.scs.map.faptebune.Repository.Repository;

import java.util.Optional;

public class NevoiService implements EntityService<Long, Nevoie> {
    Repository<Long,Nevoie> repository;
    public NevoiService(Repository<Long, Nevoie> repository) {
        this.repository = repository;
    }

    @Override
    public Repository<Long, Nevoie> getRepo() {
        return repository;
    }

    @Override
    public Optional<Nevoie> create(Nevoie entity) {
        return EntityService.super.create(entity);
    }

    @Override
    public Optional<Nevoie> update(Nevoie entity) {
        return EntityService.super.update(entity);
    }

    @Override
    public Optional<Nevoie> delete(Long entityId) {
        return EntityService.super.delete(entityId);
    }

    @Override
    public Iterable<Nevoie> getAll() {
        return EntityService.super.getAll();
    }

    @Override
    public Optional<Nevoie> getById(Long entityId) {
        return EntityService.super.getById(entityId);
    }
}
