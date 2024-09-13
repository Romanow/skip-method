[![Build project](https://github.com/Romanow/skip-method/actions/workflows/build.yml/badge.svg?branch=master)](https://github.com/Romanow/skip-method/actions/workflows/build.yml)
[![codecov](https://codecov.io/github/Romanow/skip-method/branch/master/graph/badge.svg?token=RXVXL3NUNS)](https://codecov.io/github/Romanow/skip-method)
[![pre-commit](https://img.shields.io/badge/pre--commit-enabled-brightgreen?logo=pre-commit)](https://github.com/pre-commit/pre-commit)

# Пропуск вызова метода в тестах

## Задача

Сделать исключение вызова конкретных методов (в коде) и заменить их значением по-умолчанию.
Используется для исключения вызова методов при запуске тестов на разных окружениях.

## Подключение

### Maven

```xml
<dependency>
  <groupId>ru.romanow.skip.method</groupId>
  <artifactId>skip-method</artifactId>
  <version>${skip-method.version}</version>
</dependency>
```

### Gradle

```groovy
testImplementation "ru.romanow.skip.method:skip-method:$skipMethodVersion"
```


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
