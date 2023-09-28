package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductsRepositoryJdbcImplTest {
    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = Arrays.asList(
            new Product(1L, "egg", 150L),
            new Product(2L, "apple", 50L),
            new Product(3L, "orange", 200L),
            new Product(4L, "cucumber", 100L),
            new Product(5L, "banana", 170L),
            new Product(6L, "tomatoes", 250L));
    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(5L, "banana", 170L);
    final Product EXPECTED_UPDATED_PRODUCT = new Product(5L, "gun", 1157L);
    final Product EXPECTED_SAVE_PRODUCT = new Product(7L, "flowers", 1234L);
    private final Long findId = 5L;
    private EmbeddedDatabase dataSource;
    private ProductsRepository productsRepository;

    @BeforeEach
    void init() {
        dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).addScripts("/schema.sql", "/data.sql").build();
        productsRepository = new ProductsRepositoryJdbcImpl(dataSource);
    }

    @Test
    void findALLTest() {
        List<Product> response = productsRepository.findAll();
        assertEquals(EXPECTED_FIND_ALL_PRODUCTS, response);
    }

    @Test
    void findByIdTest() {
        Product actual = productsRepository.findById(findId).get();
        assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, actual);
    }

    @Test
    void updateTest() {
        Product product = new Product(5L, "gun", 1157L);
        productsRepository.update(product);
        Product actual = productsRepository.findById(5L).get();
        assertEquals(EXPECTED_UPDATED_PRODUCT, actual);
    }

    @Test
    void saveTest() {
        Product product = new Product("flowers", 1234L);
        productsRepository.save(product);
        Product actual = productsRepository.findById(7L).get();
        assertEquals(EXPECTED_SAVE_PRODUCT, actual);
    }

    @Test
    void deleteTest() {
        productsRepository.delete(1L);
        assertFalse(productsRepository.findById(1L).isPresent());
    }

    @AfterEach
    void close() {
        dataSource.shutdown();
    }
}
