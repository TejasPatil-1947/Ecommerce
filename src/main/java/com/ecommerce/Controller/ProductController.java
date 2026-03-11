package com.ecommerce.Controller;

import com.ecommerce.Entity.Product;
import com.ecommerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create/{cId}")
    public ResponseEntity<Product> createProduct(@PathVariable Long cId, @RequestBody Product product){
        return new ResponseEntity<>(productService.createProduct(cId,product), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{pId}/{cId}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long pId,
            @PathVariable Long cId,
            @RequestBody Product product){

        Product updatedProduct = productService.updateProduct(pId, cId, product);

        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id){
        return new ResponseEntity<>(productService.getProductById(id),HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Product>> findAll(){
        return new ResponseEntity<>(productService.getAllProducts(),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
        System.out.println("INSIDE DELETE...............................................");
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product deleted successfully ",HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<Product>> getByCategory(@PathVariable Long id){
        return new ResponseEntity<>(productService.getProductsByCategory(id),HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<List<Product>> findByCategoryName(@RequestParam String name){
        return new ResponseEntity<>(productService.findByCategoryName(name),HttpStatus.OK);
    }


}
