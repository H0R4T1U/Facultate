package testing.motorest.repository.HibernateImplementation;

import org.hibernate.Session;
import testing.motorest.model.Player;
import testing.motorest.repository.HibernateUtils;
import testing.motorest.repository.PlayerRepository;


import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlayerHibernateRepository implements PlayerRepository {
    @Override
    public Map<Integer, Player> getPlayersByTeam(Integer teamId) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createSelectionQuery("from Player where team=:idM", Player.class)
                    .setParameter("idM", teamId)
                    .getResultList()
                    .stream()
                    .collect(Collectors.toMap(Player::getId, player -> player));
        }
    }

    @Override
    public Optional<Player> findOne(Integer id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.createSelectionQuery("from Player where id=:idM", Player.class)
                    .setParameter("idM", id)
                    .getSingleResultOrNull());
        }
    }

    @Override
    public Map<Integer,Player> findAll() {

        try( Session session=HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Player", Player.class)
                    .getResultList()
                    .stream()
                    .collect(Collectors.toMap(Player::getId, player -> player));
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
            Player message = session.createQuery("from Player where id=?1", Player.class).
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
            if (!Objects.isNull(session.find(Player.class, entity.getId()))) {
                System.out.println("In update, am gasit Player cu id-ul " + entity.getId());
                session.merge(entity);
                session.flush();
            }
        });
        HibernateUtils.closeSessionFactory();
        return Optional.empty();
    }
}
