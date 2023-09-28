package edu.school21.chat.models;

import java.util.Objects;
import java.util.List;

public class Chatroom {
    private Long id;
    private String name;
    private User creator;
    private List<Message> messages;

    public Chatroom(Long id, String name, User creator, List<Message> messages) {
        this.id = id;
        this.name = name;
        this.creator = creator;
        this.messages = messages;
    }

    public Chatroom(Long id, String title) {
        this.id = id;
        this.name = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, creator, messages);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        } else if (this == obj) {
            return true;
        }

        Chatroom user = (Chatroom) obj;

        return user.id.equals(this.id) && user.name.equals(this.name) && user.creator.equals(this.creator)
                && user.messages.equals(this.messages);
    }

    @Override
    public String toString() {
        return "{id=" + id + ",name=“" + name + "“,creator=" + creator
                + ",messages=" + messages + "}";
    }
}
