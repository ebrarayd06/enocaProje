package com.example.demo.service.Impl;

import com.example.demo.dao.ICartDao;
import com.example.demo.dao.ICustomerDao;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Customer;
import com.example.demo.service.ICustomerService;
import com.example.demo.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class CustomerService implements ICustomerService {

    ICustomerDao customerDao;

    ICartDao cart;

    @Autowired
    public CustomerService(ICustomerDao customerDao, ICartDao cart) {
        this.customerDao = customerDao;
        this.cart = cart;
    }

    @Override
    public void addCustomer(Customer customer) {
        if(customer==null){
            throw new NotFoundException("eksik veri");
        }

        if(customer.getKimlikNo()==null){
            throw new NotFoundException("KİMLİK NO BULUNAMADI");
        }
        Customer customer2=customerDao.getCustomerByKimlikNo(customer.getKimlikNo());
        if(customer2!=null){
            throw new NotFoundException("kullanıcı kayıtlı");
        }
        customerDao.addCustomer(customer);
        Customer customer1=customerDao.getCustomerByKimlikNo(customer.getKimlikNo());
        Cart cart1=new Cart(customer1.getId(),0,BigDecimal.ZERO);
        cart.createCard(cart1);
    }
}
