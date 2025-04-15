package repository;



import model.User;

import java.util.Optional;

public interface UserRepository extends Repository<Integer, User> {
    Optional<User> findByUsername(String username,String password);
}
