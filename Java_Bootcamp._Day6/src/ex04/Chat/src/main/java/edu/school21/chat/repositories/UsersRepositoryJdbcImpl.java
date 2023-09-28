package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private final DataSource dataSource;

    public UsersRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> findAll(int page, int size) {
        String query = String.format("with users_per_page as (select * from chat.user limit %d offset %d)" +
                " select users_per_page.id as user_id, users_per_page.login as login," +
                " users_per_page.password as password, created_chats.id as created_chats_id," +
                " created_chats.name as created_chats_name, users_chats.chatroom_id as users_chats_id," +
                " cr.name as users_chats_name, chat_owner.id as users_chats_owner_id," +
                " chat_owner.login as users_chats_owner_login, chat_owner.password as users_chats_owner_password" +
                " from users_per_page" +
                " join chat.chatroom created_chats on users_per_page.id = created_chats.owner" +
                " join chat.user_chatroom users_chats on users_per_page.id = users_chats.user_id" +
                " join chat.chatroom cr on users_chats.chatroom_id = cr.id" +
                " join chat.user chat_owner on cr.owner = chat_owner.id" +
                " ORDER BY users_per_page.id", size, page * size);

        List<User> users = new ArrayList<>();

        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Long userId = resultSet.getLong("user_id");
                Long createdChatId = resultSet.getLong("created_chats_id");
                Long usedChatId = resultSet.getLong("users_chats_id");
                User user;
                Chatroom chat;

                if (users.stream().noneMatch(userInList -> userId.equals(userInList.getId()))) {
                    String login = resultSet.getString("login");
                    String password = resultSet.getString("password");
                    user = new User(userId, login, password, new ArrayList<>(), new ArrayList<>());
                    users.add(user);
                } else {
                    user = users.stream().filter(userInList -> userId.equals(userInList.getId())).collect(Collectors.toList()).get(0);
                }

                if (createdChatId != 0 && user.getCreatedRooms().stream().noneMatch(createdChats -> createdChatId.equals(createdChats.getId()))) {
                    String createdChatName = resultSet.getString("created_chats_name");
                    User creator = new User(user.getId(), user.getLogin(), user.getPassword());
                    chat = new Chatroom(createdChatId, createdChatName, creator, null);
                    user.getCreatedRooms().add(chat);
                }

                if (usedChatId != 0 && user.getUserChats().stream().noneMatch(usersChats -> usedChatId.equals(usersChats.getId()))) {
                    String userChatName = resultSet.getString("users_chats_name");
                    User creator = new User(resultSet.getLong("users_chats_owner_id"), resultSet.getString("users_chats_owner_login"), resultSet.getString("users_chats_owner_password"));
                    chat = new Chatroom(usedChatId, userChatName, creator, null);
                    user.getUserChats().add(chat);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}
