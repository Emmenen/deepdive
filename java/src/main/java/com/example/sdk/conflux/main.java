package com.example.sdk.conflux;

import com.example.sdk.utils.ConfluxUtils;
import conflux.web3j.Account;
import conflux.web3j.Cfx;
import conflux.web3j.CfxUnit;
import conflux.web3j.Request;
import conflux.web3j.contract.ContractCall;
import conflux.web3j.contract.abi.DecodeUtil;
import conflux.web3j.response.StringResponse;
import conflux.web3j.response.UsedGasAndCollateral;
import conflux.web3j.types.Address;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Bytes3;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Bytes4;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * Created at 2022/8/10 15:26
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
public class main {
//  public static void main(String[] args) throws Exception {
//    // Use testnet RPC url create a Cfx instance, which can be used to invoke RPC method
//    Cfx cfx = Cfx.create("https://portal-test.confluxrpc.com");
//    String ADDRESS = "cfxtest:aarjwjjc6pbpetbzph0bn8cstdn207kz023wcws0m6";
//    // getBalance method can be used to query balance of one address
//    BigInteger balance = cfx.getBalance(new Address(ADDRESS)).sendAndGet();
//    // default unit is drip
//    System.out.println("balance in Drip: " + balance);
//    // Use CfxUnit.drip2Cfx convert unit to CFX
//    System.out.println("balance in CFX: " + CfxUnit.drip2Cfx(balance));
//
//    // Create an account with private key
//    Account account = Account.create(cfx, "0xc23b3aca584a8005b833e7cb30dc348f3a066dc663113d94181f34091ff7af01");
//    // Call account.transfer method to transfer CFX to another address
//    System.out.println(new Address(ADDRESS).getHexAddress());
//    String hash = account.transfer(new Address(ADDRESS), BigInteger.valueOf(100));
//    System.out.println("Transaction hash: " + hash);
//  }
  private static final TypeReference<DynamicArray<Uint256>> TYPE_DYNAMIC_ARRAY_UINT256 = new TypeReference<DynamicArray<Uint256>>() {};

  public static void main(String[] args) throws Exception {
    Cfx cfx = Cfx.create("https://portal-test.confluxrpc.com");
    Address contractAddress = new Address("cfxtest:acdz2z6eze6f784sp64geedr47rm5eu6epnht6ezsm");
    ContractCall contract = new ContractCall(cfx, contractAddress);

    Account account = Account.create(cfx, "0xc23b3aca584a8005b833e7cb30dc348f3a066dc663113d94181f34091ff7af01");
    String s1 = "3132330000000000000000000000000000000000000000000000000000000000";
    String call = account.call(contractAddress, "safeBatchTransferFrom",
            new Address("cfxtest:aam1a4z4b0hwm4nfk8b640befrxep1nmwj9z32mk99").getABIAddress(),
            new Address("cfxtest:aarjwjjc6pbpetbzph0bn8cstdn207kz023wcws0m6").getABIAddress(),
            ConfluxUtils.intArrayToUint256Array(new BigInteger[]{BigInteger.valueOf(1), BigInteger.valueOf(2)}),
            ConfluxUtils.intArrayToUint256Array(new BigInteger[]{BigInteger.valueOf(1), BigInteger.valueOf(2)}),
            new DynamicBytes("123".getBytes(StandardCharsets.UTF_8)));
    System.out.println(call);
//    String safeBatchTransferFrom = account.call(new Address("cfxtest:acdz2z6eze6f784sp64geedr47rm5eu6epnht6ezsm"), "safeBatchTransferFrom",
//            new Address("cfxtest:aam1a4z4b0hwm4nfk8b640befrxep1nmwj9z32mk99").getABIAddress(),
//            new Address("cfxtest:aarjwjjc6pbpetbzph0bn8cstdn207kz023wcws0m6").getABIAddress(),
//            ConfluxUtils.intArrayToUint256Array(new BigInteger[]{BigInteger.valueOf(1), BigInteger.valueOf(2)}),
//            ConfluxUtils.intArrayToUint256Array(new BigInteger[]{BigInteger.valueOf(1), BigInteger.valueOf(2)}),
//            new Utf8String(""));
//    System.out.println(safeBatchTransferFrom);

    String mintNewToken = null;
    mintNewToken = account.call(new Address("cfxtest:acdz2z6eze6f784sp64geedr47rm5eu6epnht6ezsm"), "mintNewToken",new Uint256(100), new Utf8String("127.0.0.1"));
    System.out.println(mintNewToken);
//    String balanceOf = contract.call("balanceOf", account.getAddress().getABIAddress(), new Uint256(3)).sendAndGet();
    try {
      Request<String, StringResponse> balanceOfAccount = contract.call("balanceOfAccount", account.getAddress().getABIAddress());
//      TypeReference<Uint256> uint256TypeReference = TypeReference.StaticArrayTypeReference.create(Uint256.class);
//      DecodeUtil.decode(balanceOfAccount, uint256TypeReference)
      String s = balanceOfAccount.sendAndGet();

      List<Uint256> decode = DecodeUtil.decode(s, TYPE_DYNAMIC_ARRAY_UINT256);
      System.out.println("balanceOf"+DecodeUtil.decode(s,TYPE_DYNAMIC_ARRAY_UINT256));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

//  public static void main(String[] args) {
//    String s = Numeric.toHexString(toByte32WithPadding("123"));
//    System.out.println(s);
//  }

  public static byte[] toByte32WithPadding(String text){
    byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
    if (bytes.length >32 ){
      throw new IllegalArgumentException("text too lang");
    }
    return Arrays.copyOf(bytes,32);
  }
}
