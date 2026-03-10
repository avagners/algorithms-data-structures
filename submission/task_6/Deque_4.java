import java.util.Stack;


public class Deque_4<T extends Comparable<T>>
{
	private final Stack<T> frontStack;
	private final Stack<T> frontMin;
	private final Stack<T> backStack;
	private final Stack<T> backMin;

	public Deque_4()
	{
		this.frontStack = new Stack<>();
		this.frontMin = new Stack<>();
		this.backStack = new Stack<>();
		this.backMin = new Stack<>();
	}

	// 6-5; size; Time: O(1), Space: O(1)
	public int size()
	{
		return frontStack.size() + backStack.size();
	}

	// 6-5; addFront; Time: O(1), Space: O(1)
	public void addFront(T item)
	{
		frontStack.push(item);
		
		if (frontMin.isEmpty() || item.compareTo(frontMin.peek()) <= 0) {
			frontMin.push(item);
		} else {
			frontMin.push(frontMin.peek());
		}
	}

	// 6-5; addTail; Time: O(1), Space: O(1)
	public void addTail(T item)
	{
		backStack.push(item);
		
		if (backMin.isEmpty() || item.compareTo(backMin.peek()) <= 0) {
			backMin.push(item);
		} else {
			backMin.push(backMin.peek());
		}
	}

	// 6-5; removeFront; Time: O(1), Space: O(1)
	public T removeFront()
	{
		if (frontStack.isEmpty() && backStack.isEmpty()) {
			return null;
		}

		if (frontStack.isEmpty()) {
			rebalance();
		}

		frontMin.pop();
		return frontStack.pop();
	}

	// 6-5; removeTail; Time: O(1), Space: O(1)
	public T removeTail()
	{
		if (frontStack.isEmpty() && backStack.isEmpty()) {
			return null;
		}

		if (backStack.isEmpty()) {
			rebalance();
		}

		backMin.pop();
		return backStack.pop();
	}

	// 6-5; getMin; Time: O(1), Space: O(1)
	public T getMin()
	{
		if (frontMin.isEmpty() && backMin.isEmpty()) {
			return null;
		}
		
		if (frontMin.isEmpty()) {
			return backMin.peek();
		}
		
		if (backMin.isEmpty()) {
			return frontMin.peek();
		}
		
		T frontMinVal = frontMin.peek();
		T backMinVal = backMin.peek();
		
		return frontMinVal.compareTo(backMinVal) <= 0 ? frontMinVal : backMinVal;
	}

	// 6-5; rebalance; Amortized Time: O(n), Space: O(1)
	private void rebalance()
	{
		if (frontStack.isEmpty()) {
			while (!backStack.isEmpty()) {
				T item = backStack.pop();
				backMin.pop();
				
				frontStack.push(item);
				
				if (frontMin.isEmpty() || item.compareTo(frontMin.peek()) <= 0) {
					frontMin.push(item);
				} else {
					frontMin.push(frontMin.peek());
				}
			}
		} else {
			while (!frontStack.isEmpty()) {
				T item = frontStack.pop();
				frontMin.pop();
				
				backStack.push(item);
				
				if (backMin.isEmpty() || item.compareTo(backMin.peek()) <= 0) {
					backMin.push(item);
				} else {
					backMin.push(backMin.peek());
				}
			}
		}
	}
}

