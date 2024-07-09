package com.example.demo.service.Impl;

import com.example.demo.dao.IProductDao;
import com.example.demo.entity.Product;
import com.example.demo.service.ICartService;
import com.example.demo.service.IProductService;
import com.example.demo.utils.NotFoundException;
import com.example.demo.utils.ProductUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService implements IProductService {

    IProductDao productDao;

    ICartService cartService;

    ProductUtil productUtil;

    public ProductService(IProductDao productDao,ICartService cartService,ProductUtil productUtil) {
        this.productDao = productDao;
        this.cartService=cartService;
        this.productUtil=productUtil;
    }

    @Override
    public void createProduct(Product product) {
        if(product.getProductName()==null||product.getPrice()==null||product.getProductCode()==null||product.getProductStock()==null){
            throw new NotFoundException("eksik bilgiler mevcut");
        }
        Product productVarMi=productDao.productVarMiByProductCode(product.getProductCode());
        if(productVarMi!=null){
            throw new NotFoundException("Bu productCodunda ürün silinmiş ya da var mevcut code değiştiriniz");
        }
        product.setProductVersiyon(0);
        productDao.createProduct(product);
    }

    /*
    her seferinde ürünün en son versiyonunu getiriyoruz
     */
    @Override
    public Product getProductByProductCode(String productCode) {
        productUtil.productCodeControl(productCode);
        return productDao.getProductByProductCode(productCode);
    }


    /*
    ürünü update ederken versiyon artırıp yeni haline getiriyoruz ve diğer tablolardaki değişiklikleri yapıyoruz
     */
    @Override
    public void updateProductByProduct(Product product) {//sadece price ve stok değiştirecek şekilde

        productUtil.productCodeControl(product.getProductCode());

        Product lastProduct=productDao.getProductByProductCode(product.getProductCode());
        Product updatedProduct=new Product();
        updatedProduct.setProductName(lastProduct.getProductName());
        updatedProduct.setProductCode(lastProduct.getProductCode());
        updatedProduct.setPrice(product.getPrice());
        updatedProduct.setProductStock(product.getProductStock());
        updatedProduct.setProductVersiyon(lastProduct.getProductVersiyon()+1);
        productDao.createProduct(updatedProduct);

        cartService.updateProductCartListAfterUpdatingProduct(lastProduct.getProductCode());

    }

    /*
    ürün silindiğinde versiyonu -1 yapıp diğer tablolardaki bu ürün ile alakalı yerleri değiştiriyoruz.
     */
    @Override
    public void deleteProduct(String productCode) {
        productUtil.productCodeControl(productCode);

        Product product=productDao.getProductByProductCode(productCode);
        product.setProductVersiyon(-1);
        productDao.updateProduct(product);
        cartService.updateProductCartListAfterDeletingProduct(productCode,product.getPrice());
    }


}
