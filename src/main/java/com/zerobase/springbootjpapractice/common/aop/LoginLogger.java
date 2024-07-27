package com.zerobase.springbootjpapractice.common.aop;

import com.zerobase.springbootjpapractice.logs.service.LogService;
import com.zerobase.springbootjpapractice.user.entity.User;
import com.zerobase.springbootjpapractice.user.model.UserLogin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@RequiredArgsConstructor
@Slf4j
public class LoginLogger {

    private final LogService logService;

    @Around("execution(* com.zerobase.springbootjpapractice..*.*Service*.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("#############################");
        log.info("Service 호출 전");
        log.info("#############################");

        Object result = joinPoint.proceed();

        if("login".equals(joinPoint.getSignature().getName())) {

            StringBuilder sb = new StringBuilder();

            sb.append("\n");
            sb.append("함수명: ").append(joinPoint.getSignature().getDeclaringTypeName()).append(", ").append(joinPoint.getSignature().getName());
            sb.append("\n");
            sb.append("매개변수: ");

            Object[] args = joinPoint.getArgs();
            for (Object arg : args) {
                if(arg instanceof UserLogin) {
                    sb.append(((UserLogin) arg).toString());
                    sb.append("\n");
                    sb.append("리턴값: "+((User) result).toString());
                }
            }

            logService.add(sb.toString());
            log.info(sb.toString());
        }


        log.info("#############################");
        log.info("Service 호출 후");
        log.info("#############################");

        return result;
    }
}
