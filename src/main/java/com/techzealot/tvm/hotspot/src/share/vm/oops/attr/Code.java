package com.techzealot.tvm.hotspot.src.share.vm.oops.attr;

import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.support.AttributeInfo;
import lombok.Data;

@Data
public class Code implements AttributeInfo {
    //u2
    private int attributeNameIndex;
    //u4
    private long attributeLength;
    //u2
    private int maxStack;
    //u2
    private int maxLocals;
    //u4
    private long codeLength;
    //u[codeLength]
    private byte[] code;
    //u2
    private int exceptionTableLength;
    //ux
    private ExceptionTable[] exceptionTables;
    //u2
    private int attributesCount;
    //ux
    private AttributeInfo[] attributes;

    @Data
    public static class ExceptionTable {
        //u2
        private int startPc;
        //u2
        private int endPc;
        //u2
        private int handlerPc;
        //u2
        private int catchType;
    }
}
