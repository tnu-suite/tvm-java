package com.techzealot.tvm.hotspot.src.share.vm.utilities;

public abstract class Bytes {

    public static int getHigher32BitsFromLong(long input) {
        return (int) (input >>> 32);
    }

    public static int getLower32BitsFromLong(long input) {
        return (int) (0x00000000ffffffffL & input);
    }

    public static long combine(int higher, int lower) {
        return ((0x00000000ffffffffL & higher) << 32) | (0x00000000ffffffffL & lower);
    }
}
