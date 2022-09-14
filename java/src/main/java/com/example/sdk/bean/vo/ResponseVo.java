package com.example.sdk.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created at 2022/8/28 17:40
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
@Data
@ApiModel(value = "统一返回对象")
public class ResponseVo<T>  {
  @ApiModelProperty("状态码")
  private int status;
  @ApiModelProperty("信息")
  private String message;
  @ApiModelProperty("数据")
  private T data;

  public ResponseVo(int status, String message, T data) {
    this.status = status;
    this.message = message;
    this.data = data;
  }

  public static <T> ResponseVo<T> ok(T data){
   return new ResponseVo<>(200,"success",data);
  }
  public static <T> ResponseVo<T> error(T data){
   return new ResponseVo<>(400,"failed",data);
  }
  public static <T> ResponseVo<T> status(int status,T data){
   return new ResponseVo<>(400,"failed",data);
  }
}
