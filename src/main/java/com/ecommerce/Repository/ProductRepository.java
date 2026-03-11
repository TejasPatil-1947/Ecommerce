package com.ecommerce.Repository;

import com.ecommerce.Entity.Category;
import com.ecommerce.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByCategory(Category category);

    List<Product> findByCategoryName(String name);
}
