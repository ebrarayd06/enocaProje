package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "cart")
public class Cart extends BaseEntity{

    @Column(name = "customer_id")
    private Integer customerId;


    @Column(name = "cart_versiyon")
    private Integer cartVersiyon;

    @Column(name = "cart_total_price")
    private BigDecimal cartTotalPrice;

    public Cart() {
    }

    public Cart(Integer customerId, Integer cartVersiyon, BigDecimal cartTotalPrice) {
        this.customerId = customerId;
        this.cartVersiyon = cartVersiyon;
        this.cartTotalPrice = cartTotalPrice;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCartVersiyon() {
        return cartVersiyon;
    }

    public void setCartVersiyon(Integer cartVersiyon) {
        this.cartVersiyon = cartVersiyon;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }
}
