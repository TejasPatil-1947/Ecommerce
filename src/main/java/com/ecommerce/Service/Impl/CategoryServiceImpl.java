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

    @Override
    @Transactional
    public void deleteCategory(Long oldCategoryId, Long newCategoryId) {
        Category oldCategory
                = categoryRepository.findById(oldCategoryId).orElseThrow(() -> new RuntimeException("Old category not found"));
        Category newCategoryNotFound
                = categoryRepository.findById(newCategoryId).orElseThrow(() -> new RuntimeException("New category not found"));

        List<Product> byCategory = productRepository.findByCategory(oldCategory);
        for(Product product: byCategory){
            product.setCategory(newCategoryNotFound);
        }

        oldCategory.setStatus(false);
        categoryRepository.save(oldCategory);
    }
}
