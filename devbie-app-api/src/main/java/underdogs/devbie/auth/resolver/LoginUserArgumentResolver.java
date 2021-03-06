package underdogs.devbie.auth.resolver;

import static org.springframework.web.context.request.RequestAttributes.*;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.auth.LoginUser;
import underdogs.devbie.auth.exception.LoginUserNotFoundException;
import underdogs.devbie.user.domain.User;
import underdogs.devbie.user.service.UserService;

@Component
@RequiredArgsConstructor
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public User resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String userId = (String)webRequest.getAttribute("userId", SCOPE_REQUEST);

        try {
            return userService.findById(Long.parseLong(userId));
        } catch (Exception e) {
            throw new LoginUserNotFoundException();
        }
    }
}
