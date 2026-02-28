import java.util.LinkedList;

public class Stack_2<T>
{
	private final LinkedList<T> storage;

	public Stack_2()
	{
		this.storage = new LinkedList<>();
	}

	// 4-2; size; Time: O(1), Space: O(1)
	public int size()
	{
		return storage.size();
	}

	// 4-2; pop; Time: O(1), Space: O(1)
	public T pop()
	{
		if (storage.isEmpty()) {
				return null;
		}
		return storage.removeFirst();
	}

	// 4-2; push; Time: O(1), Space: O(1)
	public void push(T val)
	{
		storage.addFirst(val);
	}

	// 4-2; peek; Time: O(1), Space: O(1)
	public T peek()
	{
		if (storage.isEmpty()) {
				return null;
		}
		return storage.getFirst();
	}
}

