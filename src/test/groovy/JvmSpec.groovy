import com.techzealot.tvm.jdk.Main
import spock.lang.Specification


class JvmSpec extends Specification {
    def "start jvm"(String program){
        when:
        def prefix="com.techzealot.tvm.examples.";
        then:
        Main.startJVM(prefix+program)
        where:
        program|_
        "HelloWorld"|_
    }

}