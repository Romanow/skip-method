package ru.romanow.skip

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import ru.romanow.skip.SkipMethod.Environments.*

class SimpleTest {

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

class IntegerProvider : ObjectProvider<Int> {
    override fun generate(): Int {
        return 10
    }
}

@SkipMethod(skipOn = [DEV], valueProvider = StringProvider::class)
fun skipOnDev(): String {
    return "Hello, world"
}

class StringProvider : ObjectProvider<String> {
    override fun generate(): String {
        return "default"
    }

}

@SkipMethod(skipOn = [PROD], valueProvider = VoidProvider::class)
fun skipOnProd(arr: Array<Int>) {
    arr.reverse()
}

class VoidProvider : ObjectProvider<Unit> {
    override fun generate() {}
}