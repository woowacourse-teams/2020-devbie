package underdogs.devbie.auth.controller.resolver;

import static org.springframework.web.context.request.RequestAttributes.*;

import java.util.Objects;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import underdogs.devbie.auth.exception.InvalidAuthorizationException;
import underdogs.devbie.user.domain.Role;

@Component
public class AdminUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AdminUser.class);
    }

    @Override
    public Boolean resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String role = (String)webRequest.getAttribute("role", SCOPE_REQUEST);

        if (!Objects.equals(role, Role.ADMIN.name())) {
            throw new InvalidAuthorizationException();
        }
        return true;
    }
}
