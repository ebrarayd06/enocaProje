package com.example.demo.service.Impl;

import com.example.demo.dao.ICartDao;
import com.example.demo.dao.ICustomerDao;
import com.example.demo.dao.IProductDao;
import com.example.demo.dto.CartDto;
import com.example.demo.entity.Cart;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductOnCart;
import com.example.demo.service.ICartService;
import com.example.demo.service.IProductService;
import com.example.demo.utils.NotFoundException;
import com.example.demo.utils.ProductUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class CartService implements ICartService {

    ICartDao cartDao;

    IProductDao productDao;

    ICustomerDao customerDao;

    ProductUtil productUtil;


    @Autowired
    public CartService(ICartDao cartDao,IProductDao productDao,ICustomerDao customerDao,ProductUtil productUtil) {
        this.cartDao = cartDao;
        this.productDao=productDao;
        this.customerDao=customerDao;
        this.productUtil=productUtil;
    }
/*

Ürünü product_on_cart tablosuna ekliyoruz sipariş verilmediği için orderId si boş
stok kontrolu ve güncellemesi yapılıyor sonra cart toplamını güncelliyoruz


 */
    @Override
    public void addProductToCart(Integer customerId, String productCode) {
        if(customerId==null||productCode==null){
            throw new NotFoundException("eksik veri");
        }
        Customer customer=customerDao.findById(customerId);
        if(customer == null){
            throw new NotFoundException("Customer bulunamadı");
        }

        productUtil.productCodeControl(productCode);
        Product product=productDao.getProductByProductCode(productCode);

        //stokta yoksa sepete eklemesine izin verme
        if (product.getProductStock() - 1 < 0) {
            throw new NotFoundException("Stockta Kalmadı");
        }

        Cart cart=cartDao.getCartByCustomerId(customerId);

        ProductOnCart productOnCart=new ProductOnCart(cart.getId(),cart.getCustomerId(),cart.getCartVersiyon(),product.getId(),product.getProductCode(),null);
        cartDao.addProductToCart(productOnCart);
        product.setProductStock(product.getProductStock() - 1);//stock güncelleme
        productDao.updateProduct(product);

        BigDecimal toplamFiyat=cart.getCartTotalPrice();
        BigDecimal price=product.getPrice();
        toplamFiyat=toplamFiyat.add(price);
        cart.setCartTotalPrice(toplamFiyat);
        cartDao.updateCart(cart);
    }

    /*
    product_on_cart tablosundan orderIdsi boş olan ve customerId eşleşen satırları çıkartıyoruz
    sonra cart tablosunda ki cart toplamını güncelleyip stokları düzeltiyoruz

     */
    @Override
    public void removeProductFromCart(Integer customerId, String productCode) {
        if(customerId==null||productCode==null){
            throw new NotFoundException("eksik veri");
        }

        Customer customer=customerDao.findById(customerId);

        if(customer == null){
            throw new NotFoundException("Customer bulunamadı");
        }
        productUtil.productCodeControl(productCode);
        Product product=productDao.getProductByProductCode(productCode);

        Cart cart=cartDao.getCartByCustomerId(customerId);
        BigDecimal toplamFiyat=cart.getCartTotalPrice();
        int count=0;
        List<ProductOnCart> productOnCartList=cartDao.getProductOnCartList(cart.getId(), cart.getCartVersiyon());
        if(productOnCartList==null){
            throw new NotFoundException("sepet bulunamadı");
        }
        for (ProductOnCart key:productOnCartList) {
            if(productCode.equals(key.getProductCode())){
                cartDao.removeProductOnCartList(key.getId());
                BigDecimal price=product.getPrice();
                toplamFiyat=toplamFiyat.subtract(price);
                count=count+1;
            }

        }
        product.setProductStock(product.getProductStock()+count);
        productDao.updateProduct(product);
        cart.setCartTotalPrice(toplamFiyat);
        cartDao.updateCart(cart);

    }

    //product_on_cart tablosundan sepetteki ürünleri alıyoruz orderIdsi null olan customerId ile eşleşenlerden ulaşıyoruz
    @Override
    public CartDto getCart(Integer customerId) {
        if(customerId==null){
            throw new NotFoundException("eksik veri");
        }
        Customer customer=customerDao.findById(customerId);
        if(customer == null){
            throw new NotFoundException("Customer bulunamadı");
        }

        Cart cart=cartDao.getCartByCustomerId(customerId);
        List<ProductOnCart> productOnCartList=cartDao.getProductOnCartList(cart.getId(), cart.getCartVersiyon());
        if(productOnCartList==null){
            throw new NotFoundException("sepetiniz boş");
        }
        CartDto cartDto=new CartDto(productOnCartList,cart.getCartTotalPrice());
        return cartDto;
    }


    /*
    bir product update edildiğinde gidip product_on_cart tablosunda orderIdsı null olana her satırı update ediyoruz
    her kullanıcının sepetinde ki ürün değerleri değişiyor bu yüzden her satırı değiştirirken aynı zamanda cart tablosunda
    ki cart total değeri de güncelliyoruz.Product versiyonunu artırıyoruzki geçmiş değerleri görelim ve her productİd yi update
    ediyoruz
     */
    @Override
    public void updateProductCartListAfterUpdatingProduct(String productCode) {
        Product product=productDao.getProductByProductCode(productCode);
        List<ProductOnCart> productOnCart=cartDao.getProductOnCartList(productCode);
        if(productOnCart!=null){
            for (ProductOnCart key:productOnCart) {

            Cart cart=cartDao.getCartByCustomerId(key.getCustomerId());
            BigDecimal originalToplamFiyat=cart.getCartTotalPrice();
            BigDecimal eskiUrunFiyati=productDao.getProductByProductId(key.getProductId()).getPrice();
            BigDecimal yeniUrunFiyati=product.getPrice();
            originalToplamFiyat=originalToplamFiyat.subtract(eskiUrunFiyati);
            originalToplamFiyat=originalToplamFiyat.add(yeniUrunFiyati);
            cart.setCartTotalPrice(originalToplamFiyat);

            key.setProductId(product.getId());
            cartDao.updateProductOnCard(key);
            cartDao.updateCart(cart);

            }
        }

    }

/*
bir ürün silindiğinde versiyonunu -1 e çekiyoruz ki o ürün silindiği belli olsun ayrıca product_on_cart tablosundan da siliyoruz
silerken her müşterinin sepet total price nı da update ediyoruz

 */
    @Override
    public void updateProductCartListAfterDeletingProduct(String productCode,BigDecimal productPrice) {
        List<ProductOnCart> productOnCart=cartDao.getProductOnCartList(productCode);
        if(productOnCart!=null){
            for (ProductOnCart key:productOnCart) {

                Cart cart=cartDao.getCartByCustomerId(key.getCustomerId());
                BigDecimal originalToplamFiyat=cart.getCartTotalPrice();
                originalToplamFiyat=originalToplamFiyat.subtract(productPrice);
                cart.setCartTotalPrice(originalToplamFiyat);
                cartDao.removeProductOnCartList(key.getId());
                cartDao.updateCart(cart);

            }
        }


    }

    /*
    product_on_cart tablosundan customerId eşleşen yerleri orderId si null ise siliyoruz ve cart total fiyatını sıfırlıyoruz
    ürün stoklarınıda güncelliyoruz
     */
    @Override
    public void emptyCart(Integer customerId) {
        Customer customer=customerDao.findById(customerId);
        if(customer==null){
            throw new NotFoundException("Customer bulunamadı");
        }
        Cart cart=cartDao.getCartByCustomerId(customerId);
        List<ProductOnCart> productOnCartList=cartDao.getProductOnCartList(cart.getId(), cart.getCartVersiyon());
        if(productOnCartList==null){
            throw new NotFoundException("sepetiniz boş silemezsiniz");
        }

        for (ProductOnCart key:productOnCartList) {
            Product product=productDao.getProductByProductCode(key.getProductCode());
            product.setProductStock(product.getProductStock()+1);
            productDao.updateProduct(product);
            cartDao.removeProductOnCartList(key.getId());
        }
        cart.setCartTotalPrice(BigDecimal.ZERO);
        cartDao.updateCart(cart);
    }

}
