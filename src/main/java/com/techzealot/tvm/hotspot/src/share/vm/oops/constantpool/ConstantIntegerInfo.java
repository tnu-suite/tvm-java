package com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool;

import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.support.ConstantPoolItem;
import lombok.Data;

@Data
public class ConstantIntegerInfo implements ConstantPoolItem {
    //u1
    private final int tag = 3;
    //u4
    private int value;

    public ConstantIntegerInfo(int value) {
        this.value = value;
    }
}
