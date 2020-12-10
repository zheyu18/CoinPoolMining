package com.bc.bit.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;


/**
 * AES解密工具类
 */
public class AESUtil {
    /**
     * 密钥
     */
    private static String mKey="njk1!@bas31*@agv";
    /**
     * 加解密算法/工作模式/填充方式 AES/ECB/PKCS5Padding、AES/ECB/PKCS7Padding
     */
    private static String mTransformation="AES/ECB/PKCS5Padding";
    /**
     * 算法
     */
    private static String mAlgorithm="AES";
    /**
     * 编码格式
     */
    private static String mCharset="utf-8";

    /**
     * 解密
     * @param s
     * @return
     */
    public static String decrypt(String s) {
        return decodeAES(s);
    }
    /**
     * 加密
     * @param s
     * @return
     */
    public static String encrypt(String s) {
        return encryptAES(s);
    }

    /**
     * 加密
     * @param data
     * @return
     */
    private static String encryptAES(String data){
        try {
            byte[] raw =  mKey.getBytes(mCharset);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, mAlgorithm);
            Cipher cipher = Cipher.getInstance(mTransformation);
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(data.getBytes(mCharset));
            return encryptBASE64(encrypted);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密AES
     * @param data
     * @return
     * @throws Exception
     */
    private static String decodeAES(String data) {
        try {
            byte[] raw = mKey.getBytes(mCharset);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, mAlgorithm);
            Cipher cipher = Cipher.getInstance(mTransformation);
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = decryptBASE64(data);
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original, mCharset);
                return originalString;
            } catch (Exception e) {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * BASE64解密
     * @param key
     * @return
     * @throws Exception
     */
    private static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }
    /**
     * BASE64加密
     * @param key
     * @return
     */
    private static String encryptBASE64(byte[] key){
        return (new BASE64Encoder()).encodeBuffer(key);
    }

}
