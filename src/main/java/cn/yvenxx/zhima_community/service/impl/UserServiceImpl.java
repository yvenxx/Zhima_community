package cn.yvenxx.zhima_community.service.impl;

import cn.yvenxx.zhima_community.mapper.UserMapper;
import cn.yvenxx.zhima_community.model.User;
import cn.yvenxx.zhima_community.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

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

    @Override
    public User getUserById(int id) {
        return userMapper.getUserById(id);
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username==null||"".equals(username)){
            throw new RuntimeException("不能为空");
        }
        User user = userMapper.getByUserNameUser(username);
        if (user==null){
            throw new UsernameNotFoundException("用户不存在");
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+user.getRole()));
        //暂时是未加密的
        return new org.springframework.security.core.userdetails.User(user.getUserName(),"{noop}"+user.getPassword(),authorities);
    }
}

