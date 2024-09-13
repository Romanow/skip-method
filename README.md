[![Build project](https://github.com/Romanow/skip-method/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/Romanow/skip-method/actions/workflows/build.yml)

# Пропуск вызова метода в тестах

## Задача

Сделать исключение вызова конкретных методов (в коде) и заменить их значением по-умолчанию.
Используется для исключения вызова методов при запуске тестов на разных окружениях.

## Конфигурация

Настроить поддержку AspectJ в [gradle](build.gradle):

```groovy
configurations {
    aspectjWeaverAgent
}

dependencies {
    aspectjWeaverAgent "org.aspectj:aspectjweaver:$aspectjVersion"
    testImplementation "org.aspectj:aspectjrt:$aspectjVersion"
}

test {
    useJUnitPlatform()
    systemProperty "skipOn", findProperty("skipOn")

    doFirst {
        jvmArgs "--add-opens",
                "java.base/java.lang=ALL-UNNAMED",
                "-javaagent:${configurations.aspectjWeaverAgent.singleFile}"
    }
}
```

В [META-INF/aop.xml](src/main/resources/META-INF/aop.xml) создать описание используемых аспектов.

## Использование

```kotlin
@SkipMethod(skipOn = [DEV, PROD], valueProvider = IntegerProvider::class)
fun skipOnAll(): Int {
    return 100
}

class IntegerProvider : ObjectProvider<Int> {
    override fun generate(): Int {
        return 10
    }
}
```

## Запуск

```shell
$ ./gradlew clean build -PskipOn=DEV
```
