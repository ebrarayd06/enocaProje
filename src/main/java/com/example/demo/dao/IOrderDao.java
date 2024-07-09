package com.example.demo.dao;

import com.example.demo.entity.Order;

import java.math.BigDecimal;
import java.util.List;

public interface IOrderDao {

    Order placeOrder(Order order);

    Order getOrderByOrderId(Integer orderId);

    List<Order> getOrderListByCustomerId(Integer customerId);

}
