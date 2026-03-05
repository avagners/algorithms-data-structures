import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Queue_4Test {

	@Test
	public void testConstructor() {
		Queue_4<Integer> queue = new Queue_4<>(5);
		assertEquals(5, queue.capacity());
		assertEquals(0, queue.size());
		assertTrue(queue.isEmpty());
		assertFalse(queue.isFull());
	}

	@Test
	public void testEnqueue() {
		Queue_4<Integer> queue = new Queue_4<>(5);
		assertTrue(queue.enqueue(1));
		assertTrue(queue.enqueue(2));
		assertTrue(queue.enqueue(3));
		assertEquals(3, queue.size());
		assertFalse(queue.isFull());
	}

	@Test
	public void testEnqueue_untilFull() {
		Queue_4<Integer> queue = new Queue_4<>(3);
		assertTrue(queue.enqueue(1));
		assertTrue(queue.enqueue(2));
		assertTrue(queue.enqueue(3));
		assertEquals(3, queue.size());
		assertTrue(queue.isFull());
		assertFalse(queue.enqueue(4));  // очередь полна
		assertTrue(queue.isFull());
	}

	@Test
	public void testDequeue() {
		Queue_4<Integer> queue = new Queue_4<>(5);
		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);

		assertEquals(1, queue.dequeue());
		assertEquals(2, queue.dequeue());
		assertEquals(3, queue.dequeue());
		assertNull(queue.dequeue());
		assertTrue(queue.isEmpty());
	}

	@Test
	public void testDequeue_emptyQueue_returnsNull() {
		Queue_4<Integer> queue = new Queue_4<>(5);
		assertNull(queue.dequeue());
	}

	@Test
	public void testFIFO_order() {
		Queue_4<Integer> queue = new Queue_4<>(5);
		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);
		queue.enqueue(4);
		queue.enqueue(5);

		assertEquals(1, queue.dequeue());
		assertEquals(2, queue.dequeue());
		assertEquals(3, queue.dequeue());
		assertEquals(4, queue.dequeue());
		assertEquals(5, queue.dequeue());
	}

	@Test
	public void testCircularBehavior() {
		Queue_4<Integer> queue = new Queue_4<>(3);
		
		// Заполняем очередь
		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);
		assertTrue(queue.isFull());

		// Удаляем два элемента
		assertEquals(1, queue.dequeue());
		assertEquals(2, queue.dequeue());

		// Добавляем ещё два элемента (должны занять места в начале массива)
		assertTrue(queue.enqueue(4));
		assertTrue(queue.enqueue(5));

		// Проверяем порядок: [3, 4, 5]
		assertEquals(3, queue.dequeue());
		assertEquals(4, queue.dequeue());
		assertEquals(5, queue.dequeue());
		assertTrue(queue.isEmpty());
	}

	@Test
	public void testPeek() {
		Queue_4<Integer> queue = new Queue_4<>(5);
		queue.enqueue(1);
		queue.enqueue(2);

		assertEquals(1, queue.peek());
		assertEquals(1, queue.peek());  // peek не удаляет
		assertEquals(2, queue.size());
	}

	@Test
	public void testPeek_emptyQueue_returnsNull() {
		Queue_4<Integer> queue = new Queue_4<>(5);
		assertNull(queue.peek());
	}

	@Test
	public void testClear() {
		Queue_4<Integer> queue = new Queue_4<>(5);
		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);

		queue.clear();

		assertEquals(0, queue.size());
		assertTrue(queue.isEmpty());
		assertFalse(queue.isFull());
	}

	@Test
	public void testWithStrings() {
		Queue_4<String> queue = new Queue_4<>(3);
		queue.enqueue("A");
		queue.enqueue("B");
		queue.enqueue("C");

		assertEquals("A", queue.dequeue());
		assertEquals("B", queue.dequeue());
		assertEquals("C", queue.dequeue());
	}

	@Test
	public void testMultipleCycles() {
		Queue_4<Integer> queue = new Queue_4<>(2);
		
		for (int i = 0; i < 5; i++) {
			assertTrue(queue.enqueue(i * 10));
			assertTrue(queue.enqueue(i * 10 + 1));
			assertTrue(queue.isFull());
			
			assertEquals(i * 10, queue.dequeue());
			assertEquals(i * 10 + 1, queue.dequeue());
			assertTrue(queue.isEmpty());
		}
	}
}

