package com.wangjf.service.impl;

import com.wangjf.mapper.AdminMapper;
import com.wangjf.pojo.Admin;
import com.wangjf.pojo.AdminExample;
import com.wangjf.service.AdminService;
import com.wangjf.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    //在业务逻辑层，一定会有数据访问层对象，利用Spring自动注入
    @Autowired
    AdminMapper adminMapper;

    @Override
    public Admin login(String name, String pwd) {
        //根据传入的用户信息到数据库（DB）中查询相应用户对象
        //如果有条件，则一定要创建AdminExample的对象，用来封装条件
        AdminExample example = new AdminExample();
        /**如何添加条件
         *select * from admin where a_name = 'admin'
         * */
        //添加用户名a_name条件
        example.createCriteria().andANameEqualTo(name);

        List<Admin> list = adminMapper.selectByExample(example);
        if (list.size() > 0) {
            Admin admin = list.get(0);
            //查询到用户对象，在进行密码比对。注意密码是密文
            /**
             * 分析：
             * admin.getApass ==> 拿到的是密文
             *  在进行密码比对时，先将拿到的pwd进行md5加密，再与数据库中查询到的对象的密码进行对比
             * */
            String miPwd = MD5Util.getMD5(pwd);
            if (miPwd.equals(admin.getaPass())) {
                return admin;
            }

        }
        return null;
    }
}
