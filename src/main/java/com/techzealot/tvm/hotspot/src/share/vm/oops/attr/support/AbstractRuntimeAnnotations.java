package com.techzealot.tvm.hotspot.src.share.vm.oops.attr.support;

import lombok.Data;

@Data
public abstract class AbstractRuntimeAnnotations implements AttributeInfo {
    //u2
    private int attributeNameIndex;
    //u4
    private long attributeLength;
    //u2
    private int numAnnotations;
    //ux
    private Annotation[] annotations;
}
