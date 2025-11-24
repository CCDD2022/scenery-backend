package com.doubleshan.scenery.security;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RoleRequired {
    String[] value(); // e.g. {"ADMIN"}
}
