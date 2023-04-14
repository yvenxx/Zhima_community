package cn.yvenxx.zhima_community.controller;

import cn.yvenxx.zhima_community.utils.R;
import cn.yvenxx.zhima_community.dto.AccessTokenDTO;
import cn.yvenxx.zhima_community.dto.GithubUser;
import cn.yvenxx.zhima_community.model.User;
import cn.yvenxx.zhima_community.provider.GithubProvider;
import cn.yvenxx.zhima_community.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
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
    @ResponseBody
    public R loginAuthorize(@RequestParam("username") String username,
                                 @RequestParam("password") String password,
                                 HttpServletResponse response,
                                 HttpServletRequest request){

        //把token放到cookie，如果token不为空就是登录成功
        String token = userService.doLogin(username,password);
        if(token!=null){
//            response.addCookie(new Cookie("token",token));
            return R.succ("登录成功",new Cookie("token",token));
        }
//        request.setAttribute("info","账号密码错误");
        return R.fail("登录失败，账号密码错误");
    }
    @ResponseBody
    @PostMapping("/register")
    public R register(User user){
        log.info(user.toString());
        if(user.getUserName() == null||user.getPassword()==null||user.getEmail()==null){
//            model.addAttribute("registerInfo","账户密码和Email不得为空");
            return R.fail("账户密码和Email不得为空");
        }
        User result = userService.findByUserName(user.getUserName());
        if (result!=null){
            return R.fail("此用户已存在，请重试");
        }
        if(userService.register(user)){
//            model.addAttribute("registerInfo","注册成功");
            return R.succ("注册成功，请登录",null);
        }
//        model.addAttribute("registerInfo","注册失败");
        return R.fail("注册失败，请重试");
    }

}
