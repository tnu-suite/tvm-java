package com.techzealot.tvm.hotspot.src.share.vm.interpreter;

import com.techzealot.tvm.hotspot.src.share.vm.runtime.BasicType;
import com.techzealot.tvm.hotspot.src.share.vm.runtime.StackValueCollection;
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

    /**
     * @param stack
     * @return
     */
    public Object[] getParamValues(StackValueCollection stack) {
        int size = paramDescriptors.size();
        Object[] params = new Object[size];
        //reverse order iteration
        for (int i = size - 1; i >= 0; i--) {
            DescriptorInfo descriptorInfo = paramDescriptors.get(i);
            int type = descriptorInfo.getType();
            switch (type) {
                case BasicType.T_BOOLEAN: {
                    int val = stack.pop().getVal();
                    if (val == 0) {
                        params[i] = false;
                    } else if (val == 1) {
                        params[i] = true;
                    } else {
                        throw new IllegalArgumentException(MessageFormat.format("{0} can't be cast to boolean", val));
                    }
                    break;
                }
                case BasicType.T_BYTE: {
                    int val = stack.pop().getVal();
                    params[i] = (byte) val;
                    break;
                }
                case BasicType.T_CHAR: {
                    int val = stack.pop().getVal();
                    params[i] = (char) val;
                    break;
                }
                case BasicType.T_SHORT: {
                    int val = stack.pop().getVal();
                    params[i] = (short) val;
                    break;
                }
                case BasicType.T_INT: {
                    params[i] = stack.pop().getVal();
                    break;
                }
                case BasicType.T_FLOAT: {
                    params[i] = stack.popFloat();
                    break;
                }
                case BasicType.T_LONG: {
                    params[i] = stack.popLong();
                    break;
                }
                case BasicType.T_DOUBLE: {
                    params[i] = stack.popDouble();
                    break;
                }
                case BasicType.T_OBJECT: {
                    params[i] = stack.pop().getObject();
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

    /**
     * 参数从左至右入栈，因此需要从大到小反向设置局部变量表
     * 利用2个栈结构调整顺序
     *
     * @param preStack
     * @param newLocals
     */
    public void storeLocalVariables(StackValueCollection preStack, StackValueCollection newLocals) {
        int size = paramDescriptors.size();
        StackValueCollection reverseStack = new StackValueCollection();
        for (int i = size - 1; i >= 0; i--) {
            DescriptorInfo descriptorInfo = paramDescriptors.get(i);
            int type = descriptorInfo.getType();
            switch (type) {
                case BasicType.T_BOOLEAN:
                case BasicType.T_BYTE:
                case BasicType.T_CHAR:
                case BasicType.T_SHORT:
                case BasicType.T_INT:
                case BasicType.T_FLOAT:
                case BasicType.T_OBJECT: {
                    reverseStack.push(preStack.pop());
                    break;
                }
                case BasicType.T_LONG: {
                    reverseStack.pushLong(preStack.popLong());
                    break;
                }
                case BasicType.T_DOUBLE: {
                    reverseStack.pushDouble(preStack.popDouble());
                    break;
                }
                case BasicType.T_ARRAY:
                default: {
                    throw new UnsupportedOperationException(MessageFormat.format("unsupported type {0} now", type));
                }
            }
        }
        for (int i = 0, index = 0; i < size; i++, index++) {
            DescriptorInfo descriptorInfo = paramDescriptors.get(i);
            int type = descriptorInfo.getType();
            switch (type) {
                case BasicType.T_BOOLEAN:
                case BasicType.T_BYTE:
                case BasicType.T_CHAR:
                case BasicType.T_SHORT:
                case BasicType.T_INT:
                case BasicType.T_FLOAT:
                case BasicType.T_OBJECT: {
                    newLocals.add(index, reverseStack.pop());
                    break;
                }
                case BasicType.T_LONG: {
                    newLocals.addLong(index, reverseStack.popLong());
                    //two slot,need skip one
                    index++;
                    break;
                }
                case BasicType.T_DOUBLE: {
                    newLocals.addDouble(index, reverseStack.popDouble());
                    //two slot,need skip one
                    index++;
                    break;
                }
                case BasicType.T_ARRAY:
                default: {
                    throw new UnsupportedOperationException(MessageFormat.format("unsupported type {0} now", type));
                }
            }
        }
        if (!reverseStack.empty()) {
            throw new IllegalStateException(MessageFormat.format("store method {0} locals complete but params stack not empty", descriptor));
        }
    }
}
