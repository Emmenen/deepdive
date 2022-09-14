package com.example.sdk.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.example.sdk.bean.vo.QiniuVo;
import com.example.sdk.properties.Qiniu;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Created at 2022/8/8 20:00
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
@Service
public class FileUploadService {

  @Autowired
  private UploadManager uploadManager;

  @Autowired
  private BucketManager bucketManager;

  @Autowired
  private Qiniu qiniu;
  @Autowired
  private Auth auth;

  public final static String NORM_MONTH_PATTERN = "yyyy/MM/";

//  @Transactional(rollbackFor = Exception.class)
  public QiniuVo uploadFile(byte[] bytes,String originalName) throws QiniuException {
//    String extName = FileUtil.extName(originalName);
//    String fileName = DateUtil.format(new Date(), NORM_MONTH_PATTERN)+ IdUtil.simpleUUID() + "." + extName;


    String upToken = auth.uploadToken(qiniu.getBucket(),originalName);
    uploadManager.put(bytes, originalName, upToken);
    return new QiniuVo(qiniu.getResourcesUrl()+"/"+originalName,originalName);
  }

  public void deleteFile(String fileName){
    try {
      bucketManager.delete(qiniu.getBucket(), fileName);
    } catch (QiniuException e) {
      throw new RuntimeException(e);
    }
  }

}
