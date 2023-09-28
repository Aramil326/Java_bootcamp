package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    private final DataSource dataSource;

    public ProductsRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll() {
        String query = "select * from shop.product";
        List<Product> products = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                products.add(new Product(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getLong("price")));
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    @Override
    public Optional<Product> findById(Long id) {
        String query = String.format("select * from shop.product where id = %d", id);
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            connection.close();
            if (resultSet.next()) {
                return Optional.of(new Product(resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getLong("price")));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Product product) {
        String query = String.format("update shop.product set name = '%s', price = %d where id = %d", product.getName(), product.getPrice(), product.getId());
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Product product) {
        String query = String.format("insert into shop.product (name, price) values ('%s', %d)", product.getName(), product.getPrice());
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        String query = String.format("delete from shop.product where id = %d", id);
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.execute(query);
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
