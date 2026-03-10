/**
 * Двусторонняя очередь на основе динамического массива с циклическим буфером.
 * Все операции добавления/удаления работают за амортизированное O(1).
 */
public class Deque_5<T>
{
	private T[] array;
	private int head;      // индекс первого элемента
	private int tail;      // индекс следующего свободного места после последнего элемента
	private int size;      // количество элементов
	private int capacity;  // вместимость массива

	@SuppressWarnings("unchecked")
	public Deque_5()
	{
		this.capacity = 16;
		this.array = (T[]) new Object[capacity];
		this.head = 0;
		this.tail = 0;
		this.size = 0;
	}

	// 6-6; size; Time: O(1), Space: O(1)
	public int size()
	{
		return size;
	}

	// 6-6; addFront; Amortized Time: O(1), Space: O(1)
	public void addFront(T item)
	{
		if (size == capacity) {
			resize(capacity * 2);
		}
		
		head = (head - 1 + capacity) % capacity;
		array[head] = item;
		size++;
	}

	// 6-6; addTail; Amortized Time: O(1), Space: O(1)
	public void addTail(T item)
	{
		if (size == capacity) {
			resize(capacity * 2);
		}
		
		array[tail] = item;
		tail = (tail + 1) % capacity;
		size++;
	}

	// 6-6; removeFront; Amortized Time: O(1), Space: O(1)
	public T removeFront()
	{
		if (size == 0) {
			return null;
		}
		
		T item = array[head];
		array[head] = null;
		head = (head + 1) % capacity;
		size--;
		
		if (size < capacity / 4 && capacity > 16) {
			resize(capacity / 2);
		}
		
		return item;
	}

	// 6-6; removeTail; Amortized Time: O(1), Space: O(1)
	public T removeTail()
	{
		if (size == 0) {
			return null;
		}
		
		tail = (tail - 1 + capacity) % capacity;
		T item = array[tail];
		array[tail] = null;
		size--;
		
		if (size < capacity / 4 && capacity > 16) {
			resize(capacity / 2);
		}
		
		return item;
	}

	// 6-6; resize; Time: O(n), Space: O(n)
	@SuppressWarnings("unchecked")
	private void resize(int newCapacity)
	{
		T[] newArray = (T[]) new Object[newCapacity];
		
		for (int i = 0; i < size; i++) {
			newArray[i] = array[(head + i) % capacity];
		}
		
		this.array = newArray;
		this.head = 0;
		this.tail = size;
		this.capacity = newCapacity;
	}
}

