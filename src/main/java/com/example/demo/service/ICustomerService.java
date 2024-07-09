package com.example.demo.service;

import com.example.demo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerService {

    void addCustomer(Customer customer);


}
