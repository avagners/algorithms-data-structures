import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DequeTest {

	@Test
	public void testSize_emptyDeque() {
		Deque<Integer> deque = new Deque<>();
		assertEquals(0, deque.size());
	}

	@Test
	public void testSize_afterAddFront() {
		Deque<Integer> deque = new Deque<>();
		deque.addFront(1);
		assertEquals(1, deque.size());
		deque.addFront(2);
		assertEquals(2, deque.size());
	}

	@Test
	public void testSize_afterAddTail() {
		Deque<Integer> deque = new Deque<>();
		deque.addTail(1);
		assertEquals(1, deque.size());
		deque.addTail(2);
		assertEquals(2, deque.size());
	}

	@Test
	public void testSize_afterRemove() {
		Deque<Integer> deque = new Deque<>();
		deque.addFront(1);
		deque.addTail(2);
		assertEquals(2, deque.size());
		
		deque.removeFront();
		assertEquals(1, deque.size());
		
		deque.removeTail();
		assertEquals(0, deque.size());
	}

	@Test
	public void testAddFrontRemoveFront_LIFO() {
		Deque<Integer> deque = new Deque<>();
		deque.addFront(1);
		deque.addFront(2);
		deque.addFront(3);
		assertEquals(3, deque.size());
		
		assertEquals(3, deque.removeFront());
		assertEquals(2, deque.size());
		assertEquals(2, deque.removeFront());
		assertEquals(1, deque.size());
		assertEquals(1, deque.removeFront());
		assertEquals(0, deque.size());
	}

	@Test
	public void testAddTailRemoveTail_LIFO() {
		Deque<Integer> deque = new Deque<>();
		deque.addTail(1);
		deque.addTail(2);
		deque.addTail(3);
		assertEquals(3, deque.size());
		
		assertEquals(3, deque.removeTail());
		assertEquals(2, deque.size());
		assertEquals(2, deque.removeTail());
		assertEquals(1, deque.size());
		assertEquals(1, deque.removeTail());
		assertEquals(0, deque.size());
	}

	@Test
	public void testAddTailRemoveFront_FIFO() {
		Deque<Integer> deque = new Deque<>();
		deque.addTail(1);
		deque.addTail(2);
		deque.addTail(3);
		assertEquals(3, deque.size());
		
		assertEquals(1, deque.removeFront());
		assertEquals(2, deque.size());
		assertEquals(2, deque.removeFront());
		assertEquals(1, deque.size());
		assertEquals(3, deque.removeFront());
		assertEquals(0, deque.size());
	}

	@Test
	public void testAddFrontRemoveTail_FIFO() {
		Deque<Integer> deque = new Deque<>();
		deque.addFront(1);
		deque.addFront(2);
		deque.addFront(3);
		assertEquals(3, deque.size());
		
		assertEquals(1, deque.removeTail());
		assertEquals(2, deque.size());
		assertEquals(2, deque.removeTail());
		assertEquals(1, deque.size());
		assertEquals(3, deque.removeTail());
		assertEquals(0, deque.size());
	}

	@Test
	public void testRemoveFront_emptyDeque_returnsNull() {
		Deque<Integer> deque = new Deque<>();
		assertNull(deque.removeFront());
		assertEquals(0, deque.size());
	}

	@Test
	public void testRemoveTail_emptyDeque_returnsNull() {
		Deque<Integer> deque = new Deque<>();
		assertNull(deque.removeTail());
		assertEquals(0, deque.size());
	}

	@Test
	public void testExampleFromTask() {
		Deque<String> deque = new Deque<>();
		deque.addFront("f1");
		deque.addTail("t1");
		deque.addFront("f2");
		deque.addTail("t2");
		assertEquals(4, deque.size());
		
		assertEquals("f2", deque.removeFront());
		assertEquals("t2", deque.removeTail());
		assertEquals("f1", deque.removeFront());
		assertEquals("t1", deque.removeTail());
		assertEquals(0, deque.size());
	}

	@Test
	public void testMixedOperations() {
		Deque<Integer> deque = new Deque<>();
		deque.addFront(1);
		assertEquals(1, deque.size());
		deque.addTail(2);
		assertEquals(2, deque.size());
		deque.addFront(3);
		assertEquals(3, deque.size());
		deque.addTail(4);
		assertEquals(4, deque.size());
		
		assertEquals(3, deque.removeFront());
		assertEquals(3, deque.size());
		assertEquals(4, deque.removeTail());
		assertEquals(2, deque.size());
		deque.addTail(5);
		assertEquals(3, deque.size());
		assertEquals(1, deque.removeFront());
		assertEquals(2, deque.size());
		assertEquals(2, deque.removeFront());
		assertEquals(1, deque.size());
		assertEquals(5, deque.removeTail());
		assertEquals(0, deque.size());
	}
}

