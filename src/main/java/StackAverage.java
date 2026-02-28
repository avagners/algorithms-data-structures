import java.util.Stack;

public class StackAverage<T extends Number> {

	private final Stack<T> stack;
	private double sum;

	public StackAverage() {
		this.stack = new Stack<>();
		this.sum = 0.0;
	}

	// 4-7; size; Time: O(1), Space: O(1)
	public int size() {
		return stack.size();
	}

	// 4-7; push; Time: O(1), Space: O(1)
	public void push(T val) {
		stack.push(val);
		sum += val.doubleValue();
	}

	// 4-7; pop; Time: O(1), Space: O(1)
	public T pop() {
		if (stack.isEmpty()) {
			return null;
		}
		T val = stack.pop();
		sum -= val.doubleValue();
		return val;
	}

	// 4-7; peek; Time: O(1), Space: O(1)
	public T peek() {
		if (stack.isEmpty()) {
			return null;
		}
		return stack.peek();
	}

	// 4-7; getAverage; Time: O(1), Space: O(1)
	public double getAverage() {
		if (stack.isEmpty()) {
			return 0.0;
		}
		return sum / stack.size();
	}
}

