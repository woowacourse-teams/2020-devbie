package underdogs.devbie.auth.controller.interceptor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import type.RoleType;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Role {

    RoleType[] role() default {RoleType.ADMIN, RoleType.USER, RoleType.GUEST};
}
