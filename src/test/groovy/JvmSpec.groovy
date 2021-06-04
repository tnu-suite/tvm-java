import com.techzealot.tvm.jdk.Main
import spock.lang.Specification

class JvmSpec extends Specification {


    def "start jvm"(String program, List<String> expect) {
        when:
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
        program                 | expect
        "HelloWorld"            | ["hello, world!"]
        "primitives.printBasic" | ["true", "false", "-128", "-1", "0", "1", "127", "a", "A", "-32768", "-1", "0", "1", "32767",]
        "primitives.PrintInt"   | ["-2147483648", "-1", "0", "1", "2", "3", "4", "5", "2147483647",]
        "primitives.PrintFloat" | ["1.4E-45", "-1.0", "0.0", "1.0", "2.0", "3.4028235E38",]
        "primitives.PrintLong"  | ["0", "1", "9223372036854775807", "-9223372036854775808", "-1",]
    }

}