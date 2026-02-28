import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StackAverageTest {

	@Test
	public void testGetAverage_emptyStack() {
		StackAverage<Integer> stack = new StackAverage<>();
		assertEquals(0.0, stack.getAverage(), 0.001);
	}

	@Test
	public void testGetAverage_singleElement() {
		StackAverage<Integer> stack = new StackAverage<>();
		stack.push(10);
		assertEquals(10.0, stack.getAverage(), 0.001);
	}

	@Test
	public void testGetAverage_multipleElements() {
		StackAverage<Integer> stack = new StackAverage<>();
		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.push(4);
		stack.push(5);
		assertEquals(3.0, stack.getAverage(), 0.001);
	}

	@Test
	public void testGetAverage_afterPush() {
		StackAverage<Integer> stack = new StackAverage<>();
		stack.push(10);
		assertEquals(10.0, stack.getAverage(), 0.001);
		stack.push(20);
		assertEquals(15.0, stack.getAverage(), 0.001);
		stack.push(30);
		assertEquals(20.0, stack.getAverage(), 0.001);
	}

	@Test
	public void testGetAverage_afterPop() {
		StackAverage<Integer> stack = new StackAverage<>();
		stack.push(10);
		stack.push(20);
		stack.push(30);
		assertEquals(20.0, stack.getAverage(), 0.001);

		stack.pop();
		assertEquals(15.0, stack.getAverage(), 0.001);

		stack.pop();
		assertEquals(10.0, stack.getAverage(), 0.001);
	}

	@Test
	public void testGetAverage_afterPopAllElements() {
		StackAverage<Integer> stack = new StackAverage<>();
		stack.push(5);
		stack.push(10);
		stack.push(15);

		stack.pop();
		stack.pop();
		stack.pop();

		assertEquals(0.0, stack.getAverage(), 0.001);
	}

	@Test
	public void testGetAverage_withDoubles() {
		StackAverage<Double> stack = new StackAverage<>();
		stack.push(1.5);
		stack.push(2.5);
		stack.push(3.0);
		assertEquals(2.333, stack.getAverage(), 0.001);
	}

	@Test
	public void testGetAverage_withNegativeNumbers() {
		StackAverage<Integer> stack = new StackAverage<>();
		stack.push(-5);
		stack.push(0);
		stack.push(5);
		assertEquals(0.0, stack.getAverage(), 0.001);
	}

	@Test
	public void testGetAverage_mixedPositiveNegative() {
		StackAverage<Integer> stack = new StackAverage<>();
		stack.push(10);
		stack.push(-2);
		stack.push(4);
		stack.push(-8);
		stack.push(6);
		assertEquals(2.0, stack.getAverage(), 0.001);
	}

	@Test
	public void testPushPopPeek() {
		StackAverage<Integer> stack = new StackAverage<>();
		stack.push(100);
		stack.push(200);

		assertEquals(200, stack.peek());
		assertEquals(150.0, stack.getAverage(), 0.001);
		assertEquals(200, stack.pop());
		assertEquals(100.0, stack.getAverage(), 0.001);
	}
}
