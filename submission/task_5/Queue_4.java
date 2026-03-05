public class Queue_4<T>
{
	private final T[] array;
	private final int capacity;
	private int head;
	private int tail;
	private int size;

	@SuppressWarnings("unchecked")
	public Queue_4(int capacity)
	{
		this.capacity = capacity;
		this.array = (T[]) new Object[capacity];
		this.head = 0;
		this.tail = 0;
		this.size = 0;
	}

	// 5-6; size; Time: O(1), Space: O(1)
	public int size()
	{
		return size;
	}

	// 5-6; capacity; Time: O(1), Space: O(1)
	public int capacity()
	{
		return capacity;
	}

	// 5-6; isFull; Time: O(1), Space: O(1)
	public boolean isFull()
	{
		return size == capacity;
	}

	// 5-6; isEmpty; Time: O(1), Space: O(1)
	public boolean isEmpty()
	{
		return size == 0;
	}

	// 5-6; enqueue; Time: O(1), Space: O(1)
	public boolean enqueue(T item)
	{
		if (isFull()) {
			return false;
		}
		array[tail] = item;
		tail = (tail + 1) % capacity;  // сдвиг
		size++;
		return true;
	}

	// 5-6; dequeue; Time: O(1), Space: O(1)
	public T dequeue()
	{
		if (isEmpty()) {
			return null;
		}
		T item = array[head];
		array[head] = null;
		head = (head + 1) % capacity;  // сдвиг
		size--;
		return item;
	}

	// 5-6; peek; Time: O(1), Space: O(1)
	public T peek()
	{
		if (isEmpty()) {
			return null;
		}
		return array[head];
	}

	// 5-6; clear; Time: O(1), Space: O(1)
	public void clear()
	{
		for (int i = 0; i < size; i++) {
			array[(head + i) % capacity] = null;
		}
		head = 0;
		tail = 0;
		size = 0;
	}
}

