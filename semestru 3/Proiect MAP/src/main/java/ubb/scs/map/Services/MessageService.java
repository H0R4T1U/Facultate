package ubb.scs.map.Services;

import ubb.scs.map.Domain.Message;
import ubb.scs.map.Repository.Repository;
import ubb.scs.map.Utils.observer.Observable;
import ubb.scs.map.Utils.observer.Observer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MessageService implements Observable {
    private final Repository<Long, Message> messageRepository;
    private final List<Observer> observers = new ArrayList<>();

    public MessageService(Repository<Long, Message> messageRepository ) {
        this.messageRepository = messageRepository;
    }
    public List<Message> getMessagesForConversation(Long sender, Long receiver) {
        List<Message> messages = new ArrayList<>();
        messageRepository.findAll().forEach(msg -> {
            if (msg.getSender().equals(sender) && msg.getReceiver().equals(receiver) ||
                    msg.getReceiver().equals(sender) && msg.getSender().equals(receiver)) {
                messages.add(msg);
            }
        });
        return messages;
    }

    public void addMessage(Message msg) {
        try {
            messageRepository.save(msg);
            notifyObservers();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Message> getMessageById(Long id) {
        return messageRepository.findOne(id);
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
