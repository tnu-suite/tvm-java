package com.techzealot.tvm.hotspot.src.share.vm.runtime;

import lombok.Data;

import java.text.MessageFormat;

@Data
public class StackValue {
    private int type;

    /**
     * boolean byte short char int float
     */
    private int val;

    /**
     * long采用byte数组;
     * double采用2个StackValue表示
     */
    private byte[] data;

    /**
     * object
     */
    private Object object;

    public StackValue(int type, int val) {
        this.type = type;
        this.val = val;
    }

    public StackValue(int type, Object object) {
        this.type = type;
        this.object = object;
    }

    public StackValue(int type, float f) {
        this.type = type;
        this.val = Float.floatToIntBits(f);
    }

    public float getFloat() {
        if (BasicType.T_FLOAT != type) {
            throw new UnsupportedOperationException(MessageFormat.format("can not get as float on type {0}", type));
        }
        return Float.intBitsToFloat(val);
    }
}
