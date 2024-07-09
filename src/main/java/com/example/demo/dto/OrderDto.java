package com.example.demo.dto;

import com.example.demo.entity.ProductOnCart;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class OrderDto {

    private List<ProductOnCart> orderList;

    private Integer orderId;

    private Integer customerId;

    private BigDecimal orderPrice;

    private LocalDateTime createdDate;

    public OrderDto() {
    }

    public List<ProductOnCart> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<ProductOnCart> orderList) {
        this.orderList = orderList;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
