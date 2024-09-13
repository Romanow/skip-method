package ru.romanow.skip.method

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import kotlin.reflect.full.createInstance

@Aspect
class SkipMethodAspect {
    private val skipOn = System.getProperty("skipOn")

    @Pointcut("@annotation(ru.romanow.skip.method.SkipMethod)")
    fun withStepAnnotation() {
    }

    @Pointcut("execution(* *(..))")
    fun anyMethod() {
    }

    @Around("anyMethod() && withStepAnnotation()")
    fun methodAround(joinPoint: ProceedingJoinPoint): Any? {
        var environment: SkipMethod.Environments? = null
        if (!skipOn.isNullOrEmpty()) {
            environment = SkipMethod.Environments.valueOf(skipOn)
        }

        val methodSignature = joinPoint.signature as MethodSignature
        val skipMethod = methodSignature.method.getAnnotation(SkipMethod::class.java)
        val objectProvider = skipMethod.valueProvider.createInstance()

        return if (environment in skipMethod.skipOn) {
            objectProvider.generate()
        } else {
            joinPoint.proceed()
        }
    }
}
