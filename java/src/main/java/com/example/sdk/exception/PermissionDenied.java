package com.example.sdk.exception;

/**
 * Created at 2022/8/18 13:33
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
public class PermissionDenied extends Exception{
  public PermissionDenied() {
  }

  public PermissionDenied(String message) {
    super("权限不足！");
  }
}
