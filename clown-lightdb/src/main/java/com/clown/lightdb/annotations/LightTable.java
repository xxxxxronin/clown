package com.clown.lightdb.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by len.li on 21/3/2016.
 */
@Target({TYPE})
@Retention(RUNTIME)
public @interface LightTable {
    public String name()  default "";
}
