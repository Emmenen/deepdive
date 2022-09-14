package com.example.sdk.bean.form;

import lombok.Data;

/**
 * Created at 2022/9/9 12:06
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
@Data
public class PageQueryForm<T> {
  private T param;
  private int skip;
  private int limit;
}
