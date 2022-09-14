package com.example.sdk.utils;

import cn.hutool.json.JSONArray;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * Created at 2022/8/30 11:39
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
public class HttpUtils {
  private static final String SCHEME = "https";
  private static HttpUrl httpUrl;
  public HttpUtils() {

  }

  public Builder builder(){
    return new Builder();
  }

  public class Builder{

    HttpUrl.Builder builder;

    public Builder() {
      builder = new HttpUrl.Builder()
              .scheme(SCHEME);
    }

    public Builder setScheme(String scheme){
      builder = builder
              .scheme(scheme);
      return this;
    }

    public Builder setHost(String host){
      builder = builder.host(host);
      return this;
    }
    public Builder addPathSegment(String... segments){
      for (String segment : segments) {
        builder.addPathSegment(segment);
      }
      return this;
    }

    public Builder addQueryParameter(String parameter, String value){
      if (StringUtils.hasLength(value)&&!value.equals("null")&&!value.equals("undefined")){
        builder.addQueryParameter(parameter,value);
      }
      return this;
    }

    @Deprecated
    public MyHttpRequest build(){
      Request request = new Request.Builder().url(builder.build()).build();
      return new MyHttpRequest(request);
    }


  }

  public class MyHttpRequest extends OkHttpClient{

    private Request request;

    public MyHttpRequest(Request request) {
      this.request = request;
    }

    public <T> T sendAndGet(Class<T> clazz) throws IOException {
      if (request==null){
        throw new NullPointerException("请先构建Request");
      }
      Response execute = newCall(request).execute();
      String string = execute.body().string();
      T t = JSON.parseObject(string, clazz);
      return t;
    }
    public JSONObject sendAndGet() throws IOException {
      if (request==null){
        throw new NullPointerException("请先构建Request");
      }
      Response execute = newCall(request).execute();
      String string = execute.body().string();
      return JSON.parseObject(string);
    }
  }
}
