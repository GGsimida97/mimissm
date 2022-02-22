package com.wangjf.service.impl;

import com.wangjf.mapper.ProductTypeMapper;
import com.wangjf.pojo.ProductType;
import com.wangjf.pojo.ProductTypeExample;
import com.wangjf.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "ProductTypeService")
public class ProductTypeServiceImpl implements ProductTypeService {
    @Autowired
    ProductTypeMapper productTypeMapper;

    //获取商品类别集合
    @Override
    public List<ProductType> getAll() {
        return productTypeMapper.selectByExample(new ProductTypeExample());
    }
}
