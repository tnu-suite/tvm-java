package com.techzealot.tvm.jdk;

import com.techzealot.tvm.hotspot.src.share.vm.classfile.BootClassLoader;
import com.techzealot.tvm.hotspot.src.share.vm.oops.InstanceKlass;
import com.techzealot.tvm.hotspot.src.share.vm.oops.MethodInfo;
import com.techzealot.tvm.hotspot.src.share.vm.prims.JavaNativeInterface;
import com.techzealot.tvm.hotspot.src.share.vm.runtime.JavaThread;
import com.techzealot.tvm.hotspot.src.share.vm.runtime.Threads;

public class Main {
    public static void main(String[] args) {
        String mainClass="com.techzealot.tvm.examples.HelloWorld";
        startJVM(mainClass);
    }

    public static void startJVM(String mainClass) {

        //加载main函数所在类
        InstanceKlass mainKlass = BootClassLoader.loadMainClass(mainClass);

        //获取main方法
        MethodInfo main = JavaNativeInterface.getMethod(mainKlass, "main", "([Ljava/lang/String;)V");

        //创建主线程
        JavaThread thread = new JavaThread();
        //添加至线程列表
        Threads.add(thread);
        //设置为当前线程
        Threads.setCurrentThread(thread);

        //执行main方法
        JavaNativeInterface.callStaticMethod(main);
    }
}
