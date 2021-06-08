import spock.lang.Specification


class NumberCastSpec extends Specification {

    def "test int cast"(int value) {
        expect:
        (byte) (0xff & value) == (byte) value
        where:
        value             | _
        Integer.MAX_VALUE | _
        -1                | _
        1                 | _
        Integer.MIN_VALUE | _

    }

}