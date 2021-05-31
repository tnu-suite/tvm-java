package com.techzealot.tvm.hotspot.src.share.vm.oops.attr;

import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.support.AttributeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConstantValue implements AttributeInfo {
    //u2
    private int attributeNameIndex;
    //u4 固定为2
    private long attributeLength;
    //u2
    private int constantValueIndex;

}
