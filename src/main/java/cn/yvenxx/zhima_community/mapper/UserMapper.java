package cn.yvenxx.zhima_community.mapper;

import cn.yvenxx.zhima_community.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper{
    public User getByUserNameUser(String username);
    public int insertUser(User user);

    boolean update(User user);

    User getUserByToken(@Param("token") String token);
}
