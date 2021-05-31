package com.techzealot.tvm.hotspot.src.share.vm.oops.attr.support;

import lombok.Getter;

public enum AttributeEnum {
    /****
     * 5 attributes are critical to correct interpretation of the class file by the Java Virtual Machine
     */
    ConstantValue("ConstantValue"),
    Code("Code"),
    StackMapTable("StackMapTable"),
    Exceptions("Exceptions"),
    BootstrapMethods("BootstrapMethods"),
    /****
     * 12 attributes are critical to correct interpretation of the class file by the class libraries of the Java SE platform:
     */
    InnerClasses("InnerClasses"),
    EnclosingMethod("EnclosingMethod"),
    Synthetic("Synthetic"),
    Signature("Signature"),
    RuntimeVisibleAnnotations("RuntimeVisibleAnnotations"),
    RuntimeInvisibleAnnotations("RuntimeInvisibleAnnotations"),
    RuntimeVisibleParameterAnnotations("RuntimeVisibleParameterAnnotations"),
    RuntimeInvisibleParameterAnnotations("RuntimeInvisibleParameterAnnotations"),
    RuntimeVisibleTypeAnnotations("RuntimeVisibleTypeAnnotations"),
    RuntimeInvisibleTypeAnnotations("RuntimeInvisibleTypeAnnotations"),
    AnnotationDefault("AnnotationDefault"),
    MethodParameters("MethodParameters"),
    /***
     * 6 attributes are not critical to correct interpretation of the class file by either the Java Virtual Machine or the class libraries of the Java SE platform, but are useful for tools
     */
    SourceFile("SourceFile"),
    SourceDebugExtension("SourceDebugExtension"),
    LineNumberTable("LineNumberTable"),
    LocalVariableTable("LocalVariableTable"),
    LocalVariableTypeTable("LocalVariableTypeTable"),
    Deprecated("Deprecated");

    @Getter
    private final String value;

    AttributeEnum(String value) {
        this.value = value;
    }

    public static AttributeEnum getEnumByValue(String value) {
        AttributeEnum[] values = AttributeEnum.values();
        for (AttributeEnum attributeEnum : values) {
            if (attributeEnum.getValue().equals(value)) {
                return attributeEnum;
            }
        }
        return null;
    }
}
