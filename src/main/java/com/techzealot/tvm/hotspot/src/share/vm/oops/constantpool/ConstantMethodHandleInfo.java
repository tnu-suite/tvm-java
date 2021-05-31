package com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool;

import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.support.ConstantPoolItem;
import lombok.Data;

@Data
public class ConstantMethodHandleInfo implements ConstantPoolItem {
    //u1
    private final int tag = 15;
    //u1 范围:[1,9]
    private int referenceKind;
    //u2
    private int referenceIndex;

    public ConstantMethodHandleInfo(int referenceKind, int referenceIndex) {
        this.referenceKind = referenceKind;
        this.referenceIndex = referenceIndex;
    }
}
