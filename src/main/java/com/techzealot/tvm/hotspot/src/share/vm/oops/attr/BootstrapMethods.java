package com.techzealot.tvm.hotspot.src.share.vm.oops.attr;

import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.support.AttributeInfo;
import lombok.Data;

@Data
public class BootstrapMethods implements AttributeInfo {
    //u2
    private int attributeNameIndex;
    //u4
    private long attributeLength;
    //u2
    private int numBootstrapMethods;
    //ux
    private BootstrapMethod[] bootstrapMethods;

    @Data
    public static class BootstrapMethod {
        //u2
        private int bootstrapMethodRef;
        //u2
        private int numBootstrapArguments;
        //u2[x]
        private int[] bootstrapArguments;
    }
}
