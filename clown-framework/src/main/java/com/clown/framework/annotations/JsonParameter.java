package com.clown.framework.annotations;

import java.lang.annotation.*;

/**
 * Created by lenli on 16/4/27.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonParameter {

    String value();
}
