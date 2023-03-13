package cn.yvenxx.zhima_community.mapper;

import cn.yvenxx.zhima_community.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper{
    public User getByUserNameUser(String username);
    public int insertUser(User user);

    void update(User user);
}
