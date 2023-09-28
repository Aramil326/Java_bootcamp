package edu.school21.chat.app;

import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.JdbcDataSource;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        JdbcDataSource dataSource = new JdbcDataSource();
        normalizeTable("schema.sql", dataSource);
        normalizeTable("data.sql", dataSource);
        MessagesRepository repository = new MessagesRepositoryJdbcImpl(dataSource.getDataSource());
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a message ID");
        try {
            Long id = scanner.nextLong();
            Optional<Message> message = repository.findById(id);
            if (message.isPresent()) {
                System.out.println(message.get());
            } else {
                System.err.println("No message with id=" + id);
            }
        } catch (InputMismatchException e) {
            System.err.println(e);
            System.exit(-1);
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
