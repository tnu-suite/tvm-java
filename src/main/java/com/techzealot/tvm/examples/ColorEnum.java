package com.techzealot.tvm.examples;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public enum ColorEnum {
    RED(1),
    BLUE(2);

    public static Map<Integer, ColorEnum> colors = new HashMap<>();

    static {
        for (ColorEnum value : ColorEnum.values()) {
            colors.put(value.getCode(), value);
        }
    }

    @Getter
    private int code;

    ColorEnum(int code) {
        this.code = code;
    }

}