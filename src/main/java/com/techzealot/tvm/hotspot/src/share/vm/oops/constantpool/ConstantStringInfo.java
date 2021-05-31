package com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool;

import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.support.ConstantPoolItem;
import lombok.Data;

@Data
public class ConstantStringInfo implements ConstantPoolItem {
    private final int tag = 8;
    private int stringIndex;

    public ConstantStringInfo(int stringIndex) {
        this.stringIndex = stringIndex;
    }
}
