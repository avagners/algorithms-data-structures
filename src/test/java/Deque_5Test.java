import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Deque_5Test {

	@Test
	public void testSize_emptyDeque() {
		Deque_5<Integer> deque = new Deque_5<>();
		assertEquals(0, deque.size());
	}

	@Test
	public void testSize_afterAddFront() {
		Deque_5<Integer> deque = new Deque_5<>();
		deque.addFront(1);
		assertEquals(1, deque.size());
		deque.addFront(2);
		assertEquals(2, deque.size());
	}

	@Test
	public void testSize_afterAddTail() {
		Deque_5<Integer> deque = new Deque_5<>();
		deque.addTail(1);
		assertEquals(1, deque.size());
		deque.addTail(2);
		assertEquals(2, deque.size());
	}

	@Test
	public void testAddFrontRemoveFront_LIFO() {
		Deque_5<Integer> deque = new Deque_5<>();
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
		Deque_5<Integer> deque = new Deque_5<>();
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
		Deque_5<Integer> deque = new Deque_5<>();
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
		Deque_5<Integer> deque = new Deque_5<>();
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
		Deque_5<Integer> deque = new Deque_5<>();
		assertNull(deque.removeFront());
		assertEquals(0, deque.size());
	}

	@Test
	public void testRemoveTail_emptyDeque_returnsNull() {
		Deque_5<Integer> deque = new Deque_5<>();
		assertNull(deque.removeTail());
		assertEquals(0, deque.size());
	}

	@Test
	public void testExampleFromTask() {
		Deque_5<String> deque = new Deque_5<>();
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
		Deque_5<Integer> deque = new Deque_5<>();
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

	@Test
	public void testResize_grow() {
		Deque_5<Integer> deque = new Deque_5<>();
		
		for (int i = 0; i < 20; i++) {
			deque.addTail(i);
		}
		assertEquals(20, deque.size());
		
		for (int i = 0; i < 20; i++) {
			assertEquals(i, deque.removeFront());
		}
		assertEquals(0, deque.size());
	}

	@Test
	public void testResize_shrink() {
		Deque_5<Integer> deque = new Deque_5<>();
		
		for (int i = 0; i < 20; i++) {
			deque.addTail(i);
		}
		assertEquals(20, deque.size());
		
		for (int i = 0; i < 15; i++) {
			deque.removeFront();
		}
		assertEquals(5, deque.size());
	}

	@Test
	public void testCircularBehavior() {
		Deque_5<Integer> deque = new Deque_5<>();
		
		for (int i = 0; i < 10; i++) {
			deque.addTail(i);
		}
		
		for (int i = 0; i < 5; i++) {
			deque.removeFront();
		}
		
		for (int i = 10; i < 15; i++) {
			deque.addTail(i);
		}
		
		assertEquals(10, deque.size());
		assertEquals(5, deque.removeFront());
		assertEquals(6, deque.removeFront());
		assertEquals(7, deque.removeFront());
		assertEquals(8, deque.removeFront());
		assertEquals(9, deque.removeFront());
		assertEquals(10, deque.removeFront());
		assertEquals(11, deque.removeFront());
		assertEquals(12, deque.removeFront());
		assertEquals(13, deque.removeFront());
		assertEquals(14, deque.removeFront());
		assertEquals(0, deque.size());
	}

	@Test
	public void testWithStrings() {
		Deque_5<String> deque = new Deque_5<>();
		deque.addFront("A");
		deque.addTail("B");
		deque.addFront("C");
		
		assertEquals("C", deque.removeFront());
		assertEquals("B", deque.removeTail());
		assertEquals("A", deque.removeFront());
	}
}

