package com.example.sdk.service;

import com.alibaba.fastjson.JSONObject;
import com.example.sdk.bean.form.TokensArgs;
import com.example.sdk.bean.form.TransferRecordArgs;
import com.example.sdk.common.Enums;
import com.example.sdk.config.ConfluxApp;
import com.example.sdk.security.token.ConfluxToken;
import com.example.sdk.utils.ConfluxUtils;
import com.example.sdk.utils.HttpUtils;
import conflux.web3j.Account;
import conflux.web3j.Cfx;
import conflux.web3j.contract.ContractCall;
import conflux.web3j.contract.abi.DecodeUtil;
import conflux.web3j.contract.abi.TupleDecoder;
import conflux.web3j.types.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.web3j.abi.datatypes.*;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.utils.Numeric;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

/**
 * Created at 2022/8/16 19:01
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
@Service
public class CarbonCreditCardReadService {

  @Autowired
  private Cfx cfx;

  @Autowired
  private ContractCall contract;



  public Account getAccount(){
    ConfluxToken authentication = (ConfluxToken) SecurityContextHolder.getContext().getAuthentication();
    return Account.create(cfx,authentication.getConfluxUser().getPrivateKey());
  }


  public void getTransaction(){
  }

  public static void main(String[] args) throws IOException {
    System.out.println(getNFTList(20, 0));
  }

//  https://testnet.confluxscan.net/v1/token/cfxtest:acdz2z6eze6f784sp64geedr47rm5eu6epnht6ezsm?address=cfxtest%3Aacdz2z6eze6f784sp64geedr47rm5eu6epnht6ezsm
  public JSONObject getContractInfo() throws IOException {
    HttpUtils.MyHttpRequest requset = new HttpUtils().builder()
            .setHost(ConfluxApp.ConfluxPRCURI)
            .addPathSegment("v1")
            .addPathSegment("token")
            .addPathSegment("cfxtest:acdz2z6eze6f784sp64geedr47rm5eu6epnht6ezsm")
            .addQueryParameter("address", ConfluxApp.contractAddress)
            .build();
    return requset.sendAndGet();
  }
//  https://testnet.confluxscan.net/v1/transfer?address=cfxtest%3Aacdz2z6eze6f784sp64geedr47rm5eu6epnht6ezsm&limit=10&reverse=true&skip=0&tokenId=1&transferType=ERC1155

  public JSONObject getTransferRecord(TransferRecordArgs transferRecordArgs) throws IOException {
    HttpUtils.MyHttpRequest requset = new HttpUtils().builder()
            .setHost(ConfluxApp.ConfluxPRCURI)
            .addPathSegment("v1")
            .addPathSegment("transfer")
            .addQueryParameter("address", ConfluxApp.contractAddress)
            .addQueryParameter("tokenId", String.valueOf(transferRecordArgs.getTokenId()))
            .addQueryParameter("from", String.valueOf(transferRecordArgs.getForm()))
            .addQueryParameter("to", String.valueOf(transferRecordArgs.getForm()))
            .addQueryParameter("reverse", String.valueOf(true))
            .addQueryParameter("limit", String.valueOf(transferRecordArgs.getLimit()))
            .addQueryParameter("skip", String.valueOf(transferRecordArgs.getSkip()))
            .addQueryParameter("transferType", "ERC1155")
            .build();
    return requset.sendAndGet();
  }

  public static JSONObject getTokenDetail(BigInteger tokenId) throws IOException {
    HttpUtils.MyHttpRequest requset = new HttpUtils().builder()
            .setHost(ConfluxApp.ConfluxPRCURI)
            .addPathSegment("stat")
            .addPathSegment("nft")
            .addPathSegment("checker")
            .addPathSegment("detail")
            .addQueryParameter("contractAddress", ConfluxApp.contractAddress)
            .addQueryParameter("tokenId", String.valueOf(tokenId))
            .build();
    return requset.sendAndGet();
  }

  public static JSONObject getNFTList(int limit, int skip) throws IOException {
    HttpUtils.MyHttpRequest requset = new HttpUtils().builder()
            .setHost(ConfluxApp.ConfluxPRCURI)
            .addPathSegment("stat")
            .addPathSegment("nft")
            .addPathSegment("list1155inventory")
            .addQueryParameter("contractAddr", ConfluxApp.contractAddress)
            .addQueryParameter("limit", String.valueOf(limit))
            .addQueryParameter("skip", String.valueOf(skip))
            .build();
    return requset.sendAndGet();
  }

  /**
   * ???????????????tokenId???NFT???URI
   * @param tokenId
   * @return ??????
   */
  public String tokenURI(BigInteger tokenId) {
    return contract.callAndGet(Utf8String.class,"_tokenURI", new Uint256(tokenId));
  }

  /**
   * ?????????????????????token??????
   * @return ??????
   */
  public BigInteger balanceOfTokenCount() {
    return contract.callAndGet(Uint256.class, "tokenCountOf", getAccount().getAddress().getABIAddress());
  }

  /**
   * ??????????????????????????????token
   * @return token??????
   */
  public List<Uint256> tokenBalance(){

    String tokenBalance = contract.call("balanceOfAccount",getAccount().getAddress().getABIAddress()).sendAndGet();
    return DecodeUtil.decode(tokenBalance, Enums.TYPE_DYNAMIC_ARRAY_UINT256);
  }

  /**
   * ??????????????????????????????tokenId?????????
   * @param tokenIds
   * @return ??????
   */
  public List<Uint256> balanceOfTokenIds(BigInteger[] tokenIds)  {
    String balanceOfTokenIds = contract.call("balanceOfAccountId", getAccount().getAddress().getABIAddress(),ConfluxUtils.intArrayToUint256Array(tokenIds)).sendAndGet();
    return DecodeUtil.decode(balanceOfTokenIds, Enums.TYPE_DYNAMIC_ARRAY_UINT256);
  }

  /**
   * ????????????????????????????????????tokenId?????????
   * @param address ?????????????????????
   * @param tokenId ?????????tokenId
   * @return ????????????
   */
  public List<Uint256> balanceOfBatch(String[] address, BigInteger[] tokenId) {
    StaticArray<org.web3j.abi.datatypes.Address> addressStaticArray = ConfluxUtils.stringArrayToAddressArray(address);
    DynamicArray<Uint256> uint256StaticArray = ConfluxUtils.intArrayToUint256Array(tokenId);
    String balanceOfBatch = contract.call("balanceOfBatch", addressStaticArray, uint256StaticArray).sendAndGet();
    return DecodeUtil.decode(balanceOfBatch, Enums.TYPE_DYNAMIC_ARRAY_UINT256);
  }

  /**
   * ??????????????????????????????
   * @param tokenId ??????????????????id
   * @return
   */
  public BigInteger coinBalanceOfTokenId(BigInteger tokenId){
    return contract.callAndGet(Uint256.class,"totalSupply", new Uint256(tokenId));
  }

  /**
   * ??????????????????token
   * @param index
   * @return
   */
  public BigInteger tokenByIndex(BigInteger index){
    return contract.callAndGet(Uint256.class,"tokenByIndex",new Uint256(index));
  }

  /**
   * ????????????????????????????????????token
   * @param tokenId
   * @return
   */
  public BigInteger tokenOfOwnerByIndex(BigInteger tokenId){
    return contract.callAndGet(Uint256.class,"tokenOfOwnerByIndex",getAccount().getAddress().getABIAddress(),new Uint256(tokenId));
  }

  /**
   * ???????????????????????????tokenId???NFT?????????
   * @param tokenId
   * @return ??????
   */
  public BigInteger balanceOfTokenId(BigInteger tokenId) {
    return contract.callAndGet(Uint256.class, "balanceOfAccountId", getAccount().getAddress().getABIAddress(), new Uint256(tokenId));
  }

  /**
   * ????????????????????????token??????????????????token???????????????
   * @param offset
   * @param limit
   * @return
   */
  public TokensArgs tokens(BigInteger offset, BigInteger limit){
    String tokens = contract.call("tokens", new Uint256(offset), new Uint256(limit)).sendAndGet();
//    int length = "0x0000000000000000000000000000000000000000000000000000000000000021".length();
//    char[] countChars = new char[length];
//    tokens.getChars(0,length,countChars,0);
//    BigInteger count = DecodeUtil.decode(new String(countChars), Uint256.class);
//    String json = DecodeUtil.decode(tokens, Utf8String.class);
//    StringBuffer stringBuffer = new StringBuffer("0x");
//    char[] listChars = new char[tokens.length()-length];
//    tokens.getChars(length,listChars.length,listChars,0);
//    stringBuffer.append(listChars);
//    List<Uint256> list = DecodeUtil.decode(stringBuffer.toString(), Enums.TYPE_DYNAMIC_ARRAY_UINT256);
//    DefaultFunctionReturnDecoder.decode(tokens, Arrays.asList(Enums.TOKENS_VO));
//    List decode = DefaultFunctionReturnDecoder.decode(tokens, Arrays.asList(Enums.TOKENS_VO));
    TupleDecoder tupleDecoder = new TupleDecoder(tokens);
//    TupleDecoder[] tupleDecoders = TupleDecoder.decodeDynamicArray(tokens);
    TokensArgs tokensArgs = new TokensArgs();
    List<BigInteger> list = tokensArgs.getList();
    for (int i = 0; i < 2; i++) {
      tupleDecoder.nextUint256();
    }
    tokensArgs.setCount(tupleDecoder.nextUint256());
    for (int i = 0; i < limit.intValue()+2; i++) {
      try {
        BigInteger bigInteger = tupleDecoder.nextUint256();
        list.add(bigInteger);
      }catch (Exception e){

      }
    }
//    List<Uint256> uint256List = DecodeUtil.decode(tokens, Enums.TYPE_STATIC_ARRAY2_UINT256);
//    Uint256 uint256 = uint256List.get(1);
//    String typeAsString = uint256.getTypeAsString();
    return tokensArgs;
  }

//
//  public static TokensArgs getTokensArgs(String tokensArgBytesString){
//
//  }

  /**
   * ????????????????????????????????????token??????????????????token???????????????
   * @param offset
   * @param limit
   * @return
   */
  public TokensArgs tokensOf(BigInteger offset,BigInteger limit){
   return tokensOfAccount(getAccount().getHexAddress(), offset, limit);
  }
  /**
   * ????????????????????????????????????token??????????????????token???????????????
   * @param offset
   * @param limit
   * @return
   */
  public TokensArgs tokensOfAccount(String accountAddress,BigInteger offset,BigInteger limit){
    String tokensOf = contract.call("tokensOf", new Address(accountAddress).getABIAddress(), new Uint256(offset), new Uint256(limit)).sendAndGet();
    TupleDecoder tupleDecoder = new TupleDecoder(tokensOf);
    TokensArgs tokensArgs = new TokensArgs();
    List<BigInteger> list = tokensArgs.getList();
    for (int i = 0; i < 2; i++) {
      tupleDecoder.nextUint256();
    }
    tokensArgs.setCount(tupleDecoder.nextUint256());
    for (int i = 0; i < limit.intValue()+2; i++) {
      try {
        BigInteger bigInteger = tupleDecoder.nextUint256();
        list.add(bigInteger);
      }catch (Exception e){

      }
    }
    return tokensArgs;
  }


  /**
   * ???????????????tokenId????????????
   * @param tokenId
   * @return
   */
  public Boolean exists(BigInteger tokenId) {
    return contract.callAndGet(Bool.class,"exists",new Uint256(tokenId));
  }

  /**
   * ???????????????
   * @return
   */
  public String name() {
    return contract.callAndGet(Utf8String.class,"name");
  }

  /**
   * ???????????????
   * @return
   */
  public String owner() {
    return contract.callAndGet(Utf8String.class,"owner");
  }

  /**
   * ????????????
   * @return
   */
  public String symbol() {
    return contract.callAndGet(Utf8String.class,"symbol");
  }

  /**
   * ????????????
   * @return
   */
  public Boolean paused(){
    return contract.callAndGet(Bool.class,"paused");
  }

  /**
   *
   * @param interfaceId
   * @return
   */
  public Boolean supportsInterface(String interfaceId){
    return contract.callAndGet(Bool.class,"supportsInterface",new Utf8String(interfaceId));
  }

  /**
   * ??????operatorAddress????????????accountAddress?????????NFT???????????????
   * @param accountAddress
   * @param operatorAddress
   * @return
   */
  public Boolean isApprovedForAll(String accountAddress,String operatorAddress) {
    return contract.callAndGet(Bool.class,"isApprovedForAll",new Address(accountAddress).getABIAddress(),new Address(operatorAddress).getABIAddress());
  }

  /**
   * ?????????????????????nft???????????????????????????+?????????????????????
   * @return
   */
  public BigInteger totalSupply(){
    return contract.callAndGet(Uint256.class,"totalSupply");
  }

  /**
   * baseURI
   * @return
   */
  public String baseUri(){
    return contract.callAndGet(Utf8String.class,"uri",new Uint256(0));
  }


}
