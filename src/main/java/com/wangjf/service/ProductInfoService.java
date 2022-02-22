package com.wangjf.service;

import com.github.pagehelper.PageInfo;
import com.wangjf.pojo.ProductInfo;
import com.wangjf.pojo.vo.ProductInfoVo;

import java.util.List;

public interface ProductInfoService {
    //显示全部商品（不分页）
    List<ProductInfo> getAll();

    //分页功能实现
    PageInfo splitPage(int pageNum, int pageSize);

    //点击“编辑” 功能实现
    ProductInfo editProduct(int pId);

    //更新商品信息功能实现
    int updateProduct(ProductInfo info);

    //点击“删除” 删除单个商品功能实现
    int deleteProduct(int pId);

    //批量删除商品
    int deleteBatch(String[] ids);

    //多条件查询
    List<ProductInfo> selectCondition(ProductInfoVo info);

    //多条件查询带分页
    PageInfo selectConditionPage(ProductInfoVo vo,Integer pageSize);
}
