package model;

import Utils.Util;

import java.time.LocalDateTime;

public class MessageTask extends Task {
    private String message;
    private String from;
    private String to;
    LocalDateTime date;

    public MessageTask(String id, String desc,String message, String from, String to, LocalDateTime date) {
        super(id, desc);
        this.message = message;
        this.from = from;
        this.to = to;
        this.date = date;
    }

    @Override
    public String toString() {

        return super.toString() + "\n MessageTask{" +
                "message='" + message + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", date=" + date.format(Util.formatter) +
                '}';
    }

    @Override
    public void execute() {
        System.out.println("MessageTask executed! "+ date.format(Util.formatter) + toString() + "\n");
    }
}
