package com.example.demo.dao.Impl;

import com.example.demo.dao.ICartDao;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductOnCart;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class CartDao implements ICartDao {

    private EntityManager entityManager;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public CartDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void createCard(Cart cart) {
        entityManager.persist(cart);
    }

    @Override
    public void updateCart(Cart cart) {
        entityManager.merge(cart);
    }

    @Override
    public void updateProductOnCard(ProductOnCart productOnCart) {
        entityManager.merge(productOnCart);
    }


    @Override
    public Cart getCartByCustomerId(Integer customerId) {

        String hql="Select b from Cart b where b.customerId=:customerId";
        Query query=entityManager.createQuery(hql);
        query.setParameter("customerId",customerId);
        List<Cart> resultList = query.getResultList();
        if (resultList != null && !resultList.isEmpty()) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void removeProductOnCartList(Integer productOnListId) {
        String hql = "DELETE FROM ProductOnCart p WHERE p.id=:productOnListId";
        Query query=entityManager.createQuery(hql);
        query.setParameter("productOnListId",productOnListId);
        query.executeUpdate();
    }

    @Override
    public void addProductToCart(ProductOnCart productOnCart) {
        entityManager.persist(productOnCart);
    }

    @Override
    public List<ProductOnCart> getProductOnCartList(Integer cardId, Integer cartVersiyon) {

        String hql="Select b from ProductOnCart b where b.cartId=:cardId and b.cartVersiyonu=:cartVersiyon and b.orderId is null";
        Query query=entityManager.createQuery(hql);
        query.setParameter("cardId",cardId);
        query.setParameter("cartVersiyon",cartVersiyon);
        List<ProductOnCart> resultList = query.getResultList();
        if (resultList != null && !resultList.isEmpty()) {
            return resultList;
        } else {
            return null;
        }

    }

    @Override
    public List<ProductOnCart> getProductOnCartList(String productCode) {
        String hql="Select b from ProductOnCart b where b.productCode=:productCode and b.orderId is null";
        Query query=entityManager.createQuery(hql);
        query.setParameter("productCode",productCode);
        List<ProductOnCart> resultList = query.getResultList();
        if (resultList != null && !resultList.isEmpty()) {
            return resultList;
        } else {
            return null;
        }
    }

    @Override
    public List<ProductOnCart> getProductOnCartListByOrderId(Integer orderId) {
        String hql="Select b from ProductOnCart b where b.orderId=:orderId";
        Query query=entityManager.createQuery(hql);
        query.setParameter("orderId",orderId);
        List<ProductOnCart> resultList = query.getResultList();
        if (resultList != null && !resultList.isEmpty()) {
            return resultList;
        } else {
            return null;
        }
    }

    @Override
    public void updateOrderIdAfterPlaceOrder(Integer customerId, Integer orderId) {
        String hql="Update ProductOnCart b set b.orderId=:orderId where b.customerId=:customerId and b.orderId is null";
        Query query=entityManager.createQuery(hql);
        query.setParameter("orderId",orderId);
        query.setParameter("customerId",customerId);
        query.executeUpdate();
    }

}
