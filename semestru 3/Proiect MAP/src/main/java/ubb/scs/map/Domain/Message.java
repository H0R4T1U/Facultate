package ubb.scs.map.Domain;


import java.time.LocalDateTime;

public class Message extends Entity<Long> {
    private final Long senderId;
    private final Long receiverId;
    private final String text;
    private final Long replyMessageId;
    private final LocalDateTime dateSent;

    public Message(Long sender, Long receiver, String text, Long replyMessage, LocalDateTime dateSent) {
        this.senderId = sender;
        this.receiverId = receiver;
        this.text = text;
        this.replyMessageId = replyMessage;
        this.dateSent = dateSent;
    }


    public Long getSender() {
        return senderId;
    }

    public Long getReceiver() {
        return receiverId;
    }

    public String getText() {
        return text;
    }

    public Long getReplyMessage() {
        return replyMessageId;
    }
    public LocalDateTime getDateSent() {
        return dateSent;
    }


}
