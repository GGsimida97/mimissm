package com.wangjf.service;

import com.wangjf.pojo.Admin;

public interface AdminService {
   //完成登录判断（返回查到的用户对象Admin便于后续使用）
   Admin login(String name, String pwd);
}
