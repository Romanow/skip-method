# Пропуск вызова метода в тестах

## Проблематика

Сделать исключение вызова конкретных методов и заменить их значением по-умолчанию.
Используется для исключения шагов при запуске тестов на разных окружениях.

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
        jvmArgs "-javaagent:${configurations.aspectjWeaverAgent.singleFile}"
    }
}
```

В [META-INF/aop.xml](src/test/resources/META-INF/aop.xml) создать описание используемых аспектов.