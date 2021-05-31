package com.techzealot.tvm.hotspot.src.share.vm.oops.attr;

import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.support.AttributeInfo;
import lombok.Data;

@Data
public class Synthetic implements AttributeInfo {
    //u2
    private int attributeNameIndex;
    //u4 ,0
    private long attributeLength;
}
