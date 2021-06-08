package com.techzealot.tvm.hotspot.src.share.vm.runtime;

import com.techzealot.tvm.hotspot.src.share.vm.utilities.Bytes;
import lombok.Data;

import java.util.Stack;

@Data
//todo 拆分出操作数栈和局部变量表为两个单独的结构，避免误解
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
    //局部变量表数组
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

    public void pushInt(int value) {
        container.push(new StackValue(BasicType.T_INT, value));
    }

    public void pushFloat(float value) {
        container.push(new StackValue(BasicType.T_FLOAT, Float.floatToIntBits(value)));
    }

    public StackValue pop() {
        return container.pop();
    }

    public int popInt() {
        return container.pop().getVal();
    }

    public float popFloat() {
        return Float.intBitsToFloat(container.pop().getVal());
    }

    /**
     * long 类型占用两个slot，先push低32位后push高32位，保证局部变量表中index存高位，index+1存低位
     *
     * @param value
     */
    public void pushLong(long value) {
        container.push(new StackValue(BasicType.T_LONG, Bytes.getLower32BitsFromLong(value)));
        container.push(new StackValue(BasicType.T_LONG, Bytes.getHigher32BitsFromLong(value)));
    }

    public void pushDouble(double value) {
        long longBits = Double.doubleToLongBits(value);
        container.push(new StackValue(BasicType.T_LONG, Bytes.getLower32BitsFromLong(longBits)));
        container.push(new StackValue(BasicType.T_LONG, Bytes.getHigher32BitsFromLong(longBits)));
    }

    public long popLong() {
        return Bytes.combine(container.pop().getVal(), container.pop().getVal());
    }

    public double popDouble() {
        return Double.longBitsToDouble(Bytes.combine(container.pop().getVal(), container.pop().getVal()));
    }

    public StackValue peek() {
        return container.peek();
    }

    public void add(int index, StackValue value) {
        locals[index] = value;
    }

    public void addFloat(int index, float value) {
        locals[index] = new StackValue(BasicType.T_FLOAT, Float.floatToIntBits(value));
    }

    public void addLong(int index, long value) {
        locals[index] = new StackValue(BasicType.T_LONG, Bytes.getHigher32BitsFromLong(value));
        locals[index + 1] = new StackValue(BasicType.T_LONG, Bytes.getLower32BitsFromLong(value));
    }

    public void addDouble(int index, double value) {
        long longBits = Double.doubleToLongBits(value);
        locals[index] = new StackValue(BasicType.T_LONG, Bytes.getHigher32BitsFromLong(longBits));
        locals[index + 1] = new StackValue(BasicType.T_LONG, Bytes.getLower32BitsFromLong(longBits));
    }

    public StackValue get(int index) {
        return locals[index];
    }

    public float getFloat(int index) {
        return Float.intBitsToFloat(locals[index].getVal());
    }

    public long getLong(int index) {
        return Bytes.combine(locals[index].getVal(), locals[index + 1].getVal());
    }

    public double getDouble(int index) {
        return Double.longBitsToDouble(Bytes.combine(locals[index].getVal(), locals[index + 1].getVal()));
    }
}
