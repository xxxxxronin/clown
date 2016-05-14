package com.clown.framework;

/**
 * Created by len.li on 16/3/2016.
 */
public class DefaultAjaxResult<T> {


    /**
     * 错误代码
     */
    private Integer code;
    /**
     * 响应请求结果消息
     */
    private String info;
    /**
     * 响应请求结果数据
     */
    private  T data;

    public DefaultAjaxResult() {
    }

    public DefaultAjaxResult(Integer code, String info, T data) {
        this.code = code;
        this.info = info;
        this.data = data;

    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
