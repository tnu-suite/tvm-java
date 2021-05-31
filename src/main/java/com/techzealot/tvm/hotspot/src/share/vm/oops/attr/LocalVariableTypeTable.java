package com.techzealot.tvm.hotspot.src.share.vm.oops.attr;

import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.support.AttributeInfo;
import lombok.Data;

@Data
public class LocalVariableTypeTable implements AttributeInfo {
    //u2
    private int attributeNameIndex;
    //u4
    private long attributeLength;
    //u2
    private int localVariableTypeTableLength;
    //ux
    private LocalVariableTypeTableInfo[] localVariableTypeTableInfos;

    @Data
    public static class LocalVariableTypeTableInfo {
        //u2
        private int startPc;
        //u2
        private int length;
        //u2
        private int nameIndex;
        //u2
        private int signatureIndex;
        //u2
        private int index;
    }
}
