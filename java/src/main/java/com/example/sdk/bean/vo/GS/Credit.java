package com.example.sdk.bean.vo.GS;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.math.BigInteger;

/**
 * 对标NFT
 * Created at 2022/9/9 10:28
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
@Data
@NoArgsConstructor
public class Credit {
  private BigInteger creditId;
  private BigInteger projectId;
  private BigInteger gsId;
  private BigInteger numberOfCredits;
  private String startingCreditNumber;
  private String endingCreditNumber;
  private String batchNumber;
  private String serialNumber;
  private String createdAt;
  private String updatedAt;
  private String externalUrl;
  public boolean verify(){
    String serialNumber = getSerialNumber();
    if (StringUtils.hasLength(serialNumber)){
      return true;
    }
    return false;
  }


  public String getExternalUrl() {
    externalUrl = "https://registry.goldstandard.org/credit-blocks/details/"+creditId;
    return externalUrl;
  }
}
