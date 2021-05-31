import com.techzealot.tvm.examples.HelloWorld
import com.techzealot.tvm.hotspot.src.share.vm.classfile.ClassFileParser
import spock.lang.Specification

class ClassParserSpec extends Specification {

    def "test ClassFileParser.parseClassFile"(String name) {
        expect:
        def bytes = HelloWorld.class.getResourceAsStream(name).getBytes()
        ClassFileParser.parseClassFile(bytes)
        where:
        name               | _
        "HelloWorld.class" | _
    }

}