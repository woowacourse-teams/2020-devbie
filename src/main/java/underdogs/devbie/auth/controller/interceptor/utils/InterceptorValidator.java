package underdogs.devbie.auth.controller.interceptor.utils;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.web.method.HandlerMethod;

import underdogs.devbie.auth.controller.interceptor.annotation.NoValidate;
import underdogs.devbie.auth.controller.interceptor.annotation.Role;
import underdogs.devbie.auth.exception.InvalidAuthorizationException;
import underdogs.devbie.user.domain.RoleType;

public class InterceptorValidator {

    public boolean isNotValid(Object handler) {
        NoValidate noValidate = ((HandlerMethod)handler).getMethodAnnotation(NoValidate.class);
        return Objects.nonNull(noValidate);
    }

    public boolean hasRoleAnnotation(Object handler) {
        Role methodAnnotation = ((HandlerMethod)handler).getMethodAnnotation(Role.class);
        return Objects.nonNull(methodAnnotation);
    }

    public void validateRole(Object handler, String role) {
        Role methodAnnotation = ((HandlerMethod)handler).getMethodAnnotation(Role.class);
        RoleType roleType = RoleType.valueOf(role);

        if (!Arrays.asList(methodAnnotation.role())
            .contains(roleType)) {
            throw new InvalidAuthorizationException();
        }
    }
}
