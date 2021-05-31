package com.techzealot.tvm.hotspot.src.share.vm.oops;

import lombok.Data;

@Data
public class InterfaceInfo {
    //u2
    private int constantPoolIndex;

    /**
     * linked fields
     */
    private String interfaceName;

    private InstanceKlass belongKlass;

    public InterfaceInfo(int constantPoolIndex, String interfaceName) {
        this.constantPoolIndex = constantPoolIndex;
        this.interfaceName = interfaceName;
    }
}
