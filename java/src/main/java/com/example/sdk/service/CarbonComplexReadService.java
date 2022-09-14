package com.example.sdk.service;

import com.example.sdk.bean.form.TokensArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.List;

/**
 * Created at 2022/8/29 20:14
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
@Service
public class CarbonComplexReadService {

  @Autowired
  CarbonCreditCardReadService creditCardReadService;
  public void getCoinList(){
    BigInteger bigInteger = creditCardReadService.totalSupply();
    TokensArgs tokensArgs = creditCardReadService.tokens(BigInteger.valueOf(0), bigInteger);
    List<BigInteger> list = tokensArgs.getList();
    BigInteger count = tokensArgs.getCount();
    list.forEach(tokenId->{
      String tokenURI = creditCardReadService.tokenURI(tokenId);

    });
  }
  public void getNFTList(){

  }
}
