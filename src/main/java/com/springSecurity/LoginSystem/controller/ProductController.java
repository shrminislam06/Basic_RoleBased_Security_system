package com.springSecurity.LoginSystem.controller;

import com.springSecurity.LoginSystem.model.Product;
import com.springSecurity.LoginSystem.repository.ProductRepository;
import com.springSecurity.LoginSystem.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class ProductController {
    private ProductRepository productRepository;


    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;

    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>>productList(){
        return ResponseEntity.ok(productRepository.findAll());
    }

    @PostMapping("/add-product")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String>addProduct(@RequestBody Product product){
        productRepository.save(product);
        return ResponseEntity.ok("product added successfully");
    }
    @GetMapping("/get-one-product")
    public ResponseEntity<Product>getOne(@RequestParam Integer productId){
        return ResponseEntity.ok(productRepository.findById(productId).get());
    }
    @PutMapping("/update-product")
    @PreAuthorize("hasAnyAuthority('ADMIN')")

    public ResponseEntity<String>updateProduct(@RequestBody Product product){
        Product product1=productRepository.findById(product.getId()).orElseThrow(null);
        product1.setName(product.getName());
        product1.setPrice(product.getPrice());
        productRepository.save(product1);
        return ResponseEntity.ok("update product successfully");
    }

    @DeleteMapping("/delete-product/{productId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")

    public ResponseEntity<String>deleteProduct(@PathVariable Integer productId){
        productRepository.deleteById(productId);
        return ResponseEntity.ok("product "+productId+" deleted successfully");
    }
}
