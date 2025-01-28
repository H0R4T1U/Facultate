package ubb.scs.map.Facades;

import ubb.scs.map.Domain.*;
import ubb.scs.map.Domain.validators.FriendshipRequestValidator;
import ubb.scs.map.Domain.validators.FriendshipValidator;
import ubb.scs.map.Domain.validators.MessageValidator;
import ubb.scs.map.Domain.validators.UserValidator;
import ubb.scs.map.Repository.Repository;
import ubb.scs.map.Repository.database.*;
import ubb.scs.map.Services.Configs.ApplicationContext;

public class RepositoryFacade {
    private final PagingRepository<Long, User> userRepository;
    private final PagingRepository<Tuple<Long, Long>, Friendship> friendshipRepository;
    private final Repository<Tuple<Long, Long>, FriendshipRequest> friendshipRequestRepository;
    private final Repository<Long, Message> messageRepository;
    private static RepositoryFacade instance = null;

    private RepositoryFacade() {
        // Creates repositories and connects to datbase
        userRepository = new UserDatabaseRepository(
                ApplicationContext.getPROPERTIES().getProperty("DB_URL"),
                ApplicationContext.getPROPERTIES().getProperty("DB_USERNAME"),
                ApplicationContext.getPROPERTIES().getProperty("DB_PASSWORD"),
                new UserValidator());
        friendshipRepository = new FriendshipDatabaseRepository(
                ApplicationContext.getPROPERTIES().getProperty("DB_URL"),
                ApplicationContext.getPROPERTIES().getProperty("DB_USERNAME"),
                ApplicationContext.getPROPERTIES().getProperty("DB_PASSWORD"),
                new FriendshipValidator());
        friendshipRequestRepository = new FriendshipRequestDatabaseRepository(
                ApplicationContext.getPROPERTIES().getProperty("DB_URL"),
                ApplicationContext.getPROPERTIES().getProperty("DB_USERNAME"),
                ApplicationContext.getPROPERTIES().getProperty("DB_PASSWORD"),
                new FriendshipRequestValidator());
        messageRepository = new MessageDatabaseRepository(
                ApplicationContext.getPROPERTIES().getProperty("DB_URL"),
                ApplicationContext.getPROPERTIES().getProperty("DB_USERNAME"),
                ApplicationContext.getPROPERTIES().getProperty("DB_PASSWORD"),
                new MessageValidator()
        );

    }
    public static RepositoryFacade getInstance() {
        if (instance == null) {
            instance = new RepositoryFacade();
        }
        return instance;
    }

    public PagingRepository<Long, User> getUserRepository() {
        return userRepository;
    }

    public PagingRepository<Tuple<Long, Long>, Friendship> getFriendshipRepository() {
        return friendshipRepository;
    }

    public Repository<Tuple<Long, Long>, FriendshipRequest> getFriendshipRequestRepository() {
        return friendshipRequestRepository;
    }

    public Repository<Long, Message> getMessageRepository() {
        return messageRepository;
    }
}
