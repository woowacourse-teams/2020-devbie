package underdogs.devbie.aop;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class ServiceLoggingAspect {

    @Pointcut("execution(* underdogs.devbie..*Service.*(..))")
    public void loggerPointCut() {
    }

    @Around("loggerPointCut()")
    public Object methodLogger(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try {
            Object result = proceedingJoinPoint.proceed();

            String serviceName = proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName();
            String methodName = proceedingJoinPoint.getSignature().getName();

            Map<String, Object> params = new HashMap<>();

            try {
                params.put("service", serviceName);
                params.put("method", methodName);
                params.put("params", Arrays.toString(proceedingJoinPoint.getArgs()));
                params.put("log_time", new Date());
            } catch (Exception e) {
                log.error("LoggerAspect error", e);
            }
            log.info("params : {}", params);

            return result;

        } catch (Throwable throwable) {
            log.error("AOP proceeding error", throwable);
            throw throwable;
        }
    }
}
