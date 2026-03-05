import java.util.Stack;


public class Queue_3<T>
{
	private final Stack<T> stackIn;
	private final Stack<T> stackOut;

	public Queue_3()
	{
		this.stackIn = new Stack<>();
		this.stackOut = new Stack<>();
	}

	// 5-4; size; Time: O(1), Space: O(1)
	public int size()
	{
		return stackIn.size() + stackOut.size();
	}

	// 5-4; enqueue; Time: O(1), Space: O(1)
	public void enqueue(T item)
	{
		stackIn.push(item);
	}

	// 5-4; dequeue; Amortized Time: O(1), Space: O(1)
	public T dequeue()
	{
		if (stackOut.isEmpty() && stackIn.isEmpty()) {
			return null;
		}

		if (stackOut.isEmpty()) {
			while (!stackIn.isEmpty()) {
				stackOut.push(stackIn.pop());
			}
		}

		return stackOut.pop();
	}

	// 5-4; peek; Amortized Time: O(1), Space: O(1)
	public T peek()
	{
		if (stackOut.isEmpty() && stackIn.isEmpty()) {
			return null;
		}

		if (stackOut.isEmpty()) {
			while (!stackIn.isEmpty()) {
				stackOut.push(stackIn.pop());
			}
		}

		return stackOut.peek();
	}
}

