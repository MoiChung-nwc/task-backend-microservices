package com.springsercurity.springsecurity.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OnlySelfAccess {

    String userIdParam();

}
