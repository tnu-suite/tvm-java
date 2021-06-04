import spock.lang.Specification


class StringConvertSpec extends Specification {

    def "getBytes().size == toCharArray().size() when ASCII"(String input, int size) {
        expect:
        input.toCharArray().size() == size
        input.getBytes().size() == size
        where:
        input                    | size
        "hello world!"           | 12
        "([Ljava/lang/String;)V" | 22
    }

}