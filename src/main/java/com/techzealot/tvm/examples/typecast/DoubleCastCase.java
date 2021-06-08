package com.techzealot.tvm.examples.typecast;

public class DoubleCastCase {
    public static void main(String[] args) {
        print(Double.MIN_VALUE);
        print(-1.0);
        print(Double.MAX_VALUE);
    }

    private static void print(double d) {
        byte b = (byte) d;
        short s = (short) d;
        char c = (char) d;
        int i = (int) d;
        float f = (float) d;
        long l = (long) d;
        System.out.println(d);
        System.out.println(b);
        System.out.println(s);
        System.out.println(c);
        System.out.println(i);
        System.out.println(f);
        System.out.println(l);
        System.out.println(d);
    }
}
