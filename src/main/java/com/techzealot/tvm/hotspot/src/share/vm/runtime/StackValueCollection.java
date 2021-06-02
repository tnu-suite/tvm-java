package com.techzealot.tvm.hotspot.src.share.vm.runtime;

import lombok.Data;

import java.util.Stack;

@Data
public class StackValueCollection {
    /**
     * 模拟操作数栈--栈结构
     */
    private final Stack<StackValue> container = new Stack<>();
    /**
     * 模拟局部变量表-数组结构
     */
    //最大栈深度
    private int maxLocals;
    //局部变量表数组--TODO 是否会出现删除操作？
    private StackValue[] locals;

    public StackValueCollection() {
    }

    public StackValueCollection(int maxLocals) {
        this.maxLocals = maxLocals;
        this.locals = new StackValue[maxLocals];
    }

    public void push(StackValue value) {
        container.push(value);
    }

    public StackValue pop() {
        return container.pop();
    }

    public StackValue peek() {
        return container.peek();
    }

    public void add(int index, StackValue value) {
        locals[index] = value;
    }

    public StackValue get(int index) {
        return locals[index];
    }
}
