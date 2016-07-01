package com.clown.framework.utils;

import java.security.MessageDigest;

/**
 * Created by lenli on 2016/6/15.
 *常用的加密工具类
 * @Author Libin
 * @Date 2016/6/15
 */
public class EncryptUtil {

    /**
     * md5 utf8加密
     * @param encryptString
     * @return
     */
    public static String md5(String encryptString){
        String result;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(encryptString.getBytes("UTF-8"));
            byte[] b = md.digest();

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                int i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        }
        catch (Exception e){
            result = "";
        }
        return result;
    }


    /**
     * 通用的密码加密工具方法
     * @param passwd
     * @return
     */
    public static final String password(String passwd){
        StringBuilder stringBuilder = new StringBuilder();
        String end = md5(passwd.substring(passwd.length()-2));
        passwd = md5(passwd+end.substring(end.length()-4));
        stringBuilder.append(passwd);
        stringBuilder.append(":");
        stringBuilder.append(end.substring(end.length()-2));
        return stringBuilder.toString();
    }

    public static void main(String[] args) throws Exception{

    }

}
