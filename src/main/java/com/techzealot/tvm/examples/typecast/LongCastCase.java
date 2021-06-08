package com.techzealot.tvm.examples.typecast;

public class LongCastCase {
    public static void main(String[] args) {
        print(Long.MIN_VALUE);
        print(-1L);
        print(Long.MAX_VALUE);
    }

    private static void print(long l) {
        byte b = (byte) l;
        short s = (short) l;
        char c = (char) l;
        int i = (int) l;
        float f = l;
        double d = l;
        System.out.println(l);
        System.out.println(b);
        System.out.println(s);
        System.out.println(c);
        System.out.println(i);
        System.out.println(f);
        System.out.println(d);
    }
}
