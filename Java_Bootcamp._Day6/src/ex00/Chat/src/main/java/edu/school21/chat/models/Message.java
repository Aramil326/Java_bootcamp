package edu.school21.chat.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message {
    private Long id;
    private User author;
    private Chatroom room;
    private String text;
    private LocalDateTime localDateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Chatroom getRoom() {
        return room;
    }

    public void setRoom(Chatroom room) {
        this.room = room;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, room, text, localDateTime);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        } else if (this == obj) {
            return true;
        }

        Message user = (Message) obj;

        return user.id.equals(this.id) && user.author.equals(this.author) && user.room.equals(this.room)
                && user.text.equals(this.text) && user.localDateTime.equals(this.localDateTime);
    }

    @Override
    public String toString() {
        return "id=" + id + "; author=" + author + ";room=" + room
                + ";text=" + text + ";localDateTime=" + localDateTime;
    }
}
