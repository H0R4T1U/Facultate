package scs.map.Repository.file;

import scs.map.Domain.Entity;
import scs.map.Domain.validators.Validator;
import scs.map.Repository.memory.InMemoryRepository;

import java.io.*;
import java.util.Optional;

public abstract class AbstractFileRepository<ID, E extends Entity<ID>> extends InMemoryRepository<ID, E> {
    private final String filename;

    public AbstractFileRepository(Validator<E> validator, String fileName) {
        super(validator);
        filename=fileName;
        loadData();
    }


    public abstract E createEntity(String line);
    public abstract String saveEntity(E entity);

    @Override
    public Optional<E> save(E entity) {
        Optional<E> e = super.save(entity);
        if (e.isEmpty())
            writeToFile();
        return e;
    }

    private void writeToFile() {

        try  ( BufferedWriter writer = new BufferedWriter(new FileWriter(filename))){
            // Maps entity values to the string needed to be saved
            entities.values().stream().map(this::saveEntity).forEach(ent -> {
                try {
                    writer.write(ent);
                    writer.newLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadData(){
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                E entity = createEntity(line);
                super.save(entity);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<E> delete(ID id) {
        Optional<E> entity = super.delete(id);
        if (entity.isPresent()) writeToFile();
        return entity;
    }

    @Override
    public Optional<E> update(E entity) {
        Optional<E> entity2 = super.update(entity);
        if (entity2.isEmpty()) writeToFile();
        return entity2;
    }
}
