package com.techzealot.tvm.hotspot.src.share.vm.oops;

import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.support.AttributeInfo;
import lombok.Data;

@Data
public class FieldInfo implements KlassItem {
    //u2
    private int accessFlags;
    //u2
    private int nameIndex;
    //u2
    private int descriptorIndex;
    //u2
    private int attributesCount;
    //ux
    private AttributeInfo[] attributes;

    //linked fields
    private InstanceKlass belongKlass;


}
