package com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool;

import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.support.ConstantPoolItem;
import lombok.Data;

@Data
public class ConstantLongInfo implements ConstantPoolItem {

    //u1
    private final int tag = 5;
    //u8
    private long value;

    public ConstantLongInfo(long value) {
        this.value = value;
    }
}
