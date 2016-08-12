package com.alka.example.dao;

import com.alka.example.models.UserInfoModel;
import com.alka.example.models.UserModel;


/**
 * Created by len.li on 25/3/2016.
 */
public interface UserDao {
//    public Pagination<UserInfoModel> findUserModel(@Param("userName")String userName, PageBounds pageBounds) throws Exception;
    public int insertUser(UserModel userModel) throws Exception;


}
