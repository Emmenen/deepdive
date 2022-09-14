package com.example.sdk.utils;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import sun.security.ec.ECOperations;
import sun.security.ec.ECPrivateKeyImpl;
import sun.security.ec.point.AffinePoint;
import sun.security.ec.point.MutablePoint;
import sun.security.util.math.ImmutableIntegerModuloP;
import sun.security.util.math.IntegerFieldModuloP;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.*;
import java.util.Base64;
import java.util.Optional;

/**
 * Created at 2022/7/3 14:35
 * Author: @Qi Long
 * email: 592918942@qq.com
 */
public class CryptoUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(CryptoUtils.class);

  private static final String ALGORITHM_MD5 = "MD5";
  private static final String ALGORITHM_SHA256 = "SHA-256";
  private static final String ALGORITHM_SHA1 = "SHA-1";
  private static final String CHAT_SET_UTF8 = "UTF-8";
  private static final String ENCODE_STRING_HEX = "hex";
  private static final String ENCODE_STRING_BASE64 = "base64";
  private static final String ENCODE_STRING_BASE58 = "base58";

  public static MessageDigest ripeMD160;
  public static MessageDigest sha256;

  static {
    Security.addProvider(new BouncyCastleProvider());
    try {
      ripeMD160 = MessageDigest.getInstance("RipeMD160");
      sha256 = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }

  //32个字符
  public static String encrypt_md5_hex(String str) {
    return encrypt_hash_function(str, ALGORITHM_MD5, CHAT_SET_UTF8, ENCODE_STRING_HEX);
  }

  //24个字符
  public static String encrypt_md5_base64(String str) {
    return encrypt_hash_function(str, ALGORITHM_MD5, CHAT_SET_UTF8, ENCODE_STRING_BASE64);
  }

  //22个字符
  public static String encrypt_md5_base58(String str) {
    return encrypt_hash_function(str, ALGORITHM_MD5, CHAT_SET_UTF8, ENCODE_STRING_BASE58);
  }

  //40个字符
  public static String encrypt_sha1_hex(String str) {
    return encrypt_hash_function(str, ALGORITHM_SHA1, CHAT_SET_UTF8, ENCODE_STRING_HEX);
  }


  //28个字符
  public static String encrypt_sha1_base64(String str) {
    return encrypt_hash_function(str, ALGORITHM_SHA1, CHAT_SET_UTF8, ENCODE_STRING_BASE64);
  }

  //28个字符
  public static String encrypt_sha1_base58(String str) {
    return encrypt_hash_function(str, ALGORITHM_SHA1, CHAT_SET_UTF8, ENCODE_STRING_BASE58);
  }


  //64个字符
  public static String encrypt_sha256_hex(String str) {
    return encrypt_hash_function(str, ALGORITHM_SHA256, CHAT_SET_UTF8, ENCODE_STRING_HEX);
  }

  //44个字符
  public static String encrypt_sha256_base64(String str) {
    return encrypt_hash_function(str, ALGORITHM_SHA256, CHAT_SET_UTF8, ENCODE_STRING_BASE64);
  }

  //44个字符
  public static String encrypt_sha256_base58(String str) {
    return encrypt_hash_function(str, ALGORITHM_SHA256, CHAT_SET_UTF8, ENCODE_STRING_BASE58);
  }


  private static String encrypt_hash_function(String str, String algorithm, String chatset, String encodeMethod) {
    MessageDigest messageDigest;
    String encodeStr = "";
    try {
      messageDigest = MessageDigest.getInstance(algorithm);
      messageDigest.update(str.getBytes(chatset));

      byte[] digest_bytes = messageDigest.digest();

      if (ENCODE_STRING_BASE64.equals(encodeMethod)) {
        encodeStr = byte2Base64(digest_bytes);
      } else if (ENCODE_STRING_BASE58.equals(encodeMethod)) {
        encodeStr = Base58.encode(digest_bytes);
      } else {
        encodeStr = byte2Hex(digest_bytes);
      }

    } catch (Exception e) {
      LOGGER.error("", e);
    }
    return encodeStr;
  }


  public static String byte2Base64(byte[] bytes) {
    return Base64.getEncoder().encodeToString(bytes);
  }



  public static String byte2Hex(byte[] bytes) {
    StringBuffer stringBuffer = new StringBuffer();
    String temp;
    for (int i = 0; i < bytes.length; i++) {
      //& 0xFF 的目的是去bytes[i]的后8位，
      temp = Integer.toHexString(bytes[i] & 0xFF );
      if (temp.length() == 1) {
        stringBuffer.append("0");
      }
      stringBuffer.append(temp);
    }
    return stringBuffer.toString();
  }


}
