package com.ecommerce.Service;

import com.ecommerce.Entity.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(Long categoryId,Product product);

    Product updateProduct(Long id, Product product);

    Product getProductById(Long id);

    List<Product> getAllProducts();

    void deleteProduct(Long id);

    List<Product> getProductsByCategory(Long categoryId);
}
