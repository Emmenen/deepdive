package com.example.sdk.security.token;

import com.example.sdk.bean.ConfluxUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created at 2022/8/18 13:25
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
public class ConfluxToken extends UsernamePasswordAuthenticationToken {

  public ConfluxToken(Object principal, Object credentials) {
    super(principal, credentials);
  }

  public ConfluxToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
    super(principal, credentials, authorities);
  }


  public ConfluxUser getConfluxUser() {
    return (ConfluxUser) this.getPrincipal();
  }

}
