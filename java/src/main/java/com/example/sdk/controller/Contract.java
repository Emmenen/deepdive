package com.example.sdk.controller;

import com.alibaba.fastjson.JSON;
import com.example.sdk.bean.form.MetaDataForm;
import com.example.sdk.bean.vo.QiniuVo;
import com.example.sdk.bean.vo.ResponseVo;
import com.example.sdk.common.FileUploadConfig;
import com.example.sdk.properties.Qiniu;
import com.example.sdk.service.FileUploadService;
import com.qiniu.common.QiniuException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.ws.Response;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created at 2022/8/8 16:46
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
@RestController()
public class Contract {


  @Autowired
  private FileUploadConfig fileUploadConfig;

  @Autowired
  private FileUploadService fileUploadService;


  @PostMapping("/uploadMetaData")
  public ResponseVo<QiniuVo> uploadMetaData(@RequestBody MetaDataForm metaDataForm) throws QiniuException {
    String s = JSON.toJSONString(metaDataForm);
    QiniuVo qiniuVo = fileUploadService.uploadFile(s.getBytes(StandardCharsets.UTF_8), metaDataForm.hash() + ".json");
    return ResponseVo.ok(qiniuVo);
  }

  @PostMapping("/uploadImageFile")
  public ResponseVo<QiniuVo> uploadImageFile(@RequestParam("file") MultipartFile file) throws IOException {
    return ResponseVo.ok(uploadElementFile(file));
  }

  public QiniuVo uploadElementFile(MultipartFile file) throws IOException {
    if(file.isEmpty()){
      throw new FileNotFoundException("空文件");
    }
    QiniuVo qiniuVo = fileUploadService.uploadFile(file.getBytes(),file.getOriginalFilename());
    return qiniuVo;
  }
}
