package com.yumyum.sns.aop;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExecutionTimeAOP {

    private static final Logger logger = LoggerFactory.getLogger(ExecutionTimeAOP.class);

    // 패키지 경로 및 클래스명, 메서드명에 따라 조정 가능
    @Around("execution(* com.yumyum.sns.*.service..*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();  // 대상 메서드 실행

        long end = System.currentTimeMillis();
        long duration = end - start;

        logger.info("[ExecutionTime] {}#{} executed in {} ms",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                duration);

        return result;
    }
}
