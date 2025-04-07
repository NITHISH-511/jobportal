package com.job.jobportal.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    //Log execution of any method in com.examly.springapp.service package
    @Pointcut("execution(* com.examly.springapp.services.*.*(..))")
    public void serviceMethods() {
    }

    
    @Before("serviceMethods()")
    public void logBeforeMethodExecution() {
        logger.info("A service method is about to be executed...");
    }

    // successful method execution
    @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    public void logAfterReturning(Object result) {
        if (result != null) {
            logger.info("Method executed successfully. Result: {}", result);
        } else {
            logger.info("Method executed successfully. No result returned.");
        }
    }

    //  service method throws an exception
    @AfterThrowing(pointcut = "serviceMethods()", throwing = "ex")
    public void logAfterException(Exception ex) {
        logger.error("An error occurred: {}", ex.getMessage());
    }

    // execution time using @Around
    @Around("serviceMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed(); // Execute method
        long executionTime = System.currentTimeMillis() - start;
        logger.info("{} executed in {} ms", joinPoint.getSignature(), executionTime);
        return result;
    }
}
