package com.example.demo.controller;

import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import com.example.demo.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {


    IProductService productService;

    @Autowired
    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(@RequestBody Product product) {
        productService.createProduct(product);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Product getProductByProductCode(@RequestParam String productCode) {
       return productService.getProductByProductCode(productCode);
    }
    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public void updateProductByProductCode(@RequestBody Product product) {
        productService.updateProductByProduct(product);
    }
    @DeleteMapping("/delete")
    public void deleteProduct(@RequestParam String productCode) {
        productService.deleteProduct(productCode);
    }
}
