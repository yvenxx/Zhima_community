package cn.yvenxx.zhima_community.controller;

import cn.yvenxx.zhima_community.controller.utils.R;
import cn.yvenxx.zhima_community.model.User;
import cn.yvenxx.zhima_community.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
public class HomeController {
    @Autowired
    UserServiceImpl userService;

    @RequestMapping({"/","/index"})
    public R index(@RequestParam("token") String token){
        log.info(token);
        User user = userService.findUserByToken(token);
        if (user!=null){
            return R.succ(user);
        }
        return R.fail("未登录");
    }

}
