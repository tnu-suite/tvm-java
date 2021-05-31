package com.techzealot.tvm.hotspot.src.share.vm.runtime;

import lombok.Data;

@Data
public class StackValue {
    private int type;

    private int val;

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
