package cn.yvenxx.zhima_community.controller;

import cn.yvenxx.zhima_community.dto.AccessTokenDTO;
import cn.yvenxx.zhima_community.dto.User;
import cn.yvenxx.zhima_community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;
    @Autowired
    AccessTokenDTO accessTokenDTO;
    @GetMapping("/github/callback")
    public String gitHubCallback(@RequestParam("code") String code,
                                 @RequestParam("state") String state,
                                 HttpServletRequest request){
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        User user = githubProvider.getUser(accessToken);
        if (user != null){
            request.getSession().setAttribute("user",user);
            return "redirect:/index";
        }
        return "redirect:/login";
    }

}
