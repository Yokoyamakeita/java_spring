package com.example.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    @Before("execution(String com.example.demo.controller.HomeController.showList(..))")
    public void startLog(JoinPoint jp) {
        log.info("{}: Before処理", jp.getSignature());
    }

    @After("execution(* com.example.demo.controller.HomeController.*(..))")
    public void endLog(JoinPoint jp) {
        log.info("{}: After処理", jp.getSignature());
    }

    @Around("execution(* com.example.demo..*(..))")
    public Object startAndEndLog(ProceedingJoinPoint pjp)
            throws Throwable {

        log.info("{}: Around前処理", pjp.getSignature());
        var result = pjp.proceed();
        log.info("{}: Around後処理", pjp.getSignature());

        return result;
    }

    @AfterReturning(
            pointcut = "within(com.example.demo.controller.*Controller)",
            returning = "result")
    public void afterReturning(JoinPoint jp, Object result) {
        log.info("{}: return結果 = {}", jp.getSignature(), result);
    }

    @AfterThrowing(
            pointcut = "bean(homeController)",
            throwing = "e")
    public void afterThrowing(JoinPoint jp, Throwable e) {
        log.error("{}: 処理中に例外が発生しました: {}",
                jp.getSignature(), e.getMessage());
    }
}