package edu.school21.chat.app;

import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.*;

import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        JdbcDataSource dataSource = new JdbcDataSource();
        normalizeTable("schema.sql", dataSource);
        normalizeTable("data.sql", dataSource);

        UsersRepository usersRepository = new UsersRepositoryJdbcImpl(dataSource.getDataSource());
        List<User> list = usersRepository.findAll(0, 10);
        for (User u : list) {
            System.out.println(u);
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
