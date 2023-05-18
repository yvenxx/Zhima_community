package cn.yvenxx.zhima_community.mapper;

import cn.yvenxx.zhima_community.model.User;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper{
    public User getByUserNameUser(String username);
    public int insertUser(User user);

    boolean update(User user);

    User getUserByToken(@Param("token") String token);

    User getUserById(int id);

    Page<User> getAllUser();

    int deleteUser(int id);

    boolean updatePassword(int id,String oldpwd, String password);
}
