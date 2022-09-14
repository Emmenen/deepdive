package com.example.sdk.bean.form;

import com.example.sdk.conflux.DynamicStaticArray;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.web3j.abi.datatypes.DynamicStruct;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created at 2022/8/29 22:07
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
@Data
@NoArgsConstructor
public class TokensArgs extends DynamicStruct {
  private BigInteger count = new BigInteger("0");
  private List<BigInteger> list = new ArrayList<>();

  public TokensArgs(BigInteger count, List<BigInteger> list) {
    super(new Uint256(count));
    this.count = count;
    this.list = list;
  }

  public TokensArgs(Uint256 count, DynamicStaticArray list) {
    super(count);
    this.count = count.getValue();
    this.list = list.getValue();
  }


  public TokensArgs(BigInteger count, List<BigInteger> list, Type... values) {
    super(values);
    this.count = count;
    this.list = list;
  }

  public TokensArgs(Class<Type> type, BigInteger count, List<BigInteger> list, Type... values) {
    super(type, values);
    this.count = count;
    this.list = list;
  }
}
