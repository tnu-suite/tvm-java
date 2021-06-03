package com.techzealot.tvm.hotspot.src.share.vm.interpreter;

import com.techzealot.tvm.hotspot.src.share.vm.runtime.BasicType;
import lombok.Data;

@Data
public class DescriptorInfo {

    public static final DescriptorInfo BOOLEAN = new DescriptorInfo(BasicType.T_BOOLEAN, boolean.class);
    public static final DescriptorInfo BYTE = new DescriptorInfo(BasicType.T_BYTE, byte.class);
    public static final DescriptorInfo CHAR = new DescriptorInfo(BasicType.T_CHAR, char.class);
    public static final DescriptorInfo SHORT = new DescriptorInfo(BasicType.T_SHORT, short.class);
    public static final DescriptorInfo INT = new DescriptorInfo(BasicType.T_INT, int.class);
    public static final DescriptorInfo FLOAT = new DescriptorInfo(BasicType.T_FLOAT, float.class);
    public static final DescriptorInfo DOUBLE = new DescriptorInfo(BasicType.T_DOUBLE, double.class);
    public static final DescriptorInfo LONG = new DescriptorInfo(BasicType.T_LONG, long.class);
    public static final DescriptorInfo VOID = new DescriptorInfo(BasicType.T_VOID, void.class);
    // 类型
    private int type;
    // 类型,array类型时为null
    private Class<?> clazz;
    // 数组维度
    private int arrayDimension;
    //数组元素描述符
    private DescriptorInfo elementDescriptorInfo;

    /**
     * 对象类型,基本类型
     *
     * @param type
     * @param clazz
     */
    public DescriptorInfo(int type, Class<?> clazz) {
        this.type = type;
        this.clazz = clazz;
    }

    /**
     * 数组类型
     *
     * @param type
     * @param arrayDimension
     * @param elementDescriptorInfo
     */
    public DescriptorInfo(int type, int arrayDimension, DescriptorInfo elementDescriptorInfo) {
        this.type = type;
        this.arrayDimension = arrayDimension;
        this.elementDescriptorInfo = elementDescriptorInfo;
    }

    public void incArrayDimension() {
        arrayDimension++;
    }
}
