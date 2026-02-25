package com.ecommerce.Controller;

import com.ecommerce.Entity.Product;
import com.ecommerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/create/{cId}")
    public ResponseEntity<Product> createProduct(@PathVariable Long cId, @RequestBody Product product){
        return new ResponseEntity<>(productService.createProduct(cId,product), HttpStatus.CREATED);
    }

    @PutMapping("/update/{pId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long pId, @RequestBody Product newProduct){
        return new ResponseEntity<>(productService.updateProduct(pId,newProduct),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id){
        return new ResponseEntity<>(productService.getProductById(id),HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Product>> findAll(){
        return new ResponseEntity<>(productService.getAllProducts(),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product deleted successfully ",HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable Long id){
        return new ResponseEntity<>(productService.getProductsByCategory(id),HttpStatus.OK);
    }


}
