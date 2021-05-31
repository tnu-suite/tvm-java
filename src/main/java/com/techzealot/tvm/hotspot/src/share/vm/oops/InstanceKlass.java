package com.techzealot.tvm.hotspot.src.share.vm.oops;

import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.support.AttributeInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class InstanceKlass extends Klass {
    //u4
    private int magic;
    //u2
    private int minorVersion;
    //u2
    private int majorVersion;
    //ux
    private ConstantPool constantPool;

    //u2
    private int accessFlags;
    //u2
    private int thisClass;
    //u2
    private int supperClass;

    //u2 unsigned
    private int interfacesCount;
    //ux
    private InterfaceInfo[] interfaces;

    //u2 unsigned
    private int fieldsCount;
    //ux
    private FieldInfo[] fields;

    //u2 unsigned
    private int methodsCount;
    //ux
    private MethodInfo[] methods;

    //u2 unsigned
    private int attributesCount;
    //ux
    private AttributeInfo[] attributes;

    public InstanceKlass() {
        constantPool = new ConstantPool();
        constantPool.setKlass(this);
    }

    /**
     * todo
     *
     * @return
     */
    public boolean containsMainMethod() {
        return true;
    }

    @Override
    public String toString() {
        return "InstanceKlass{" +
                "thisClass=" + thisClass +
                ", supperClass=" + supperClass +
                ", interfacesCount=" + interfacesCount +
                ", fieldsCount=" + fieldsCount +
                ", methodsCount=" + methodsCount +
                ", attributesCount=" + attributesCount +
                '}';
    }
}
