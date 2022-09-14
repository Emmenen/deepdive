package com.example.sdk.common;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Created at 2022/8/18 13:48
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
public class HttpUtils {
  public static String getStringFromStream(HttpServletRequest req) {
    ServletInputStream is;
    try {
      is = req.getInputStream();
      int nRead = 1;
      int nTotalRead = 0;
      byte[] bytes = new byte[10240];
      while (nRead > 0) {
        nRead = is.read(bytes, nTotalRead, bytes.length - nTotalRead);
        if (nRead > 0) {
          nTotalRead = nTotalRead + nRead;
        }
      }
      return new String(bytes, 0, nTotalRead, StandardCharsets.UTF_8);
    } catch (IOException e) {
      e.printStackTrace();
      return "";
    }
  }

}
