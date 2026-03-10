import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Deque_2Test {

	@Test
	public void testSize_emptyDeque() {
		Deque_2<Integer> deque = new Deque_2<>();
		assertEquals(0, deque.size());
	}

	@Test
	public void testSize_afterAddFront() {
		Deque_2<Integer> deque = new Deque_2<>();
		deque.addFront(1);
		assertEquals(1, deque.size());
		deque.addFront(2);
		assertEquals(2, deque.size());
	}

	@Test
	public void testSize_afterAddTail() {
		Deque_2<Integer> deque = new Deque_2<>();
		deque.addTail(1);
		assertEquals(1, deque.size());
		deque.addTail(2);
		assertEquals(2, deque.size());
	}

	@Test
	public void testSize_afterRemove() {
		Deque_2<Integer> deque = new Deque_2<>();
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
		Deque_2<Integer> deque = new Deque_2<>();
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
		Deque_2<Integer> deque = new Deque_2<>();
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
		Deque_2<Integer> deque = new Deque_2<>();
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
		Deque_2<Integer> deque = new Deque_2<>();
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
		Deque_2<Integer> deque = new Deque_2<>();
		assertNull(deque.removeFront());
		assertEquals(0, deque.size());
	}

	@Test
	public void testRemoveTail_emptyDeque_returnsNull() {
		Deque_2<Integer> deque = new Deque_2<>();
		assertNull(deque.removeTail());
		assertEquals(0, deque.size());
	}

	@Test
	public void testExampleFromTask() {
		Deque_2<String> deque = new Deque_2<>();
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
	public void testRebalance_removeFront_whenFrontEmpty() {
		Deque_2<Integer> deque = new Deque_2<>();
		deque.addTail(1);
		deque.addTail(2);
		deque.addTail(3);
		deque.addTail(4);
		deque.addTail(5);
		assertEquals(5, deque.size());

		assertEquals(1, deque.removeFront());
		assertEquals(4, deque.size());
		assertEquals(2, deque.removeFront());
		assertEquals(3, deque.removeFront());
		assertEquals(4, deque.removeFront());
		assertEquals(5, deque.removeFront());
		assertEquals(0, deque.size());
	}

	@Test
	public void testRebalance_removeTail_whenBackEmpty() {
		Deque_2<Integer> deque = new Deque_2<>();
		deque.addFront(1);
		deque.addFront(2);
		deque.addFront(3);
		deque.addFront(4);
		deque.addFront(5);
		assertEquals(5, deque.size());

		assertEquals(1, deque.removeTail());
		assertEquals(4, deque.size());
		assertEquals(2, deque.removeTail());
		assertEquals(3, deque.removeTail());
		assertEquals(4, deque.removeTail());
		assertEquals(5, deque.removeTail());
		assertEquals(0, deque.size());
	}

	@Test
	public void testMixedOperations() {
		Deque_2<Integer> deque = new Deque_2<>();
		deque.addFront(1);
		assertEquals(1, deque.size());
		deque.addTail(2);
		assertEquals(2, deque.size());
		deque.addFront(3);
		assertEquals(3, deque.size());
		deque.addTail(4);
		assertEquals(4, deque.size());
		deque.addFront(5);
		assertEquals(5, deque.size());

		assertEquals(5, deque.removeFront());
		assertEquals(4, deque.size());
		assertEquals(3, deque.removeFront());
		assertEquals(3, deque.size());
		deque.addTail(6);
		assertEquals(4, deque.size());
		assertEquals(1, deque.removeFront());
		assertEquals(3, deque.size());
		assertEquals(6, deque.removeTail());
		assertEquals(2, deque.size());
		assertEquals(4, deque.removeTail());
		assertEquals(1, deque.size());
		assertEquals(2, deque.removeTail());
		assertEquals(0, deque.size());
	}
}

