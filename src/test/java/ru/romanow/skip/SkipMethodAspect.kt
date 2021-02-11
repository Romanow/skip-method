package ru.romanow.skip

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature

@Aspect
class SkipMethodAspect {
    private val skipOn = System.getProperty("skipOn")

    @Pointcut("@annotation(ru.romanow.skip.SkipMethod)")
    fun withStepAnnotation() {
    }

    @Pointcut("execution(* *(..))")
    fun anyMethod() {
    }

    @Around("anyMethod() && withStepAnnotation()")
    fun methodAround(joinPoint: ProceedingJoinPoint): DummyObject {
        var environment: SkipMethod.Environments? = null
        if (!skipOn.isNullOrEmpty()) {
            environment = SkipMethod.Environments.valueOf(skipOn)
        }

        val methodSignature = joinPoint.signature as MethodSignature
        val skipMethod = methodSignature.method.getAnnotation(SkipMethod::class.java)
        return if (environment in skipMethod.skipOn) {
            buildDefault()
        } else {
            joinPoint.proceed() as DummyObject
        }
    }
}