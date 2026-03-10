import java.util.LinkedList;

public class Deque<T>
{
	private final LinkedList<T> storage;

	public Deque()
	{
		this.storage = new LinkedList<>();
	}

	// 6-1; size; Time: O(1), Space: O(1)
	public int size()
	{
		return storage.size();
	}

	// 6-1; addFront; Time: O(1), Space: O(1)
	public void addFront(T item)
	{
		storage.addFirst(item);
	}

	// 6-1; removeFront; Time: O(1), Space: O(1)
	public T removeFront()
	{
		if (storage.isEmpty()) {
			return null;
		}
		return storage.removeFirst();
	}

	// 6-1; addTail; Time: O(1), Space: O(1)
	public void addTail(T item)
	{
		storage.addLast(item);
	}

	// 6-1; removeTail; Time: O(1), Space: O(1)
	public T removeTail()
	{
		if (storage.isEmpty()) {
			return null;
		}
		return storage.removeLast();
	}
}

