package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "product_on_cart")
public class  ProductOnCart extends BaseEntity{

    @Column(name = "cart_id")
    private Integer cartId;
    @Column(name = "customer_id")
    private Integer customerId;
    @Column(name = "cart_versiyonu")
    private Integer cartVersiyonu;
    @Column(name = "product_id")
    private Integer productId;
    @Column(name = "product_code")
    private String productCode;
    @Column(name = "order_id")
    private Integer orderId;

    public ProductOnCart() {
    }

    public ProductOnCart(Integer cartId, Integer customerId, Integer cartVersiyonu, Integer productId, String productCode, Integer orderId) {
        this.cartId = cartId;
        this.customerId = customerId;
        this.cartVersiyonu = cartVersiyonu;
        this.productId = productId;
        this.productCode = productCode;
        this.orderId = orderId;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCartVersiyonu() {
        return cartVersiyonu;
    }

    public void setCartVersiyonu(Integer cartVersiyonu) {
        this.cartVersiyonu = cartVersiyonu;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
