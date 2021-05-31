package com.techzealot.tvm.hotspot.src.share.vm.classfile;

import com.techzealot.tvm.hotspot.src.share.tools.ByteStream;
import com.techzealot.tvm.hotspot.src.share.vm.oops.ConstantPool;
import com.techzealot.tvm.hotspot.src.share.vm.oops.FieldInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.InstanceKlass;
import com.techzealot.tvm.hotspot.src.share.vm.oops.InterfaceInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.MethodInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.AnnotationDefault;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.BootstrapMethods;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.Code;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.ConstantValue;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.Deprecated;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.EnclosingMethod;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.Exceptions;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.InnerClass;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.LineNumberTable;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.LocalVariableTable;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.LocalVariableTypeTable;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.MethodParameters;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.RuntimeInvisibleAnnotations;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.RuntimeInvisibleParameterAnnotations;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.RuntimeInvisibleTypeAnnotations;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.RuntimeVisibleAnnotations;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.RuntimeVisibleParameterAnnotations;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.RuntimeVisibleTypeAnnotations;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.Signature;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.SourceDebugExtension;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.SourceFile;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.StackMapTable;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.Synthetic;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.support.Annotation;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.support.AnnotationDescriptorEnum;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.support.AttributeEnum;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.support.AttributeInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.support.ParameterAnnotation;
import com.techzealot.tvm.hotspot.src.share.vm.oops.attr.support.TypeAnnotation;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.ConstantClassInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.ConstantDoubleInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.ConstantFieldRefInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.ConstantFloatInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.ConstantIntegerInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.ConstantInterfaceMethodRefInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.ConstantInvokeDynamicInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.ConstantLongInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.ConstantMethodHandleInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.ConstantMethodRefInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.ConstantMethodTypeInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.ConstantNameAndTypeInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.ConstantStringInfo;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.ConstantUtf8Info;
import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.support.ConstantPoolEnum;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.util.Objects;

@Slf4j
public class ClassFileParser {

    /**
     * ClassFile {
     * u4             magic;
     * u2             minor_version;
     * u2             major_version;
     * u2             constant_pool_count; memberSize+1
     * cp_info        constant_pool[constant_pool_count-1];
     * u2             access_flags;
     * u2             this_class;
     * u2             super_class;
     * u2             interfaces_count;
     * u2             interfaces[interfaces_count];
     * u2             fields_count;
     * field_info     fields[fields_count];
     * u2             methods_count;
     * method_info    methods[methods_count];
     * u2             attributes_count;
     * attribute_info attributes[attributes_count];
     * }
     *
     * @param content
     * @return
     */
    //todo 尝试visitor模式
    public static InstanceKlass parseClassFile(byte[] content) {
        InstanceKlass klass = new InstanceKlass();
        try (ByteStream byteStream = new ByteStream(content)) {
            klass.setMagic(byteStream.readU4());
            checkMagic(klass.getMagic());
            klass.setMinorVersion(byteStream.readU2());
            klass.setMajorVersion(byteStream.readU2());
            //parse constantPool
            parseConstantPool(byteStream, klass);
            ConstantPool constantPool = klass.getConstantPool();
            constantPool.print();
            constantPool.validate();
            klass.setAccessFlags(byteStream.readU2());
            klass.setThisClass(byteStream.readU2());
            klass.setSupperClass(byteStream.readU2());
            //parse interfaces
            parseInterfaces(byteStream, klass);
            //parse fields
            parseFields(byteStream, klass);
            //parse methods
            parseMethods(byteStream, klass);
            //parse Attributes
            parseClassAttributes(byteStream, klass);
            if (byteStream.available() > 0) {
                throw new IllegalStateException("parse all class elements,but remain unresolved bytes");
            }
            checkInstanceKlass(klass);
        }
        return klass;
    }

    private static AttributeInfo[] parseAttributes(ByteStream byteStream, ConstantPool constantPool, int attributesCount) {
        AttributeInfo[] attributeInfos = new AttributeInfo[attributesCount];
        for (int i = 0; i < attributesCount; i++) {
            int attributeNameIndex = byteStream.readU2();
            log.info("start get attributeName at index {}", attributeNameIndex);
            String attributeName = constantPool.get(attributeNameIndex, ConstantUtf8Info.class).getValue();
            AttributeEnum attributeEnum = AttributeEnum.getEnumByValue(attributeName);
            int attributeLength = byteStream.readU4();
            //todo check int overflow
            //byte[] bytes = new byte[attributeLength];
            //byteStream.readBytes(bytes);
            switch (attributeEnum) {
                case ConstantValue: {
                    if (attributeLength != 2) {
                        throw new IllegalStateException(MessageFormat.format("ConstantValue {0} attributeLength must be 2,but is {1}", attributeName, attributeLength));
                    }
                    int constantValueIndex = byteStream.readU2();
                    attributeInfos[i] = new ConstantValue(attributeNameIndex, attributeLength, constantValueIndex);
                    break;
                }
                case Code: {
                    Code code = new Code();
                    code.setAttributeNameIndex(attributeNameIndex);
                    code.setAttributeLength(attributeLength);
                    code.setMaxStack(byteStream.readU2());
                    code.setMaxLocals(byteStream.readU2());
                    int codeLength = byteStream.readU4();
                    code.setCodeLength(codeLength);
                    byte[] bytecode = new byte[codeLength];
                    byteStream.readBytes(bytecode);
                    code.setCode(bytecode);
                    code.setExceptionTableLength(byteStream.readU2());
                    code.setExceptionTables(parseExceptionTable(byteStream, code));
                    int codeAttributeCount = byteStream.readU2();
                    code.setAttributesCount(codeAttributeCount);
                    code.setAttributes(parseAttributes(byteStream, constantPool, codeAttributeCount));
                    attributeInfos[i] = code;
                    break;
                }
                case StackMapTable: {
                    StackMapTable stackMapTable = new StackMapTable();
                    stackMapTable.setAttributeNameIndex(attributeNameIndex);
                    stackMapTable.setAttributeLength(attributeLength);
                    //todo just ignore now
                    byteStream.readBytes(new byte[attributeLength]);
                    //stackMapTable.setNumberOfEntries(byteStream.readU2());
                    //stackMapTable.setEntries(parseStackMapFrameEntries(byteStream, stackMapTable));
                    attributeInfos[i] = stackMapTable;
                    break;
                }
                case Exceptions: {
                    Exceptions exceptions = new Exceptions();
                    exceptions.setAttributeNameIndex(attributeNameIndex);
                    exceptions.setAttributeLength(attributeLength);
                    exceptions.setNumberOfExceptions(byteStream.readU2());
                    exceptions.setExceptionIndexTable(parseExceptionIndexTable(byteStream, exceptions));
                    attributeInfos[i] = exceptions;
                    break;
                }
                case InnerClasses: {
                    InnerClass innerClass = new InnerClass();
                    innerClass.setAttributeNameIndex(attributeNameIndex);
                    innerClass.setAttributeLength(attributeLength);
                    innerClass.setNumberOfClasses(byteStream.readU2());
                    innerClass.setClasses(parseClassAttrs(byteStream, innerClass));
                    attributeInfos[i] = innerClass;
                    break;
                }
                case EnclosingMethod: {
                    EnclosingMethod enclosingMethod = new EnclosingMethod();
                    enclosingMethod.setAttributeNameIndex(attributeNameIndex);
                    enclosingMethod.setAttributeLength(attributeLength);
                    enclosingMethod.setClassIndex(byteStream.readU2());
                    enclosingMethod.setMethodIndex(byteStream.readU2());
                    attributeInfos[i] = enclosingMethod;
                    break;
                }
                case Synthetic: {
                    Synthetic synthetic = new Synthetic();
                    synthetic.setAttributeNameIndex(attributeNameIndex);
                    synthetic.setAttributeLength(attributeLength);
                    attributeInfos[i] = synthetic;
                    break;
                }
                case Signature: {
                    Signature signature = new Signature();
                    signature.setAttributeNameIndex(attributeNameIndex);
                    signature.setAttributeLength(attributeLength);
                    signature.setSignatureIndex(byteStream.readU2());
                    attributeInfos[i] = signature;
                    break;
                }
                case SourceFile: {
                    SourceFile sourceFile = new SourceFile();
                    sourceFile.setAttributeNameIndex(attributeNameIndex);
                    sourceFile.setAttributeLength(attributeLength);
                    sourceFile.setSourceFileIndex(byteStream.readU2());
                    attributeInfos[i] = sourceFile;
                    break;
                }
                case SourceDebugExtension: {
                    SourceDebugExtension sourceDebugExtension = new SourceDebugExtension();
                    sourceDebugExtension.setAttributeNameIndex(attributeNameIndex);
                    sourceDebugExtension.setAttributeLength(attributeLength);
                    byte[] debug = new byte[attributeLength];
                    byteStream.readBytes(debug);
                    sourceDebugExtension.setDebugExtension(debug);
                    attributeInfos[i] = sourceDebugExtension;
                    break;
                }
                case LineNumberTable: {
                    LineNumberTable lineNumberTable = new LineNumberTable();
                    lineNumberTable.setAttributeNameIndex(attributeNameIndex);
                    lineNumberTable.setAttributeLength(attributeLength);
                    lineNumberTable.setLineNumberTableLength(byteStream.readU2());
                    lineNumberTable.setLineNumberTableInfos(parseLineNumberTableInfos(byteStream, lineNumberTable));
                    attributeInfos[i] = lineNumberTable;
                    break;
                }
                case LocalVariableTable: {
                    LocalVariableTable localVariableTable = new LocalVariableTable();
                    localVariableTable.setAttributeNameIndex(attributeNameIndex);
                    localVariableTable.setAttributeLength(attributeLength);
                    localVariableTable.setLocalVariableTableLength(byteStream.readU2());
                    localVariableTable.setLocalVariableTableInfos(parseLocalVariableTableInfos(byteStream, localVariableTable));
                    attributeInfos[i] = localVariableTable;
                    break;
                }
                case LocalVariableTypeTable: {
                    LocalVariableTypeTable localVariableTypeTable = new LocalVariableTypeTable();
                    localVariableTypeTable.setAttributeNameIndex(attributeNameIndex);
                    localVariableTypeTable.setAttributeLength(attributeLength);
                    localVariableTypeTable.setLocalVariableTypeTableLength(byteStream.readU2());
                    localVariableTypeTable.setLocalVariableTypeTableInfos(parseLocalVariableTypeTableInfos(byteStream, localVariableTypeTable));
                    attributeInfos[i] = localVariableTypeTable;
                    break;
                }
                case Deprecated: {
                    Deprecated deprecated = new Deprecated(attributeNameIndex, attributeLength);
                    attributeInfos[i] = deprecated;
                    break;
                }
                case RuntimeVisibleAnnotations: {
                    RuntimeVisibleAnnotations runtimeVisibleAnnotations = new RuntimeVisibleAnnotations();
                    runtimeVisibleAnnotations.setAttributeNameIndex(attributeNameIndex);
                    runtimeVisibleAnnotations.setAttributeLength(attributeLength);
                    int numAnnotations = byteStream.readU2();
                    runtimeVisibleAnnotations.setNumAnnotations(numAnnotations);
                    runtimeVisibleAnnotations.setAnnotations(parseAnnotations(byteStream, numAnnotations));
                    attributeInfos[i] = runtimeVisibleAnnotations;
                    break;
                }
                case RuntimeInvisibleAnnotations: {
                    RuntimeInvisibleAnnotations runtimeInvisibleAnnotations = new RuntimeInvisibleAnnotations();
                    runtimeInvisibleAnnotations.setAttributeNameIndex(attributeNameIndex);
                    runtimeInvisibleAnnotations.setAttributeLength(attributeLength);
                    int numAnnotations = byteStream.readU2();
                    runtimeInvisibleAnnotations.setNumAnnotations(numAnnotations);
                    runtimeInvisibleAnnotations.setAnnotations(parseAnnotations(byteStream, numAnnotations));
                    attributeInfos[i] = runtimeInvisibleAnnotations;
                    break;
                }
                case RuntimeVisibleParameterAnnotations: {
                    RuntimeVisibleParameterAnnotations runtimeVisibleParameterAnnotations = new RuntimeVisibleParameterAnnotations();
                    runtimeVisibleParameterAnnotations.setAttributeNameIndex(attributeNameIndex);
                    runtimeVisibleParameterAnnotations.setAttributeLength(attributeLength);
                    int numParameters = byteStream.readU2();
                    runtimeVisibleParameterAnnotations.setNumParameters(numParameters);
                    runtimeVisibleParameterAnnotations.setParameterAnnotations(parseParameterAnnotations(byteStream, numParameters));
                    attributeInfos[i] = runtimeVisibleParameterAnnotations;
                    break;
                }
                case RuntimeInvisibleParameterAnnotations: {
                    RuntimeInvisibleParameterAnnotations runtimeInvisibleParameterAnnotations = new RuntimeInvisibleParameterAnnotations();
                    runtimeInvisibleParameterAnnotations.setAttributeNameIndex(attributeNameIndex);
                    runtimeInvisibleParameterAnnotations.setAttributeLength(attributeLength);
                    int numParameters = byteStream.readU2();
                    runtimeInvisibleParameterAnnotations.setNumParameters(numParameters);
                    runtimeInvisibleParameterAnnotations.setParameterAnnotations(parseParameterAnnotations(byteStream, numParameters));
                    attributeInfos[i] = runtimeInvisibleParameterAnnotations;
                    break;
                }
                case RuntimeVisibleTypeAnnotations: {
                    RuntimeVisibleTypeAnnotations runtimeVisibleTypeAnnotations = new RuntimeVisibleTypeAnnotations();
                    runtimeVisibleTypeAnnotations.setAttributeNameIndex(attributeNameIndex);
                    runtimeVisibleTypeAnnotations.setAttributeLength(attributeLength);
                    byteStream.readBytes(new byte[attributeLength]);
                    //todo ignore now
                    //int numAnnotations = byteStream.readU2();
                    //runtimeVisibleTypeAnnotations.setNumAnnotations(numAnnotations);
                    //runtimeVisibleTypeAnnotations.setTypeAnnotations(parseTypeAnnotations(byteStream, numAnnotations));
                    attributeInfos[i] = runtimeVisibleTypeAnnotations;
                    break;
                }
                case RuntimeInvisibleTypeAnnotations: {
                    RuntimeInvisibleTypeAnnotations runtimeInvisibleTypeAnnotations = new RuntimeInvisibleTypeAnnotations();
                    runtimeInvisibleTypeAnnotations.setAttributeNameIndex(attributeNameIndex);
                    runtimeInvisibleTypeAnnotations.setAttributeLength(attributeLength);
                    byteStream.readBytes(new byte[attributeLength]);
                    //todo ignore now
                    //int numAnnotations = byteStream.readU2();
                    //runtimeInvisibleTypeAnnotations.setNumAnnotations(numAnnotations);
                    //runtimeInvisibleTypeAnnotations.setTypeAnnotations(parseTypeAnnotations(byteStream, numAnnotations));
                    attributeInfos[i] = runtimeInvisibleTypeAnnotations;
                    break;
                }
                case AnnotationDefault: {
                    byte[] defaultValue = new byte[attributeLength];
                    byteStream.readBytes(defaultValue);
                    attributeInfos[i] = new AnnotationDefault(attributeNameIndex, attributeLength, defaultValue);
                    break;
                }
                case BootstrapMethods: {
                    BootstrapMethods bootstrapMethods = new BootstrapMethods();
                    bootstrapMethods.setAttributeNameIndex(attributeNameIndex);
                    bootstrapMethods.setAttributeLength(attributeLength);
                    bootstrapMethods.setNumBootstrapMethods(byteStream.readU2());
                    bootstrapMethods.setBootstrapMethods(parseBootstrapMethods(byteStream, bootstrapMethods));
                    attributeInfos[i] = bootstrapMethods;
                    break;
                }
                case MethodParameters: {
                    MethodParameters methodParameters = new MethodParameters();
                    methodParameters.setAttributeNameIndex(attributeNameIndex);
                    methodParameters.setAttributeLength(attributeLength);
                    int parametersCount = byteStream.readU1();
                    methodParameters.setParametersCount(parametersCount);
                    methodParameters.setParameters(parseParameters(byteStream, parametersCount));
                    attributeInfos[i] = methodParameters;
                    break;
                }
                default: {
                    log.warn("not predefined attribute {}", attributeName);
                }
            }
        }
        return attributeInfos;
    }

    private static MethodParameters.Parameter[] parseParameters(ByteStream byteStream, int parametersCount) {
        MethodParameters.Parameter[] parameters = new MethodParameters.Parameter[parametersCount];
        for (int i = 0; i < parametersCount; i++) {
            MethodParameters.Parameter parameter = new MethodParameters.Parameter();
            parameter.setNameIndex(byteStream.readU2());
            parameter.setAccessFlags(byteStream.readU2());
            parameters[i] = parameter;
        }
        return parameters;
    }

    private static BootstrapMethods.BootstrapMethod[] parseBootstrapMethods(ByteStream byteStream, BootstrapMethods bootstrapMethods) {
        int numBootstrapMethods = bootstrapMethods.getNumBootstrapMethods();
        BootstrapMethods.BootstrapMethod[] bms = new BootstrapMethods.BootstrapMethod[numBootstrapMethods];
        for (int i = 0; i < numBootstrapMethods; i++) {
            BootstrapMethods.BootstrapMethod bootstrapMethod = new BootstrapMethods.BootstrapMethod();
            bootstrapMethod.setBootstrapMethodRef(byteStream.readU2());
            int numBootstrapArguments = byteStream.readU2();
            bootstrapMethod.setNumBootstrapArguments(numBootstrapArguments);
            int[] bootstrapArguments = new int[numBootstrapArguments];
            for (int j = 0; j < numBootstrapArguments; j++) {
                bootstrapArguments[j] = byteStream.readU2();
            }
            bootstrapMethod.setBootstrapArguments(bootstrapArguments);
            bms[i] = bootstrapMethod;
        }
        return bms;
    }

    private static TypeAnnotation[] parseTypeAnnotations(ByteStream byteStream, int numAnnotations) {
        TypeAnnotation[] typeAnnotations = new TypeAnnotation[numAnnotations];
        for (int i = 0; i < numAnnotations; i++) {
            TypeAnnotation typeAnnotation = new TypeAnnotation();
            //todo ignore now not support annotation on type
            typeAnnotations[i] = typeAnnotation;
        }
        return typeAnnotations;
    }

    private static ParameterAnnotation[] parseParameterAnnotations(ByteStream byteStream, int numParameters) {
        ParameterAnnotation[] parameterAnnotations = new ParameterAnnotation[numParameters];
        for (int i = 0; i < numParameters; i++) {
            ParameterAnnotation parameterAnnotation = new ParameterAnnotation();
            int numAnnotations = byteStream.readU2();
            parameterAnnotation.setNumAnnotations(numAnnotations);
            parameterAnnotation.setAnnotations(parseAnnotations(byteStream, numAnnotations));
            parameterAnnotations[i] = parameterAnnotation;
        }
        return parameterAnnotations;
    }

    private static Annotation[] parseAnnotations(ByteStream byteStream, int numAnnotations) {
        Annotation[] annotations = new Annotation[numAnnotations];
        for (int i = 0; i < numAnnotations; i++) {
            Annotation annotation = new Annotation();
            annotation.setTypeIndex(byteStream.readU2());
            annotation.setNumElementValuePairs(byteStream.readU2());
            annotation.setElementValuePairs(parseElementValuePairs(byteStream, annotation));
            annotations[i] = annotation;
        }
        return annotations;
    }

    private static Annotation.ElementValuePair[] parseElementValuePairs(ByteStream byteStream, Annotation annotation) {
        int numElementValuePairs = annotation.getNumElementValuePairs();
        Annotation.ElementValuePair[] elementValuePairs = new Annotation.ElementValuePair[numElementValuePairs];
        for (int i = 0; i < numElementValuePairs; i++) {
            Annotation.ElementValuePair elementValuePair = new Annotation.ElementValuePair();
            elementValuePair.setElementNameIndex(byteStream.readU2());
            elementValuePair.setElementValue(parseAnnotationElementValue(byteStream));
            elementValuePairs[i] = elementValuePair;
        }
        return elementValuePairs;
    }

    private static Annotation.ElementValue parseAnnotationElementValue(ByteStream byteStream) {
        int tag = byteStream.readUnsignedByte();
        AnnotationDescriptorEnum annotationDescriptorEnum = AnnotationDescriptorEnum.getEnumByTag(tag);
        switch (Objects.requireNonNull(annotationDescriptorEnum)) {
            case BooleanType:
            case ByteType:
            case CharType:
            case ShortType:
            case IntType:
            case FloatType:
            case LongType:
            case Double:
            case StringType: {
                return new Annotation.ConstantValue(annotationDescriptorEnum.getTag(), byteStream.readU2());
            }
            case EnumType: {
                return new Annotation.EnumConstantValue(byteStream.readU2(), byteStream.readU2());
            }
            case ClassType: {
                return new Annotation.ClassInfo(byteStream.readU2());
            }
            case AnnotationType: {
                Annotation[] annotations = parseAnnotations(byteStream, byteStream.readU2());
                if (annotations.length != 1) {
                    throw new IllegalStateException(MessageFormat.format("annotation element value: {0} must be one ,but is {1}", annotationDescriptorEnum.name(), annotations.length));
                }
                return new Annotation.AnnotationValue(annotations[0]);
            }
            case ArrayType: {
                int numValues = byteStream.readU2();
                Annotation.ElementValue[] elementValues = new Annotation.ElementValue[numValues];
                for (int i = 0; i < numValues; i++) {
                    elementValues[i] = parseAnnotationElementValue(byteStream);
                }
                return new Annotation.ArrayValue(numValues, elementValues);
            }
            default: {
                throw new IllegalStateException(MessageFormat.format("undefined annotation element value tag: {0}", annotationDescriptorEnum.name()));
            }
        }
    }

    private static LocalVariableTypeTable.LocalVariableTypeTableInfo[] parseLocalVariableTypeTableInfos(ByteStream byteStream, LocalVariableTypeTable localVariableTypeTable) {
        int localVariableTypeTableLength = localVariableTypeTable.getLocalVariableTypeTableLength();
        LocalVariableTypeTable.LocalVariableTypeTableInfo[] localVariableTypeTableInfos = new LocalVariableTypeTable.LocalVariableTypeTableInfo[localVariableTypeTableLength];
        for (int i = 0; i < localVariableTypeTableLength; i++) {
            LocalVariableTypeTable.LocalVariableTypeTableInfo localVariableTypeTableInfo = new LocalVariableTypeTable.LocalVariableTypeTableInfo();
            localVariableTypeTableInfo.setStartPc(byteStream.readU2());
            localVariableTypeTableInfo.setLength(byteStream.readU2());
            localVariableTypeTableInfo.setNameIndex(byteStream.readU2());
            localVariableTypeTableInfo.setSignatureIndex(byteStream.readU2());
            localVariableTypeTableInfo.setIndex(byteStream.readU2());
            localVariableTypeTableInfos[i] = localVariableTypeTableInfo;
        }
        return localVariableTypeTableInfos;
    }

    private static LocalVariableTable.LocalVariableTableInfo[] parseLocalVariableTableInfos(ByteStream byteStream, LocalVariableTable localVariableTable) {
        int localVariableTableLength = localVariableTable.getLocalVariableTableLength();
        LocalVariableTable.LocalVariableTableInfo[] localVariableTableInfos = new LocalVariableTable.LocalVariableTableInfo[localVariableTableLength];
        for (int i = 0; i < localVariableTableLength; i++) {
            LocalVariableTable.LocalVariableTableInfo localVariableTableInfo = new LocalVariableTable.LocalVariableTableInfo();
            localVariableTableInfo.setStartPc(byteStream.readU2());
            localVariableTableInfo.setLength(byteStream.readU2());
            localVariableTableInfo.setNameIndex(byteStream.readU2());
            localVariableTableInfo.setDescriptorIndex(byteStream.readU2());
            localVariableTableInfo.setIndex(byteStream.readU2());
            localVariableTableInfos[i] = localVariableTableInfo;
        }
        return localVariableTableInfos;
    }

    private static LineNumberTable.LineNumberTableInfo[] parseLineNumberTableInfos(ByteStream byteStream, LineNumberTable lineNumberTable) {
        int lineNumberTableLength = lineNumberTable.getLineNumberTableLength();
        LineNumberTable.LineNumberTableInfo[] lineNumberTableInfos = new LineNumberTable.LineNumberTableInfo[lineNumberTableLength];
        for (int i = 0; i < lineNumberTableLength; i++) {
            LineNumberTable.LineNumberTableInfo lineNumberTableInfo = new LineNumberTable.LineNumberTableInfo();
            lineNumberTableInfo.setStartPc(byteStream.readU2());
            lineNumberTableInfo.setLineNumber(byteStream.readU2());
            lineNumberTableInfos[i] = lineNumberTableInfo;
        }
        return lineNumberTableInfos;
    }

    private static InnerClass.ClassAttr[] parseClassAttrs(ByteStream byteStream, InnerClass innerClass) {
        int numberOfClasses = innerClass.getNumberOfClasses();
        InnerClass.ClassAttr[] classAttrs = new InnerClass.ClassAttr[numberOfClasses];
        for (int i = 0; i < numberOfClasses; i++) {
            InnerClass.ClassAttr classAttr = new InnerClass.ClassAttr();
            classAttr.setInnerClassInfoIndex(byteStream.readU2());
            classAttr.setOuterClassInfoIndex(byteStream.readU2());
            classAttr.setInnerNameIndex(byteStream.readU2());
            classAttr.setInnerClassAccessFlags(byteStream.readU2());
            classAttrs[i] = classAttr;
        }
        return classAttrs;
    }

    private static int[] parseExceptionIndexTable(ByteStream byteStream, Exceptions exceptions) {
        int numberOfExceptions = exceptions.getNumberOfExceptions();
        int[] exceptionIndexTable = new int[numberOfExceptions];
        for (int i = 0; i < numberOfExceptions; i++) {
            exceptionIndexTable[i] = byteStream.readU2();
        }
        return exceptionIndexTable;
    }

    private static StackMapTable.StackMapFrame[] parseStackMapFrameEntries(ByteStream byteStream, StackMapTable stackMapTable) {
        int numberOfEntries = stackMapTable.getNumberOfEntries();
        StackMapTable.StackMapFrame[] stackMapFrames = new StackMapTable.StackMapFrame[numberOfEntries];
        for (int i = 0; i < numberOfEntries; i++) {
            StackMapTable.StackMapFrame stackMapFrame = new StackMapTable.StackMapFrame();
            //todo ignore now
        }
        return stackMapFrames;
    }

    private static Code.ExceptionTable[] parseExceptionTable(ByteStream byteStream, Code code) {
        int exceptionTableLength = code.getExceptionTableLength();
        Code.ExceptionTable[] exceptionTables = new Code.ExceptionTable[exceptionTableLength];
        for (int i = 0; i < exceptionTableLength; i++) {
            Code.ExceptionTable exceptionTable = new Code.ExceptionTable();
            exceptionTable.setStartPc(byteStream.readU2());
            exceptionTable.setEndPc(byteStream.readU2());
            exceptionTable.setHandlerPc(byteStream.readU2());
            exceptionTable.setCatchType(byteStream.readU2());
            exceptionTables[i] = exceptionTable;
        }
        return exceptionTables;
    }

    private static void parseFieldAttributes(ByteStream byteStream, FieldInfo fieldInfo) {
        int attributesCount = byteStream.readU2();
        fieldInfo.setAttributesCount(attributesCount);
        fieldInfo.setAttributes(parseAttributes(byteStream, fieldInfo.getBelongKlass().getConstantPool(), attributesCount));
    }

    private static void parseMethodAttributes(ByteStream byteStream, MethodInfo methodInfo) {
        int attributesCount = byteStream.readU2();
        methodInfo.setAttributesCount(attributesCount);
        methodInfo.setAttributes(parseAttributes(byteStream, methodInfo.getBelongKlass().getConstantPool(), attributesCount));
    }

    private static void parseClassAttributes(ByteStream byteStream, InstanceKlass klass) {
        int attributesCount = byteStream.readU2();
        klass.setAttributesCount(attributesCount);
        klass.setAttributes(parseAttributes(byteStream, klass.getConstantPool(), attributesCount));
    }

    private static void parseMethods(ByteStream byteStream, InstanceKlass klass) {
        int methodCount = byteStream.readU2();
        klass.setMethodsCount(methodCount);
        MethodInfo[] methods = new MethodInfo[methodCount];
        for (int i = 0; i < methodCount; i++) {
            MethodInfo methodInfo = new MethodInfo();
            methodInfo.setAccessFlags(byteStream.readU2());
            methodInfo.setNameIndex(byteStream.readU2());
            methodInfo.setDescriptorIndex(byteStream.readU2());
            methodInfo.setBelongKlass(klass);
            log.info("start parse method: {}", methodInfo);
            parseMethodAttributes(byteStream, methodInfo);
            methods[i] = methodInfo;
        }
        klass.setMethods(methods);
    }

    private static void parseFields(ByteStream byteStream, InstanceKlass klass) {
        int fieldCount = byteStream.readU2();
        klass.setFieldsCount(fieldCount);
        FieldInfo[] fields = new FieldInfo[fieldCount];
        for (int i = 0; i < fieldCount; i++) {
            FieldInfo fieldInfo = new FieldInfo();
            fieldInfo.setAccessFlags(byteStream.readU2());
            fieldInfo.setNameIndex(byteStream.readU2());
            fieldInfo.setDescriptorIndex(byteStream.readU2());
            fieldInfo.setBelongKlass(klass);
            log.info("start parse field {}", fieldInfo.getNameIndex());
            parseFieldAttributes(byteStream, fieldInfo);
            fields[i] = fieldInfo;
        }
        klass.setFields(fields);
    }

    private static void parseInterfaces(ByteStream byteStream, InstanceKlass klass) {
        int interfacesCount = byteStream.readU2();
        klass.setInterfacesCount(interfacesCount);
        InterfaceInfo[] interfaces = new InterfaceInfo[interfacesCount];
        ConstantPool constantPool = klass.getConstantPool();
        for (int i = 0; i < interfacesCount; i++) {
            int index = byteStream.readU2();
            ConstantClassInfo constantClassInfo = constantPool.get(index, ConstantClassInfo.class);
            InterfaceInfo interfaceInfo = new InterfaceInfo(index, constantPool.get(constantClassInfo.getNameIndex(), ConstantUtf8Info.class).getValue());
            interfaceInfo.setBelongKlass(klass);
            interfaces[i] = interfaceInfo;
        }
        klass.setInterfaces(interfaces);
    }

    private static void parseConstantPool(ByteStream byteStream, InstanceKlass klass) {
        int constantPoolCount = byteStream.readU2();
        ConstantPool constantPool = klass.getConstantPool();
        constantPool.setLength(constantPoolCount);
        constantPool.init();
        //从1开始存储，0代表不指向任何常量
        for (int i = 1; i < constantPoolCount; i++) {
            int tag = byteStream.readU1();
            ConstantPoolEnum constantPoolEnum = ConstantPoolEnum.getEnumByValue(tag);
            switch (Objects.requireNonNull(constantPoolEnum)) {
                case JvmConstantClass: {
                    constantPool.put(i, new ConstantClassInfo(byteStream.readU2()));
                    break;
                }
                case JvmConstantFieldRef: {
                    constantPool.put(i, new ConstantFieldRefInfo(byteStream.readU2(), byteStream.readU2()));
                    break;
                }
                case JvmConstantMethodRef: {
                    constantPool.put(i, new ConstantMethodRefInfo(byteStream.readU2(), byteStream.readU2()));
                    break;
                }
                case JvmConstantInterfaceMethodRef: {
                    constantPool.put(i, new ConstantInterfaceMethodRefInfo(byteStream.readU2(), byteStream.readU2()));
                    break;
                }
                case JvmConstantString: {
                    constantPool.put(i, new ConstantStringInfo(byteStream.readU2()));
                    break;
                }
                case JvmConstantInteger: {
                    constantPool.put(i, new ConstantIntegerInfo(byteStream.readInt()));
                    break;
                }
                case JvmConstantFloat: {
                    constantPool.put(i, new ConstantFloatInfo(byteStream.readFloat()));
                    break;
                }
                case JvmConstantLong: {
                    //long 占用2个表项
                    constantPool.put(i++, new ConstantLongInfo(byteStream.readLong()));
                    break;
                }
                case JvmConstantDouble: {
                    //double 占用2个表项
                    constantPool.put(i++, new ConstantDoubleInfo(byteStream.readDouble()));
                    break;
                }
                case JvmConstantNameAndType: {
                    constantPool.put(i, new ConstantNameAndTypeInfo(byteStream.readU2(), byteStream.readU2()));
                    break;
                }
                case JvmConstantUtf8: {
                    int length = byteStream.readU2();
                    byte[] bytes = new byte[length];
                    byteStream.readBytes(bytes);
                    constantPool.put(i, new ConstantUtf8Info(length, new String(bytes)));
                    break;
                }
                case JvmConstantMethodHandle: {
                    constantPool.put(i, new ConstantMethodHandleInfo(byteStream.readUnsignedByte(), byteStream.readU2()));
                    break;
                }
                case JvmConstantMethodType: {
                    constantPool.put(i, new ConstantMethodTypeInfo(byteStream.readU2()));
                    break;
                }
                case JvmConstantInvokeDynamic: {
                    constantPool.put(i, new ConstantInvokeDynamicInfo(byteStream.readU2(), byteStream.readU2()));
                    break;
                }
                default:
                    throw new UnsupportedOperationException("unsupported constant tag: " + tag);
            }
        }
    }

    private static void checkMagic(int magic) {
        if (!"cafebabe".equals(Integer.toHexString(magic))) {
            throw new IllegalArgumentException("magic number is not 0xcafebabe");
        }
    }

    //todo ignore now
    private static void checkInstanceKlass(InstanceKlass klass) {

    }
}
