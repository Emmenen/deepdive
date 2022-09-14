package com.example.sdk.conflux;

import org.web3j.abi.datatypes.StaticArray;
import org.web3j.abi.datatypes.Type;

import java.util.List;

/**
 * Created at 2022/8/23 20:40
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
public class DynamicStaticArray <T extends Type> extends StaticArray<T> {
  public DynamicStaticArray(T... values) {
    super(values);
  }

  public DynamicStaticArray(int expectedSize, T... values) {
    super(expectedSize, values);
  }

  public DynamicStaticArray(List<T> values) {
    super(values);
  }

  public DynamicStaticArray(int expectedSize, List<T> values) {
    super(expectedSize, values);
  }

  public DynamicStaticArray(Class<T> type, T... values) {
    super(type, values);
  }

  public DynamicStaticArray(Class<T> type, int expectedSize, T... values) {
    super(type, expectedSize, values);
  }

  public DynamicStaticArray(Class<T> type, List<T> values) {
    super(type, values);
  }

  public DynamicStaticArray(Class<T> type, int expectedSize, List<T> values) {
    super(type, expectedSize, values);
  }

  //
//  public DynamicStaticArray(Class<T> type, List<T> values) {
//    super(type, 0, values);
//  }
//
//  @SafeVarargs
//  public DynamicStaticArray(Class<T> type, T... values) {
//    super(type, 0, values);
//  }

}
