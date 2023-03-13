package cn.yvenxx.zhima_community.controller;

import cn.yvenxx.zhima_community.dto.AccessTokenDTO;
import cn.yvenxx.zhima_community.dto.GithubUser;
import cn.yvenxx.zhima_community.model.User;
import cn.yvenxx.zhima_community.provider.GithubProvider;
import cn.yvenxx.zhima_community.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    AccessTokenDTO accessTokenDTO;
    @Autowired
    UserServiceImpl userService;

    @GetMapping("/github/callback")
    public String gitHubCallback(@RequestParam("code") String code,
                                 @RequestParam("state") String state,
                                 HttpServletRequest request){
        /**
         * 需要完善网站的用户信息对接,token等
         * 对接GitHub登录
         */
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);

        if (githubUser != null){
            request.getSession().setAttribute("username",githubUser.getName());
            return "redirect:/index";
        }
        return "redirect:/login";
    }

    @RequestMapping("/doLogin")
    public String loginAuthorize(@RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 HttpServletResponse response,
                                 HttpServletRequest request){

        //把token放到cookie，如果token不为空就是登录成功
        String token = userService.doLogin(username, password);
        if(token!=null){
            response.addCookie(new Cookie("token",token));
            return "redirect:/index";
        }
        request.setAttribute("info","账号密码错误");
        return "/login";
    }

    @PostMapping("/register")
    public String register(User user,
                           Model model){

        if(user.getUserName() == null){
            model.addAttribute("registerInfo","账户名不得为空");
            return "/login";
        }

        if(userService.register(user)){
            model.addAttribute("registerInfo","注册成功");
            return "/login";
        }

        model.addAttribute("registerInfo","注册失败");
        return "/login";
    }
}
