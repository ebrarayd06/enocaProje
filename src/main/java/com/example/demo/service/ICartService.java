package com.example.demo.service;

import com.example.demo.dto.CartDto;
import com.example.demo.entity.ProductOnCart;

import java.math.BigDecimal;
import java.util.List;

public interface ICartService {

    void addProductToCart(Integer customerId,String productCode);

    void removeProductFromCart(Integer customerId,String productCode);

    CartDto getCart(Integer customerId);

    void updateProductCartListAfterUpdatingProduct(String productCode);

    void updateProductCartListAfterDeletingProduct(String productCode, BigDecimal productPrice);

    void emptyCart(Integer customerId);
}
