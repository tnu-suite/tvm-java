package com.techzealot.tvm.hotspot.src.share.vm.oops.attr;

import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.support.AttributeInfo;
import lombok.Data;

@Data
public class InnerClass implements AttributeInfo {
    //u2
    private int attributeNameIndex;
    //u4
    private long attributeLength;
    //u2
    private int numberOfClasses;
    //ux
    private ClassAttr[] classes;

    @Data
    public static class ClassAttr {
        //u2
        private int innerClassInfoIndex;
        //u2
        private int outerClassInfoIndex;
        //u2
        private int innerNameIndex;
        //u2
        private int innerClassAccessFlags;
    }
}
