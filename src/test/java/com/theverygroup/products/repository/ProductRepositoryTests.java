package com.theverygroup.products.repository;

import com.theverygroup.products.dto.Price;
import com.theverygroup.products.dto.Product;
import com.theverygroup.products.dto.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductRepositoryTests {

    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        productRepository = new ProductRepository();
    }

    @Test
    public void testFindAll_containsElement() {
        // Given

        // When
        List<Product> products = productRepository.findAll();

        // Then
        Price price = Price.builder()
                .value(new BigDecimal("18.99"))
                .currency("GBP")
                .build();
        Product expected = Product.builder()
                .id("CLN-CDE-BOOK")
                .name("Clean Code")
                .description("Clean Code: A Handbook of Agile Software Craftsmanship (Robert C. Martin)")
                .price(price)
                .type(Type.BOOKS)
                .department("Books and Stationery")
                .weight("220g")
                .build();

        assertThat(products).contains(expected);
    }

    @Test
    public void testFilterById_returnsProduct() {
        Product product = productRepository.filterById("CLN-CDE-BOOK");
        Price price = Price.builder()
                .value(new BigDecimal("18.99"))
                .currency("GBP")
                .build();
        Product expected = Product.builder()
                .id("CLN-CDE-BOOK")
                .name("Clean Code")
                .description("Clean Code: A Handbook of Agile Software Craftsmanship (Robert C. Martin)")
                .price(price)
                .type(Type.BOOKS)
                .department("Books and Stationery")
                .weight("220g")
                .build();
        assertThat(product).isEqualTo(expected);
    }

    @Test
    public void testFilterById_returns_null() {
        Product product = productRepository.filterById("test");

        assertThat(product).isEqualTo(null);
    }

    @Test
    public void testFilterByType_returnsProducts() {
        List<Product> products = productRepository.filterByType("book");
        Price price = Price.builder()
                .value(new BigDecimal("18.99"))
                .currency("GBP")
                .build();
        Product expected = Product.builder()
                .id("CLN-CDE-BOOK")
                .name("Clean Code")
                .description("Clean Code: A Handbook of Agile Software Craftsmanship (Robert C. Martin)")
                .price(price)
                .type(Type.BOOKS)
                .department("Books and Stationery")
                .weight("220g")
                .build();

        assertThat(products).contains(expected);
        assertThat(products.size()).isEqualTo(2);
    }

    @Test
    public void testFilterByType_returnsEmptyList() {
        List<Product> products = productRepository.filterByType("fish");

        assertThat(products.size()).isEqualTo(0);
    }

    @Test
    public void testDeleteProduct_deletesProduct() {
        int productRepositorySizeBefore = productRepository.findAll().size();
        Product product = productRepository.deleteProduct("CLN-CDE-BOOK");
        Price price = Price.builder()
                .value(new BigDecimal("18.99"))
                .currency("GBP")
                .build();
        Product expected = Product.builder()
                .id("CLN-CDE-BOOK")
                .name("Clean Code")
                .description("Clean Code: A Handbook of Agile Software Craftsmanship (Robert C. Martin)")
                .price(price)
                .type(Type.BOOKS)
                .department("Books and Stationery")
                .weight("220g")
                .build();
        int productRepositorySizeAfter = productRepository.findAll().size();

        assertThat(product).isEqualTo(expected);
        assertThat(productRepositorySizeAfter).isEqualTo(productRepositorySizeBefore - 1);
    }

    @Test
    public void testDeleteProduct_returnsNull() {
        Product product = productRepository.deleteProduct("test");

        assertThat(product).isEqualTo(null);
    }

    @Test
    public void testAddProduct_addsProduct() {
        int productRepositorySizeBefore = productRepository.findAll().size();
        Price price = Price.builder()
                .value(new BigDecimal("1.99"))
                .currency("GBP")
                .build();
        Product newProduct = Product.builder()
                .id("Bottle")
                .name("Water bottle")
                .description("Blue water bottle with white spots")
                .price(price)
                .type(Type.CASUAL)
                .department("Home")
                .weight("220g")
                .build();

        Product product = productRepository.addProduct(newProduct);
        int productRepositorySizeAfter = productRepository.findAll().size();

        assertThat(product).isEqualTo(newProduct);
        assertThat(productRepositorySizeAfter).isEqualTo(productRepositorySizeBefore + 1);
    }

    @Test
    public void testAddProduct_returnsNull() {
        int productRepositorySizeBefore = productRepository.findAll().size();
        Price price = Price.builder()
                .value(new BigDecimal("18.99"))
                .currency("GBP")
                .build();
        Product newProduct = Product.builder()
                .id("CLN-CDE-BOOK")
                .name("Clean Code")
                .description("Clean Code: A Handbook of Agile Software Craftsmanship (Robert C. Martin)")
                .price(price)
                .type(Type.BOOKS)
                .department("Books and Stationery")
                .weight("220g")
                .build();

        Product product = productRepository.addProduct(newProduct);
        int productRepositorySizeAfter = productRepository.findAll().size();

        assertThat(product).isEqualTo(null);
        assertThat(productRepositorySizeAfter).isEqualTo(productRepositorySizeBefore);
    }
}