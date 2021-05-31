import spock.lang.Specification


class ByteCastSpec extends Specification {

    def "负数的进制转换"() {
        when:
        int x = 0xcafebabe;
        then:
        x == -889275714
        "cafebabe" == Integer.toHexString(x)
    }

}