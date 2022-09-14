package com.example.sdk.bean.vo.GS;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.List;

/**
 * Created at 2022/9/9 9:49
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
@Data
@NoArgsConstructor
public class GS {
  private BigInteger projectId;
  private BigInteger gsId;
  private String createdAt;
  private String updatedAt;
//[{"product":"VER","summary":[{"status":"ISSUED","total":500935},{"status":"RETIRED","total":424448}]},{"product":"PER","summary":[{"status":"ISSUED","total":853185},{"status":"ASSIGNED","total":245482}]}]
  private Product[] product;
  private List<Credit> credits;


  @Data
  public class Product{
    private String product;
    private Summary summary;
  }
  @Data
  class Summary{
    private String status;
    private BigInteger total;
  }

  public void setProduct(JSONArray jsonArray){
    Product[] objects = (Product[]) jsonArray.toArray(product);
    this.product = objects;
  }
}
