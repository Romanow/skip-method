[![CI](https://github.com/Romanow/skip-method/actions/workflows/build.yml/badge.svg)](https://github.com/Romanow/skip-method/actions/workflows/build.yml)
[![pre-commit](https://img.shields.io/badge/pre--commit-enabled-brightgreen?logo=pre-commit)](https://github.com/pre-commit/pre-commit)
[![Release](https://img.shields.io/github/v/release/Romanow/skip-method?logo=github&sort=semver)](https://github.com/Romanow/skip-method/releases/latest)
[![Codecov](https://codecov.io/gh/Romanow/skip-method/branch/master/graph/badge.svg?token=Cckw6pHLh7)](https://codecov.io/gh/Romanow/skip-method)
[![License](https://img.shields.io/github/license/Romanow/skip-method)](https://github.com/Romanow/skip-method/blob/master/LICENSE)

# Пропуск вызова метода в тестах

## Задача

Сделать исключение вызова конкретных методов (в коде) и заменить их значением по-умолчанию.
Используется для исключения вызова методов при запуске тестов на разных окружениях.

## Подключение

### Maven

```xml
<dependency>
  <groupId>ru.romanow-alex</groupId>
  <artifactId>skip-method</artifactId>
  <version>${skip-method.version}</version>
</dependency>
```

### Gradle

```groovy
testImplementation "ru.romanow-alex:skip-method:$skipMethodVersion"
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
