package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;


@Entity
@Table(name = "customerorder")
public class Order extends BaseEntity{

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "order_price")
    private BigDecimal orderPrice;

    public Order() {
    }

    public Order(Integer customerId, BigDecimal orderPrice) {
        this.customerId = customerId;
        this.orderPrice = orderPrice;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }
}
