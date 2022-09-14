package com.example.sdk.common;

import com.example.sdk.bean.form.TokensArgs;
import org.web3j.abi.TypeReference;
import org.web3j.abi.TypeReference.StaticArrayTypeReference;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.StaticArray;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.StaticArray2;
import org.web3j.abi.datatypes.generated.Uint256;

/**
 * Created at 2022/8/24 9:20
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
public class Enums {
  public static final TypeReference<DynamicArray<Uint256>> TYPE_DYNAMIC_ARRAY_UINT256 = new TypeReference<DynamicArray<Uint256>>() {};
  public static final TypeReference<Utf8String> TYPE_STRING = new TypeReference<Utf8String>() {};
  public static final StaticArrayTypeReference<StaticArray<Uint256>> TYPE_STATIC_ARRAY2_UINT256 = new StaticArrayTypeReference<StaticArray<Uint256>>(2) {
    @Override
    public int getSize() {
      return super.getSize();
    }
  };
  public static final TypeReference TOKENS_VO = new TypeReference<TokensArgs>() {};
}
