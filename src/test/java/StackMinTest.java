import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StackMinTest {

	@Test
	public void testGetMin_emptyStack() {
		StackMin<Integer> stack = new StackMin<>();
		assertNull(stack.getMin());
	}

	@Test
	public void testGetMin_singleElement() {
		StackMin<Integer> stack = new StackMin<>();
		stack.push(5);
		assertEquals(5, stack.getMin());
	}

	@Test
	public void testGetMin_increasingElements() {
		StackMin<Integer> stack = new StackMin<>();
		stack.push(1);
		stack.push(2);
		stack.push(3);
		assertEquals(1, stack.getMin());
	}

	@Test
	public void testGetMin_decreasingElements() {
		StackMin<Integer> stack = new StackMin<>();
		stack.push(3);
		stack.push(2);
		stack.push(1);
		assertEquals(1, stack.getMin());
	}

	@Test
	public void testGetMin_mixedElements() {
		StackMin<Integer> stack = new StackMin<>();
		stack.push(5);
		stack.push(3);
		stack.push(7);
		stack.push(1);
		stack.push(4);
		assertEquals(1, stack.getMin());
	}

	@Test
	public void testGetMin_afterPop() {
		StackMin<Integer> stack = new StackMin<>();
		stack.push(5);
		stack.push(3);
		stack.push(7);
		stack.push(1);

		assertEquals(1, stack.getMin());
		stack.pop(); // удаляем 1
		assertEquals(3, stack.getMin());
		stack.pop(); // удаляем 7
		assertEquals(3, stack.getMin());
		stack.pop(); // удаляем 3
		assertEquals(5, stack.getMin());
	}

	@Test
	public void testGetMin_afterPopAllElements() {
		StackMin<Integer> stack = new StackMin<>();
		stack.push(2);
		stack.push(1);
		stack.push(3);

		stack.pop();
		stack.pop();
		stack.pop();

		assertNull(stack.getMin());
	}

	@Test
	public void testGetMin_duplicateMinValues() {
		StackMin<Integer> stack = new StackMin<>();
		stack.push(3);
		stack.push(1);
		stack.push(1);
		stack.push(1);
		stack.push(5);

		assertEquals(1, stack.getMin());
		stack.pop(); // 5
		assertEquals(1, stack.getMin());
		stack.pop(); // 1
		assertEquals(1, stack.getMin());
		stack.pop(); // 1
		assertEquals(1, stack.getMin());
		stack.pop(); // 1
		assertEquals(3, stack.getMin());
		stack.pop(); // 3
		assertNull(stack.getMin());
	}

	@Test
	public void testGetMin_withStrings() {
		StackMin<String> stack = new StackMin<>();
		stack.push("banana");
		stack.push("apple");
		stack.push("cherry");

		assertEquals("apple", stack.getMin());
	}

	@Test
	public void testPushPopPeek() {
		StackMin<Integer> stack = new StackMin<>();
		stack.push(10);
		stack.push(20);
		stack.push(5);

		assertEquals(5, stack.peek());
		assertEquals(5, stack.getMin());
		assertEquals(5, stack.pop());
		assertEquals(20, stack.peek());
		assertEquals(10, stack.getMin());
	}
}
