package com.example.demo.controller;

import com.example.demo.dto.OrderDto;
import com.example.demo.entity.Product;
import com.example.demo.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {


    IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void placeOrder(@RequestParam Integer customerId) {
        orderService.placeOrder(customerId);
    }

    @GetMapping("/orderForCode")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto getOrderForCode(@RequestParam Integer orderId) {
        return orderService.getOrderForCode(orderId);
    }

    @GetMapping("/allOrder")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> getAllOrdersForCustomer(@RequestParam Integer customerId) {
        return orderService.getAllOrdersForCustomer(customerId);
    }

}
