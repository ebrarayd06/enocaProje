package com.example.demo.service;

import com.example.demo.dto.OrderDto;

import java.util.List;

public interface IOrderService {

    void placeOrder(Integer customerId);

    OrderDto getOrderForCode(Integer orderId);

    List<OrderDto> getAllOrdersForCustomer(Integer customerId);


}
