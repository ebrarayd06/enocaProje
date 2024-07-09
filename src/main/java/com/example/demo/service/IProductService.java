package com.example.demo.service;

import com.example.demo.entity.Product;

public interface IProductService {

    void createProduct(Product product);

    Product getProductByProductCode(String productCode);

    void updateProductByProduct(Product product);

    void deleteProduct(String productCode);

}
