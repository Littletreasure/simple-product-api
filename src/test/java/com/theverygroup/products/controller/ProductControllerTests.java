package com.theverygroup.products.controller;

import com.theverygroup.products.dto.Price;
import com.theverygroup.products.dto.Product;
import com.theverygroup.products.dto.Type;
import com.theverygroup.products.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        Price price = Price.builder()
                .value(new BigDecimal("18.99"))
                .currency("GBP")
                .build();
        Product product = Product.builder()
                .id("CLN-CDE-BOOK")
                .name("Clean Code")
                .description("Clean Code: A Handbook of Agile Software Craftsmanship (Robert C. Martin)")
                .price(price)
                .type(Type.BOOKS)
                .department("Books and Stationery")
                .weight("220g")
                .build();

        when(productRepository.findAll()).thenReturn(Arrays.asList(product));
        when(productRepository.filterById("CLN-CDE-BOOK")).thenReturn(product);
        when(productRepository.filterByType("book")).thenReturn(Arrays.asList(product));
        when(productRepository.deleteProduct("CLN-CDE-BOOK")).thenReturn(product);
        when(productRepository.addProduct(product)).thenReturn(product);
    }

    @Test
    public void testFindAll() throws Exception {
        String expected = "[{\"id\":\"CLN-CDE-BOOK\"," +
                "\"name\":\"Clean Code\"," +
                "\"description\":\"Clean Code: A Handbook of Agile Software Craftsmanship (Robert C. Martin)\"," +
                "\"price\":{\"value\":18.99,\"currency\":\"GBP\"}," +
                "\"type\":\"Book\"," +
                "\"department\":\"Books and Stationery\"," +
                "\"weight\":\"220g\"}]";

        mockMvc.perform(get("/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    public void testFilterById() throws Exception {
        String expected = "{\"id\":\"CLN-CDE-BOOK\"," +
                "\"name\":\"Clean Code\"," +
                "\"description\":\"Clean Code: A Handbook of Agile Software Craftsmanship (Robert C. Martin)\"," +
                "\"price\":{\"value\":18.99,\"currency\":\"GBP\"}," +
                "\"type\":\"Book\"," +
                "\"department\":\"Books and Stationery\"," +
                "\"weight\":\"220g\"}";

        mockMvc.perform(get("/products/{id}", "CLN-CDE-BOOK"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    public void testFilterByIdInvalid() throws Exception {
        mockMvc.perform(get("/products/{id}", "test"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFilterByType() throws Exception {
        String expected = "[{\"id\":\"CLN-CDE-BOOK\"," +
                "\"name\":\"Clean Code\"," +
                "\"description\":\"Clean Code: A Handbook of Agile Software Craftsmanship (Robert C. Martin)\","
                +
                "\"price\":{\"value\":18.99,\"currency\":\"GBP\"}," +
                "\"type\":\"Book\"," +
                "\"department\":\"Books and Stationery\"," +
                "\"weight\":\"220g\"}]";

        mockMvc.perform(get("/products/type/{type}", "book"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    public void testFilterByTypeInvalid() throws Exception {
        mockMvc.perform(get("/products/type/{type}", "test"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteProduct() throws Exception {
        String expected = "{\"id\":\"CLN-CDE-BOOK\"," +
                "\"name\":\"Clean Code\"," +
                "\"description\":\"Clean Code: A Handbook of Agile Software Craftsmanship (Robert C. Martin)\","
                +
                "\"price\":{\"value\":18.99,\"currency\":\"GBP\"}," +
                "\"type\":\"Book\"," +
                "\"department\":\"Books and Stationery\"," +
                "\"weight\":\"220g\"}";

        mockMvc.perform(delete("/products/{id}", "CLN-CDE-BOOK"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    public void testDeleteProductInvalid() throws Exception {
        mockMvc.perform(get("/products/{id}", "test"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddProduct() throws Exception {
        String expected = "{\"id\":\"CLN-CDE-BOOK\"," +
                "\"name\":\"Clean Code\"," +
                "\"description\":\"Clean Code: A Handbook of Agile Software Craftsmanship (Robert C. Martin)\","
                +
                "\"price\":{\"value\":18.99,\"currency\":\"GBP\"}," +
                "\"type\":\"Book\"," +
                "\"department\":\"Books and Stationery\"," +
                "\"weight\":\"220g\"}";

        mockMvc.perform(post("/products")
                .content(expected)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }
}
