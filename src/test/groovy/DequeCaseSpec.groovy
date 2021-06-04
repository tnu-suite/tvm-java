import spock.lang.Specification


class DequeCaseSpec extends Specification {

    def "test deque push and pop"() {
        when:
        Deque<Integer> deque = new ArrayDeque<>();
        deque.addLast(1);
        deque.addLast(2);
        deque.addLast(3);
        deque.addLast(4);
        deque.addLast(5);
        then:
        deque.pollFirst() == 1
        deque.pollFirst() == 2
        deque.pollFirst() == 3
        deque.pollFirst() == 4
        deque.pollFirst() == 5
        deque.isEmpty()

        when:
        deque.push(1)
        deque.push(2)
        deque.push(3)
        then:
        deque.pop() == 3
        deque.pop() == 2
        deque.pop() == 1

    }

}