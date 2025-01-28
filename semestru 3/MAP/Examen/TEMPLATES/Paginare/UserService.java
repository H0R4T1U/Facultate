package ubb.scs.map.Services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ubb.scs.map.Domain.User;
import ubb.scs.map.Domain.UserInstance;
import ubb.scs.map.Repository.Repository;
import ubb.scs.map.Repository.database.PagingRepository;
import ubb.scs.map.Utils.Paging.Page;
import ubb.scs.map.Utils.Paging.Pageable;
import ubb.scs.map.Utils.observer.Observable;
import ubb.scs.map.Utils.observer.Observer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class UserService implements EntityService<Long, User>,
        Observable {

    private final PagingRepository<Long,User> repository;
    private final List<Observer> observers = new ArrayList<>();

    public UserService(PagingRepository<Long,User> repository) {
        this.repository = repository;
    }



    public Optional<User> findByUsername(String username) {
        return StreamSupport.stream(getAll().spliterator(), false).filter(user -> user.getUsername().equals(username)).findFirst();
    }
    public boolean login(String username, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Optional<User> user = findByUsername(username);
        if (user.isPresent()) {
            if(encoder.matches(password, user.get().getPassword())) {
                UserInstance.getInstance().setId(user.get().getId());
                UserInstance.getInstance().setUsername(username);
                return true;
            }
        }
        return false;

    }
    @Override
    public Repository<Long, User> getRepo() {
        return repository;
    }

    @Override
    public Optional<User> create(User entity) {
        if(EntityService.super.create(entity).isPresent()){
            notifyObservers();
            return Optional.of(entity);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> update(User entity) {
        Optional<User> oldUser = repository.findOne(entity.getId());
        if(oldUser.isPresent()){
            Optional<User> newUser = repository.update(entity);
            if(newUser.isPresent()){
                notifyObservers();
            }
            return newUser;
        }
        return oldUser;
    }
    public Optional<User> getByUsername(String username) {
        return StreamSupport.stream(repository.findAll().spliterator(),false).filter(user -> user.getUsername().equals(username)).findFirst();

    }
    @Override
    public Optional<User> delete(Long entityId) {
        Optional<User> user = repository.delete(entityId);
        user.ifPresent(value -> notifyObservers());
        return user;
    }

    @Override
    public Iterable<User> getAll() {
        return EntityService.super.getAll();
    }


    @Override
    public Optional<User> getById(Long entityId) {
        return EntityService.super.getById(entityId);
    }

    public Page<User> findAllOnPage(Pageable pageable) {
        return repository.findAllOnPage(pageable);
    }
    @Override
    public void addObserver(Observer e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(Observer::update);
    }
}
