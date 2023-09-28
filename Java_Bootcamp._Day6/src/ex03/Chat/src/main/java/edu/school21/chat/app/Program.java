package edu.school21.chat.app;

import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.JdbcDataSource;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        JdbcDataSource dataSource = new JdbcDataSource();
        normalizeTable("schema.sql", dataSource);
        normalizeTable("data.sql", dataSource);

        MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(dataSource.getDataSource());
        Optional<Message> messageOptional = messagesRepository.findById(1L);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            message.setText("Bye");
            message.setDateTime(null);
            messagesRepository.update(message);
        }
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
