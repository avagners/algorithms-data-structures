import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Queue_3Test {

	@Test
	public void testSize_emptyQueue() {
		Queue_3<Integer> queue = new Queue_3<>();
		assertEquals(0, queue.size());
	}

	@Test
	public void testSize_afterEnqueue() {
		Queue_3<Integer> queue = new Queue_3<>();
		queue.enqueue(1);
		assertEquals(1, queue.size());
		queue.enqueue(2);
		assertEquals(2, queue.size());
	}

	@Test
	public void testEnqueueDequeueFIFO() {
		Queue_3<Integer> queue = new Queue_3<>();
		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);

		assertEquals(1, queue.dequeue());
		assertEquals(2, queue.dequeue());
		assertEquals(3, queue.dequeue());
		assertEquals(0, queue.size());
	}

	@Test
	public void testDequeue_emptyQueue_returnsNull() {
		Queue_3<Integer> queue = new Queue_3<>();
		assertNull(queue.dequeue());
	}

	@Test
	public void testDequeue_afterAllElementsRemoved_returnsNull() {
		Queue_3<Integer> queue = new Queue_3<>();
		queue.enqueue(1);
		queue.dequeue();
		assertNull(queue.dequeue());
	}

	@Test
	public void testQueue_withStrings() {
		Queue_3<String> queue = new Queue_3<>();
		queue.enqueue("first");
		queue.enqueue("second");
		queue.enqueue("third");

		assertEquals("first", queue.dequeue());
		assertEquals("second", queue.dequeue());
		assertEquals("third", queue.dequeue());
	}

	@Test
	public void testQueue_mixedOperations() {
		Queue_3<Integer> queue = new Queue_3<>();
		queue.enqueue(1);
		queue.enqueue(2);
		assertEquals(2, queue.size());
		assertEquals(1, queue.dequeue());
		queue.enqueue(3);
		assertEquals(2, queue.dequeue());
		queue.enqueue(4);
		assertEquals(3, queue.dequeue());
		assertEquals(4, queue.dequeue());
	}

	@Test
	public void testPeek() {
		Queue_3<Integer> queue = new Queue_3<>();
		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);

		assertEquals(1, queue.peek());
		assertEquals(1, queue.peek()); // peek не удаляет
		assertEquals(3, queue.size());
	}

	@Test
	public void testPeek_emptyQueue_returnsNull() {
		Queue_3<Integer> queue = new Queue_3<>();
		assertNull(queue.peek());
	}

	@Test
	public void testExampleFromTask() {
		Queue_3<Integer> queue = new Queue_3<>();
		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);

		assertEquals(3, queue.size());
		assertEquals(1, queue.dequeue());
		assertEquals(2, queue.dequeue());
		assertEquals(3, queue.dequeue());
		assertEquals(0, queue.size());
	}
}

