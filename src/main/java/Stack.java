import java.util.*;

public class Stack<T>
{
	private final List<T> storage;

	public Stack()
	{
		this.storage = new ArrayList<>();
	}

	// 4-1; size; Time: O(1), Space: O(1)
	public int size()
	{
		return storage.size();
	}

	// 4-1; pop; Time: O(1), Space: O(1)
	public T pop()
	{
		if (storage.isEmpty()) {
				return null;
		}
		return storage.remove(storage.size() - 1);
	}

	// 4-1; push; Amortized Time: O(1), Space: O(1)
	public void push(T val)
	{
		storage.add(val);
	}

	// 4-1; peek; Time: O(1), Space: O(1)
	public T peek()
	{
		if (storage.isEmpty()) {
				return null;
		}
		return storage.get(storage.size() - 1);
	}
}
