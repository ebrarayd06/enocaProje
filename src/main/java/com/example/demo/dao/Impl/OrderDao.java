package com.example.demo.dao.Impl;

import com.example.demo.dao.IOrderDao;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Order;
import com.example.demo.entity.ProductOnCart;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderDao implements IOrderDao {

    private EntityManager entityManager;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public OrderDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Order placeOrder(Order order) {
        entityManager.persist(order);
        return order;
    }

    @Override
    public Order getOrderByOrderId(Integer orderId) {
        String hql="Select b from Order b where b.id=:orderId";
        Query query=entityManager.createQuery(hql);
        query.setParameter("orderId",orderId);
        List<Order> resultList = query.getResultList();
        if (resultList != null && !resultList.isEmpty()) {
            return resultList.get(0);
        } else {
            return null;
        }

    }

    @Override
    public List<Order> getOrderListByCustomerId(Integer customerId) {
        String hql="Select b from Order b where b.customerId=:customerId";
        Query query=entityManager.createQuery(hql);
        query.setParameter("customerId",customerId);
        List<Order> resultList = query.getResultList();
        if (resultList != null && !resultList.isEmpty()) {
            return resultList;
        } else {
            return null;
        }

    }
}
