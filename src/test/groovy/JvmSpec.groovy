import com.techzealot.tvm.jdk.Main
import spock.lang.Specification


class JvmSpec extends Specification {


    def "start jvm"(String program){
        expect:
        def prefix = "com.techzealot.tvm.examples.";
        Main.startJVM(prefix+program)
        where:
        program|_
        "HelloWorld"|_
        "primitives.printBasic" | _
    }

}