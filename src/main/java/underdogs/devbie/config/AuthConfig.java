package underdogs.devbie.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;
import underdogs.devbie.auth.controller.interceptor.BearerAuthInterceptor;
import underdogs.devbie.auth.controller.resolver.LoginUserArgumentResolver;

@Configuration
@RequiredArgsConstructor
public class AuthConfig implements WebMvcConfigurer {

    private final BearerAuthInterceptor bearerAuthInterceptor;
    private final LoginUserArgumentResolver loginUserArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(bearerAuthInterceptor)
            .addPathPatterns("/api/**")
            .excludePathPatterns("/api/auth/login", "/api/auth/login-url");
            // .excludePathPatterns("/api/notices", "/api/notices/**");
    }

    @Override
    public void addArgumentResolvers(List argumentResolvers) {
        argumentResolvers.add(loginUserArgumentResolver);
    }
}
