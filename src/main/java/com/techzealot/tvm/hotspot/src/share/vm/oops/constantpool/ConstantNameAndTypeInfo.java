package com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool;

import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.support.ConstantPoolItem;
import lombok.Data;

@Data
public class ConstantNameAndTypeInfo implements ConstantPoolItem {
    //u1
    private final int tag = 12;
    //u2
    private int nameIndex;
    //u2
    private int descriptorIndex;

    public ConstantNameAndTypeInfo(int nameIndex, int descriptorIndex) {
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
    }
}
