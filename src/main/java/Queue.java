import java.util.LinkedList;

// 5-1; Queue
public class Queue<T>
{
	private final LinkedList<T> storage;

	public Queue()
	{
		this.storage = new LinkedList<>();
	}

	// 5-2; size; Time: O(1), Space: O(1)
	public int size()
	{
		return storage.size();
	}

	// 5-2; enqueue; Time: O(1), Space: O(1)
	public void enqueue(T item)
	{
		storage.addLast(item);
	}

	// 5-2; dequeue; Time: O(1), Space: O(1)
	public T dequeue()
	{
		if (storage.isEmpty()) {
			return null;
		}
		return storage.removeFirst();
	}
}

