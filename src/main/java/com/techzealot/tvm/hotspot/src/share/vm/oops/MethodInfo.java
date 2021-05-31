package com.techzealot.tvm.hotspot.src.share.vm.oops;

import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.Code;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.support.AttributeInfo;
import lombok.Data;

import java.text.MessageFormat;

@Data
public class MethodInfo implements KlassItem {
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

    public Code getCode() {
        for (AttributeInfo attribute : attributes) {
            if (attribute instanceof Code) {
                return Code.class.cast(attribute);
            }
        }
        throw new IllegalStateException(MessageFormat.format("no code found for method {0})", nameIndex));
    }
}
