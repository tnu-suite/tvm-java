package com.techzealot.tvm.hotspot.src.share.vm.oops.attr.support;

import lombok.Data;

@Data
public abstract class AbstractRuntimeParameterAnnotations implements AttributeInfo {
    //u2
    private int attributeNameIndex;
    //u4
    private long attributeLength;
    //u4
    private int numParameters;
    //ux
    private ParameterAnnotation[] parameterAnnotations;

}
