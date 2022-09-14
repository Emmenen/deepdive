package com.example.sdk.config;

import conflux.web3j.Account;
import conflux.web3j.Cfx;
import conflux.web3j.contract.ContractCall;
import conflux.web3j.contract.abi.DecodeUtil;
import conflux.web3j.types.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.abi.datatypes.generated.Uint256;

import java.math.BigInteger;

/**
 * Created at 2022/8/16 17:40
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
@Configuration
public class ConfluxApp {

  public static final String ConfluxPRCURI = "testnet.confluxscan.net";

  public static final String contractAddress = "cfxtest:acdz2z6eze6f784sp64geedr47rm5eu6epnht6ezsm";

  private String ownerPrivateKey = "0xc23b3aca584a8005b833e7cb30dc348f3a066dc663113d94181f34091ff7af01";
  @Bean
  public Cfx cfx(){
    Cfx cfx = Cfx.create("https://portal-test.confluxrpc.com");
    return cfx;
  }

  @Bean
  public ContractCall  contractCall(){
    return new ContractCall(cfx(),new Address(contractAddress));
  }

  @Bean("contractAddress")
  public Address contractAddress(){
    return new Address(contractAddress);
  }

}
