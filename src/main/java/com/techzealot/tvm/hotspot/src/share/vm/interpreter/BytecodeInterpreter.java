package com.techzealot.tvm.hotspot.src.share.vm.interpreter;

import com.techzealot.tvm.hotspot.src.share.tools.ByteStream;
import com.techzealot.tvm.hotspot.src.share.vm.oops.ConstantPool;
import com.techzealot.tvm.hotspot.src.share.vm.oops.MethodInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.ConstantStringInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.ConstantUtf8Info;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.support.ConstantPoolItem;
import com.techzealot.tvm.hotspot.src.share.vm.runtime.BasicType;
import com.techzealot.tvm.hotspot.src.share.vm.runtime.JavaThread;
import com.techzealot.tvm.hotspot.src.share.vm.runtime.JavaVFrame;
import com.techzealot.tvm.hotspot.src.share.vm.runtime.StackValue;
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
        while (codeStream.available() > 0) {
            int opCode = codeStream.readU1();
            switch (opCode) {
                case Bytecodes._ldc: {
                    log.info("execute ldc");
                    //todo 思考为什么是u1，常量池大小为u2?会不会无法表达？
                    int operand = codeStream.readU1();
                    ConstantPoolItem constantPoolItem = constantPool.get(operand);
                    if (constantPoolItem instanceof ConstantStringInfo) {
                        String value = constantPool.get(((ConstantStringInfo) constantPoolItem).getStringIndex(), ConstantUtf8Info.class).getValue();
                        frame.getStack().push(new StackValue(BasicType.T_OBJECT, value));
                    } else {
                        throw new UnsupportedOperationException();
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
                    frame.getStack().push(new StackValue(BasicType.T_OBJECT, field.get(null)));
                    break;
                }
                case Bytecodes._invokevirtual: {
                    log.info("execute invokevirtual");
                    int methodIndex = codeStream.readU2();
                    //获取类名 方法名 方法描述符
                    String className = constantPool.getMemberContainerClass(methodIndex);
                    String methodName = constantPool.getMemberName(methodIndex);
                    String descriptor = constantPool.getMemberDescriptor(methodIndex);
                    MethodDescriptorStream mds = new MethodDescriptorStream(descriptor);
                    //must pop params first
                    Object[] paramValues = mds.getParamValues(frame);
                    //pop target object
                    Object target = frame.getStack().pop().getObject();
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
                    throw new UnsupportedOperationException(MessageFormat.format("unsupported opOcde {}", opCode));
                }
            }
        }
    }
}
