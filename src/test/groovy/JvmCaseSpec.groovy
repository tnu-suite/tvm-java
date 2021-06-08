import com.techzealot.tvm.jdk.Main
import spock.lang.Specification

class JvmCaseSpec extends Specification {


    def "test Main.startJvm()"(String program, List<String> expect) {
        when: "调整输出流至文件"
        def prefix = "com.techzealot.tvm.examples."
        //将输出流定位到文件,便于自动化比较测试结果
        // Q:无法调用文件输入输出api
        // A:1.编写虚拟机的过程中刚开始只支持静态方法调用
        // A:2.简洁方便
        def outFile = System.getProperty("user.dir") + File.separator + "build" + File.separator + program + ".txt"
        //使用FileOutputStream,默认覆盖模式，无需处理多次执行追加写问题
        def out = new PrintStream(outFile)
        System.setOut(out)
        then:
        Main.startJVM(prefix + program)
        System.out.flush()
        when:
        def outputs = []
        new File(outFile).splitEachLine("\n") {
            outputs.addAll(it)
        }
        then:
        outputs == expect
        where:
        program                    | expect
        "HelloWorld"               | ["hello, world!"]
        "primitives.printBasic"    | ["true", "false", "-128", "-1", "0", "1", "127", "a", "A", "-32768", "-1", "0", "1", "32767",]
        "primitives.PrintInt"      | ["-2147483648", "-1", "0", "1", "2", "3", "4", "5", "2147483647",]
        "primitives.PrintFloat"    | ["1.4E-45", "-1.0", "0.0", "1.0", "2.0", "3.4028235E38",]
        "primitives.PrintLong"     | ["0", "1", "9223372036854775807", "-9223372036854775808", "-1",]
        "primitives.PrintDouble"   | ["4.9E-324", "-1.0", "0.0", "1.0", "1.7976931348623157E308",]
        "primitives.PrintComplex"  | ["true", "100", "d", "10", "2000000", "234.5", "12345", "1.23456789E7",]
        "typecast.IntegerCastCase" | ["-2147483648", "0", "0", "\u0000", "-2.14748365E9", "-2147483648", "-2.147483648E9", "-1", "-1", "-1", "\uFFFF", "-1.0", "-1", "-1.0", "2147483647", "-1", "-1", "\uFFFF", "2.14748365E9", "2147483647", "2.147483647E9",]
        "typecast.FloatCastCase"   | ["1.4E-45", "0", "0", "\u0000", "0", "0", "1.401298464324817E-45", "-1.1", "-1", "-1", "\uFFFF", "-1", "-1", "-1.100000023841858", "3.4028235E38", "-1", "-1", "\uFFFF", "2147483647", "9223372036854775807", "3.4028234663852886E38",]
        "typecast.BaseCastCase"    | ["127", "127", "\u007F", "127", "127.0", "127", "127.0",]
        "typecast.LongCastCase"    | ["-9223372036854775808", "0", "0", "\u0000", "0", "-9.223372E18", "-9.223372036854776E18", "-1", "-1", "-1", "\uFFFF", "-1", "-1.0", "-1.0", "9223372036854775807", "-1", "-1", "\uFFFF", "-1", "9.223372E18", "9.223372036854776E18",]
        "typecast.DoubleCastCase"  | ["4.9E-324", "0", "0", "\u0000", "0", "0.0", "0", "4.9E-324", "-1.0", "-1", "-1", "\uFFFF", "-1", "-1.0", "-1", "-1.0", "1.7976931348623157E308", "-1", "-1", "\uFFFF", "2147483647", "Infinity", "9223372036854775807", "1.7976931348623157E308",]
    }

}