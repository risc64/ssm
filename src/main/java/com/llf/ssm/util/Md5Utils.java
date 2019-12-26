package com.llf.ssm.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Utils {
	// 全局数组
    private final static String[] STRDIGITS_AD = { "m", "s", "s", "y", "z", "d",
            "1", "s", "h", "a", "9", "7", "6", "3", "5", "8" };

    private final static String[] STRDIGITS_PAGE = { "E", "D", "N", "X", "Y", "4",
            "1", "H", "8", "A", "9", "7", "C", "V", "5", "8" };
    public Md5Utils() {
    }

    // 返回形式为数字跟字符串
    private static String byteToArrayString(byte bByte, String[] strArray) {
        int iRet = bByte;
        //System.out.println("iRet="+iRet);
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return strArray[iD1] + strArray[iD2];
    }  

    // 转换字节数组为16进制字串
    private static String byteToString(byte[] bByte, String[] strArray) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i], strArray));
        }
        return sBuffer.toString();
    }

    public static String GetMD5Code(String strObj) {
        String resultString = null;
        try {
            resultString = new String(strObj);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(strObj.getBytes()), STRDIGITS_AD);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }
    
    public static String GetMD5Code2(String str) {
    		String resultString = null;
        try {
            resultString = new String(str);
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            resultString = byteToString(md.digest(str.getBytes()), STRDIGITS_PAGE);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return resultString;
    }
}
