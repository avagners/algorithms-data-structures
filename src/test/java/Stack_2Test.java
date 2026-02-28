import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Stack_2Test {

	@Test
	public void testPushPopLIFO() {
		Stack_2<Integer> stack = new Stack_2<>();
		stack.push(1);
		stack.push(2);
		stack.push(3);

		assertEquals(3, stack.pop());
		assertEquals(2, stack.pop());
		assertEquals(1, stack.pop());
		assertEquals(0, stack.size());
	}

	@Test
	public void testPeekReturnsFirstElement() {
		Stack_2<Integer> stack = new Stack_2<>();
		stack.push(1);
		stack.push(2);
		stack.push(3);

		assertEquals(3, stack.peek());
		assertEquals(3, stack.size());
	}

	@Test
	public void testEmptyReturnsNull() {
		Stack_2<Integer> stack = new Stack_2<>();
		assertNull(stack.pop());
		assertNull(stack.peek());
	}

	@Test
	public void testWithStrings() {
		Stack_2<String> stack = new Stack_2<>();
		stack.push("first");
		stack.push("second");
		stack.push("third");

		assertEquals("third", stack.pop());
		assertEquals("second", stack.pop());
		assertEquals("first", stack.pop());
	}

	@Test
	public void testAfterPopReturnsNewTop() {
		Stack_2<Integer> stack = new Stack_2<>();
		stack.push(1);
		stack.push(2);
		stack.push(3);

		stack.pop();
		assertEquals(2, stack.peek());
	}

	@Test
	public void testMixedOperations() {
		Stack_2<Integer> stack = new Stack_2<>();
		stack.push(1);
		stack.push(2);
		assertEquals(2, stack.peek());
		stack.push(3);
		assertEquals(3, stack.pop());
		assertEquals(2, stack.peek());
		assertEquals(2, stack.size());
	}
}
