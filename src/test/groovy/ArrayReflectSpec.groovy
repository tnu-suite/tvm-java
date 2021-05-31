import com.techzealot.tvm.examples.ArrayDemo
import spock.lang.Specification

import java.lang.reflect.Array

class ArrayReflectSpec extends Specification {

    def "test get dynamic array type"() {
        when:
        Class<?> a = ArrayDemo.class;
        def method = a.getMethod("test", new Class<?>[]{Array.newInstance(int.class, new int[]{0, 0, 0}).getClass()})
        method.invoke(new ArrayDemo(), new Object[]{new int[0][0][0]})
        then:
        method!=null
    }

}