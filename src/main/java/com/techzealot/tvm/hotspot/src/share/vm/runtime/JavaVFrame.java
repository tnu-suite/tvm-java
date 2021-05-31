package com.techzealot.tvm.hotspot.src.share.vm.runtime;

import com.techzealot.tvm.hotspot.src.share.vm.oops.MethodInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class JavaVFrame extends VFrame {
    /**
     * 局部变量表-->数组
     */
    private StackValueCollection locals;

    /**
     * 操作数栈-->栈
     */
    private StackValueCollection stack = new StackValueCollection();

    /**
     * 栈帧关联的方法信息
     */
    private MethodInfo ownerMethod;

    public JavaVFrame(int maxLocals) {
        locals = new StackValueCollection(maxLocals);
    }

    public JavaVFrame(int maxLocals, MethodInfo methodInfo) {
        locals = new StackValueCollection(maxLocals);
        ownerMethod = methodInfo;
    }
}
