package cn.yvenxx.zhima_community.controller;

import cn.yvenxx.zhima_community.model.User;
import cn.yvenxx.zhima_community.service.impl.UserServiceImpl;
import cn.yvenxx.zhima_community.utils.JwtTokenUtil;
import cn.yvenxx.zhima_community.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@Slf4j
public class HomeController {
    @Autowired
    UserServiceImpl userService;

    @RequestMapping({"/","/index"})
    public R index(HttpServletRequest request){
//    public R index(@RequestParam("token") String token){

        String token = request.getHeader("Authorization").replace(JwtTokenUtil.TOKEN_PREFIX, "");

        String username = JwtTokenUtil.getUsername(token);
        User user = userService.findByUserName(username);
        if (user!=null){
            user.setPassword(null);
            return R.succ(user);
        }
        return R.fail("未登录");
    }

}
