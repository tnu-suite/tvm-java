使用java实现JVM

IO:

DataInputStream读取class

DataOutputStream输出class

设计模式:

代理模式 代理DataInputStream

io装饰器模式

visitor输出反汇编

命令模式实现字节码解析

解释器模式

二进制:

学习byte 转化 为 long float double int short

todo:

u4使用int 溢出问题

能否使用枚举组织字节码指令

jd-core

cfr

javap

asm:class reader/writer

implementation group: 'org.jclasslib', name: 'jclasslib-data', version: '5.8'
https://github.com/alibaba/arthas/tree/master/memorycompiler

todo:
更好的调试日志 新增获取可读数据的方法，而非显示常量池 类似javap 是否可使用visitor构造边解析边输出日志的能力 终极目标： 能否实现模板解释器？ 使用自己编写的jvm运行spring,需要编写完善的类加载机制
