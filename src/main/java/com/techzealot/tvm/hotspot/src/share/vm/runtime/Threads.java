package com.techzealot.tvm.hotspot.src.share.vm.runtime;

import com.techzealot.tvm.hotspot.src.share.vm.memory.AllStatic;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Threads extends AllStatic {

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private static List<Thread> threadList = new ArrayList<>();

    private static Thread currentThread;

    static {

    }

    public static List<Thread> getThreadList() {
        return threadList;
    }

    public static JavaThread currentThread() {
        return (JavaThread) currentThread;
    }

    public static void setCurrentThread(Thread thread) {
        currentThread = thread;
    }

    public static void add(Thread thread) {
        threadList.add(thread);
    }
}
