package com.techzealot.tvm.hotspot.src.share.vm.runtime;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Stack;

@EqualsAndHashCode(callSuper = false)
@Data
public class JavaThread extends Thread {
    //线程栈
    private Stack<VFrame> stack = new Stack<>();
}
