import java.util.Stack;

/**
 * Двусторонняя очередь на основе двух стеков.
 * Обеспечивает O(1) амортизационную сложность для всех операций.
 * Выравнивание сложности происходит за счёт того, что дорогая операция rebalance “раскладывается” на много дешёвых операций.
 * 
 * frontStack -- хранит первую половину элементов (голова в вершине)
 * backStack -- хранит вторую половину элементов (хвост в вершине)
 */
public class Deque_2<T>
{
	private final Stack<T> frontStack;
	private final Stack<T> backStack;

	public Deque_2()
	{
		this.frontStack = new Stack<>();
		this.backStack = new Stack<>();
	}

	// 6-2; size; Time: O(1), Space: O(1)
	public int size()
	{
		return frontStack.size() + backStack.size();
	}

	// 6-2; addFront; Time: O(1), Space: O(1)
	public void addFront(T item)
	{
		frontStack.push(item);
	}

	// 6-2; removeFront; Amortized Time: O(1), Space: O(1)
	public T removeFront()
	{
		if (frontStack.isEmpty() && backStack.isEmpty()) {
			return null;
		}

		if (frontStack.isEmpty()) {
			rebalance();
		}

		return frontStack.pop();
	}

	// 6-2; addTail; Time: O(1), Space: O(1)
	public void addTail(T item)
	{
		backStack.push(item);
	}

	// 6-2; removeTail; Amortized Time: O(1), Space: O(1)
	public T removeTail()
	{
		if (frontStack.isEmpty() && backStack.isEmpty()) {
			return null;
		}

		if (backStack.isEmpty()) {
			rebalance();
		}

		return backStack.pop();
	}

	// 6-2; rebalance; Amortized Time: O(n), Space: O(1)
	private void rebalance()
	{
		if (frontStack.isEmpty()) {
			while (!backStack.isEmpty()) {
				frontStack.push(backStack.pop());
			}
		} else {
			while (!frontStack.isEmpty()) {
				backStack.push(frontStack.pop());
			}
		}
	}
}

