package repository.HibernateImplementation;

import model.Team;
import org.hibernate.Session;
import repository.HibernateUtils;
import repository.TeamRepository;
import repository.TextUtils;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class TeamHibernateRepository implements TeamRepository {

    @Override
    public Optional<Team> findOne(Integer id) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.createSelectionQuery("from Team where id=:idM", Team.class)
                    .setParameter("idM", id)
                    .getSingleResultOrNull());
        }
    }

    @Override
    public Map<Integer,Team> findAll() {

        try( Session session=HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("from Team", Team.class)
                    .getResultList()
                    .stream()
                    .collect(Collectors.toMap(Team::getId, Team -> Team));
        }
    }

    @Override
    public Optional<Team> save(Team entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> session.persist(entity));
        return Optional.empty();
    }

    @Override
    public Optional<Team> delete(Integer integer) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            Team message = session.createQuery("from Team where id=?1", Team.class).
                    setParameter(1, integer).uniqueResult();
            System.out.println("In delete am gasit Teamul " + message);
            if (message != null) {
                session.remove(message);
                session.flush();
            }
        });
        return Optional.empty();
    }

    @Override
    public Optional<Team> update(Team entity) {
        HibernateUtils.getSessionFactory().inTransaction(session -> {
            if (!Objects.isNull(session.find(Team.class, entity.getId()))) {
                System.out.println("In update, am gasit Team cu id-ul " + entity.getId());
                session.merge(entity);
                session.flush();
            }
        });
        return Optional.empty();
    }

    @Override
    public Optional<Team> findTeamByName(String name) {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return Optional.ofNullable(session.createSelectionQuery("from Team where name=:nameP", Team.class)
                    .setParameter("nameP", name)
                    .getSingleResultOrNull());
        }
    }
}
