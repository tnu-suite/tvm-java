package com.techzealot.tvm.examples.typecast;

public class IntegerCastCase {

    public static void main(String[] args) {
        print(Integer.MIN_VALUE);
        print(-1);
        print(Integer.MAX_VALUE);
    }

    private static void print(int i) {
        byte b = (byte) i;
        short s = (short) i;
        char c = (char) i;
        float f = i;
        long l = i;
        double d = i;
        System.out.println(i);
        System.out.println(b);
        System.out.println(s);
        System.out.println(c);
        System.out.println(f);
        System.out.println(l);
        System.out.println(d);
    }
}
