import com.techzealot.tvm.jdk.Main
import spock.lang.Specification


class JvmSpec extends Specification {
    def "start jvm"(){
        expect:
        Main.main(null)
    }

}