package com.ecommerce.Service;

import com.ecommerce.Entity.Category;

import java.util.List;

public interface CategoryService {

    Category createCategory(Category category);

    Category findById(Long id);

    List<Category> getAllCategory();

    List<Category> findByName(String name);

    void deleteCategory(Long oldCategoryId, Long newCategoryId );
}
