package com.techzealot.tvm.hotspot.src.share.vm.oops.attr;

import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.support.AttributeInfo;
import lombok.Data;

@Data
public class EnclosingMethod implements AttributeInfo {
    //u2
    private int attributeNameIndex;
    //u4 4
    private long attributeLength;
    //u2
    private int classIndex;
    //u2
    private int methodIndex;
}