import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QueueTest {

	@Test
	public void testSize_emptyQueue() {
		Queue<Integer> queue = new Queue<>();
		assertEquals(0, queue.size());
	}

	@Test
	public void testSize_afterEnqueue() {
		Queue<Integer> queue = new Queue<>();
		queue.enqueue(1);
		assertEquals(1, queue.size());
		queue.enqueue(2);
		assertEquals(2, queue.size());
	}

	@Test
	public void testSize_afterDequeue() {
		Queue<Integer> queue = new Queue<>();
		queue.enqueue(1);
		queue.enqueue(2);
		queue.dequeue();
		assertEquals(1, queue.size());
	}

	@Test
	public void testEnqueueDequeueFIFO() {
		Queue<Integer> queue = new Queue<>();
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
		Queue<Integer> queue = new Queue<>();
		assertNull(queue.dequeue());
	}

	@Test
	public void testDequeue_afterAllElementsRemoved_returnsNull() {
		Queue<Integer> queue = new Queue<>();
		queue.enqueue(1);
		queue.dequeue();
		assertNull(queue.dequeue());
	}

	@Test
	public void testQueue_withStrings() {
		Queue<String> queue = new Queue<>();
		queue.enqueue("first");
		queue.enqueue("second");
		queue.enqueue("third");

		assertEquals("first", queue.dequeue());
		assertEquals("second", queue.dequeue());
		assertEquals("third", queue.dequeue());
	}

	@Test
	public void testQueue_mixedOperations() {
		Queue<Integer> queue = new Queue<>();
		queue.enqueue(1);
		queue.enqueue(2);
		assertEquals(2, queue.size());
		assertEquals(1, queue.dequeue());
		queue.enqueue(3);
		assertEquals(2, queue.dequeue());
		assertEquals(3, queue.dequeue());
	}

	@Test
	public void testExampleFromTask() {
		Queue<Integer> queue = new Queue<>();
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

