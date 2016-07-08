package com.clown.framework.utils;

import com.clown.framework.core.ApplicationContextUtil;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lenli on 2016/7/8.
 *Freemarker 模板工具类
 * @Author Libin
 * @Date 2016/7/8
 */
public class FreemarkerUtil {

    private static final Logger logger = LoggerFactory.getLogger(FreemarkerUtil.class);

    /**
     * 根据模板文件来生成文件
     * @param templatePath
     * @param params
     * @param targetFile
     * @return
     */
    public static boolean createFile(String templatePath,Object params,String targetFile){
        FreeMarkerConfigurer freeMarkerConfigurer = (FreeMarkerConfigurer) ApplicationContextUtil.getBean(FreeMarkerConfigurer.class);
        try {
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
            FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream,"UTF-8");
            Writer out = new BufferedWriter(outputStreamWriter);
            template.process(params,out);

            out.flush();

            fileOutputStream.close();
            outputStreamWriter.close();
            out.close();


            logger.info("创建文件成功:{}",targetFile);
            return true;
        } catch (IOException e) {
            logger.info("FreemarkerUtil::createFile",e);
        }
        catch (TemplateException e){
            logger.info("FreemarkerUtil::createFile",e);
        }
        return false;
    }



    public static void main(String[] args) throws Exception{
        String dd = "name_user";
        Matcher matcher = Pattern.compile("_\\w").matcher(dd);
        String r = dd;
        while (matcher.find()){
            String v = matcher.group().replace("_","").toUpperCase();
            r = r.replaceFirst("_\\w",v);
            System.out.println(r);
        }

    }
}
