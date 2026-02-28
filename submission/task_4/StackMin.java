import java.util.Stack;

public class StackMin<T extends Comparable<T>> {

	private final Stack<T> stack;
	private final Stack<T> minStack;

	public StackMin() {
		this.stack = new Stack<>();
		this.minStack = new Stack<>();
	}

	// 4-6; size; Time: O(1), Space: O(1)
	public int size() {
		return stack.size();
	}

	// 4-6; push; Time: O(1), Space: O(1)
	public void push(T val) {
		stack.push(val);
		if (minStack.isEmpty() || val.compareTo(minStack.peek()) <= 0) {
			minStack.push(val);
		}
	}

	// 4-6; pop; Time: O(1), Space: O(1)
	public T pop() {
		if (stack.isEmpty()) {
			return null;
		}
		T val = stack.pop();
		if (minStack.peek() != null && val.compareTo(minStack.peek()) == 0) {
			minStack.pop();
		}
		return val;
	}

	// 4-6; peek; Time: O(1), Space: O(1)
	public T peek() {
		if (stack.isEmpty()) {
			return null;
		}
		return stack.peek();
	}

	// 4-6; getMin; Time: O(1), Space: O(1)
	public T getMin() {
		if (minStack.isEmpty()) {
			return null;
		}
		return minStack.peek();
	}
}

