package com.example.sdk.bean.form;

import com.example.sdk.config.ConfluxApp;
import lombok.Data;

import java.math.BigInteger;

/**
 * Created at 2022/9/1 19:23
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
//https://testnet.confluxscan.net/v1/transaction?accountAddress=cfxtest:acdz2z6eze6f784sp64geedr47rm5eu6epnht6ezsm&from=cfxtest:aam1a4z4b0hwm4nfk8b640befrxep1nmwj9z32mk99&limit=10&skip=0
@Data
public class TransferRecordArgs {
  private String accountAddress= ConfluxApp.contractAddress;
  private BigInteger tokenId;
  private String form;
  private String to;
  private int limit;
  private int skip;
}
