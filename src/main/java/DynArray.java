public class DynArray<T>
{
	public T [] array;
	public int count;
	public int capacity;
	Class<T> clazz;

	public DynArray(Class<T> clz)
	{
	clazz = clz; // нужен для безопасного приведения типов
	// new DynArray<Integer>(Integer.class);

	count = 0;
	makeArray(16);
	}

	// 3-1; makeArray; Time: O(n), Space: O(n)
	@SuppressWarnings("unchecked")
	public void makeArray(int new_capacity)
	{
	T[] newArray = (T[]) java.lang.reflect.Array.newInstance(this.clazz, new_capacity);

	if (this.array != null && this.count > 0) {
		int elementsToCopy = Math.min(this.count, new_capacity);
		for (int i = 0; i < elementsToCopy; i++) {
		newArray[i] = this.array[i];
		}
	}

	this.array = newArray;
	this.capacity = new_capacity;
	}

	// 3-1; getItem; Time: O(1), Space: O(1)
	public T getItem(int index)
	{
	if (index < 0 || index >= count) {
		throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + count);
	}
	return array[index];
	}

	// 3-1; append; Amortized Time: O(1), Space: O(1)
	public void append(T itm)
	{
	if (count == capacity) {
		makeArray(capacity * 2);
	}
	array[count] = itm;
	count++;
	}

	// 3-2; insert; Time: O(n), Space: O(1)
	public void insert(T itm, int index)
	{
	if (index < 0 || index > count) {
		throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + count);
	}

	if (count == capacity) {
		makeArray(capacity * 2);
	}

	for (int i = count; i > index; i--) {
		array[i] = array[i - 1];
	}

	array[index] = itm;
	count++;
	}

	// 3-3; remove; Time: O(n), Space: O(1)
	public void remove(int index)
	{
	if (index < 0 || index >= count) {
		throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + count);
	}

	for (int i = index; i < count - 1; i++) {
		array[i] = array[i + 1];
	}

	array[count - 1] = null;
	count--;

	if (count < capacity / 2 && capacity > 16) {
		int newCapacity = (int) (capacity / 1.5);
		if (newCapacity < 16) {
		newCapacity = 16;
		}
		makeArray(newCapacity);
	}
	}

}

