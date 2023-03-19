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
    public String doLogin(String username, String password) {
        /**
         * 登录逻辑
         * 返回token字符串
         */
        User user = userMapper.getByUserNameUser(username);
        if (user!=null){
            if (user.getPassword().equals(password)){
                String token = UUID.randomUUID().toString();
                user.setToken(token);
                userMapper.update(user);
                return token;
            }
        }
        return null;
    }

    @Override
    public boolean register(User user) {
        /**
         * 注册逻辑
         */
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());

        if(userMapper.getByUserNameUser(user.getUserName()) == null){
            return userMapper.insertUser(user) == 1;
        }
        return false;
    }

    public User findUserByToken(String token){
        /**
         * 登录成功后，通过cookie中的token来返回用户数据，存储到session中
         */
        return userMapper.getUserByToken(token);
    }

    public User findByUserName(String username){
        return userMapper.getByUserNameUser(username);
    }
}

