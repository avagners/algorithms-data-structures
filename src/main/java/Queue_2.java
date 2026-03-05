import java.util.Stack;


public class Queue_2
{
	// 5-3; turn; Time: O(n), Space: O(n)
	public static <T> void turn(Queue<T> queue, int count)
	{
		Stack<T> stack = new Stack<>();

		for (int i = 0; i < count && queue.size() > 0; i++) {
			stack.push(queue.dequeue());
		}

		while (!stack.isEmpty()) {
			queue.enqueue(stack.pop());
		}
	}

	// 5-5; reverse; Time: O(n), Space: O(n)
	public static <T> void reverse(Queue<T> queue)
	{
		Stack<T> stack = new Stack<>();

		while (queue.size() > 0) {
			stack.push(queue.dequeue());
		}

		while (!stack.isEmpty()) {
			queue.enqueue(stack.pop());
		}
	}
}

