package com.ecommerce.Controller;


import com.ecommerce.Entity.Category;
import com.ecommerce.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
        return new ResponseEntity<>(categoryService.createCategory(category), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> findByCategory(@PathVariable Long id){
        return new ResponseEntity<>(categoryService.findById(id),HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<List<Category>> findAllCategory(){
        return new ResponseEntity<>(categoryService.getAllCategory(),HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<List<Category>> findByName(@RequestParam String name){
        return new ResponseEntity<>(categoryService.findByName(name),HttpStatus.OK);
    }

    @PutMapping("/deactivate/{oldC}/{newC}")
    public ResponseEntity<?> deactivateCategory(@PathVariable Long oldC, @PathVariable Long newC){
        categoryService.deleteCategory(oldC,newC);
        return new ResponseEntity<>("Products are successfully assigned to new category",HttpStatus.OK);
    }
    @PutMapping("/activate/{id}")
    public ResponseEntity<?> activateCategory(@PathVariable Long id){
        return new ResponseEntity<>(categoryService.activateCategory(id),HttpStatus.OK);
    }
}
