package underdogs.devbie.auth.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import io.jsonwebtoken.Claims;
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
        // todo : 이걸 왜 매번 생성하지 ? 동시성 문제가 발생할까 ?
        InterceptorValidator interceptorValidator = new InterceptorValidator();

        if (interceptorValidator.isNotValid(handler)) {
            return true;
        }

        String token = authExtractor.extract(request, "bearer");
        Claims claims = jwtTokenProvider.extractValidSubject(token);
        String userId = claims.get("userId").toString();
        String role = claims.get("role").toString();

        if (interceptorValidator.isValidRole(handler, role)) {
            return true;
        }

        request.setAttribute("userId", userId);
        return true;
    }
}
