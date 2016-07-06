package com.clown.framework.core;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;

/**
 * Created by lenli on 2016/7/6.
 *
 * @Author Libin
 * @Date 2016/7/6
 */
public class AppStart {
    public static void main(String[] args) throws Exception{
        File dd = new File("C:\\Users\\lenli\\Documents\\Tencent Files\\652231946\\FileRecv\\sfsd.ff");
        Resource resource = new FileSystemResource(dd);
        System.out.println(resource.getFilename());
    }
}
