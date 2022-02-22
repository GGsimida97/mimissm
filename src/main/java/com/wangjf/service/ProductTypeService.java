package com.wangjf.service;

import com.wangjf.pojo.ProductType;

import java.util.List;

public interface ProductTypeService {
    //获取商品类别集合
    List<ProductType> getAll();
}
