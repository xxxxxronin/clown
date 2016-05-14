package com.alka.example.restful;

import com.alka.example.models.UserModel;
import com.clown.framework.RestFulController;

import com.clown.lightdb.Mapper;
import org.springframework.web.bind.annotation.*;

/**
 * Created by len.li on 16/3/2016.
 * 所有继承RestFulController类的restful API访问路径自动加载restful前缀
 * 如下示例/v1/example 的正确访问为/restful/v1/example
 * GET POST PUT DELETE
 */
@RestController
@RequestMapping(value = {"/v1/example"})
public class ExampleRestful extends RestFulController {





    /**
     *@api GET /resetful/v1/example
     *@apiGroup 用户管理
     *@apiName len.li
     *@apiDescription
     *@apiParam
     *@Author len.li
     *@Date 2016-04-21 10:03:33
     *@params
     *@returns
    */

    @RequestMapping(value = "",method = {RequestMethod.GET})
    public Object index() throws Exception{



        UserModel userModel = new UserModel();
        userModel.setUserName("libin");
        userModel.setPasswd("000000");

        int count = Mapper.insert(userModel,"uid").exec();
//        userInfoModel = new UserInfoModel();
//        userInfoModel.setEmail("dfdfsd");
//        userInfoModel.setIsDisable(1);
//        userInfoModel.setMobile("4444");
//        userInfoModel.setUserName("4444");
//        userInfoModel.setUserPasswd("333333");
//        userInfoModel.setUserIp("455555555");
//        int count = Mapper.insert(userInfoModel).exec();
//        int count = Mapper.delete(UserInfoModel.class).where(new Condition("id",2)).exec();
//        Mapper.insert(userModel).exec();
//        Mapper.delete(UserModel.class).where(new Condition("id",17)).exec();
//        Mapper.update(userModel).where(new Condition("id",17)).exec();
//        userModel= Mapper.select(UserModel.class).where(new Condition("user_name","karl")).findOne(UserModel.class);

//          Pagination<UserModel> pages= Mapper.select(UserModel.class)
//                  .where(new Condition("id",4,Condition.Gle))
//                 .orderDESC("id").findPages(UserModel.class,1,5);

//        int count = Mapper.select(UserModel.class).where(new Condition("user_name","jimmy")).count();

//        List<UserVo> userVoList = Mapper.select(UserModel.class)
//                .fields("t.user_name as userName","u.phone as phone")
//                .join(new Join(UserInfoModel.class,"t.id = u.user_id","u"))
//                .where(new Condition("u","user_id",2))
//                .findList(UserVo.class);

//       Pagination<UserInfoModel> modelPagination= userDao.findUserModel("jimmy",new PageBounds(2,5));
        return userModel;
    }


}
