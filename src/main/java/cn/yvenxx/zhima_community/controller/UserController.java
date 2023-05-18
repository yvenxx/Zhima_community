package cn.yvenxx.zhima_community.controller;

import cn.yvenxx.zhima_community.model.User;
import cn.yvenxx.zhima_community.service.UserService;
import cn.yvenxx.zhima_community.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/user/{id}")
    public R articleCreator(@PathVariable("id") int id){

        User user = userService.getUserById(id);
        user.setPassword(null);

        return R.succ(user);
    }

    @PutMapping("/user/{id}")
    public R updateUser(@PathVariable("id")int id,@RequestBody User user){
        user.setId(id);
        if(userService.update(user)){
            return R.succ(null);
        }
        return R.fail("修改失败");
    }

    @PutMapping("/user/{id}/password")
    public R updatePassword(@PathVariable("id")int id,@RequestBody Map<String,String> map){
        userService.updatePassword(id,map.get("oldpwd"),map.get("password"));
        return R.succ(null);
    }
}
