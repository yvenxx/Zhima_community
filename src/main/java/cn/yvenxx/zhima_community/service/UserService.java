package cn.yvenxx.zhima_community.service;

import cn.yvenxx.zhima_community.model.User;
import com.github.pagehelper.PageInfo;


public interface UserService{

    String doLogin(String username, String password);
    boolean register(User user);

    User getUserById(int id);

    PageInfo<User> getAllUser(int currentPage);

    int deleteUser(int id);

    boolean update(User user);

    boolean updatePassword(int id,String oldpwd, String password);
}
