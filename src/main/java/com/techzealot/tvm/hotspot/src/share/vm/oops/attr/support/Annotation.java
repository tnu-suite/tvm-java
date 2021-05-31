package com.techzealot.tvm.hotspot.src.share.vm.oops.attr.support;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Annotation {
    //u2
    private int typeIndex;
    //u2
    private int numElementValuePairs;
    //ux
    private ElementValuePair[] elementValuePairs;

    public interface ElementValue {
        //u1
        int getTag();

    }

    @Data
    public static class ElementValuePair {
        //u2
        private int elementNameIndex;
        //ux
        private ElementValue elementValue;
    }

    @Data
    @AllArgsConstructor
    public static class ConstantValue implements ElementValue {
        //u1
        private int tag;
        //u2
        private int constantValueIndex;
    }

    @Data
    @AllArgsConstructor
    public static class EnumConstantValue implements ElementValue {
        //u1
        private final int tag = 'e';
        //u2
        private int typeNameIndex;
        //u2
        private int constantNameIndex;

    }

    @Data
    @AllArgsConstructor
    public static class ClassInfo implements ElementValue {
        //u1
        private final int tag = 'c';
        //u2
        private int classInfoIndex;
    }

    @Data
    @AllArgsConstructor
    public static class AnnotationValue implements ElementValue {
        //u1
        private final int tag = '@';
        //ux
        private Annotation annotationValue;
    }

    @Data
    @AllArgsConstructor
    public static class ArrayValue implements ElementValue {
        //u1
        private final int tag = '[';
        //u2
        private int numValues;
        //ux
        private ElementValue[] values;
    }


}