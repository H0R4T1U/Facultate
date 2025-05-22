package testing.motorest.repository.HibernateImplementation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import testing.motorest.model.Race;
import testing.motorest.repository.HibernateUtils;
import testing.motorest.repository.RaceRepository;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
@Repository
public class RaceHibernateRepository implements RaceRepository {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Optional<Race> findOne(Integer rID) {
        logger.traceEntry("findOne race with id {}", rID);
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Race race = session.get(Race.class, rID);
            if (race != null) {
                logger.traceExit("Race found in Database!");
                return Optional.of(race);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        logger.traceExit("Race not found");
        return Optional.empty();
    }

    @Override
    public Map<Integer, Race> findAll() {
        logger.traceEntry("Finding all races");
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            List<Race> races = session.createQuery("from Race", Race.class).list();
            Map<Integer, Race> raceMap = races.stream()
                    .collect(Collectors.toMap(Race::getId, race -> race));
            logger.traceExit("found {} races", raceMap.size());
            return raceMap;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Race> save(Race race) {
        logger.traceEntry("saving race {}", race);
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(race);
            transaction.commit();
            logger.traceExit("Race saved with id {}", race.getId());
            return Optional.of(race);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Race> delete(Integer raceId) {
        logger.traceEntry("deleting race with id {}", raceId);
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Race race = session.get(Race.class, raceId);
            if (race != null) {
                transaction = session.beginTransaction();
                session.remove(race);
                transaction.commit();
                logger.traceExit("Race deleted with id {}", raceId);
                return Optional.of(race);
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        logger.traceExit("Race not Deleted");
        return Optional.empty();
    }

    @Override
    public Optional<Race> update(Race race) {
        logger.traceEntry("updating race {}", race);
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(race);
            transaction.commit();
            logger.traceExit("Race updated with id {}", race.getId());
            return Optional.of(race);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}