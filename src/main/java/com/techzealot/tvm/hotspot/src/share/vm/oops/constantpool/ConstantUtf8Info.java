package com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool;

import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.support.ConstantPoolItem;
import lombok.Data;

@Data
public class ConstantUtf8Info implements ConstantPoolItem {
    //u1
    private final int tag = 1;
    //u2
    private int length;
    //u[length]
    private String value;

    public ConstantUtf8Info(int length, String value) {
        this.length = length;
        this.value = value;
    }
}
