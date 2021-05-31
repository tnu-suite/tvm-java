package com.techzealot.tvm.hotspot.src.share.vm.oops.attr.support;

import lombok.Data;

@Data
public class ParameterAnnotation {
    //u2
    private int numAnnotations;
    //ux
    private Annotation[] annotations;
}