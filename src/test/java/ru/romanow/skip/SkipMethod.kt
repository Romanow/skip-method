package ru.romanow.skip

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class SkipMethod(
    val skipOn: Array<Environments> = [Environments.PROD]
) {
    enum class Environments {
        DEV, STAGE, PROD
    }
}
