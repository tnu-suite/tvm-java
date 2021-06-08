package com.techzealot.tvm.hotspot.src.share.vm.prims;

import com.techzealot.tvm.hotspot.src.share.vm.interpreter.BytecodeInterpreter;
import com.techzealot.tvm.hotspot.src.share.vm.oops.ConstantPool;
import com.techzealot.tvm.hotspot.src.share.vm.oops.InstanceKlass;
import com.techzealot.tvm.hotspot.src.share.vm.oops.MethodInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.Code;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.ConstantUtf8Info;
import com.techzealot.tvm.hotspot.src.share.vm.runtime.JavaThread;
import com.techzealot.tvm.hotspot.src.share.vm.runtime.JavaVFrame;
import com.techzealot.tvm.hotspot.src.share.vm.runtime.Threads;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.util.Objects;

@Slf4j
public class JavaNativeInterface {

    /**
     * todo 需要声明AccessFlags
     *
     * @param klass
     * @param name
     * @param descriptor
     * @return
     */
    public static MethodInfo getMethod(InstanceKlass klass, String name, String descriptor) {
        ConstantPool constantPool = klass.getConstantPool();
        MethodInfo[] methods = klass.getMethods();
        for (MethodInfo method : methods) {
            ConstantUtf8Info nameUtf8 = constantPool.get(method.getNameIndex(), ConstantUtf8Info.class);
            ConstantUtf8Info descriptorUtf8 = constantPool.get(method.getDescriptorIndex(), ConstantUtf8Info.class);
            if (nameUtf8.getValue().equals(name) && descriptorUtf8.getValue().equals(descriptor)) {
                return method;
            }
        }
        throw new IllegalStateException(MessageFormat.format("method {0}:{1} not found in class {2}", name, descriptor, klass.getThisClass()));
    }

    public static void callStaticMethod(MethodInfo methodInfo) {
        JavaThread thread = Threads.currentThread();
        if (Objects.isNull(thread)) {
            throw new IllegalStateException("thread is not initialized");
        }
        //获取方法字节码相关信息
        Code code = methodInfo.getCode();
        //创建方法调用栈帧
        JavaVFrame frame = new JavaVFrame(code.getMaxLocals(), methodInfo);

        //栈帧压入线程栈
        thread.getStack().push(frame);

        //执行字节码
        BytecodeInterpreter.run(thread, methodInfo);
    }
}
