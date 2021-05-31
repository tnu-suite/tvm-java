package com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool;

import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.support.ConstantPoolItem;
import lombok.Data;

@Data
public class ConstantFloatInfo implements ConstantPoolItem {
    //u1
    private final int tag = 4;
    //u4
    private float value;

    public ConstantFloatInfo(float value) {
        this.value = value;
    }
}
