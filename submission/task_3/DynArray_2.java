public class DynArray_2<T>
{
		public T[] array;
		public int count;
		public int capacity;
		Class<T> clazz;
		public int bankBalance;
		public int totalInserts;
		public int totalReallocations;
		public int totalCoinsDeposited;
		public int totalCoinsSpent;

		@SuppressWarnings("unchecked")
		public DynArray_2(Class<T> clz)
		{
			clazz = clz;
			count = 0;
			capacity = 16;
			bankBalance = 0;
			totalInserts = 0;
			totalReallocations = 0;
			totalCoinsDeposited = 0;
			totalCoinsSpent = 0;
			array = (T[]) java.lang.reflect.Array.newInstance(this.clazz, capacity);
		}

		// 3-1; makeArray; Time: O(n), Space: O(n)
		@SuppressWarnings("unchecked")
		public void makeArray(int new_capacity)
		{
			T[] newArray = (T[]) java.lang.reflect.Array.newInstance(this.clazz, new_capacity);

			int elementsToCopy = Math.min(this.count, new_capacity);

			totalCoinsSpent += elementsToCopy;
			bankBalance -= elementsToCopy;

			for (int i = 0; i < elementsToCopy; i++) {
				newArray[i] = this.array[i];
			}

			this.array = newArray;
			this.capacity = new_capacity;
			totalReallocations++;
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
			bankBalance += 3;
			totalCoinsDeposited += 3;
			totalInserts++;

			if (count == capacity) {
				makeArray(capacity * 2);
			}

			totalCoinsSpent += 1;
			bankBalance -= 1;

			array[count] = itm;
			count++;
		}

		// 3-2; insert; Time: O(n), Space: O(1)
		public void insert(T itm, int index)
		{
			if (index < 0 || index > count) {
				throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + count);
			}

			bankBalance += 3;
			totalCoinsDeposited += 3;
			totalInserts++;

			if (count == capacity) {
				makeArray(capacity * 2);
			}

			for (int i = count; i > index; i--) {
				array[i] = array[i - 1];
			}

			totalCoinsSpent += 1;
			bankBalance -= 1;

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

		public int getBankBalance()
		{
			return bankBalance;
		}

		public String getBankingStats()
		{
			return String.format(
				"Банковская статистика:\n" +
				"  Всего вставок: %d\n" +
				"  Всего реаллокаций: %d\n" +
				"  Всего монет внесено: %d\n" +
				"  Всего монет потрачено: %d\n" +
				"  Текущий баланс банка: %d\n" +
				"  Баланс >= 0: %b",
				totalInserts,
				totalReallocations,
				totalCoinsDeposited,
				totalCoinsSpent,
				bankBalance,
				bankBalance >= 0
			);
		}

		public int getCapacity()
		{
			return capacity;
		}

		public int getCount()
		{
			return count;
		}
}

