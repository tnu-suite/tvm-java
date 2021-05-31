package com.techzealot.tvm.hotspot.src.share.vm.oops.attr;

import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.support.AttributeInfo;
import lombok.Data;

@Data
public class StackMapTable implements AttributeInfo {
    //u2
    private int attributeNameIndex;
    //u4
    private long attributeLength;
    //u2
    private int numberOfEntries;
    //ux
    private StackMapFrame[] entries;

    public static class StackMapFrame {

    }
}
