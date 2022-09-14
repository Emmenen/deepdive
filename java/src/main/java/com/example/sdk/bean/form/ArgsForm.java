package com.example.sdk.bean.form;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;

/**
 * Created at 2022/8/23 19:18
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
@Data
public class ArgsForm {
  String accountAddressFrom;
  String[] accountAddressArray;
  String accountAddressTo;
  BigInteger[] tokenIds;
  BigInteger tokenId;
  BigInteger[] amounts;
  BigInteger amount;
  String data;
}
