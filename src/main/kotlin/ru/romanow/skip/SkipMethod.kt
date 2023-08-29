package ru.romanow.skip

import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class SkipMethod(
    val valueProvider: KClass<out ObjectProvider<*>>,
    val skipOn: Array<Environments> = []
) {
    enum class Environments {
        DEV, STAGE, PROD
    }
}

interface ObjectProvider<T> {
    fun generate(): T
}
