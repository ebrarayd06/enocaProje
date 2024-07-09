package com.example.demo.controller;

import com.example.demo.dto.CartDto;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductOnCart;
import com.example.demo.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    ICartService cartService;

    @Autowired
    public CartController(ICartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public void addProductToCart(@RequestParam Integer customerId, @RequestParam String productCode) {
       cartService.addProductToCart(customerId,productCode);
    }
    @DeleteMapping("/remove")
    public void removeProductFromCart(@RequestParam Integer customerId, @RequestParam String productCode) {
        cartService.removeProductFromCart(customerId,productCode);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public CartDto getProductOnCartList(@RequestParam Integer customerId) {
        return cartService.getCart(customerId);
    }

    @DeleteMapping("/deleteCart")
    public void emptyCart(@RequestParam Integer customerId) {
        cartService.emptyCart(customerId);
    }

}
