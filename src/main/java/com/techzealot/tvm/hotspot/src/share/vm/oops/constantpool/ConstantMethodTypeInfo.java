package com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool;

import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.support.ConstantPoolItem;
import lombok.Data;

@Data
public class ConstantMethodTypeInfo implements ConstantPoolItem {
    //u1
    private final int tag = 16;
    //u2
    private int descriptorIndex;

    public ConstantMethodTypeInfo(int descriptorIndex) {
        this.descriptorIndex = descriptorIndex;
    }
}
