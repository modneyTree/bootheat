package com.example.bootheat;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeTraceAop {

    @Around("execution(* com.example.bootheat..*(..))") // 전체 패키지 적용
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        try {
            return joinPoint.proceed(); // 실제 메서드 실행
        } finally {
            long end = System.currentTimeMillis();
            System.out.println("[AOP] " + joinPoint.getSignature() + " 실행 시간: " + (end - start));
        }
    }
}