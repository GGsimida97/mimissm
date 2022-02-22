package com.wanjf.test;

import com.wangjf.mapper.ProductInfoMapper;
import com.wangjf.pojo.ProductInfo;
import com.wangjf.pojo.vo.ProductInfoVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext_dao.xml","classpath:applicationContext_service.xml"})
public class MyTest02 {
    @Autowired
    ProductInfoMapper mapper;
    @Test
    public void testSelectCondition(){
        ProductInfoVo vo = new ProductInfoVo();
        vo.setProType(1);
        vo.setProName("座机");
        List<ProductInfo> list = mapper.selectCondition(vo);
        list.forEach(p-> System.out.println(p));
    }
}
