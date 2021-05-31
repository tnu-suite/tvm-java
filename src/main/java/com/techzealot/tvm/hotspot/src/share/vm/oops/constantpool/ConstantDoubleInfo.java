package com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool;

import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.support.ConstantPoolItem;
import lombok.Data;

@Data
public class ConstantDoubleInfo implements ConstantPoolItem {
    //u1
    private final int tag = 6;
    //u8
    private double value;

    public ConstantDoubleInfo(double value) {
        this.value = value;
    }
}
