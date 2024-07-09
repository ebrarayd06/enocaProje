package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.math.BigDecimal;


@Entity
public class Product extends BaseEntity{

    @Column(name = "product_name")
    private String productName;


    @Column(name = "product_code")
    private String productCode;

    @Column(name = "price")
    private BigDecimal price;


    @Column(name = "product_stock")
    private Integer productStock;

    @Column(name = "product_version")
    private Integer productVersiyon;


    public Product() {
    }

    public Product(String productName, String productCode, BigDecimal price, Integer productStock, Integer productVersiyon) {
        this.productName = productName;
        this.productCode = productCode;
        this.price = price;
        this.productStock = productStock;
        this.productVersiyon = productVersiyon;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public Integer getProductStock() {
        return productStock;
    }

    public void setProductStock(Integer productStock) {
        this.productStock = productStock;
    }

    public Integer getProductVersiyon() {
        return productVersiyon;
    }

    public void setProductVersiyon(Integer productVersiyon) {
        this.productVersiyon = productVersiyon;
    }

}
