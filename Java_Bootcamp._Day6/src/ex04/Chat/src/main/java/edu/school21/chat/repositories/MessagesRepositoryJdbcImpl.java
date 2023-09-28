package edu.school21.chat.repositories;

import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private final DataSource dataSource;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) {
        String query = "SELECT * FROM chat.message WHERE chat.message.id = " + id;
        try {
            Statement statement = getStatement();
            ResultSet messageFromDB = statement.executeQuery(query);
            if (!messageFromDB.next()) {
                return Optional.empty();
            }
            return Optional.of(new Message(id, getUser(messageFromDB.getLong(2))
                    , getChatroom(messageFromDB.getLong(3)), messageFromDB.getString(4)
                    , messageFromDB.getTimestamp(5).toLocalDateTime()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Message message) {
        checkMessage(message);
        checkUser(message.getAuthor());
        checkRoom(message.getRoom());

        String ch = "'";
        String timestamp = (message.getDateTime() == null) ? "null" :
                ("TIMESTAMP " + ch + Timestamp.valueOf(message.getDateTime()) + ch);

        String query = String.format(
                "INSERT INTO chat.message (author, room, text, date_time) VALUES (%d, %d, %s, %s) RETURNING id"
                , message.getAuthor().getId(), message.getRoom().getId(), message.getText(), timestamp);

        Statement statement = getStatement();
        try {
            ResultSet response = statement.executeQuery(query);
            if (response.next()) {
                message.setId(response.getLong(1));
            } else {
                throw new NotSavedSubEntityException("Interrupted");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void checkMessage(Message message) {
        if (message.getAuthor() == null || message.getAuthor().getId() == null) {
            throw new NotSavedSubEntityException("Author does not exist");
        }
        if (message.getRoom() == null || message.getRoom().getId() == null) {
            throw new NotSavedSubEntityException("Chat does not exist");
        }
        if (message.getRoom().getCreator() == null || message.getRoom().getCreator().getId() == null) {
            throw new NotSavedSubEntityException("Chat author does not exist");
        }
        if (message.getText() == null) {
            throw new NotSavedSubEntityException("Message cannot be empty");
        }
    }

    private void checkUser(User user) {
        Long id = user.getId();
        String query = "SELECT * FROM chat.message WHERE id = " + id;
        String exception = "User with id=" + id + " does not exist";
        checkFieldsInDatabase(query, exception);
    }

    private void checkRoom(Chatroom room) {
        Long id = room.getId();
        String query = "SELECT * FROM chat.message WHERE id = " + id;
        String exception = "Chatroom with id=" + id + " does not exist";
        checkFieldsInDatabase(query, exception);
    }

    private void checkFieldsInDatabase(String query, String exception) {
        Statement statement = getStatement();
        try {
            if (!statement.executeQuery(query).next()) {
                throw new NotSavedSubEntityException(exception);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Statement getStatement() {
        Connection connection;
        try {
            connection = dataSource.getConnection();
            return connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Message message) {
        String id = message.getId() == null ? "null" : message.getId().toString();
        String author = message.getAuthor() == null ? "null" : message.getAuthor().getId().toString();
        String room = message.getRoom() == null ? "null" : message.getRoom().getId().toString();
        String text = message.getText() == null ? "null" : message.getText();
        String dateTime = message.getDateTime() == null ? "null" : message.getDateTime().toString();
        String ch = "'";
        String query = "UPDATE chat.message SET "
                + "id = " + id
                + ", author = " + author
                + ", room = " + room
                + ", text = " + ch + text + ch
                + ", date_time = " + dateTime
                + " WHERE id = " + id;

        try {
            Statement statement = getStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User getUser(Long id) {
        String query = String.format("SELECT * FROM chat.user WHERE id = %d", id);
        try {
            Statement statement = getStatement();
            ResultSet userFromDB = statement.executeQuery(query);
            if (!userFromDB.next()) {
                return null;
            }
            return new User(id, userFromDB.getString("login"), userFromDB.getString("password"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Chatroom getChatroom(Long id) {
        String query = String.format("SELECT * FROM chat.chatroom WHERE id = %d", id);
        try {
            Statement statement = getStatement();
            ResultSet chatroomFromDB = statement.executeQuery(query);
            if (!chatroomFromDB.next()) {
                return null;
            }
            return new Chatroom(id, chatroomFromDB.getString(2));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
