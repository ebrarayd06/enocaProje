package com.example.demo.dao.Impl;

import com.example.demo.dao.ICustomerDao;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductOnCart;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerDao implements ICustomerDao {

    private EntityManager entityManager;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public CustomerDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Customer findById(Integer id) {
        try {
            return entityManager.find(Customer.class,id);
        } catch (NoResultException e) {
            return null;
        }

    }

    @Override
    public void addCustomer(Customer customer) {
        entityManager.persist(customer);
    }

    @Override
    public Customer getCustomerByKimlikNo(String kimlikNo) {

        String hql="Select b from Customer b where b.kimlikNo=:kimlikNo";
        Query query=entityManager.createQuery(hql);
        query.setParameter("kimlikNo",kimlikNo);
        List<Customer> resultList = query.getResultList();
        if (resultList != null && !resultList.isEmpty()) {
            return resultList.get(0);
        } else {
            return null;
        }

    }
}
