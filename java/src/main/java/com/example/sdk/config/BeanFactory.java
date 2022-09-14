package com.example.sdk.config;

import com.example.sdk.bean.ConfluxUser;
import com.owlike.genson.Genson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * Created at 2022/8/18 12:28
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
@Configuration
public class BeanFactory {

  private HashMap<Integer, ConfluxUser> confluxUserHashMap;

  @Bean
  public Genson genson(){
    return new Genson();
  }
}
