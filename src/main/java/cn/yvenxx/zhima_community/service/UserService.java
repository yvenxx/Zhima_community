package cn.yvenxx.zhima_community.service;

import cn.yvenxx.zhima_community.model.User;


public interface UserService{

    boolean doLogin(String username, String password);
    boolean register(User user);
}
