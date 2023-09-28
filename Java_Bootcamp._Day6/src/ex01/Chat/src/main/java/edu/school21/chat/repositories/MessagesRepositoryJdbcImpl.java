package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private DataSource dataSource;

    public MessagesRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Optional<Message> findById(Long id) {
        String query = "SELECT * FROM chat.message WHERE id = " + id;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
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

    private User getUser(Long id) {
        String query = "SELECT * FROM chat.user WHERE id = " + id;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
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
        String query = "SELECT * FROM chat.chatroom WHERE id = " + id;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            ResultSet chatroomFromDB = connection.createStatement().executeQuery(query);
            if (!chatroomFromDB.next()) {
                return null;
            }
            return new Chatroom(id, chatroomFromDB.getString(2));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
