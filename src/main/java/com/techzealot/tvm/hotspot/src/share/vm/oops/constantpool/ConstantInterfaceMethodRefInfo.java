package com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool;

import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.support.AbstractConstantMemberRefInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data()
public class ConstantInterfaceMethodRefInfo extends AbstractConstantMemberRefInfo {
    //u1
    private final int tag = 11;

    public ConstantInterfaceMethodRefInfo(int classIndex, int nameAndTypeIndex) {
        super(classIndex, nameAndTypeIndex);
    }
}
