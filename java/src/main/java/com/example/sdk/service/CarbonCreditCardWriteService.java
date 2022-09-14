package com.example.sdk.service;

import com.example.sdk.security.token.ConfluxToken;
import com.example.sdk.utils.ConfluxUtils;
import conflux.web3j.Account;
import conflux.web3j.Cfx;
import conflux.web3j.contract.ContractCall;
import conflux.web3j.types.Address;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.StaticArray0;
import org.web3j.abi.datatypes.generated.Uint256;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * CarbonCreditCard的写入合约
 * Created at 2022/8/16 14:35
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
@Service
@Slf4j
public class CarbonCreditCardWriteService {


  @Autowired
  private Cfx cfx;

  @Autowired
  private Address contractAddress;

  public Account getAccount(){
    ConfluxToken authentication = (ConfluxToken) SecurityContextHolder.getContext().getAuthentication();
    return Account.create(cfx,authentication.getConfluxUser().getPrivateKey());
  }

  /**
   * 批量创建NFT
   * @param amount
   * @return
   */
  public String[] mintNewNFTBatch(int amount) throws Exception {
    Account account = getAccount();
    String[] strings = new String[amount];
    for (int i = 0; i < amount; i++) {
      String mintNewToken = account.call(contractAddress, "mintNewToken", new Uint256(1), new Utf8String(""));
      strings[i] = mintNewToken;
    }
    return strings;
  }

  /**
   * 创建单个NFT
   * @param metadataURI
   * @return
   * @throws Exception
   */
  public String mintNewNFT(String metadataURI) throws Exception {
    Account account = getAccount();
    String mintNewToken = account.call(contractAddress, "mintNewToken", new Uint256(1), new Utf8String(metadataURI));
    log.info("{method: mintNewNFT, result: " + mintNewToken );
    return mintNewToken;
  }

  /**
   * 创建同质化货币
   * @param amount
   * @param metadataURI
   * @return
   * @throws Exception
   */
  public String mintNewCoin(BigInteger amount, String metadataURI) throws Exception {
    Account account = getAccount();
    String mintNewToken = account.call(contractAddress, "mintNewToken", new Uint256(amount), new Utf8String(metadataURI));
    return mintNewToken;
  }

  /**
   * 铸造已经存在的Coin币种；
   * @param tokenId
   * @param amount
   * @throws Exception
   */
  public String mintExistingCoin(BigInteger tokenId, BigInteger amount) throws Exception {
    Account account = getAccount();
    return account.call(contractAddress,"mintExistingToken",new Uint256(tokenId),new Uint256(amount));
  }


  /**
   * 销毁同质化代币或非同质化的NFT
   * @param accountAddress 需要销毁的账户地址
   * @param tokenId NFT的tokenID
   * @param value 销毁的数量（同质化代币时可>1）
   * @return
   * @throws Exception
   */
  public String burn(String accountAddress,BigInteger tokenId,BigInteger value) throws Exception {
    Account account = getAccount();
    String burn = account.call(contractAddress, "burn",new Address(accountAddress).getABIAddress(), new Uint256(tokenId), new Uint256(value));
    return burn;
  }

  /**
   * 批量销毁NFT
   * @param accountAddress
   * @param tokenIds
   * @param values
   * @throws Exception
   */
  private void burnBatch(Address accountAddress,BigInteger[] tokenIds,BigInteger[] values) throws Exception {
    Account account = getAccount();
    List<Uint256> tokenIdList = new ArrayList<>();
    for (int i = 0; i < tokenIds.length; i++) {
      tokenIdList.add(new Uint256(tokenIds[i]));
    }
    ArrayList<Uint256> valueList = new ArrayList<>();
    for (int i = 0; i < values.length; i++) {
      valueList.add(new Uint256(values[i]));
    }
    StaticArray<Uint256> tokenIdsArray = new StaticArray0<>(Uint256.class, tokenIdList);
    StaticArray<Uint256> valuesArray = new StaticArray0<>(Uint256.class, valueList);
    account.call(contractAddress, "burnBatch", tokenIdsArray,valuesArray);
  }

  /**
   * 放弃所有权
   * @return
   */
  public String renounceOwnership() throws Exception {
    return getAccount().call(contractAddress, "renounceOwnership");
  }

  /**
   * 批量交易
   * @param accountAddressFrom
   * @param accountAddressTo
   * @param tokenIds
   * @param amounts
   * @param data
   * @return
   * @throws Exception
   */
  public String safeBatchTransferFrom(String accountAddressFrom,
                                 String accountAddressTo,
                                 BigInteger[] tokenIds,
                                 BigInteger[] amounts,
                                 String data) throws Exception {
    Account account = getAccount();
    return account.call(contractAddress,"safeBatchTransferFrom",
            new Address(accountAddressFrom).getABIAddress(),
            new Address(accountAddressTo).getABIAddress(),
            ConfluxUtils.intArrayToUint256Array(tokenIds),
            ConfluxUtils.intArrayToUint256Array(amounts),
            new DynamicBytes(data.getBytes(StandardCharsets.UTF_8)));
  }
  /**
   * 交易
   * @param accountAddressFrom
   * @param accountAddressTo
   * @param tokenId
   * @param amount
   * @param data
   */
  public String safaTransferFrom(String accountAddressFrom,
                                 String accountAddressTo,
                                 BigInteger tokenId,
                                 BigInteger amount,
                                 String data) throws Exception {
    Account account = getAccount();
    return account.call(contractAddress,"safeTransferFrom",
            new Address(accountAddressFrom).getABIAddress(),
            new Address(accountAddressTo).getABIAddress(),
            new Uint256(tokenId),
            new Uint256(amount),
            new DynamicBytes(data.getBytes(StandardCharsets.UTF_8)));
  }


  /**
   * 批量交易
   * @param accountAddressTo
   * @param tokenId
   * @param amount
   * @throws Exception
   */
  public void safaBatchTransferForm(Address accountAddressTo,
                                    BigInteger[] tokenId,
                                    BigInteger[] amount) throws Exception {
    DynamicArray<Uint256> tokenIds = ConfluxUtils.intArrayToUint256Array(tokenId);
    DynamicArray<Uint256> amounts = ConfluxUtils.intArrayToUint256Array(amount);
    Account account = getAccount();
    account.call(contractAddress,"safaTransferForm",tokenIds,amounts);
  }


  public String _setTokenURI(String tokenURI, BigInteger tokenId) throws Exception {
    Account account = getAccount();
    return account.call(contractAddress,"_setTokenURI",new Utf8String("_setTokenURI"),new Uint256(tokenId));
  }

  public String setBaseURI(String tokenURI) throws Exception {
    Account account = getAccount();
    return account.call(contractAddress,"_setTokenURI",new Utf8String("setBaseURI"));
  }



  /**
   * 撤销或批准所属NFT的使用权限
   * @param operatorAddress
   * @param operator
   * @throws Exception
   */
  public String setApprovalForAll(String operatorAddress, boolean operator) throws Exception {
    Account account = getAccount();
    return account.call(contractAddress,"setApprovalForAll", new Address(operatorAddress).getABIAddress(),new Bool(operator));
  }

  /**
   * 转义合约的拥有者
   * @param newOwnerAddress
   */
  public String transferOwnerShip(String newOwnerAddress) throws Exception {
    Account account = getAccount();
    return account.call(contractAddress,"transferOwnerShip", new Address(newOwnerAddress).getABIAddress());
  }

  /**
   * 暂定或开始合约
   * @param b
   * @return
   * @throws Exception
   */
  public String switchPause(boolean b) throws Exception {
    Account account = getAccount();
    if (b){
     return account.call(contractAddress,"pause");
    }else {
     return account.call(contractAddress,"unpause");
    }
  }

  /**
   *
   * @return
   * @throws Exception
   */
  public String withdraw() throws Exception {
    Account account = getAccount();
    return account.call(contractAddress, "withdraw");
  }




}
