package com.techzealot.tvm.hotspot.src.share.vm.interpreter;

import com.techzealot.tvm.hotspot.src.share.vm.runtime.BasicType;
import com.techzealot.tvm.hotspot.src.share.vm.runtime.JavaVFrame;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Array;
import java.text.MessageFormat;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

@Data
@Slf4j
public class MethodDescriptorStream {
    private final String descriptor;
    private final Deque<Character> deque;
    private List<DescriptorInfo> paramDescriptors = new ArrayList<>();
    private DescriptorInfo returnDescriptor;

    public MethodDescriptorStream(String descriptor) {
        this.descriptor = descriptor;
        deque = new ArrayDeque<>();
        for (char c : descriptor.toCharArray()) {
            deque.addLast(c);
        }
        parse(deque);
    }

    private void parse(Deque<Character> deque) {
        Character ch = deque.pollFirst();
        if (ch != BasicType.JVM_SIGNATURE_FUNC) {
            throw new IllegalStateException(MessageFormat.format("descriptor must start with {0}", BasicType.JVM_SIGNATURE_FUNC));
        }
        for (; ; ) {
            DescriptorInfo descriptorInfo = nextFieldDescriptor(deque);
            if (descriptorInfo == null) {
                break;
            }
            paramDescriptors.add(descriptorInfo);
        }
        returnDescriptor = nextFieldDescriptor(deque);
    }


    @SneakyThrows
    private DescriptorInfo nextFieldDescriptor(Deque<Character> deque) {
        //todo 使用枚举聚合描述符和类型
        Character ch = deque.pollFirst();
        //1.队列为空时结束 2.方法结束符需要消耗并结束
        if (ch == null || ch == BasicType.JVM_SIGNATURE_ENDFUNC) {
            return null;
        }
        switch (ch) {
            case BasicType.JVM_SIGNATURE_BOOLEAN: {
                return DescriptorInfo.BOOLEAN;
            }
            case BasicType.JVM_SIGNATURE_BYTE: {
                return DescriptorInfo.BYTE;
            }
            case BasicType.JVM_SIGNATURE_CHAR: {
                return DescriptorInfo.CHAR;
            }
            case BasicType.JVM_SIGNATURE_SHORT: {
                return DescriptorInfo.SHORT;
            }
            case BasicType.JVM_SIGNATURE_INT: {
                return DescriptorInfo.INT;
            }
            case BasicType.JVM_SIGNATURE_FLOAT: {
                return DescriptorInfo.FLOAT;
            }
            case BasicType.JVM_SIGNATURE_LONG: {
                return DescriptorInfo.LONG;
            }
            case BasicType.JVM_SIGNATURE_DOUBLE: {
                return DescriptorInfo.DOUBLE;
            }
            case BasicType.JVM_SIGNATURE_VOID: {
                return DescriptorInfo.VOID;
            }
            case BasicType.JVM_SIGNATURE_CLASS: {
                return parseObjectField(deque);
            }
            case BasicType.JVM_SIGNATURE_ARRAY: {
                return parseArrayField(deque);
            }
            default: {
                throw new UnsupportedOperationException(MessageFormat.format("unsupported descriptor {0}", ch));
            }
        }
    }

    @SneakyThrows
    private DescriptorInfo parseArrayField(Deque<Character> deque) {
        DescriptorInfo descriptorInfo = new DescriptorInfo(BasicType.T_ARRAY, 1, null);
        for (; deque.peekFirst() == BasicType.JVM_SIGNATURE_ARRAY; ) {
            deque.pollFirst();
            descriptorInfo.incArrayDimension();
        }
        descriptorInfo.setElementDescriptorInfo(nextFieldDescriptor(deque));
        return descriptorInfo;
    }

    @SneakyThrows
    private DescriptorInfo parseObjectField(Deque<Character> deque) {
        StringBuilder sb = new StringBuilder();
        for (; deque.peekFirst() != BasicType.JVM_SIGNATURE_ENDCLASS; ) {
            sb.append(deque.pollFirst());
        }
        //consume ';'
        deque.pollFirst();
        return new DescriptorInfo(BasicType.T_OBJECT, Class.forName(sb.toString().replace("/", ".")));
    }

    public Class<?>[] getParamTypes() {
        Class<?>[] types = new Class[paramDescriptors.size()];
        for (int i = 0; i < paramDescriptors.size(); i++) {
            DescriptorInfo descriptorInfo = paramDescriptors.get(i);
            Class<?> clazz = descriptorInfo.getClazz();
            if (clazz != null) {
                types[i] = clazz;
            } else {
                if (descriptorInfo.getType() != BasicType.JVM_SIGNATURE_ARRAY) {
                    throw new IllegalArgumentException(MessageFormat.format("descriptorInfo {0} must have clazz ,only array can be null", descriptorInfo));
                }
                int[] dims = new int[descriptorInfo.getArrayDimension()];
                //数组类型
                types[i] = Array.newInstance(descriptorInfo.getElementDescriptorInfo().getClazz(), dims).getClass();
            }
        }
        return types;
    }

    public Object[] getParamValues(JavaVFrame frame) {
        int size = paramDescriptors.size();
        Object[] params = new Object[size];
        for (int i = 0; i < size; i++) {
            DescriptorInfo descriptorInfo = paramDescriptors.get(i);
            int type = descriptorInfo.getType();
            switch (type) {
                case BasicType.T_BOOLEAN:
                case BasicType.T_BYTE:
                case BasicType.T_CHAR:
                case BasicType.T_SHORT:
                case BasicType.T_INT:
                case BasicType.T_FLOAT:
                case BasicType.T_DOUBLE:
                case BasicType.T_LONG: {
                    params[i] = frame.getStack().pop().getVal();
                    break;
                }
                case BasicType.T_OBJECT: {
                    params[i] = frame.getStack().pop().getObject();
                    break;
                }
                case BasicType.T_ARRAY:
                default: {
                    throw new UnsupportedOperationException(MessageFormat.format("unsupported type {0} now", type));
                }

            }
        }
        return params;
    }

    public boolean isReturnVoid() {
        return returnDescriptor == DescriptorInfo.VOID;
    }
}
