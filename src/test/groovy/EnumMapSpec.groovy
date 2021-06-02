import com.techzealot.tvm.examples.ColorEnum
import spock.lang.Specification

class EnumMapSpec extends Specification {

    def "test enum static"(){
        expect:
        ColorEnum.colors.keySet().contains(1)
        ColorEnum.colors.keySet().contains(2)
    }
}