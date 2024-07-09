package com.example.demo.service.Impl;

import com.example.demo.dao.ICartDao;
import com.example.demo.dao.ICustomerDao;
import com.example.demo.dao.IOrderDao;
import com.example.demo.dto.OrderDto;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Order;
import com.example.demo.entity.ProductOnCart;
import com.example.demo.service.IOrderService;
import com.example.demo.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderService implements IOrderService {

    ICustomerDao customerDao;

    ICartDao cartDao;

    IOrderDao orderDao;

    @Autowired
    public OrderService(ICustomerDao customerDao,ICartDao cartDao,IOrderDao orderDao) {
        this.customerDao = customerDao;
        this.cartDao=cartDao;
        this.orderDao=orderDao;
    }

    /*
    Order oluşturuluyor sonra oluşan order id ile product_on_cart tablosunda  customerId
    ile eşlelen satırlarda orderId boş olan yerleri order id ile setliyoruz cart tablosunun versiyonunu artırıp
    değerini sıfır yapıyoruz.İstediğimiz zaman orderId ile product_on_cart tablosundan ordera erişebiliyoruz
    product id ile de o an ki spiariş verilen fiyatları elde ediyoruz.
    */
    @Override
    public void placeOrder(Integer customerId) {
        Customer customer=customerDao.findById(customerId);
        if(customer==null){
            throw new NotFoundException("Sipariş verecek customer yok");
        }


        Cart cart =cartDao.getCartByCustomerId(customerId);
        if(cart.getCartTotalPrice().equals(BigDecimal.ZERO)){
            throw new NotFoundException("SEPET BOŞ");
        }

        Order order=new Order();//order oluşturma
        order.setCustomerId(customerId);
        order.setOrderPrice(cart.getCartTotalPrice());
        Order placeOrder=orderDao.placeOrder(order);

        cart.setCartVersiyon(cart.getCartVersiyon()+1);//cartın versiyonunu bir artırma
        cart.setCartTotalPrice(BigDecimal.ZERO);//cart sıfırlandı
        cartDao.updateCart(cart);

        cartDao.updateOrderIdAfterPlaceOrder(customerId,placeOrder.getId());

    }

    @Override//orderId ile productOnTablosundan order içindeki ürünleri alıyoruz
    public OrderDto getOrderForCode(Integer orderId) {

        Order order=orderDao.getOrderByOrderId(orderId);
        if(order==null){
            throw new NotFoundException("order bulunamadi");
        }

        OrderDto orderDto=new OrderDto();
        List<ProductOnCart> orderList=cartDao.getProductOnCartListByOrderId(orderId);

        orderDto.setOrderList(orderList);
        orderDto.setOrderId(orderId);
        orderDto.setCustomerId(order.getCustomerId());
        orderDto.setOrderPrice(order.getOrderPrice());
        orderDto.setCreatedDate(order.getCreatedDate());

        return orderDto;
    }

    @Override//customera ait orderları çekip tüm orderIdler sayesinde product_on_cart tablosundan orderiçeriklerini alıyoruz
    public List<OrderDto> getAllOrdersForCustomer(Integer customerId) {
       Customer customer=customerDao.findById(customerId);
        if(customer==null){
            throw new NotFoundException("customer yok");
        }

        List<Order> orderList=orderDao.getOrderListByCustomerId(customerId);
        if(orderList==null){
            throw new NotFoundException("orderList yok");
        }
        List<OrderDto> orderDtoList=new ArrayList<>();

        for (Order order:orderList) {

            OrderDto orderDto=new OrderDto();
            List<ProductOnCart> orderProductList=cartDao.getProductOnCartListByOrderId(order.getId());

            orderDto.setOrderList(orderProductList);
            orderDto.setOrderId(order.getId());
            orderDto.setCustomerId(order.getCustomerId());
            orderDto.setOrderPrice(order.getOrderPrice());
            orderDto.setCreatedDate(order.getCreatedDate());

            orderDtoList.add(orderDto);
        }

        return orderDtoList;
    }
}
