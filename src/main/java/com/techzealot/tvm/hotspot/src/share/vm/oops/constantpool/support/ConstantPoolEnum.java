package com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.support;

import lombok.Getter;

public enum ConstantPoolEnum {
    JvmConstantUtf8(1),
    /**
     * unused
     */
    JvmConstantUnicode(2),
    JvmConstantInteger(3),
    JvmConstantFloat(4),
    JvmConstantLong(5),
    JvmConstantDouble(6),
    JvmConstantClass(7),
    JvmConstantString(8),
    JvmConstantFieldRef(9),
    JvmConstantMethodRef(10),
    JvmConstantInterfaceMethodRef(11),
    JvmConstantNameAndType(12),
    JvmConstantMethodHandle(15),
    JvmConstantMethodType(16),
    JvmConstantInvokeDynamic(18),
    ;

    @Getter
    private final int value;

    ConstantPoolEnum(int value) {
        this.value = value;
    }

    public static ConstantPoolEnum getEnumByValue(int value) {
        ConstantPoolEnum[] values = ConstantPoolEnum.values();
        for (ConstantPoolEnum constantPoolEnum : values) {
            if (constantPoolEnum.getValue() == value) {
                return constantPoolEnum;
            }
        }
        return null;
    }
}
