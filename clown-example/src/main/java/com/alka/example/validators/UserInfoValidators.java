package com.alka.example.validators;

import com.alka.example.models.UserInfoModel;
import com.alka.example.models.UserModel;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Created by lenli on 16/4/27.
 */
public class UserInfoValidators implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return UserModel.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(target != null){
            UserModel userModel = (UserModel) target;
            if(userModel.getUserName()==null ||  userModel.getUserName().length()<=0){
                errors.rejectValue("userName","message");
            }
        }

    }
}
