package com.example.sdk.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.sdk.bean.form.ArgsForm;
import com.example.sdk.bean.form.TokensArgs;
import com.example.sdk.bean.form.TransferRecordArgs;
import com.example.sdk.bean.vo.ResponseVo;
import com.example.sdk.service.CarbonCreditCardReadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web3j.abi.datatypes.generated.Uint256;

import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created at 2022/8/19 21:44
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
@RestController
@Api(value = "读取合约controller", tags = {"读取合约接口"})
public class CarbonReadController {
  public static void main(String[] args) {
//    1661432681 2022-08-25 21:04:41
//    1661432606 2022-08-25 21:03:26
//    差75秒差75，所以这里应该是秒级的时间戳
    Date date1 = new Date(1661432681-1661432227);//差一小时
    Date date3 = new Date(1661432681-1661417192);//差5小时
    Date date = new Date(new Long("1661432681000"));
    Date date2 = new Date(1660631575);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    System.out.println(simpleDateFormat.format(date1));
    System.out.println(simpleDateFormat.format(date2));
    System.out.println(simpleDateFormat.format(date3));
    System.out.println(simpleDateFormat.format(date));
    System.out.println(1661432681-1661432227);
    System.out.println(1661432681-1661417192);
  }

  /**
   * date2比date1多的天数
   * @param date1
   * @param date2
   * @return
   */
  private static int differentDays(Date date1,Date date2) {
    Calendar cal1 = Calendar.getInstance();
    cal1.setTime(date1);

    Calendar cal2 = Calendar.getInstance();
    cal2.setTime(date2);
    int day1= cal1.get(Calendar.DAY_OF_YEAR);
    int day2 = cal2.get(Calendar.DAY_OF_YEAR);

    int year1 = cal1.get(Calendar.YEAR);
    int year2 = cal2.get(Calendar.YEAR);
    if(year1 != year2) {//同一年
      int timeDistance = 0 ;
      for(int i = year1 ; i < year2 ; i ++)
      {
        if(i%4==0 && i%100!=0 || i%400==0)    //闰年
        {
          timeDistance += 366;
        }
        else    //不是闰年
        {
          timeDistance += 365;
        }
      }

      return timeDistance + (day2-day1) ;
    } else {// 不同年
      System.out.println("判断day2 - day1 : " + (day2-day1));
      return day2-day1;
    }
  }

  @Autowired
  CarbonCreditCardReadService creditCardReadService;

  @GetMapping("balanceOfTokenCount")
  @ApiOperation(value = "获取当前用户的token余额", httpMethod = "GET", response = BigInteger.class, notes = "")
  public ResponseEntity<ResponseVo<BigInteger>> balanceOfTokenCount(){
    return ResponseEntity.ok(ResponseVo.ok(creditCardReadService.balanceOfTokenCount()));
  }

  @GetMapping("tokenURI")
  public ResponseEntity<ResponseVo<String> >tokenURI(@RequestParam BigInteger tokenId){
    return ResponseEntity.ok(ResponseVo.ok(creditCardReadService.tokenURI(tokenId)));
  }

  @GetMapping("balanceOfBatch")
  public ResponseEntity<ResponseVo<List<Uint256>> >balanceOfBatch(@RequestBody ArgsForm argsForm){
    return ResponseEntity.ok(ResponseVo.ok(creditCardReadService.balanceOfBatch(argsForm.getAccountAddressArray(),argsForm.getTokenIds())));
  }
  @GetMapping("balanceOfTokenId")
  public ResponseEntity<ResponseVo<BigInteger>>balanceOfTokenId(@RequestParam BigInteger tokenId){
    return ResponseEntity.ok(ResponseVo.ok(creditCardReadService.balanceOfTokenId(tokenId)));
  }
  @GetMapping("balanceOfTokenIds")
  public ResponseEntity<ResponseVo<List<Uint256>>> balanceOfTokenIds(@RequestBody ArgsForm argsForm){
    return ResponseEntity.ok(ResponseVo.ok(creditCardReadService.balanceOfTokenIds(argsForm.getTokenIds())));
  }
  @GetMapping("tokenBalance")
  public ResponseEntity<ResponseVo<List<Uint256>>> tokenBalance(){
    return ResponseEntity.ok(ResponseVo.ok(creditCardReadService.tokenBalance()));
  }
  @GetMapping("baseUri")
  public ResponseEntity<ResponseVo<String>> baseUri(){
    return ResponseEntity.ok(ResponseVo.ok(creditCardReadService.baseUri()));
  }
  @GetMapping("coinBalanceOfTokenId")
  public ResponseEntity<ResponseVo<BigInteger>> coinBalanceOfTokenId(@RequestParam BigInteger tokenId){
    return ResponseEntity.ok(ResponseVo.ok(creditCardReadService.coinBalanceOfTokenId(tokenId)));
  }
  @GetMapping("tokenByIndex")
  public ResponseEntity<ResponseVo<BigInteger>> tokenByIndex(@RequestParam BigInteger index){
    return ResponseEntity.ok(ResponseVo.ok(creditCardReadService.tokenByIndex(index)));
  }
  @GetMapping("tokenOfOwnerByIndex")
  public ResponseEntity<ResponseVo<BigInteger>> tokenOfOwnerByIndex(@RequestParam BigInteger tokenId){
    return ResponseEntity.ok(ResponseVo.ok(creditCardReadService.tokenOfOwnerByIndex(tokenId)));
  }
  @GetMapping("tokens")
  public ResponseEntity<ResponseVo<TokensArgs> >tokens(@RequestParam BigInteger offset, @RequestParam BigInteger limit){
    return ResponseEntity.ok(ResponseVo.ok(creditCardReadService.tokens(offset,limit)));
  }

  @GetMapping("tokensOf")
  public ResponseEntity<ResponseVo<TokensArgs>> tokensOf(@RequestParam BigInteger offset,@RequestParam BigInteger limit){
    return ResponseEntity.ok(ResponseVo.ok(creditCardReadService.tokensOf(offset,limit)));
  }

  @GetMapping("tokensOfAccount")
  public ResponseEntity<ResponseVo<TokensArgs>> tokensOfAccount(@RequestParam String accountAddress,@RequestParam BigInteger offset,@RequestParam BigInteger limit){
    return ResponseEntity.ok(ResponseVo.ok(creditCardReadService.tokensOfAccount(accountAddress,offset,limit)));
  }

  @GetMapping("getTokenDetail")
  public ResponseEntity<ResponseVo<JSONObject> >getTokenDetail(@RequestParam BigInteger tokenId) throws IOException {
    return ResponseEntity.ok(ResponseVo.ok(creditCardReadService.getTokenDetail(tokenId)));
  }
  @GetMapping("getContractInfo")
  public ResponseEntity<ResponseVo<JSONObject> >getContractInfo() throws IOException {
    return ResponseEntity.ok(ResponseVo.ok(creditCardReadService.getContractInfo()));

  }
  @PostMapping("getTransferRecord")
  public ResponseEntity<ResponseVo<JSONObject> >getTransferRecord(@RequestBody TransferRecordArgs transferRecordArgs) throws IOException {
    JSONObject transferRecord = null;
    transferRecord = creditCardReadService.getTransferRecord(transferRecordArgs);

    return ResponseEntity.ok(ResponseVo.ok(transferRecord));
  }

  @GetMapping("getNFTList")
  public ResponseEntity<ResponseVo<JSONObject> >getNFTList(@RequestParam int limit,@RequestParam int skip ) throws IOException {
    return ResponseEntity.ok(ResponseVo.ok(creditCardReadService.getNFTList(limit,skip)));
  }

  @GetMapping("exists")
  public ResponseEntity<ResponseVo<Boolean>> exists(@RequestParam BigInteger tokenId){
    return ResponseEntity.ok(ResponseVo.ok(creditCardReadService.exists(tokenId)));
  }

  @GetMapping("name")
  public ResponseEntity<ResponseVo<String>> name(){
    return ResponseEntity.ok(ResponseVo.ok(creditCardReadService.name()));
  }
  @GetMapping("owner")
  public ResponseEntity<ResponseVo<String>> owner(){
    return ResponseEntity.ok(ResponseVo.ok(creditCardReadService.owner()));
  }
  @GetMapping("symbol")
  public ResponseEntity<ResponseVo<String>> symbol(){
    return ResponseEntity.ok(ResponseVo.ok(creditCardReadService.symbol()));
  }
  @GetMapping("paused")
  public ResponseEntity<ResponseVo<Boolean>> paused(){
    return ResponseEntity.ok(ResponseVo.ok(creditCardReadService.paused()));
  }
  @GetMapping("supportsInterface")
  public ResponseEntity<ResponseVo<Boolean>> supportsInterface(@RequestParam String interfaceId){
    return ResponseEntity.ok(ResponseVo.ok(creditCardReadService.supportsInterface(interfaceId)));
  }
  @GetMapping("isApprovedForAll")
  public ResponseEntity<ResponseVo<Boolean>> isApprovedForAll(@RequestParam String accountAddress,@RequestParam String operatorAddress){
    return ResponseEntity.ok(ResponseVo.ok(creditCardReadService.isApprovedForAll(accountAddress,operatorAddress)));
  }
  @GetMapping("totalSupply")
  public ResponseEntity<ResponseVo<BigInteger>> totalSupply(){
    return ResponseEntity.ok(ResponseVo.ok(creditCardReadService.totalSupply()));
  }
}
