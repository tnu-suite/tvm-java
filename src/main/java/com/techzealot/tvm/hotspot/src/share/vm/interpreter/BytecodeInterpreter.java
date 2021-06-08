package com.techzealot.tvm.hotspot.src.share.vm.interpreter;

import com.techzealot.tvm.hotspot.src.share.tools.ByteStream;
import com.techzealot.tvm.hotspot.src.share.vm.classfile.BootClassLoader;
import com.techzealot.tvm.hotspot.src.share.vm.oops.ConstantPool;
import com.techzealot.tvm.hotspot.src.share.vm.oops.InstanceKlass;
import com.techzealot.tvm.hotspot.src.share.vm.oops.MethodInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.Code;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.ConstantDoubleInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.ConstantFloatInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.ConstantIntegerInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.ConstantLongInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.ConstantStringInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.ConstantUtf8Info;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.support.ConstantPoolItem;
import com.techzealot.tvm.hotspot.src.share.vm.prims.JavaNativeInterface;
import com.techzealot.tvm.hotspot.src.share.vm.runtime.BasicType;
import com.techzealot.tvm.hotspot.src.share.vm.runtime.JavaThread;
import com.techzealot.tvm.hotspot.src.share.vm.runtime.JavaVFrame;
import com.techzealot.tvm.hotspot.src.share.vm.runtime.StackValue;
import com.techzealot.tvm.hotspot.src.share.vm.runtime.StackValueCollection;
import com.techzealot.tvm.hotspot.src.share.vm.utilities.PrintUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.MessageFormat;

@Slf4j
public class BytecodeInterpreter {
    @SneakyThrows
    public static void run(JavaThread thread, MethodInfo methodInfo) {
        //获取字节码
        ByteStream codeStream = new ByteStream(methodInfo.getCode().getCode());
        //获取栈帧
        JavaVFrame frame = (JavaVFrame) thread.getStack().peek();
        ConstantPool constantPool = methodInfo.getBelongKlass().getConstantPool();
        StackValueCollection stack = frame.getStack();
        StackValueCollection locals = frame.getLocals();
        while (codeStream.available() > 0) {
            int opCode = codeStream.readU1();
            //todo 使用枚举,根据opCode获取枚举，并获取名称 OpCodeEnum(opCode,name,msg)
            //todo 使用命令模式 按数据类型 or 模式？
            //todo 封装pushXX popXX setXX getXX,不要直接操作StackValue
            //TODO 分离stack和locals
            log.info("execute opCode: {}", opCode);
            switch (opCode) {
                case Bytecodes._bipush: {
                    //有符号byte入栈
                    stack.push(new StackValue(BasicType.T_INT, codeStream.readByte()));
                    break;
                }
                case Bytecodes._sipush: {
                    stack.push(new StackValue(BasicType.T_SHORT, codeStream.readShort()));
                    break;
                }
                case Bytecodes._iconst_m1: {
                    stack.push(new StackValue(BasicType.T_INT, -1));
                    break;
                }
                case Bytecodes._iconst_0: {
                    stack.push(new StackValue(BasicType.T_INT, 0));
                    break;
                }
                case Bytecodes._iconst_1: {
                    stack.push(new StackValue(BasicType.T_INT, 1));
                    break;
                }
                case Bytecodes._iconst_2: {
                    stack.push(new StackValue(BasicType.T_INT, 2));
                    break;
                }
                case Bytecodes._iconst_3: {
                    stack.push(new StackValue(BasicType.T_INT, 3));
                    break;
                }
                case Bytecodes._iconst_4: {
                    stack.push(new StackValue(BasicType.T_INT, 4));
                    break;
                }
                case Bytecodes._iconst_5: {
                    stack.push(new StackValue(BasicType.T_INT, 5));
                    break;
                }
                case Bytecodes._istore: {
                    locals.add(codeStream.readUnsignedByte(), stack.pop());
                    break;
                }
                case Bytecodes._istore_0: {
                    locals.add(0, stack.pop());
                    break;
                }
                case Bytecodes._istore_1: {
                    locals.add(1, stack.pop());
                    break;
                }
                case Bytecodes._istore_2: {
                    locals.add(2, stack.pop());
                    break;
                }
                case Bytecodes._istore_3: {
                    locals.add(3, stack.pop());
                    break;
                }
                case Bytecodes._iload: {
                    stack.push(new StackValue(BasicType.T_INT, locals.get(codeStream.readUnsignedByte()).getVal()));
                    break;
                }
                case Bytecodes._iload_0: {
                    stack.push(new StackValue(BasicType.T_INT, locals.get(0).getVal()));
                    break;
                }
                case Bytecodes._iload_1: {
                    stack.push(new StackValue(BasicType.T_INT, locals.get(1).getVal()));
                    break;
                }
                case Bytecodes._iload_2: {
                    stack.push(new StackValue(BasicType.T_INT, locals.get(2).getVal()));
                    break;
                }
                case Bytecodes._iload_3: {
                    stack.push(new StackValue(BasicType.T_INT, locals.get(3).getVal()));
                    break;
                }
                case Bytecodes._fconst_0: {
                    stack.pushFloat(0f);
                    break;
                }
                case Bytecodes._fconst_1: {
                    stack.pushFloat(1f);
                    break;
                }
                case Bytecodes._fconst_2: {
                    stack.pushFloat(2f);
                    break;
                }
                case Bytecodes._fload: {
                    stack.pushFloat(locals.getFloat(codeStream.readUnsignedByte()));
                    break;
                }
                case Bytecodes._fload_0: {
                    stack.pushFloat(locals.getFloat(0));
                    break;
                }
                case Bytecodes._fload_1: {
                    stack.pushFloat(locals.getFloat(1));
                    break;
                }
                case Bytecodes._fload_2: {
                    stack.pushFloat(locals.getFloat(2));
                    break;
                }
                case Bytecodes._fload_3: {
                    stack.pushFloat(locals.getFloat(3));
                    break;
                }
                case Bytecodes._fstore: {
                    locals.addFloat(codeStream.readUnsignedByte(), stack.popFloat());
                    break;
                }
                case Bytecodes._fstore_0: {
                    locals.addFloat(0, stack.popFloat());
                    break;
                }
                case Bytecodes._fstore_1: {
                    locals.addFloat(1, stack.popFloat());
                    break;
                }
                case Bytecodes._fstore_2: {
                    locals.addFloat(2, stack.popFloat());
                    break;
                }
                case Bytecodes._fstore_3: {
                    locals.addFloat(3, stack.popFloat());
                    break;
                }
                case Bytecodes._lconst_0: {
                    //先push低32位 后push高32位 == locals[n] << 8 | locals[n=1]
                    stack.pushLong(0L);
                    break;
                }
                case Bytecodes._lconst_1: {
                    stack.pushLong(1L);
                    break;
                }
                case Bytecodes._lload: {
                    int index = codeStream.readUnsignedByte();
                    stack.pushLong(locals.getLong(index));
                    break;
                }
                case Bytecodes._lload_0: {
                    stack.pushLong(locals.getLong(0));
                    break;
                }
                case Bytecodes._lload_1: {
                    stack.pushLong(locals.getLong(1));
                    break;
                }
                case Bytecodes._lload_2: {
                    stack.pushLong(locals.getLong(2));
                    break;
                }
                case Bytecodes._lload_3: {
                    stack.pushLong(locals.getLong(3));
                    break;
                }
                case Bytecodes._lstore: {
                    locals.addLong(codeStream.readUnsignedByte(), stack.popLong());
                    break;
                }
                case Bytecodes._lstore_0: {
                    locals.addLong(0, stack.popLong());
                    break;
                }
                case Bytecodes._lstore_1: {
                    locals.addLong(1, stack.popLong());
                    break;
                }
                case Bytecodes._lstore_2: {
                    locals.addLong(2, stack.popLong());
                    break;
                }
                case Bytecodes._lstore_3: {
                    locals.addLong(3, stack.popLong());
                    break;
                }
                case Bytecodes._dconst_0: {
                    stack.pushDouble(0.0d);
                    break;
                }
                case Bytecodes._dconst_1: {
                    stack.pushDouble(1.0d);
                    break;
                }
                case Bytecodes._dload: {
                    stack.pushDouble(locals.getDouble(codeStream.readUnsignedByte()));
                    break;
                }
                case Bytecodes._dload_0: {
                    stack.pushDouble(locals.getDouble(0));
                    break;
                }
                case Bytecodes._dload_1: {
                    stack.pushDouble(locals.getDouble(1));
                    break;
                }
                case Bytecodes._dload_2: {
                    stack.pushDouble(locals.getDouble(2));
                    break;
                }
                case Bytecodes._dload_3: {
                    stack.pushDouble(locals.getDouble(3));
                    break;
                }
                case Bytecodes._dstore: {
                    locals.addDouble(codeStream.readUnsignedByte(), stack.popDouble());
                    break;
                }
                case Bytecodes._dstore_0: {
                    locals.addDouble(0, stack.popDouble());
                    break;
                }
                case Bytecodes._dstore_1: {
                    locals.addDouble(1, stack.popDouble());
                    break;
                }
                case Bytecodes._dstore_2: {
                    locals.addDouble(2, stack.popDouble());
                    break;
                }
                case Bytecodes._dstore_3: {
                    locals.addDouble(3, stack.popDouble());
                    break;
                }
                case Bytecodes._ldc2_w: {
                    int index = codeStream.readU2();
                    ConstantPoolItem constantPoolItem = constantPool.get(index);
                    if (constantPoolItem instanceof ConstantLongInfo) {
                        long value = ((ConstantLongInfo) constantPoolItem).getValue();
                        stack.pushLong(value);
                    } else if (constantPoolItem instanceof ConstantDoubleInfo) {
                        double value = ((ConstantDoubleInfo) constantPoolItem).getValue();
                        stack.pushDouble(value);
                    } else {
                        //todo 可加载更多类型
                        throw new UnsupportedOperationException(MessageFormat.format("ldc2_w not support now for {0}", constantPoolItem));
                    }
                    break;
                }
                case Bytecodes._i2b: {
                    int val = stack.pop().getVal();
                    byte b = (byte) val;
                    stack.pushInt(b);
                    break;
                }
                case Bytecodes._i2c: {
                    int val = stack.pop().getVal();
                    char c = (char) val;
                    stack.pushInt(c);
                    break;
                }
                case Bytecodes._i2s: {
                    int val = stack.pop().getVal();
                    short s = (short) val;
                    stack.pushInt(s);
                    break;
                }
                case Bytecodes._i2f: {
                    int val = stack.pop().getVal();
                    float f = (float) val;
                    stack.pushFloat(f);
                    break;
                }
                case Bytecodes._i2l: {
                    int val = stack.pop().getVal();
                    stack.pushLong(val);
                    break;
                }
                case Bytecodes._i2d: {
                    int val = stack.pop().getVal();
                    stack.pushDouble(val);
                    break;
                }
                case Bytecodes._f2i: {
                    float val = stack.popFloat();
                    stack.pushInt((int) val);
                    break;
                }
                case Bytecodes._f2l: {
                    float val = stack.popFloat();
                    stack.pushLong((long) val);
                    break;
                }
                case Bytecodes._f2d: {
                    float val = stack.popFloat();
                    stack.pushDouble(val);
                    break;
                }
                case Bytecodes._l2d: {
                    long val = stack.popLong();
                    stack.pushDouble(val);
                    break;
                }
                case Bytecodes._l2f: {
                    long val = stack.popLong();
                    stack.pushFloat(val);
                    break;
                }
                case Bytecodes._l2i: {
                    long val = stack.popLong();
                    stack.pushInt((int) val);
                    break;
                }
                case Bytecodes._d2f: {
                    double val = stack.popDouble();
                    stack.pushFloat((float) val);
                    break;
                }
                case Bytecodes._d2i: {
                    double val = stack.popDouble();
                    stack.pushInt((int) val);
                    break;
                }
                case Bytecodes._d2l: {
                    double val = stack.popDouble();
                    stack.pushLong((long) val);
                    break;
                }
                case Bytecodes._ldc: {
                    //Q:为什么是u1，常量池大小为u2?会不会无法表达？A:编译器在超过u1后会使用ldc_w,ldc2_w
                    int operand = codeStream.readU1();
                    ConstantPoolItem constantPoolItem = constantPool.get(operand);
                    if (constantPoolItem instanceof ConstantStringInfo) {
                        String value = constantPool.get(((ConstantStringInfo) constantPoolItem).getStringIndex(), ConstantUtf8Info.class).getValue();
                        stack.push(new StackValue(BasicType.T_OBJECT, value));
                    } else if (constantPoolItem instanceof ConstantIntegerInfo) {
                        stack.push(new StackValue(BasicType.T_INT, ((ConstantIntegerInfo) constantPoolItem).getValue()));
                    } else if (constantPoolItem instanceof ConstantFloatInfo) {
                        stack.pushFloat(((ConstantFloatInfo) constantPoolItem).getValue());
                    } else {
                        //todo 可加载更多类型
                        throw new UnsupportedOperationException(MessageFormat.format("ldc not support now for {0}", constantPoolItem));
                    }
                    break;
                }
                case Bytecodes._return: {
                    //弹出栈帧
                    thread.getStack().pop();
                    log.info("stack size is {}", thread.getStack().size());
                    break;
                }
                case Bytecodes._getstatic: {
                    int fieldIndex = codeStream.readU2();
                    String className = constantPool.getMemberContainerClass(fieldIndex).replace("/", ".");
                    String fieldName = constantPool.getMemberName(fieldIndex);
                    //todo 暂时使用反射api
                    Class<?> containerClass = Class.forName(className);
                    Field field = containerClass.getField(fieldName);
                    stack.push(new StackValue(BasicType.T_OBJECT, field.get(null)));
                    break;
                }
                case Bytecodes._invokevirtual: {
                    int methodIndex = codeStream.readU2();
                    log.info("execute (invoke virtual) : methodIndex {}", methodIndex);
                    //获取类名 方法名 方法描述符
                    String className = constantPool.getMemberContainerClass(methodIndex).replace("/", ".");
                    String methodName = constantPool.getMemberName(methodIndex);
                    String descriptor = constantPool.getMemberDescriptor(methodIndex);
                    MethodDescriptorStream mds = new MethodDescriptorStream(descriptor);
                    //must pop params first
                    Object[] paramValues = mds.getParamValues(frame);
                    //pop target object
                    Object target = stack.pop().getObject();
                    log.info("invoke virtual target: {}", target);
                    if (className.startsWith("java")) {
                        Class<?> targetClass = Class.forName(className);
                        Method method = targetClass.getMethod(methodName, mds.getParamTypes());
                        //判断是否有返回值
                        if (mds.isReturnVoid()) {
                            method.invoke(target, paramValues);
                        } else {
                            throw new UnsupportedOperationException("don't support return value now");
                        }
                    } else {
                        throw new UnsupportedOperationException();
                    }
                    break;
                }
                case Bytecodes._invokestatic: {
                    int methodIndex = codeStream.readUnsignedShort();
                    String containerClass = constantPool.getMemberContainerClass(methodIndex);
                    String memberDescriptor = constantPool.getMemberDescriptor(methodIndex);
                    String methodName = constantPool.getMemberName(methodIndex);
                    InstanceKlass staticKlass = BootClassLoader.loadKlass(containerClass);
                    MethodInfo staticMethod = JavaNativeInterface.getMethod(staticKlass, methodName, memberDescriptor);
                    //获取方法字节码相关信息
                    Code code = staticMethod.getCode();
                    //创建方法调用栈帧
                    JavaVFrame newFrame = new JavaVFrame(code.getMaxLocals(), methodInfo);
                    MethodDescriptorStream mds = new MethodDescriptorStream(memberDescriptor);
                    mds.storeLocalVariables(stack, newFrame.getLocals());
                    //栈帧压入线程栈
                    thread.getStack().push(newFrame);
                    //执行字节码
                    BytecodeInterpreter.run(thread, staticMethod);
                    break;
                }
                default: {
                    throw new UnsupportedOperationException(MessageFormat.format("unsupported opCode {0}", opCode));
                }
            }
            PrintUtils.printOpStackAndLVT(stack, locals);
        }
    }
}
