package com.clown.orm.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by len.li on 21/3/2016.
 */
@Target({FIELD})
@Retention(RUNTIME)
public @interface LightColumn {
    public String value()  default "";
}
