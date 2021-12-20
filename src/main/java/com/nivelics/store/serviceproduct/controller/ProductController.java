package com.nivelics.store.serviceproduct.controller;

import com.nivelics.store.serviceproduct.entity.Category;
import com.nivelics.store.serviceproduct.entity.Product;
import com.nivelics.store.serviceproduct.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> listProduct(@RequestParam(name = "categoryId", required = false) Long categoryId){
        List<Product> products = new ArrayList<>();
        if (null ==  categoryId){
            products = productService.listAllProduct();
            if (products.isEmpty()){
                return ResponseEntity.noContent().build();
            }
        }else{
            products = productService.findByCategory(Category.builder().id(categoryId).build());
            if (products.isEmpty()){
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id) {
        Product product =  productService.getProduct(id);
        if (null==product){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        Product productCreate =  productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreate);
    }
}
