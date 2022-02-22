package com.wanjf.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wangjf.pojo.ProductType;
import com.wangjf.service.ProductTypeService;
import com.wangjf.utils.MD5Util;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class MyTest {
    @Test
    public void testMD5(){
        String mi = MD5Util.getMD5("000000");
        System.out.println(mi);
    }
    @Test
    public void test01(){
        String json="";
        //因为spring容器也是通过全局监听器创建的，所以无法通过spring框架自动注入ProductTypeServiceImpl对象
        //采用手动注入
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext_*.xml");
        ProductTypeService productTypeService = (ProductTypeService) ac.getBean("productTypeService");
        List<ProductType> typeList = productTypeService.getAll();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            json = objectMapper.writeValueAsString(typeList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(json);
    }
}
