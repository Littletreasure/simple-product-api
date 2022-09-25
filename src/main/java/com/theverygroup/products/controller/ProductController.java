package com.theverygroup.products.controller;

import com.theverygroup.products.dto.Product;
import com.theverygroup.products.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> findAll() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> filterById(@PathVariable("id") String id) {
        Product product = productRepository.filterById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        } else return ResponseEntity.ok(product);
    }

    @GetMapping("/products/type/{type}")
    public ResponseEntity<List<Product>> filterByType(@PathVariable("type") String type) {
        List<Product> products = productRepository.filterByType(type);
        if (products.size() == 0) {
            return ResponseEntity.notFound().build();
        } else return ResponseEntity.ok(products);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") String id) {
        Product product = productRepository.deleteProduct(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        } else return ResponseEntity.ok(product);
    }

    @PostMapping("/products")
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product product) {
        Product newProduct = productRepository.addProduct(product);
        if (newProduct == null) {
            return ResponseEntity.badRequest().body("Product id already exists");
        } else return ResponseEntity.ok(newProduct);
    
    }
}