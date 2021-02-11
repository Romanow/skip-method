package ru.romanow.skip;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class SkipMethodAspect {

    @Pointcut("@annotation(ru.romanow.skip.SkipMethod)")
    public void withStepAnnotation() {}

    @Pointcut("execution(* *(..))")
    public void anyMethod() {}

    @Around("anyMethod() && withStepAnnotation()")
    public DummyObject methodAround(final ProceedingJoinPoint joinPoint) {
        return DummyObject.buildDefault();
    }
}
