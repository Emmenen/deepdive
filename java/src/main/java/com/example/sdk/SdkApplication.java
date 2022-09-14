package com.example.sdk;

import com.example.sdk.common.SpringContextUtil;
import com.example.sdk.properties.Qiniu;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class SdkApplication {


//  Configuration cfg = new Configuration(Region.region0());
//  //...其他参数参考类注释
//  UploadManager uploadManager = new UploadManager(cfg);
//  //...生成上传凭证，然后准备上传
//  String accessKey = "your access key";
//  String secretKey = "your secret key";
//  String bucket = "your bucket name";
//
//  //默认不指定key的情况下，以文件内容的hash值作为文件名
//  String key = null;
//
//  try {
//    byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
//    Auth auth = Auth.create(accessKey, secretKey);
//    String upToken = auth.uploadToken(bucket);
//    try {
//      Response response = uploadManager.put(uploadBytes, key, upToken);
//      //解析上传成功的结果
//      DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//      System.out.println(putRet.key);
//      System.out.println(putRet.hash);
//    } catch (QiniuException ex) {
//      Response r = ex.response;
//      System.err.println(r.toString());
//      try {
//        System.err.println(r.bodyString());
//      } catch (QiniuException ex2) {
//        //ignore
//      }
//    }
//  } catch (
//      UnsupportedEncodingException ex) {
//    //ignore
//  }

  public static void main(String[] args) {

    SpringApplication.run(SdkApplication.class, args);
//    Auth auth = Auth.create("hQV1AUh1nhXczNc9h_BxPNJE-gvjKuX4qwfg6nZy","LxOyfhej3bXw4a8JGxhdtxVkPDomXZoGdzX4LbWA");
//    Qiniu qiniu = SpringContextUtil.getBean(Qiniu.class);
//    Auth auth = Auth.create(qiniu.ak, qiniu.sk);
//    String bucket = "deepdive";
//    String upToken = auth.uploadToken(bucket,"conflux.json");
//    Configuration configuration = new Configuration(Region.region2());
//    UploadManager uploadManager = new UploadManager(configuration);
//    try {
//      uploadManager.put("conflux".getBytes(StandardCharsets.UTF_8),"conflux.json",upToken);
//    } catch (QiniuException e) {
//      e.printStackTrace();
//    }
//    System.out.println(upToken);
  }

}
