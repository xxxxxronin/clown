package com.alka.example.models;


import com.clown.lightdb.annotations.LightTable;

/**
 * Created by len.li on 21/3/2016.
 * user_id userId
 */
@LightTable(name = "ms_users")
public class UserModel {
    private Integer id;


    private String userName;
    private String passwd;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
