package com.zerobase.springbootjpapractice.common.aop;

import com.zerobase.springbootjpapractice.logs.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@RequiredArgsConstructor
@Slf4j
public class BoardLogger {

    private final LogService logService;

    @Around("execution(* com.zerobase.springbootjpapractice..*.*Controller.detail(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("#############################");
        log.info("컨트롤러 detail 호출 전");
        log.info("#############################");

        Object result = joinPoint.proceed();


        if (joinPoint.getSignature().getDeclaringTypeName().contains("ApiBoardController")
                && "detail".equals(joinPoint.getSignature().getName())) {
            StringBuilder sb = new StringBuilder();
            sb.append("파라미터 : ");
            Object[] args = joinPoint.getArgs();
            for(Object arg : args) {
                sb.append(arg.toString());
            }
            sb.append("결과 : ");
            sb.append(result.toString());

            logService.add(sb.toString());
            log.info(sb.toString());
        }

        log.info("#############################");
        log.info("컨트롤러 detail 호출 후");
        log.info("#############################");

        return result;
    }
}
