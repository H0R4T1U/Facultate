package ubb.scs.map.faptebune.Service;

import ubb.scs.map.faptebune.Domain.Entity;
import ubb.scs.map.faptebune.Domain.Persoana;
import ubb.scs.map.faptebune.Repository.Repository;

import java.util.Optional;

public class PersoanaService implements EntityService<Long,Persoana> {
    Repository<Long, Persoana> repository;
    public PersoanaService(Repository<Long,Persoana> repository) {
    this.repository = repository;
    }

    @Override
    public Repository getRepo() {
        return repository;
    }

    @Override
    public Optional<Persoana> create(Persoana entity) {
        return EntityService.super.create(entity);
    }

    @Override
    public Optional<Persoana> update(Persoana entity) {
        return EntityService.super.update(entity);
    }

    @Override
    public Optional<Persoana> delete(Long entityId) {
        return EntityService.super.delete(entityId);
    }

    @Override
    public Iterable<Persoana> getAll() {
        return EntityService.super.getAll();
    }

    @Override
    public Optional<Persoana> getById(Long entityId) {
        return EntityService.super.getById(entityId);
    }

}
