package com.example.sdk.security.filter;

import com.alibaba.fastjson.JSON;
import com.example.sdk.bean.vo.ResponseVo;
import com.example.sdk.exception.UserNotFoundException;
import com.example.sdk.security.token.JWTProvider;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA at 2022/5/5 16:05
 * User: @Qi Long
 */
@Slf4j
@Configuration
public class TokenFilter implements Filter {
    public static final String HEADER_AUTH_NAME = "Authorization";

    @Autowired
    JWTProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws  ServletException, IOException {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context.getAuthentication()==null) {
            System.out.println("****************");
            HttpServletRequest httpServletRequest = (HttpServletRequest) req;
            String authToken = httpServletRequest.getHeader(HEADER_AUTH_NAME);
            log.info("authToken"+authToken);
            if (StringUtils.hasLength(authToken)&&!authToken.equals("null")) {
                Authentication authentication = null;
                try {
                    authentication = this.jwtProvider.getAuthentication(authToken);
                }catch (Exception e){
                    HttpServletResponse response = (HttpServletResponse) res;
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter writer = res.getWriter();
                    ResponseVo<String> error = ResponseVo.error("登录信息错误");
                    String message = JSON.toJSONString(error);
                    writer.print(message);
                    writer.close();
                }

                context.setAuthentication(authentication);
            }
        }
        chain.doFilter(req,res);

    }
}
