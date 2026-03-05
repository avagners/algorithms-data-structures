import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Queue_2Test {

	@Test
	public void testTurn_emptyQueue() {
		Queue<Integer> queue = new Queue<>();
		Queue_2.turn(queue, 0);
		assertEquals(0, queue.size());
	}

	@Test
	public void testTurn_singleElement() {
		Queue<Integer> queue = new Queue<>();
		queue.enqueue(1);
		Queue_2.turn(queue, 1);
		assertEquals(1, queue.dequeue());
		assertEquals(0, queue.size());
	}

	@Test
	public void testTurn_fullQueue() {
		Queue<Integer> queue = new Queue<>();
		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);
		queue.enqueue(4);
		queue.enqueue(5);

		Queue_2.turn(queue, 5);

		assertEquals(5, queue.dequeue());
		assertEquals(4, queue.dequeue());
		assertEquals(3, queue.dequeue());
		assertEquals(2, queue.dequeue());
		assertEquals(1, queue.dequeue());
		assertEquals(0, queue.size());
	}

	@Test
	public void testTurn_partialQueue() {
		Queue<Integer> queue = new Queue<>();
		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);
		queue.enqueue(4);
		queue.enqueue(5);

		Queue_2.turn(queue, 3);

		// После turn первые 3 элемента (1,2,3) разворачиваются и добавляются в хвост
		// Очередь становится: [4, 5, 3, 2, 1]
		assertEquals(4, queue.dequeue());
		assertEquals(5, queue.dequeue());
		assertEquals(3, queue.dequeue());
		assertEquals(2, queue.dequeue());
		assertEquals(1, queue.dequeue());
		assertEquals(0, queue.size());
	}

	@Test
	public void testTurn_countGreaterThanSize() {
		Queue<Integer> queue = new Queue<>();
		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);

		Queue_2.turn(queue, 10);

		assertEquals(3, queue.dequeue());
		assertEquals(2, queue.dequeue());
		assertEquals(1, queue.dequeue());
		assertEquals(0, queue.size());
	}

	@Test
	public void testTurn_withStrings() {
		Queue<String> queue = new Queue<>();
		queue.enqueue("A");
		queue.enqueue("B");
		queue.enqueue("C");
		queue.enqueue("D");

		Queue_2.turn(queue, 4);

		assertEquals("D", queue.dequeue());
		assertEquals("C", queue.dequeue());
		assertEquals("B", queue.dequeue());
		assertEquals("A", queue.dequeue());
	}

	@Test
	public void testReverse_emptyQueue() {
		Queue<Integer> queue = new Queue<>();
		Queue_2.reverse(queue);
		assertEquals(0, queue.size());
	}

	@Test
	public void testReverse_singleElement() {
		Queue<Integer> queue = new Queue<>();
		queue.enqueue(1);
		Queue_2.reverse(queue);
		assertEquals(1, queue.dequeue());
		assertEquals(0, queue.size());
	}

	@Test
	public void testReverse_multipleElements() {
		Queue<Integer> queue = new Queue<>();
		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);
		queue.enqueue(4);
		queue.enqueue(5);

		Queue_2.reverse(queue);

		assertEquals(5, queue.dequeue());
		assertEquals(4, queue.dequeue());
		assertEquals(3, queue.dequeue());
		assertEquals(2, queue.dequeue());
		assertEquals(1, queue.dequeue());
		assertEquals(0, queue.size());
	}

	@Test
	public void testReverse_withStrings() {
		Queue<String> queue = new Queue<>();
		queue.enqueue("A");
		queue.enqueue("B");
		queue.enqueue("C");

		Queue_2.reverse(queue);

		assertEquals("C", queue.dequeue());
		assertEquals("B", queue.dequeue());
		assertEquals("A", queue.dequeue());
	}
}

