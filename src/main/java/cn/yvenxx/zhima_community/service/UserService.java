package cn.yvenxx.zhima_community.service;

import cn.yvenxx.zhima_community.model.User;


public interface UserService{

    String doLogin(String username, String password);
    boolean register(User user);

    User getUserById(int id);
}
