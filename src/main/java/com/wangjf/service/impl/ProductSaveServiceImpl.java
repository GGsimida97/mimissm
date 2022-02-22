package com.wangjf.service.impl;

import com.wangjf.mapper.ProductInfoMapper;
import com.wangjf.pojo.ProductInfo;
import com.wangjf.service.ProductSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductSaveServiceImpl implements ProductSaveService {
    @Autowired
    ProductInfoMapper productInfoMapper;

    @Override
    public int saveProduct(ProductInfo info) {
         //新增商品功能实现
        return productInfoMapper.insert(info);
    }
}
