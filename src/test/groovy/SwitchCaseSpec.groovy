import com.techzealot.tvm.hotspot.src.share.vm.oops.constantpool.support.ConstantPoolEnum
import spock.lang.Specification


class SwitchCaseSpec extends Specification {

    def "switch case enum input null"() {
        when:
        switch (null) {
            case ConstantPoolEnum.JvmConstantClass: {
                break
            }
            default: {
                throw new UnsupportedOperationException()
            }
        }
        then:
        thrown(UnsupportedOperationException)
    }

    def "test switch boxing"(){
        when:
        Character ch='a';
        switch (ch){
            case 'a':{
                ch='b';
                break;
            }
        }
        then:
        ch=='b'
    }

}