package edu.school21.chat.app;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.JdbcDataSource;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        JdbcDataSource dataSource = new JdbcDataSource();
        normalizeTable("schema.sql", dataSource);
        normalizeTable("data.sql", dataSource);

        User creator = new User(3L, "user", "user", new ArrayList<>(), new ArrayList<>());
        User author = creator;
        Chatroom room = new Chatroom(1L, "room", creator, new ArrayList<>());
        Message message = new Message(null, author, room, "hello", LocalDateTime.now());


        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource.getDataSource());
        messagesRepository.save(message);
        System.out.println(message.getId());
    }

    private static void normalizeTable(String file, JdbcDataSource dataSource) {
        try {
            Statement statement = dataSource.getConnection().createStatement();
            InputStream input = Program.class.getClassLoader().getResourceAsStream(file);
            assert input != null;
            Scanner scanner = new Scanner(input).useDelimiter(";");
            while (scanner.hasNext()) {
                statement.executeUpdate(scanner.next().trim());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
