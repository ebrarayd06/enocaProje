package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "customer")
public class Customer extends BaseEntity{

    @Column(name = "name")
    private String name;


    @Column(name = "kimlik_no")
    private String kimlikNo;

    @Column(name = "phone_number")
    private String phoneNumber;

    public Customer() {

    }

    public Customer(String name, String kimlikNo, String phoneNumber) {
        this.name = name;
        this.kimlikNo = kimlikNo;
        this.phoneNumber = phoneNumber;
    }

    public String getKimlikNo() {
        return kimlikNo;
    }

    public void setKimlikNo(String kimlikNo) {
        this.kimlikNo = kimlikNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
