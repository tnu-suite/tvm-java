import com.techzealot.tvm.hotspot.src.share.vm.utilities.Bytes
import spock.lang.Specification


class BitCaseSpec extends Specification {

    def "test float to bit"(float f) {
        when:
        int i = Float.floatToIntBits(f)
        float i2f = Float.intBitsToFloat(i)
        then:
        f == i2f
        where:
        f      | _
        -1.0f  | _
        1.0f   | _
        100.0f | _
    }

    def "test signed shift and unsigned shift"(byte source, byte target) {
        /*expect:
        (source >> 2) == target
        where:
        source      | target
        0b1111_1011 | 0b1111_1110*/
    }

    def "test unsigned shift"(byte source, byte target) {
        /*expect:
        (source >>> 2) == target
        where:
        source      | target
        0b1111_1011 | 0b0011_11_10*/
    }

    def "test long separate to 2 int "(long input) {
        expect:
        input == Bytes.combine(Bytes.getHigher32BitsFromLong(input), Bytes.getLower32BitsFromLong(input))
        where:
        input          | _
        0L             | _
        1L             | _
        -1             | _
        100            | _
        Long.MAX_VALUE | _
        Long.MIN_VALUE | _
    }

}