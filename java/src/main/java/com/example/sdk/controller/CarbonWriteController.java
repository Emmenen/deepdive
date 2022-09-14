package com.example.sdk.controller;

import com.example.sdk.bean.form.ArgsForm;
import com.example.sdk.service.CarbonCreditCardWriteService;
import conflux.web3j.types.Address;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.sdk.bean.vo.ResponseVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;

/**
 * Created at 2022/8/18 15:34
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
@RestController
public class CarbonWriteController {

  @Autowired
  CarbonCreditCardWriteService writeService;

  @PostMapping("/mintNewNFT")
  public ResponseVo<String> mintNewNFT(@RequestParam String metaDataURI) throws Exception {
    return ResponseVo.ok(writeService.mintNewNFT(metaDataURI));
  }
  @PostMapping("/mintNewCoin")
  public ResponseVo<String> mintNewCoin(@RequestParam BigInteger amount,@RequestParam String metaDataURI) throws Exception {
    return ResponseVo.ok(writeService.mintNewCoin(amount,metaDataURI));
  }
  @PostMapping("/mintNewToken")
  public ResponseVo<String> mintNewToken(@RequestParam BigInteger amount,@RequestParam String metaDataURI) throws Exception {
    return ResponseVo.ok(writeService.mintNewCoin(amount,metaDataURI));
  }
  @PostMapping("/mintExistingCoin")
  public ResponseVo<String> mintNewNFT(@RequestParam BigInteger tokenId,@RequestParam BigInteger amount) throws Exception {
    return ResponseVo.ok(writeService.mintExistingCoin(tokenId,amount));
  }
  @PostMapping("/setTokenURI")
  public ResponseVo<String> setTokenURI(@RequestParam String tokenURI,@RequestParam BigInteger tokenId) throws Exception {
    return ResponseVo.ok(writeService._setTokenURI(tokenURI,tokenId));
  }
  @PostMapping("/burn")
  public ResponseVo<String> burn(@RequestBody ArgsForm argsForm) throws Exception {
    return ResponseVo.ok(writeService.burn(argsForm.getAccountAddressFrom(),argsForm.getTokenId(),argsForm.getAmount()));
  }
  @PostMapping("/switchPause")
  public ResponseVo<String> pause(@RequestParam boolean b) throws Exception {
    return ResponseVo.ok(writeService.switchPause(b));
  }
  @PostMapping("/renounceOwnership")
  public ResponseVo<String> renounceOwnership() throws Exception {
    return ResponseVo.ok(writeService.renounceOwnership());
  }
  @PostMapping("/safeBatchTransferFrom")
  public ResponseVo<String> safeBatchTransferFrom(@RequestBody ArgsForm argsForm) throws Exception {
    return ResponseVo.ok(writeService.safeBatchTransferFrom(argsForm.getAccountAddressFrom(),argsForm.getAccountAddressTo(), argsForm.getTokenIds(), argsForm.getAmounts(), argsForm.getData()));

  }
  @PostMapping("/safeTransferFrom")
  public ResponseVo<String> safeTransferFrom(@RequestBody ArgsForm argsForm) throws Exception {
    return ResponseVo.ok(writeService.safaTransferFrom(argsForm.getAccountAddressFrom(), argsForm.getAccountAddressTo(), argsForm.getTokenId(), argsForm.getAmount(), argsForm.getData()));
  }
  @PostMapping("/setApprovalForAll")
  public ResponseVo<String> setApprovalForAll(@RequestParam String operatorAddress,@RequestParam boolean operator) throws Exception {
    return ResponseVo.ok(writeService.setApprovalForAll(operatorAddress,operator));
  }
  @PostMapping("/setBaseURI")
  public ResponseVo<String> setBaseURI(@RequestParam String baseURI) throws Exception {
    return ResponseVo.ok(writeService.setBaseURI(baseURI));
  }
  @PostMapping("/transferOwnerShip")
  public ResponseVo<String> transferOwnerShip(@RequestParam String newOwnerAddress) throws Exception {
    return ResponseVo.ok(writeService.transferOwnerShip(newOwnerAddress));
  }
  @PostMapping("/withdraw")
  public ResponseVo<String> withdraw() throws Exception {
    return ResponseVo.ok(writeService.withdraw());
  }
}
