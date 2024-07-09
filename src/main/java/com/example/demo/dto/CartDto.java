package com.example.demo.dto;

import com.example.demo.entity.ProductOnCart;

import java.math.BigDecimal;
import java.util.List;

public class CartDto {
    private List<ProductOnCart> products;
    private BigDecimal cartTotalPrice;

    public CartDto() {
    }

    public CartDto(List<ProductOnCart> products, BigDecimal cartTotalPrice) {
        this.products = products;
        this.cartTotalPrice = cartTotalPrice;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    // Getters and Setters

    public List<ProductOnCart> getProducts() {
        return products;
    }

    public void setProducts(List<ProductOnCart> products) {
        this.products = products;
    }


}
