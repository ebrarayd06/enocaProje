package com.example.demo.dao;

import com.example.demo.entity.Cart;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductOnCart;

import java.math.BigDecimal;
import java.util.List;

public interface ICartDao {
    void createCard(Cart cart);

    void updateCart(Cart cart);

    void updateProductOnCard(ProductOnCart productOnCart);

    Cart getCartByCustomerId(Integer customerId);

    void removeProductOnCartList(Integer productOnListId);

    void addProductToCart(ProductOnCart productOnCart);

    List<ProductOnCart> getProductOnCartList(Integer cardId,Integer cartVersiyon);
    List<ProductOnCart> getProductOnCartList(String productCode);
    List<ProductOnCart> getProductOnCartListByOrderId(Integer orderId);

    void updateOrderIdAfterPlaceOrder(Integer customerId,Integer orderId);




}
