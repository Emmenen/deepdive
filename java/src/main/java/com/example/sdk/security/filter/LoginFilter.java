package com.example.sdk.security.filter;

import com.example.sdk.bean.ConfluxUser;
import com.example.sdk.bean.form.LoginForm;
import com.example.sdk.common.HttpUtils;
import com.example.sdk.security.token.ConfluxToken;
import com.example.sdk.security.token.JWTProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.owlike.genson.Genson;
import conflux.web3j.Cfx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created at 2022/8/18 13:45
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
@Configuration
public class LoginFilter extends AbstractAuthenticationProcessingFilter {

  @Autowired
  Genson genson;

  @Autowired
  JWTProvider jwtProvider;

  @Autowired
  Cfx cfx;

  protected LoginFilter() {
    super("/login");
  }

  protected LoginFilter(String defaultFilterProcessesUrl) {
    super(defaultFilterProcessesUrl);
  }

  protected LoginFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
    super(requiresAuthenticationRequestMatcher);
  }
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
    String requestBody = HttpUtils.getStringFromStream(request);
    if (!StringUtils.hasLength(requestBody)) {
      throw new AuthenticationServiceException("无法获取输入信息");
    }
    LoginForm loginForm = genson.deserialize(requestBody, LoginForm.class);
    String privateKey = loginForm.getPrivateKey();
    ConfluxUser confluxUser;
    try {
      confluxUser = new ConfluxUser(cfx,privateKey);
    } catch (Exception e) {
      throw new AuthenticationServiceException(e.getMessage());
    }

    ConfluxToken confluxToken = new ConfluxToken(confluxUser, "");
    return confluxToken;
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
    response.setContentType("application/json;charset=utf-8");
    PrintWriter writer = response.getWriter();
    Map<String, Object> map = new HashMap<>(16);
    map.put("status:", 200);
    ConfluxToken confluxToken = (ConfluxToken) authResult;
    String token = jwtProvider.createToken(confluxToken);
    map.put("token", token);
    map.put("address", confluxToken.getConfluxUser().getAddress());
    writer.write(new ObjectMapper().writeValueAsString(map));
//        SecurityContextHolder.getContext().setAuthentication(authResult);
    writer.flush();
    writer.close();
  }

  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
    response.setContentType("application/json;charset=utf-8");
    PrintWriter writer = response.getWriter();
    Map<String, Object> map = new HashMap<>(16);
    map.put("status:", 401);
    writer.write(new ObjectMapper().writeValueAsString(map));
    writer.flush();
    writer.close();
  }

  @Autowired
  @Override
  public void setAuthenticationManager(AuthenticationManager authenticationManager) {
    super.setAuthenticationManager(authenticationManager);
  }
}
