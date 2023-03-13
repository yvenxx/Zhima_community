package cn.yvenxx.zhima_community.service.impl;

import cn.yvenxx.zhima_community.mapper.UserMapper;
import cn.yvenxx.zhima_community.model.User;
import cn.yvenxx.zhima_community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    @Override
    public boolean doLogin(String username, String password) {
        User user = userMapper.getByUserNameUser(username);
        if (user!=null){
            if (user.getPassword().equals(password)){
                user.setToken(UUID.randomUUID().toString());
                userMapper.update(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean register(User user) {
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());

        if(userMapper.getByUserNameUser(user.getUserName()) == null){
            return userMapper.insertUser(user) == 1;
        }
        return false;
    }

}

