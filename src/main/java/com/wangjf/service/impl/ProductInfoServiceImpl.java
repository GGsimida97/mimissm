package com.wangjf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wangjf.mapper.ProductInfoMapper;
import com.wangjf.pojo.ProductInfo;
import com.wangjf.pojo.ProductInfoExample;
import com.wangjf.pojo.vo.ProductInfoVo;
import com.wangjf.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductInfo> getAll() {
        return productInfoMapper.selectByExample(new ProductInfoExample());
    }

    @Override
    public PageInfo splitPage(int pageNum, int pageSize) {
        //分页插件pagehelper会使用工具类PageHelper完成分页设置
        PageHelper.startPage(pageNum, pageSize);
        //进行PageInfo的数据封装
        //创建ProductInfoExample对象，进行有条件查询
        ProductInfoExample example = new ProductInfoExample();
        //追加条件设置排序，按主键降序
        example.setOrderByClause("p_id desc");
        //设置完排序，取集合
        List<ProductInfo> list = productInfoMapper.selectByExample(example);
        //将查到的集合封装到PageInfo对象中
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    //点击“编辑” 功能实现
    @Override
    public ProductInfo editProduct(int pId) {
        return productInfoMapper.selectByPrimaryKey(pId);
    }

    @Override
    public int updateProduct(ProductInfo info) {
        return productInfoMapper.updateByPrimaryKey(info);
    }

    //点击“删除” 删除单个商品功能实现
    @Override
    public int deleteProduct(int pId) {

        return productInfoMapper.deleteByPrimaryKey(pId);
    }

    //批量删除
    @Override
    public int deleteBatch(String[] ids) {
        return productInfoMapper.deleteBatch(ids);
    }

    //多条件查询
    @Override
    public List<ProductInfo> selectCondition(ProductInfoVo info) {
        return productInfoMapper.selectCondition(info);
    }

    //多条件查询带分页
    @Override
    public PageInfo selectConditionPage(ProductInfoVo vo, Integer pageSize) {
        PageHelper.startPage(vo.getPage(), pageSize);
        List<ProductInfo> list = productInfoMapper.selectCondition(vo);
        return new PageInfo(list);
    }
}
