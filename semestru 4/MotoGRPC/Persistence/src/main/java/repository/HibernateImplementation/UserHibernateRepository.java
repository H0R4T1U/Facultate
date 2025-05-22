package repository.HibernateImplementation;

import model.User;
import repository.HibernateUtils;
import repository.TextUtils;
import repository.UserRepository;
import org.hibernate.Session;
import repository.UserRepository;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserHibernateRepository implements UserRepository {


    @Override
    public Optional<User> findByUsername(String username, String password) {
        password = TextUtils.simpleEncode(password);
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.createSelectionQuery("from User where username=:usernameP and password=:passwordP", User.class)
                    .setParameter("usernameP", username)
                    .setParameter("passwordP", password)
                    .getSingleResultOrNull());
        }
    }

    @Override
    public Optional<User> findOne(Integer id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.createSelectionQuery("from User where id=:idM", User.class)
                    .setParameter("idM", id)
                    .getSingleResultOrNull());
        }
    }

    @Override
    public Map<Integer,User> findAll() {

        try( Session session=HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class)
                    .getResultList()
                    .stream()
                    .collect(Collectors.toMap(model.User::getId, user -> user));
        }
    }

    @Override
    public Optional<User> save(User entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(entity));
        return Optional.empty();
    }

    @Override
    public Optional<User> delete(Integer integer) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            User message = session.createQuery("from model.User where id=?1", User.class).
                    setParameter(1, integer).uniqueResult();
            System.out.println("In delete am gasit Userul " + message);
            if (message != null) {
                session.remove(message);
                session.flush();
            }
        });
        return Optional.empty();
    }

    @Override
    public Optional<User> update(User entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            if (!Objects.isNull(session.find(model.User.class, entity.getId()))) {
                System.out.println("In update, am gasit User cu id-ul " + entity.getId());
                session.merge(entity);
                session.flush();
            }
        });
        return Optional.empty();
    }
}
