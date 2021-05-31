package com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.support;

import lombok.Data;

@Data
public abstract class AbstractConstantMemberRefInfo implements ConstantPoolItem {

    //u2
    private int classIndex;
    //u2
    private int nameAndTypeIndex;

    public AbstractConstantMemberRefInfo(int classIndex, int nameAndTypeIndex) {
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }
}
