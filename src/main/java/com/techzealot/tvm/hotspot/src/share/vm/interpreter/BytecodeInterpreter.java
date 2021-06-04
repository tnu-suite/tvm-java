package com.techzealot.tvm.hotspot.src.share.vm.interpreter;

import com.techzealot.tvm.hotspot.src.share.tools.ByteStream;
import com.techzealot.tvm.hotspot.src.share.vm.oops.ConstantPool;
import com.techzealot.tvm.hotspot.src.share.vm.oops.MethodInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.ConstantFloatInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.ConstantIntegerInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.ConstantLongInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.ConstantStringInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.ConstantUtf8Info;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.support.ConstantPoolItem;
import com.techzealot.tvm.hotspot.src.share.vm.runtime.BasicType;
import com.techzealot.tvm.hotspot.src.share.vm.runtime.JavaThread;
import com.techzealot.tvm.hotspot.src.share.vm.runtime.JavaVFrame;
import com.techzealot.tvm.hotspot.src.share.vm.runtime.StackValue;
import com.techzealot.tvm.hotspot.src.share.vm.runtime.StackValueCollection;
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
            switch (opCode) {
                case Bytecodes._bipush: {
                    //有符号byte入栈
                    log.info("execute bipush");
                    stack.push(new StackValue(BasicType.T_INT, codeStream.readByte()));
                    break;
                }
                case Bytecodes._sipush: {
                    log.info("execute _sipush");
                    stack.push(new StackValue(BasicType.T_SHORT, codeStream.readShort()));
                    break;
                }
                case Bytecodes._iconst_m1: {
                    log.info("execute iconst_m1");
                    stack.push(new StackValue(BasicType.T_INT, -1));
                    break;
                }
                case Bytecodes._iconst_0: {
                    log.info("execute iconst_0");
                    stack.push(new StackValue(BasicType.T_INT, 0));
                    break;
                }
                case Bytecodes._iconst_1: {
                    log.info("execute iconst_1");
                    stack.push(new StackValue(BasicType.T_INT, 1));
                    break;
                }
                case Bytecodes._iconst_2: {
                    log.info("execute iconst_2");
                    stack.push(new StackValue(BasicType.T_INT, 2));
                    break;
                }
                case Bytecodes._iconst_3: {
                    log.info("execute iconst_3");
                    stack.push(new StackValue(BasicType.T_INT, 3));
                    break;
                }
                case Bytecodes._iconst_4: {
                    log.info("execute iconst_4");
                    stack.push(new StackValue(BasicType.T_INT, 4));
                    break;
                }
                case Bytecodes._iconst_5: {
                    log.info("execute iconst_5");
                    stack.push(new StackValue(BasicType.T_INT, 5));
                    break;
                }
                case Bytecodes._istore: {
                    log.info("execute istore");
                    locals.add(codeStream.readUnsignedByte(), stack.pop());
                    break;
                }
                case Bytecodes._istore_0: {
                    log.info("execute istore_0");
                    locals.add(0, stack.pop());
                    break;
                }
                case Bytecodes._istore_1: {
                    log.info("execute istore_1");
                    locals.add(1, stack.pop());
                    break;
                }
                case Bytecodes._istore_2: {
                    log.info("execute istore_2");
                    locals.add(2, stack.pop());
                    break;
                }
                case Bytecodes._istore_3: {
                    log.info("execute istore_3");
                    locals.add(3, stack.pop());
                    break;
                }
                case Bytecodes._iload: {
                    log.info("execute _iload");
                    stack.push(new StackValue(BasicType.T_INT, locals.get(codeStream.readUnsignedByte()).getVal()));
                    break;
                }
                case Bytecodes._iload_0: {
                    log.info("execute _iload_0");
                    stack.push(new StackValue(BasicType.T_INT, locals.get(0).getVal()));
                    break;
                }
                case Bytecodes._iload_1: {
                    log.info("execute _iload_1");
                    stack.push(new StackValue(BasicType.T_INT, locals.get(1).getVal()));
                    break;
                }
                case Bytecodes._iload_2: {
                    log.info("execute _iload_2");
                    stack.push(new StackValue(BasicType.T_INT, locals.get(2).getVal()));
                    break;
                }
                case Bytecodes._iload_3: {
                    log.info("execute _iload_3");
                    stack.push(new StackValue(BasicType.T_INT, locals.get(3).getVal()));
                    break;
                }
                case Bytecodes._fconst_0: {
                    log.info("execute _fconst_0");
                    stack.push(new StackValue(BasicType.T_FLOAT, 0.0f));
                    break;
                }
                case Bytecodes._fconst_1: {
                    log.info("execute _fconst_1");
                    stack.push(new StackValue(BasicType.T_FLOAT, 1.0f));
                    break;
                }
                case Bytecodes._fconst_2: {
                    log.info("execute _fconst_2");
                    stack.push(new StackValue(BasicType.T_FLOAT, 2.0f));
                    break;
                }
                case Bytecodes._fload: {
                    log.info("execute _fload");
                    stack.push(new StackValue(BasicType.T_FLOAT, locals.get(codeStream.readUnsignedByte()).getFloat()));
                    break;
                }
                case Bytecodes._fload_0: {
                    log.info("execute _fload_0");
                    stack.push(new StackValue(BasicType.T_FLOAT, locals.get(0).getFloat()));
                    break;
                }
                case Bytecodes._fload_1: {
                    log.info("execute _fload_1");
                    stack.push(new StackValue(BasicType.T_FLOAT, locals.get(1).getFloat()));
                    break;
                }
                case Bytecodes._fload_2: {
                    log.info("execute _fload_2");
                    stack.push(new StackValue(BasicType.T_FLOAT, locals.get(2).getFloat()));
                    break;
                }
                case Bytecodes._fload_3: {
                    log.info("execute _fload_3");
                    stack.push(new StackValue(BasicType.T_FLOAT, locals.get(3).getFloat()));
                    break;
                }
                case Bytecodes._fstore: {
                    log.info("execute _fstore");
                    locals.add(codeStream.readUnsignedByte(), new StackValue(BasicType.T_FLOAT, stack.pop().getFloat()));
                    break;
                }
                case Bytecodes._fstore_0: {
                    log.info("execute _fstore_0");
                    locals.add(0, new StackValue(BasicType.T_FLOAT, stack.pop().getFloat()));
                    break;
                }
                case Bytecodes._fstore_1: {
                    log.info("execute _fstore_0");
                    locals.add(1, new StackValue(BasicType.T_FLOAT, stack.pop().getFloat()));
                    break;
                }
                case Bytecodes._fstore_2: {
                    log.info("execute _fstore_2");
                    locals.add(2, new StackValue(BasicType.T_FLOAT, stack.pop().getFloat()));
                    break;
                }
                case Bytecodes._fstore_3: {
                    log.info("execute _fstore_3");
                    locals.add(3, new StackValue(BasicType.T_FLOAT, stack.pop().getFloat()));
                    break;
                }
                case Bytecodes._lconst_0: {
                    //先push低32位 后push高32位 == locals[n] << 8 | locals[n=1]
                    log.info("execute _lconst_0");
                    stack.pushLong(0L);
                    break;
                }
                case Bytecodes._lconst_1: {
                    log.info("execute _lconst_1");
                    stack.pushLong(1L);
                    break;
                }
                case Bytecodes._lload: {
                    log.info("execute _lload");
                    int index = codeStream.readUnsignedByte();
                    stack.pushLong(locals.getLong(index));
                    break;
                }
                case Bytecodes._lload_0: {
                    log.info("execute _lload_0");
                    stack.pushLong(locals.getLong(0));
                    break;
                }
                case Bytecodes._lload_1: {
                    log.info("execute _lload_1");
                    stack.pushLong(locals.getLong(1));
                    break;
                }
                case Bytecodes._lload_2: {
                    log.info("execute _lload_2");
                    stack.pushLong(locals.getLong(2));
                    break;
                }
                case Bytecodes._lload_3: {
                    log.info("execute _lload_3");
                    stack.pushLong(locals.getLong(3));
                    break;
                }
                case Bytecodes._lstore: {
                    log.info("execute _lstore");
                    int index = codeStream.readUnsignedByte();
                    locals.addLong(index, stack.popLong());
                    break;
                }
                case Bytecodes._lstore_0: {
                    log.info("execute _lstore_0");
                    locals.addLong(0, stack.popLong());
                    break;
                }
                case Bytecodes._lstore_1: {
                    log.info("execute _lstore_1");
                    locals.addLong(1, stack.popLong());
                    break;
                }
                case Bytecodes._lstore_2: {
                    log.info("execute _lstore_2");
                    locals.addLong(2, stack.popLong());
                    break;
                }
                case Bytecodes._lstore_3: {
                    log.info("execute _lstore_3");
                    locals.addLong(3, stack.popLong());
                    break;
                }
                case Bytecodes._ldc2_w: {
                    int index = codeStream.readU2();
                    ConstantPoolItem constantPoolItem = constantPool.get(index);
                    if (constantPoolItem instanceof ConstantLongInfo) {
                        long value = ((ConstantLongInfo) constantPoolItem).getValue();
                        stack.pushLong(value);
                    } else {
                        //todo 可加载更多类型
                        throw new UnsupportedOperationException(MessageFormat.format("ldc2_w not support now for {0}", constantPoolItem));
                    }
                    break;
                }
                case Bytecodes._ldc: {
                    log.info("execute ldc");
                    //Q:为什么是u1，常量池大小为u2?会不会无法表达？A:编译器在超过u1后会使用ldc_w,ldc2_w
                    int operand = codeStream.readU1();
                    ConstantPoolItem constantPoolItem = constantPool.get(operand);
                    if (constantPoolItem instanceof ConstantStringInfo) {
                        String value = constantPool.get(((ConstantStringInfo) constantPoolItem).getStringIndex(), ConstantUtf8Info.class).getValue();
                        stack.push(new StackValue(BasicType.T_OBJECT, value));
                    } else if (constantPoolItem instanceof ConstantIntegerInfo) {
                        stack.push(new StackValue(BasicType.T_INT, ((ConstantIntegerInfo) constantPoolItem).getValue()));
                    } else if (constantPoolItem instanceof ConstantFloatInfo) {
                        stack.push(new StackValue(BasicType.T_FLOAT, ((ConstantFloatInfo) constantPoolItem).getValue()));
                    } else {
                        //todo 可加载更多类型
                        throw new UnsupportedOperationException(MessageFormat.format("ldc not support now for {0}", constantPoolItem));
                    }
                    break;
                }
                case Bytecodes._return: {
                    log.info("execute return");
                    //弹出栈帧
                    thread.getStack().pop();
                    log.info("stack size is {}", thread.getStack().size());
                    break;
                }
                case Bytecodes._getstatic: {
                    log.info("execute getstatic");
                    int fieldIndex = codeStream.readU2();
                    String className = constantPool.getMemberContainerClass(fieldIndex);
                    String fieldName = constantPool.getMemberName(fieldIndex);
                    //todo 暂时使用反射api
                    Class<?> containerClass = Class.forName(className);
                    Field field = containerClass.getField(fieldName);
                    stack.push(new StackValue(BasicType.T_OBJECT, field.get(null)));
                    break;
                }
                case Bytecodes._invokevirtual: {
                    int methodIndex = codeStream.readU2();
                    log.info("execute invokevirtual: methodIndex {}", methodIndex);
                    //获取类名 方法名 方法描述符
                    String className = constantPool.getMemberContainerClass(methodIndex);
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
                default: {
                    throw new UnsupportedOperationException(MessageFormat.format("unsupported opCode {0}", opCode));
                }
            }
        }
    }
}
