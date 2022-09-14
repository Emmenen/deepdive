package com.example.sdk.utils;

import com.example.sdk.conflux.DynamicStaticArray;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.StaticArray;
import org.web3j.abi.datatypes.generated.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created at 2022/8/21 10:37
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
public class ConfluxUtils {

  public static DynamicArray<Uint256> intArrayToUint256Array(BigInteger[] array){
    List<Uint256> tokenIdList = new ArrayList<>();
    for (int i = 0; i < array.length; i++) {
      tokenIdList.add(new Uint256(array[i]));
    }

    return new DynamicArray<Uint256>(Uint256.class,tokenIdList);
  }


//  public static StaticArray<Uint256> intArrayToUint256Array(BigInteger[] array){
//    List<Uint256> tokenIdList = new ArrayList<>();
//    for (int i = 0; i < array.length; i++) {
//      tokenIdList.add(new Uint256(array[i]));
//    }
//
//    return new DynamicStaticArray<Uint256>(Uint256.class,array.length,tokenIdList);
//  }

  public static StaticArray<Address> stringArrayToAddressArray(String[] addressArray){
    List<Address> addressList = new ArrayList<>();
    for (int i = 0; i < addressArray.length; i++) {
      addressList.add(new Address(addressArray[i]));
    }

    return new DynamicStaticArray<Address>(Address.class,addressArray.length,addressList);
  }

//  public static StaticArray getStaticArray(Class classes, int size, ){
//    StaticArray1 staticArray1 = new StaticArray1(classes);
//    switch (size){
//      case 1: return ;
//    }
//  }
}
