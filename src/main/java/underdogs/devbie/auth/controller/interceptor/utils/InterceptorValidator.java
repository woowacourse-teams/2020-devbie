package underdogs.devbie.auth.controller.interceptor.utils;

import java.util.Objects;

import org.springframework.web.method.HandlerMethod;

import underdogs.devbie.auth.controller.interceptor.annotation.NoValidate;

public class InterceptorValidator {

    public boolean isNotValid(Object handler) {
        NoValidate noValidate = ((HandlerMethod)handler).getMethodAnnotation(NoValidate.class);
        return Objects.nonNull(noValidate);
    }
}
