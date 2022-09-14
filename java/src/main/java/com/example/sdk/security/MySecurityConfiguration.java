package com.example.sdk.security;

import com.example.sdk.security.filter.LoginFilter;
import com.example.sdk.security.filter.TokenFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA at 2022/4/27 22:52
 * User: @Qi Long
 */
@Configuration
public class MySecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginFilter loginFilter;

    @Autowired
    private TokenFilter tokenFilter;

    @Autowired
    private CorsConfigurationSource configurationSource;

    @Autowired
    private LogoutHandler logoutHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests()
                .antMatchers("/login","/register","/logout","/getCert","/swagger-ui.html").permitAll()
                .anyRequest().authenticated()
                .and()
                .cors().configurationSource(configurationSource)
                // 关闭 csrf 保护
                .and()
                .csrf().disable()
                .logout().addLogoutHandler(logoutHandler)
                .logoutUrl("/logout")
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        // 返回 json 格式的数据
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter writer = httpServletResponse.getWriter();
                        Map<String, Object> map = new HashMap<>(16);
                        map.put("status:", 200);
                        map.put("msg:","注销登录成功！");
                        writer.write(new ObjectMapper().writeValueAsString(map));
                        writer.flush();
                        writer.close();
                    }
                });
    }

    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
