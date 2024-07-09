package com.example.demo.dao;

import com.example.demo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerDao{

     void addCustomer(Customer customer);

     Customer getCustomerByKimlikNo(String kimlikNo);

     Customer findById(Integer id);
}
