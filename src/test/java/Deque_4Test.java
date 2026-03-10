import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Deque_4Test {

	@Test
	public void testGetMin_emptyDeque() {
		Deque_4<Integer> deque = new Deque_4<>();
		assertNull(deque.getMin());
	}

	@Test
	public void testGetMin_singleElement() {
		Deque_4<Integer> deque = new Deque_4<>();
		deque.addFront(5);
		assertEquals(5, deque.getMin());
	}

	@Test
	public void testGetMin_addFront() {
		Deque_4<Integer> deque = new Deque_4<>();
		deque.addFront(3);
		deque.addFront(2);
		deque.addFront(1);
		assertEquals(1, deque.getMin());
	}

	@Test
	public void testGetMin_addTail() {
		Deque_4<Integer> deque = new Deque_4<>();
		deque.addTail(3);
		deque.addTail(2);
		deque.addTail(1);
		assertEquals(1, deque.getMin());
	}

	@Test
	public void testGetMin_mixedAdd() {
		Deque_4<Integer> deque = new Deque_4<>();
		deque.addFront(5);
		deque.addTail(3);
		deque.addFront(7);
		deque.addTail(1);
		assertEquals(1, deque.getMin());
	}

	@Test
	public void testGetMin_afterRemoveFront() {
		Deque_4<Integer> deque = new Deque_4<>();
		deque.addFront(3);
		deque.addFront(2);
		deque.addFront(1);
		assertEquals(1, deque.getMin());
		
		deque.removeFront();
		assertEquals(2, deque.getMin());
		
		deque.removeFront();
		assertEquals(3, deque.getMin());
	}

	@Test
	public void testGetMin_afterRemoveTail() {
		Deque_4<Integer> deque = new Deque_4<>();
		deque.addTail(1);
		deque.addTail(2);
		deque.addTail(3);
		assertEquals(1, deque.getMin());
		
		deque.removeTail();
		assertEquals(1, deque.getMin());
		
		deque.removeTail();
		assertEquals(1, deque.getMin());
	}

	@Test
	public void testGetMin_afterRemoveAll() {
		Deque_4<Integer> deque = new Deque_4<>();
		deque.addFront(3);
		deque.addTail(1);
		deque.addFront(2);
		
		deque.removeFront();
		deque.removeFront();
		deque.removeTail();
		
		assertNull(deque.getMin());
	}

	@Test
	public void testGetMin_withRebalance() {
		Deque_4<Integer> deque = new Deque_4<>();
		deque.addTail(5);
		deque.addTail(4);
		deque.addTail(3);
		deque.addTail(2);
		deque.addTail(1);
		
		assertEquals(1, deque.getMin());
		
		deque.removeFront();
		deque.removeFront();
		
		assertEquals(1, deque.getMin());
	}

	@Test
	public void testGetMin_duplicateMinValues() {
		Deque_4<Integer> deque = new Deque_4<>();
		deque.addFront(3);
		deque.addFront(1);
		deque.addFront(1);
		deque.addFront(1);
		deque.addFront(5);
		
		assertEquals(1, deque.getMin());
		
		deque.removeFront();  // 5
		assertEquals(1, deque.getMin());
		
		deque.removeFront();  // 1
		assertEquals(1, deque.getMin());
		
		deque.removeFront();  // 1
		assertEquals(1, deque.getMin());
		
		deque.removeFront();  // 1
		assertEquals(3, deque.getMin());
		
		deque.removeFront();  // 3
		assertNull(deque.getMin());
	}

	@Test
	public void testSize() {
		Deque_4<Integer> deque = new Deque_4<>();
		assertEquals(0, deque.size());
		
		deque.addFront(1);
		assertEquals(1, deque.size());
		
		deque.addTail(2);
		assertEquals(2, deque.size());
		
		deque.removeFront();
		assertEquals(1, deque.size());
	}

	@Test
	public void testRemoveFront_emptyDeque() {
		Deque_4<Integer> deque = new Deque_4<>();
		assertNull(deque.removeFront());
	}

	@Test
	public void testRemoveTail_emptyDeque() {
		Deque_4<Integer> deque = new Deque_4<>();
		assertNull(deque.removeTail());
	}
}

