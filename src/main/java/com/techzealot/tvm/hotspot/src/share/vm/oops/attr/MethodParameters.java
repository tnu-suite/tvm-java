package com.techzealot.tvm.hotspot.src.share.vm.oops.attr;

import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.support.AttributeInfo;
import lombok.Data;

@Data
public class MethodParameters implements AttributeInfo {
    //u2
    private int attributeNameIndex;
    //u4
    private long attributeLength;
    //u1
    private int parametersCount;
    //ux
    private Parameter[] parameters;

    @Data
    public static class Parameter {
        //u2
        private int nameIndex;
        //u2
        private int accessFlags;
    }
}
