package com.example.sdk.bean;

import conflux.web3j.Account;
import conflux.web3j.Cfx;
import conflux.web3j.types.AddressException;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created at 2022/8/18 12:24
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
@Data
public class ConfluxUser implements UserDetails {
  private String contractAddress;
  private String privateKey;
  private String address;
  public ConfluxUser() {
  }

  public ConfluxUser(String contractAddress, String privateKey) {
    this.contractAddress = contractAddress;
    this.privateKey = privateKey;
  }

  public ConfluxUser(Cfx cfx,String privateKey) throws Exception {
    this.privateKey = privateKey;
    Account account;
    try {
      account = Account.create(cfx, privateKey);
    }catch (AddressException addressException){
      throw new Exception("私钥不正确！");
    }
    this.address = account.getAddress().getAddress();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return null;
  }

  @Override
  public String getUsername() {
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }

//  public Account getAccount() {
//    return account;
//  }
//
//  private String call(String method, Type<?>... inputs) throws Exception {
//    return account.call(new Account.Option(), new Address(contractAddress), method, inputs);
//  }

}