package cn.yvenxx.zhima_community.controller;

import cn.yvenxx.zhima_community.model.User;
import cn.yvenxx.zhima_community.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    UserServiceImpl userService;
    @RequestMapping({"/","/index"})
    public String index(){
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
