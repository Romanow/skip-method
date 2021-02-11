package ru.romanow.skip;

import lombok.Data;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleTest {

    @Test
    public void test() {
        assertThat(DummyObject.buildDefault())
                .isEqualTo(DummyObjectFactory.createObject());
    }
}

class DummyObjectFactory {

    @SkipMethod
    public static DummyObject createObject() {
        return new DummyObject("A", "B");
    }
}

@Data
class DummyObject {
    private final String a;
    private final String b;

    public static DummyObject buildDefault() {
        return new DummyObject("DEFAULT_A", "DEFAULT_B");
    }
}
