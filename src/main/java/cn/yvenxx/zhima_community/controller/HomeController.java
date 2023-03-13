package cn.yvenxx.zhima_community.controller;

import cn.yvenxx.zhima_community.model.User;
import cn.yvenxx.zhima_community.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class HomeController {
    @Autowired
    UserServiceImpl userService;
    @RequestMapping({"/","/index"})
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    log.info("token{}",token);
                    User user = userService.findUserByToken(token);
                    log.info(String.valueOf(user));
                    request.getSession().setAttribute("user",user);
                }
            }
        }
        return "index";
    }

    @RequestMapping("/login")
    public String loginPage(HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        if(username!=null){
            return "/index";
        }
        return "login";
    }

}
