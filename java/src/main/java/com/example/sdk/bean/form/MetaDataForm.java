package com.example.sdk.bean.form;

import com.example.sdk.utils.CryptoUtils;
import lombok.Data;

import javax.xml.bind.DatatypeConverter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

/**
 * Created at 2022/9/4 16:24
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
@Data
public class MetaDataForm {
  private BigInteger gsId;
  private BigInteger creditId;
  private BigInteger projectId;
  private String publishDate;
  public String hash(){
    byte[] digest = CryptoUtils.sha256.digest(String.format("%d%d%s%d",gsId, creditId, publishDate, projectId).getBytes());
    String s = DatatypeConverter.printHexBinary(digest);
    return s;
  }
}
