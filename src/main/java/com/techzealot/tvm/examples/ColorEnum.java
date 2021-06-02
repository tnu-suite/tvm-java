package com.techzealot.tvm.examples;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum ColorEnum {
    RED(1),
    BLUE(2);

    @Getter
    private int code;

    public static Map<Integer, ColorEnum> colors = new HashMap<>();

    ColorEnum(int code) {
        this.code = code;
    }

    static {
        for (ColorEnum value : ColorEnum.values()) {
            colors.put(value.getCode(), value);
        }
    }

}