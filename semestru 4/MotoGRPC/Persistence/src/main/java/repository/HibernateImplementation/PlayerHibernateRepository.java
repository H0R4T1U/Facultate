package repository.HibernateImplementation;

import model.Player;
import repository.HibernateUtils;
import repository.PlayerRepository;
import org.hibernate.Session;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlayerHibernateRepository implements PlayerRepository {
    @Override
    public Map<Integer, Player> getPlayersByTeam(Integer teamId) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from model.Player where team=:idM", model.Player.class)
                    .setParameter("idM", teamId)
                    .getResultList()
                    .stream()
                    .collect(Collectors.toMap(model.Player::getId, player -> player));
        }
    }

    @Override
    public Optional<Player> findOne(Integer id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.createSelectionQuery("from model.Player where id=:idM", model.Player.class)
                    .setParameter("idM", id)
                    .getSingleResultOrNull());
        }
    }

    @Override
    public Map<Integer,Player> findAll() {

        try( Session session=HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Player", model.Player.class)
                    .getResultList()
                    .stream()
                    .collect(Collectors.toMap(model.Player::getId, player -> player));
        }
    }

    @Override
    public Optional<Player> save(Player entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            if (entity.getId() == null) {
                session.persist(entity);  // new entity
            } else {
                session.merge(entity);    // existing entity
            }
        });
        HibernateUtils.closeSessionFactory();
        return Optional.empty();
    }

    @Override
    public Optional<Player> delete(Integer integer) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Player message = session.createQuery("from model.Player where id=?1", Player.class).
                    setParameter(1, integer).uniqueResult();
            System.out.println("In delete am gasit Playerul " + message);
            if (message != null) {
                session.remove(message);
                session.flush();
            }
        });
        HibernateUtils.closeSessionFactory();
        return Optional.empty();
    }

    @Override
    public Optional<Player> update(Player entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            if (!Objects.isNull(session.find(model.Player.class, entity.getId()))) {
                System.out.println("In update, am gasit Player cu id-ul " + entity.getId());
                session.merge(entity);
                session.flush();
            }
        });
        HibernateUtils.closeSessionFactory();
        return Optional.empty();
    }
}
