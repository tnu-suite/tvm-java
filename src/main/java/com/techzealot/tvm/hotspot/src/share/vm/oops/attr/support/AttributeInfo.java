package com.techzealot.tvm.hotspot.src.share.vm.oops.attr.support;

/**
 * jdk8虚拟机预定义23种,可以自定义新的属性,虚拟机必须忽略不识别的属性
 */
public interface AttributeInfo {
    //u2
    int getAttributeNameIndex();

    //u4
    long getAttributeLength();
}
