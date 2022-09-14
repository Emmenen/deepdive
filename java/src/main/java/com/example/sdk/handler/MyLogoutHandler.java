package com.example.sdk.handler;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA at 2022/5/11 21:19
 * User: @Qi Long
 */
@Configuration("myLogoutHandler")
public class MyLogoutHandler implements LogoutHandler  {

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

    }
}
