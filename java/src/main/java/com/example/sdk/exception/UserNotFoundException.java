package com.example.sdk.exception;

/**
 * Created at 2022/8/18 13:31
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
public class UserNotFoundException extends Exception{
  public UserNotFoundException() {
  }

  public UserNotFoundException(String message) {
    super("找不到用户！");
  }
}
