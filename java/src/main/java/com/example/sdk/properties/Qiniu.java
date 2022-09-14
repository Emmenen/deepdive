package com.example.sdk.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created at 2022/8/8 15:23
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
@Service
@Data
public class Qiniu {
  @Value("${qiniu.AccessKey}")
  public  String ak;
  @Value("${qiniu.SecretKey}")
  public  String sk;
  @Value("${qiniu.zone}")
  private QiniuZone zone;
  @Value("${qiniu.bucket}")
  private String bucket;
  @Value("${qiniu.resourcesUrl}")
  private String resourcesUrl;
}
