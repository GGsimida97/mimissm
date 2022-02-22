package com.wangjf.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wangjf.pojo.ProductType;
import com.wangjf.service.ProductTypeService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;
@WebListener
public class ProductTypeListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        //因为spring容器也是通过全局监听器创建的，所以无法通过spring框架自动注入ProductTypeServiceImpl对象
        //采用手动注入
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext_*.xml");
        ProductTypeService productTypeService = (ProductTypeService) ac.getBean("ProductTypeService");
        List<ProductType> typeList = productTypeService.getAll();
        //将取到的typeList集合放入到全局作用域ServletContext中
        //放在全局作用域中便于后期直接调用，如在点击“编辑”时，会回显至update.jsp
        sce.getServletContext().setAttribute("typeList",typeList);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
