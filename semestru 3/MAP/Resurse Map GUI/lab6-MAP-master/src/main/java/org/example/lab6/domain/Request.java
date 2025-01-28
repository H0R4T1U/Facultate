package org.example.lab6.domain;

import java.time.LocalDateTime;

public class Request extends Entity<Tuple<Long, Long>> {
    private final LocalDateTime dateSent;
    private final String status;

    public Request(Long sender, Long receiver, LocalDateTime dateSent, String status) {
        this.setId(new Tuple<>(sender, receiver)); // Use the Entity's setId method
        this.dateSent = dateSent;
        this.status = status;
    }

    public Long getSender() {
        return getId().getLeft();
    }

    public Long getReceiver() {
        return getId().getRight();
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
                " on " + dateSent +
                " status: " + status;
    }
}
