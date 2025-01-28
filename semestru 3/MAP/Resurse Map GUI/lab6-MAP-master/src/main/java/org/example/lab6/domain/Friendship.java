package org.example.lab6.domain;

import java.time.LocalDateTime;

public class Friendship extends Entity<Tuple<Long, Long>> {
    private LocalDateTime dateSent;

    public LocalDateTime getDateSent() {
        return dateSent;
    }

    public void setDateSent(LocalDateTime dateSent) {
        this.dateSent = dateSent;
    }
}

