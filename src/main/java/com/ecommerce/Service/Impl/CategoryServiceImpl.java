package com.ecommerce.Service.Impl;

import com.ecommerce.Entity.Category;
import com.ecommerce.Entity.Product;
import com.ecommerce.Repository.CategoryRepository;
import com.ecommerce.Repository.ProductRepository;
import com.ecommerce.Service.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Category createCategory(Category category) {
        Category byName = categoryRepository.findByName(category.getName());
        if(byName !=null){
            throw new RuntimeException("Category already exist");
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found with id"));
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> findByName(String name) {
        return categoryRepository.findByNameContaining(name);
    }



    @Transactional
    public void deleteCategory(Long oldCategoryId, Long newCategoryId) {

        Category oldCategory = categoryRepository.findById(oldCategoryId)
                .orElseThrow(() -> new RuntimeException("Old category not found"));

        Category newCategory = categoryRepository.findById(newCategoryId)
                .orElseThrow(() -> new RuntimeException("New category not found"));

        List<Product> products = productRepository.findByCategory(oldCategory);

        for (Product product : products) {
            product.setCategory(newCategory);
        }

        productRepository.saveAll(products);

        oldCategory.setStatus(false);

        categoryRepository.save(oldCategory);
    }

    @Override
    public Category activateCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category with id not found"));
        category.setStatus(true);
        return categoryRepository.save(category);
    }
}
