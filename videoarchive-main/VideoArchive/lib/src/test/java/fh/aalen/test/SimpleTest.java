package fh.aalen.test;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SimpleTest {
    private String value;

    @BeforeMethod
    public void setup() {
        value = "set";
    }

    @Test
    public void testValue() {
        assert "set".equals(value);
    }
}