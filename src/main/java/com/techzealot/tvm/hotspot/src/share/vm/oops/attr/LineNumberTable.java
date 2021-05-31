package com.techzealot.tvm.hotspot.src.share.vm.oops.attr;

import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.support.AttributeInfo;
import lombok.Data;

@Data
public class LineNumberTable implements AttributeInfo {
    //u2
    private int attributeNameIndex;
    //u4
    private long attributeLength;
    //u2
    private int lineNumberTableLength;
    //ux
    private LineNumberTableInfo[] lineNumberTableInfos;

    @Data
    public static class LineNumberTableInfo {
        //u2
        private int startPc;
        //u2
        private int lineNumber;
    }
}
