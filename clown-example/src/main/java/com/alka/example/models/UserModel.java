package com.alka.example.models;


import com.clown.lightdb.annotations.LightTable;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Created by len.li on 21/3/2016.
 * user_id userId
 */
@LightTable(name = "ms_users")
public class UserModel {

    private Integer userId;


    private String userName;
    private String passwd;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
