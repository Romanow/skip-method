package ru.romanow.skip

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.romanow.skip.SkipMethod.Environments.*

class SimpleTest {

    @Test
    fun test() {
        assertEquals(buildDefault(), DummyObjectFactory.createObject())
    }
}

object DummyObjectFactory {
    @SkipMethod(skipOn = [DEV, PROD])
    fun createObject(): DummyObject {
        return DummyObject("A", "B")
    }
}