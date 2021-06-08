package com.techzealot.tvm.hotspot.src.share.vm.runtime;

import lombok.Data;

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
}
