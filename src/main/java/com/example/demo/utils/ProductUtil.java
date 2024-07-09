package com.example.demo.utils;

import com.example.demo.dao.IProductDao;
import com.example.demo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductUtil {

    IProductDao productDao;

    @Autowired
    public ProductUtil(IProductDao productDao) {
        this.productDao = productDao;
    }

    public void productCodeControl(String productCode) {
        Product productSilinmisMi=productDao.getDeletedProductByProductCode(productCode);
        if(productSilinmisMi!=null){
            throw new NotFoundException("Bu urunsilinmis");
        }
        Product productVarMi=productDao.productVarMiByProductCode(productCode);
        if(productVarMi==null){
            throw new NotFoundException("Bu productCodunda ürün mevcut değildir");
        }
    }
}
