package com.clown.code.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lenli on 2016/7/8.
 *
 * @Author Libin
 * @Date 2016/7/8
 */
public class ConvertUtil {


    public static String converName(String preFix,String name,String enfix){
        name = name.replace(preFix,"").replace(enfix,"");
        Matcher matcher = Pattern.compile("_\\w").matcher(name);
        String r = name;
        while (matcher.find()){
            String v = matcher.group().replace("_","").toUpperCase();
            r = r.replaceFirst("_\\w",v);
            System.out.println(r);
        }
        return r;
    }

    public static String firstCap(String str){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str.substring(0,1).toUpperCase());
        stringBuilder.append(str.substring(1));
        return stringBuilder.toString();
    }




    public static void main(String[] args) throws Exception{
        System.out.println(converName("tsys_","tsys_menus",""));
    }
}
