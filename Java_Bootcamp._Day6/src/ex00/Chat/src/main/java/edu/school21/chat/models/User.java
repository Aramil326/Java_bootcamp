package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class User {
    private Long id;
    private String login;
    private String password;
    private List<Chatroom> createdChatroom;
    private List<Chatroom> userChats;

    public User(Long id, String login, String password, List<Chatroom> createdChatroom, List<Chatroom> userChats) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.createdChatroom = createdChatroom;
        this.userChats = userChats;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Chatroom> getCreatedChatroom() {
        return createdChatroom;
    }

    public void setCreatedChatroom(List<Chatroom> createdChatroom) {
        this.createdChatroom = createdChatroom;
    }

    public List<Chatroom> getUserChats() {
        return userChats;
    }

    public void setUserChats(List<Chatroom> userChats) {
        this.userChats = userChats;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, createdChatroom, userChats);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        } else if (this == obj) {
            return true;
        }

        User user = (User) obj;

        return user.id.equals(this.id) && user.login.equals(this.login) && user.password.equals(this.password)
                && user.createdChatroom.equals(this.createdChatroom) && user.userChats.equals(this.userChats);
    }

    @Override
    public String toString() {
        return "id=" + id + "; login=" + login + ";password=" + password
                + ";createdChatroom=" + createdChatroom + ";userChats=" + userChats;
    }
}