import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StackTest {

	@Test
	public void testSize_emptyStack() {
		Stack<Integer> stack = new Stack<>();
		assertEquals(0, stack.size());
	}

	@Test
	public void testSize_afterPush() {
		Stack<Integer> stack = new Stack<>();
		stack.push(1);
		assertEquals(1, stack.size());
		stack.push(2);
		assertEquals(2, stack.size());
	}

	@Test
	public void testSize_afterPop() {
		Stack<Integer> stack = new Stack<>();
		stack.push(1);
		stack.push(2);
		stack.pop();
		assertEquals(1, stack.size());
	}

	@Test
	public void testPush_and_pop_LIFO_order() {
		Stack<Integer> stack = new Stack<>();
		stack.push(1);
		stack.push(2);
		stack.push(3);

		assertEquals(3, stack.pop());
		assertEquals(2, stack.pop());
		assertEquals(1, stack.pop());
		assertEquals(0, stack.size());
	}

	@Test
	public void testPop_emptyStack_returnsNull() {
		Stack<Integer> stack = new Stack<>();
		assertNull(stack.pop());
	}

	@Test
	public void testPop_afterAllElementsRemoved_returnsNull() {
		Stack<Integer> stack = new Stack<>();
		stack.push(1);
		stack.pop();
		assertNull(stack.pop());
	}

	@Test
	public void testPeek_returnsTopElement() {
		Stack<Integer> stack = new Stack<>();
		stack.push(1);
		stack.push(2);
		stack.push(3);

		assertEquals(3, stack.peek());
		assertEquals(3, stack.size());
	}

	@Test
	public void testPeek_emptyStack_returnsNull() {
		Stack<Integer> stack = new Stack<>();
		assertNull(stack.peek());
	}

	@Test
	public void testPeek_afterPop_returnsNewTop() {
		Stack<Integer> stack = new Stack<>();
		stack.push(1);
		stack.push(2);
		stack.push(3);

		stack.pop();
		assertEquals(2, stack.peek());
	}

	@Test
	public void testStack_withStrings() {
		Stack<String> stack = new Stack<>();
		stack.push("first");
		stack.push("second");
		stack.push("third");

		assertEquals("third", stack.pop());
		assertEquals("second", stack.pop());
		assertEquals("first", stack.pop());
	}

	@Test
	public void testStack_withMixedOperations() {
		Stack<Integer> stack = new Stack<>();
		stack.push(1);
		stack.push(2);
		assertEquals(2, stack.peek());
		stack.push(3);
		assertEquals(3, stack.pop());
		assertEquals(2, stack.peek());
		assertEquals(2, stack.size());
	}

	@Test
	public void testExampleFromTask() {
		Stack<Object> stack = new Stack<>();
		stack.push(1);
		stack.push("2");
		stack.push(3.14);

		assertEquals(3, stack.size());
		assertEquals(3.14, stack.pop());
		assertEquals("2", stack.pop());
		assertEquals(1, stack.pop());
		assertEquals(0, stack.size());
	}
}
