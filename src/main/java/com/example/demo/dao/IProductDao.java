package com.example.demo.dao;

import com.example.demo.entity.Product;

import java.util.List;

public interface IProductDao {

    void createProduct(Product product);

    Product getProductByProductCode(String productCode);

    Product productVarMiByProductCode(String productCode);

    Product getProductByProductId(Integer productId);

    void updateProduct(Product product);

    Product getDeletedProductByProductCode(String productCode);



}
