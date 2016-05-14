package com.clown.framework.utils;

import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;

/**
 * Created by len.li on 2016/4/27.
 */
public class ExceptionUtil {

    //MethodArgumentNotValidException
    public static String getMessage(Exception ex){
        String message = null;
        if(ex instanceof HttpRequestMethodNotSupportedException){
           message = ex.getMessage();
        }
        else if(ex instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) ex;
            FieldError fieldError = (FieldError) methodArgumentNotValidException.getBindingResult().getAllErrors().get(0);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(fieldError.getDefaultMessage());
            message = stringBuilder.toString();
        }
        else if(ex instanceof BindException){
            BindException bindException = (BindException) ex;
            FieldError fieldError = (FieldError) bindException.getAllErrors().get(0);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(fieldError.getDefaultMessage());
            message = stringBuilder.toString();
        }
        return message;
    }

}
