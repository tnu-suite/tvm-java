package com.techzealot.tvm.examples.typecast;

public class BaseCastCase {

    public static void main(String[] args) {
        byte b = 127;
        print(b);
    }

    private static void print(byte b) {
        short s = b;
        char c = (char) b;
        int i = s;
        float f = i;
        long l = i;
        double d = f;
        System.out.println(b);
        System.out.println(s);
        System.out.println(c);
        System.out.println(i);
        System.out.println(f);
        System.out.println(l);
        System.out.println(d);
    }
}
