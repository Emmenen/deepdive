package com.example.sdk.controller;

import com.example.sdk.bean.vo.GS.Credit;
import com.example.sdk.bean.vo.ResponseVo;
import com.example.sdk.service.GSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigInteger;

/**
 * Created at 2022/9/9 9:43
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
@RestController
public class GSController {


  @Autowired
  private GSService gsService;

  @GetMapping("getCreditByCreditId")
  public ResponseVo<Credit> getCreditByCreditId(@RequestParam BigInteger creditId) throws IOException {
    Credit credit = gsService.getCreditByCreditId(creditId);
    if (!credit.verify()) {
      return new ResponseVo<Credit>(404,"找不到CreditId",null);
    }
    return ResponseVo.ok(credit);
  }
}
