package com.techzealot.tvm.hotspot.src.share.vm.oops.attr.support;

import lombok.Getter;

public enum AnnotationDescriptorEnum {
    BooleanType('Z'),
    ByteType('B'),
    CharType('C'),
    ShortType('S'),
    IntType('I'),
    FloatType('F'),
    LongType('J'),
    Double('D'),
    StringType('s'),
    EnumType('e'),
    ClassType('c'),
    AnnotationType('@'),
    ArrayType('[');
    @Getter
    private final int tag;

    AnnotationDescriptorEnum(int tag) {
        this.tag = tag;
    }

    public static AnnotationDescriptorEnum getEnumByTag(int tag) {
        AnnotationDescriptorEnum[] values = AnnotationDescriptorEnum.values();
        for (AnnotationDescriptorEnum value : values) {
            if (value.getTag() == tag) {
                return value;
            }
        }
        return null;
    }
}
