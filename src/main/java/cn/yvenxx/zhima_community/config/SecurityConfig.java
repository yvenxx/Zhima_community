package cn.yvenxx.zhima_community.config;

import cn.yvenxx.zhima_community.filter.JWTAuthenticationEntryPoint;
import cn.yvenxx.zhima_community.filter.JWTAuthorizationFilter;
import cn.yvenxx.zhima_community.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userServiceImpl")
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    /**
     * 安全配置
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 跨域共享
        http.cors()
                .and()
                // 跨域伪造请求限制无效
                .csrf().disable()
                .authorizeRequests()
                // 访问/data需要normal角色
                .antMatchers("/comment").hasRole("normal")
                .antMatchers("/publish").hasRole("normal")
                .antMatchers("/article").hasRole("normal")
                .antMatchers("/").hasRole("normal")
                .antMatchers("/index").hasRole("normal")
                // 其余资源任何人都可访问
                .anyRequest().permitAll()
                .and()
                // 添加JWT登录拦截器
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                // 添加JWT鉴权拦截器
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .sessionManagement()
                // 设置Session的创建策略为：Spring Security永不创建HttpSession 不使用HttpSession来获取SecurityContext
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 异常处理
                .exceptionHandling()
                // 匿名用户访问无权限资源时的异常
                .authenticationEntryPoint(new JWTAuthenticationEntryPoint());
    }

    /**
     * 跨域配置
     * @return 基于URL的跨域配置信息
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 注册跨域配置
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}
