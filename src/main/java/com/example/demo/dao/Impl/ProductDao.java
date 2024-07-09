package com.example.demo.dao.Impl;

import com.example.demo.dao.IProductDao;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductDao implements IProductDao {

    private EntityManager entityManager;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    public ProductDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void createProduct(Product product) {
        entityManager.persist(product);
    }

    @Override
    public Product getProductByProductCode(String productCode) {

        String hql = "SELECT b FROM Product b WHERE b.productCode = :productCode AND b.productVersiyon = (SELECT MAX(p.productVersiyon) FROM Product p WHERE p.productCode = :productCode)";
        Query query=entityManager.createQuery(hql);
        query.setParameter("productCode",productCode);
        List<Product> resultList = query.getResultList();
        if (resultList != null && !resultList.isEmpty()) {
            return resultList.get(0);
        } else {
            return null;
        }
    }
    @Override
    public Product getDeletedProductByProductCode(String productCode) {

        String hql = "SELECT b FROM Product b WHERE b.productCode = :productCode AND b.productVersiyon = -1";
        Query query=entityManager.createQuery(hql);
        query.setParameter("productCode",productCode);
        List<Product> resultList = query.getResultList();
        if (resultList != null && !resultList.isEmpty()) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Product productVarMiByProductCode(String productCode) {

        String hql = "SELECT b FROM Product b WHERE b.productCode = :productCode and b.productVersiyon in (0,-1)";
        Query query=entityManager.createQuery(hql);
        query.setParameter("productCode",productCode);
        List<Product> resultList = query.getResultList();
        if (resultList != null && !resultList.isEmpty()) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public Product getProductByProductId(Integer productId) {
        String hql = "SELECT b FROM Product b WHERE b.id=:productId";
        Query query=entityManager.createQuery(hql);
        query.setParameter("productId",productId);
        List<Product> resultList = query.getResultList();
        if (resultList != null && !resultList.isEmpty()) {
            return resultList.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void updateProduct(Product product) {
        entityManager.merge(product);
    }



}
