package com.techzealot.tvm.examples;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HelloWorldTest {
    @Test
    public void test() {
        Integer a = null;
        boolean b = (a == null);
        Assertions.assertTrue(b);
    }
}
