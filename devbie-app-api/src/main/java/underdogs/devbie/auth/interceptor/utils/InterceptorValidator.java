package underdogs.devbie.auth.interceptor.utils;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import underdogs.devbie.auth.NoValidate;
import underdogs.devbie.auth.Role;
import underdogs.devbie.auth.exception.NotExistUserRoleException;
import underdogs.devbie.user.RoleType;

@Component
public class InterceptorValidator {

    public boolean isNotValid(Object handler) {
        NoValidate noValidate = ((HandlerMethod)handler).getMethodAnnotation(NoValidate.class);
        return Objects.nonNull(noValidate);
    }

    public void validateRole(Object handler, String role) {
        Role methodAnnotation = ((HandlerMethod)handler).getMethodAnnotation(Role.class);
        RoleType roleType = RoleType.valueOf(role);

        if (Objects.isNull(methodAnnotation)) {
            return;
        }

        validateUserRole(methodAnnotation, roleType);
    }

    private void validateUserRole(Role methodAnnotation, RoleType roleType) {
        if (!Arrays.asList(methodAnnotation.role())
            .contains(roleType)) {
            throw new NotExistUserRoleException();
        }
    }
}