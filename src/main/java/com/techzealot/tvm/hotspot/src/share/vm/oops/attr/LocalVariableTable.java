package com.techzealot.tvm.hotspot.src.share.vm.oops.attr;

import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.support.AttributeInfo;
import lombok.Data;

@Data
public class LocalVariableTable implements AttributeInfo {
    //u2
    private int attributeNameIndex;
    //u4
    private long attributeLength;
    //u2
    private int localVariableTableLength;
    //ux
    private LocalVariableTableInfo[] localVariableTableInfos;


    @Data
    public static class LocalVariableTableInfo {
        //u2
        private int startPc;
        //u2
        private int length;
        //u2
        private int nameIndex;
        //u2
        private int descriptorIndex;
        //u2
        private int index;
    }
}
