package com.techzealot.tvm.examples.typecast;

public class FloatCastCase {

    public static void main(String[] args) {
        print(Float.MIN_VALUE);
        print(-1.1f);
        print(Float.MAX_VALUE);
    }

    private static void print(float f) {
        byte b = (byte) f;
        short s = (short) f;
        char c = (char) f;
        int i = (int) f;
        long l = (long) f;
        double d = f;
        System.out.println(f);
        System.out.println(b);
        System.out.println(s);
        System.out.println(c);
        System.out.println(i);
        System.out.println(l);
        System.out.println(d);
    }
}
