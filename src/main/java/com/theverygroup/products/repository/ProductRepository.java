package com.theverygroup.products.repository;

import com.theverygroup.products.dto.Product;
import com.theverygroup.products.util.ProductDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ArrayList;

@Repository
public class ProductRepository {

    private List<Product> products;

    @Autowired
    public ProductRepository() {
        products = ProductDataUtils.loadAll();
    }

    public List<Product> findAll() {
        return products;
    }

    public Product filterById(String id) {
        for (Product item : products) {
            if (item.getId().equals(id)) {
                return item;
            }
        } return null;
    }

    public List<Product> filterByType(String type) {
        List<Product> filteredProducts = new ArrayList<>();
        for (Product item : products) {
            if (item.getType().toUpperCase().equals(type.toUpperCase())) {
                filteredProducts.add(item);
            }
        } return filteredProducts;
    }

    public Product deleteProduct(String id) {
        Product itemToDeleted = filterById(id);
        if (itemToDeleted != null) {
            products.remove(itemToDeleted);
        } return itemToDeleted;
    }

    public Product addProduct(Product product) {
        String newProductId = product.getId();
        for (Product item : products) {
            if (item.getId().equals(newProductId)) {
                return null;
            }
        }
        products.add(product);
        return product;
    }
}