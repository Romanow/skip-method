package ru.romanow.skip

data class DummyObject(
    var a: String,
    var b: String
)

fun buildDefault() = DummyObject("DEFAULT_A", "DEFAULT_B")