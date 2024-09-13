package ru.romanow.skip.method

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import ru.romanow.skip.method.SkipMethod.Environments.DEV
import ru.romanow.skip.method.SkipMethod.Environments.PROD

internal class SkipMethodTest {

    @Test
    fun testSkipOnAll() {
        assertThat(skipOnAll()).isEqualTo(10)
    }

    @Test
    fun testSkipOnDev() {
        assertThat(skipOnDev()).isEqualTo("default")
    }

    @Test
    fun testSkipOnProd() {
        val arr = arrayOf(1, 2, 3, 4, 5)
        skipOnProd(arr)
        assertThat(arr).isEqualTo(arrayOf(5, 4, 3, 2, 1))
    }
}

@SkipMethod(skipOn = [DEV, PROD], valueProvider = IntegerProvider::class)
fun skipOnAll(): Int {
    return 100
}

@SkipMethod(skipOn = [DEV], valueProvider = StringProvider::class)
fun skipOnDev(): String {
    return "Hello, world"
}

@SkipMethod(skipOn = [PROD], valueProvider = VoidProvider::class)
fun skipOnProd(arr: Array<Int>) {
    arr.reverse()
}

class IntegerProvider : ObjectProvider<Int> {
    override fun generate() = 10
}

class StringProvider : ObjectProvider<String> {
    override fun generate() = "default"
}

class VoidProvider : ObjectProvider<Unit> {
    override fun generate() {}
}
