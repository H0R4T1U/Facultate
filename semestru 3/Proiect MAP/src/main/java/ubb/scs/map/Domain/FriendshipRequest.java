package ubb.scs.map.Domain;

import ubb.scs.map.Utils.Constants;

import java.time.LocalDateTime;

public class FriendshipRequest extends Entity<Tuple<Long, Long>> {
    private final LocalDateTime dateSent;
    private final String status;

    public FriendshipRequest(Long sender, Long receiver, LocalDateTime dateSent, String status) {
        this.setId(new Tuple<>(sender, receiver)); // Use the Entity's setId method
        this.dateSent = dateSent;
        this.status = status;
    }

    public Long getSender() {
        return getId().getE1();
    }

    public Long getReceiver() {
        return getId().getE2();
    }

    public LocalDateTime getDateSent() {
        return dateSent;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "from " +  getSender() +
                " to " + getReceiver() +
                " on " + dateSent.format(Constants.DATE_TIME_FORMATTER) +
                " status: " + status;
    }
}
