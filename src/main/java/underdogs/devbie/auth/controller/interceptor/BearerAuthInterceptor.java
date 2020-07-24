package underdogs.devbie.auth.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.auth.controller.interceptor.utils.AuthorizationExtractor;
import underdogs.devbie.auth.controller.interceptor.utils.InterceptorValidator;
import underdogs.devbie.auth.jwt.JwtTokenProvider;

@Component
@RequiredArgsConstructor
public class BearerAuthInterceptor implements HandlerInterceptor {

    private final AuthorizationExtractor authExtractor;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println("그럼요기는");
        InterceptorValidator interceptorValidator = new InterceptorValidator();
        if (!interceptorValidator.isValid(handler)) {
            return true;
        }
        System.out.println("여기들어온겨?");
        String token = authExtractor.extract(request, "bearer");
        String userId = jwtTokenProvider.extractValidSubject(token);

        request.setAttribute("userId", userId);
        return true;
    }
}
