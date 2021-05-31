package com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool;

import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.support.ConstantPoolItem;
import lombok.Data;

@Data
public class ConstantClassInfo implements ConstantPoolItem {
    //u1
    private final int tag = 7;
    //u2
    private int nameIndex;

    public ConstantClassInfo(int nameIndex) {
        this.nameIndex = nameIndex;
    }
}
