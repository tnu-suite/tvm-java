import spock.lang.Specification

import java.text.MessageFormat


class MessageFormatCaseSpec extends Specification {

    def "test MessageFormat"(String pattern, Object[] args, String target) {
        expect:
        MessageFormat.format(pattern, args) == target
        where:
        pattern     | args                     | target
        "{0}{1}{3}" | [1, 2] as Object[]       | "12{3}"
        "{0}{1}{3}" | [1, 2, 3, 4] as Object[] | "124"
        //格式化字符串时,两个单引号才表示一个单引号,单个单引号会被省略
        //单引号会使其后面的占位符均失效,导致直接输出占位符
        "don't {0}" | [1] as Object[]          | "dont {0}"
    }

}