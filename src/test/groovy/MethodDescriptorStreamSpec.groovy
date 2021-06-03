import com.techzealot.tvm.hotspot.src.share.vm.interpreter.DescriptorInfo
import com.techzealot.tvm.hotspot.src.share.vm.interpreter.MethodDescriptorStream
import com.techzealot.tvm.hotspot.src.share.vm.runtime.BasicType
import spock.lang.Specification


class MethodDescriptorStreamSpec extends Specification {

    def "test method descriptor parse"(String descriptor, List<DescriptorInfo> paramDescriptors, DescriptorInfo returnDescriptor) {
        when:
        MethodDescriptorStream ds = new MethodDescriptorStream(descriptor);
        then:
        ds.paramDescriptors == paramDescriptors
        ds.returnDescriptor == returnDescriptor
        where:
        descriptor                    | paramDescriptors                                                                                                                                                 | returnDescriptor
        "(Z)"                         | [DescriptorInfo.BOOLEAN]                                                                                                                                         | null
        "()V"                         | []                                                                                                                                                               | DescriptorInfo.VOID
        "(Z)V"                        | [DescriptorInfo.BOOLEAN]                                                                                                                                         | DescriptorInfo.VOID
        "(Z)D"                        | [DescriptorInfo.BOOLEAN]                                                                                                                                         | DescriptorInfo.DOUBLE
        "(Z)Ljava/lang/String;"       | [DescriptorInfo.BOOLEAN]                                                                                                                                         | new DescriptorInfo(BasicType.T_OBJECT, String.class)
        "([B)V"                       | [new DescriptorInfo(BasicType.T_ARRAY, 1, DescriptorInfo.BYTE)]                                                                                                  | DescriptorInfo.VOID
        "([BDLjava/lang/String;I)[[I" | [new DescriptorInfo(BasicType.T_ARRAY, 1, DescriptorInfo.BYTE), DescriptorInfo.DOUBLE, new DescriptorInfo(BasicType.T_OBJECT, String.class), DescriptorInfo.INT] | new DescriptorInfo(BasicType.T_ARRAY, 2, DescriptorInfo.INT)
        "([[[B)V"                     | [new DescriptorInfo(BasicType.T_ARRAY, 3, DescriptorInfo.BYTE)]                                                                                                  | DescriptorInfo.VOID
        "(Ljava/lang/String;)V"       | [new DescriptorInfo(BasicType.T_OBJECT, String.class)]                                                                                                           | DescriptorInfo.VOID
        "([[Ljava/lang/String;)V"     | [new DescriptorInfo(BasicType.T_ARRAY, 2, new DescriptorInfo(BasicType.T_OBJECT, String.class))]                                                                 | DescriptorInfo.VOID
    }

    def "constants validate"(DescriptorInfo source, DescriptorInfo target) {
        expect:
        source == target
        where:
        source                 | target
        DescriptorInfo.VOID    | new DescriptorInfo(BasicType.T_VOID, void.class)
        DescriptorInfo.BOOLEAN | new DescriptorInfo(BasicType.T_BOOLEAN, boolean.class)
        DescriptorInfo.BYTE    | new DescriptorInfo(BasicType.T_BYTE, byte.class)
        DescriptorInfo.CHAR    | new DescriptorInfo(BasicType.T_CHAR, char.class)
        DescriptorInfo.SHORT   | new DescriptorInfo(BasicType.T_SHORT, short.class)
        DescriptorInfo.INT     | new DescriptorInfo(BasicType.T_INT, int.class)
        DescriptorInfo.FLOAT   | new DescriptorInfo(BasicType.T_FLOAT, float.class)
        DescriptorInfo.LONG    | new DescriptorInfo(BasicType.T_LONG, long.class)
        DescriptorInfo.DOUBLE  | new DescriptorInfo(BasicType.T_DOUBLE, double.class)
    }

}