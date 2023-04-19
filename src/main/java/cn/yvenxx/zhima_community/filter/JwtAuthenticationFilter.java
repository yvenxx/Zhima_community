package cn.yvenxx.zhima_community.filter;

import cn.yvenxx.zhima_community.utils.JwtTokenUtil;
import cn.yvenxx.zhima_community.utils.R;
import com.alibaba.fastjson2.JSON;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager)
    {
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/doLogin");
    }

    /**
     * 验证操作 接收并解析用户凭证
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 从输入流中获取到登录的信息
        // 创建一个token并调用authenticationManager.authenticate() 让Spring security进行验证
        return authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getParameter("username"),
                        request.getParameter("password")));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        Collection<GrantedAuthority> authorities = user.getAuthorities();
        String token = JwtTokenUtil.createToken(user.getUsername(), authorities.toString());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        // 在请求头里返回创建成功的token
        // 设置请求头为带有"Bearer "前缀的token字符串
//        response.setHeader("Authorization", JwtTokenUtil.TOKEN_PREFIX + token);
        // 处理编码方式 防止中文乱码
        response.setContentType("text/json;charset=utf-8");
        // 将反馈塞到HttpServletResponse中返回给前台
        response.getWriter()
                .write(JSON.toJSONString(R.succ("登录成功",new Cookie("token",JwtTokenUtil.TOKEN_PREFIX+token))));
    }

    /**
     * 验证【失败】调用的方法
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        String returnData="";
        // 账号过期
        if (failed instanceof AccountExpiredException) {
            returnData="账号过期";
        }
        // 密码错误
        else if (failed instanceof BadCredentialsException) {
            returnData="密码错误";
        }
        // 密码过期
        else if (failed instanceof CredentialsExpiredException) {
            returnData="密码过期";
        }
        // 账号不可用
        else if (failed instanceof DisabledException) {
            returnData="账号不可用";
        }
        //账号锁定
        else if (failed instanceof LockedException) {
            returnData="账号锁定";
        }
        // 用户不存在
        else if (failed instanceof InternalAuthenticationServiceException) {
            returnData="用户不存在";
        }
        // 其他错误
        else{
            returnData="未知异常";
        }

        // 处理编码方式 防止中文乱码
        response.setContentType("text/json;charset=utf-8");
        // 将反馈塞到HttpServletResponse中返回给前台
        response.getWriter().write(JSON.toJSONString(R.fail(returnData)));
    }



}
