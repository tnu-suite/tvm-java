package com.techzealot.tvm.hotspot.src.share.vm.oops.attr.support;

import lombok.Data;

@Data
public abstract class AbstractRuntimeTypeAnnotations implements AttributeInfo {
    //u2
    private int attributeNameIndex;
    //u4
    private long attributeLength;
    //u4
    private int numAnnotations;
    //ux
    private TypeAnnotation[] typeAnnotations;
}
