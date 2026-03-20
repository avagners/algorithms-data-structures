/**
 * Динамическая хэш-таблица с автоматическим увеличением размера.
 * При заполнении таблицы автоматически увеличивается размер и происходит ре-хэширование.
 */
public class HashTable_2
{
    private int size;
    private final int step;
    private String[] slots;
    private int count;
    private final double loadFactor;

    // 8-3; HashTable_2; Time: O(1), Space: O(n)
    public HashTable_2(int initialSize, int step)
    {
        this.size = initialSize;
        this.step = step;
        this.slots = new String[size];
        this.count = 0;
        this.loadFactor = 0.75; // Порог загрузки для расширения
    }

    // 8-3; hashFun; Time: O(1), Space: O(1)
    // Хэш-функция: сумма кодов символов по модулю размера таблицы
    public int hashFun(String value)
    {
        int hash = 0;
        for (int i = 0; i < value.length(); i++) {
            hash += value.charAt(i);
        }
        return hash % size;
    }

    // 8-3; seekSlot; Time: O(n), Space: O(1)
    // Поиск слота с учётом коллизий (метод последовательных проб)
    public int seekSlot(String value)
    {
        int index = hashFun(value);
        int startIndex = index;

        for (int i = 0; i < size; i++) {
            if (slots[index] == null) {
                return index;
            }
            if (value.equals(slots[index])) {
                return index;
            }
            index = (index + step) % size;
            if (index == startIndex) {
                break;
            }
        }

        return -1;
    }

    // 8-3; resize; Time: O(n), Space: O(n)
    // Увеличивает размер таблицы и перераспределяет все элементы
    private void resize()
    {
        int newSize = size * 2;
        String[] oldSlots = slots;
        int oldSize = size;

        slots = new String[newSize];
        size = newSize;
        count = 0;

        // Перераспределяем все элементы в новую таблицу
        for (int i = 0; i < oldSize; i++) {
            if (oldSlots[i] != null) {
                put(oldSlots[i]);
            }
        }
    }

    // 8-3; put; Time: O(n) амортизационная, Space: O(1)
    // Помещает значение в таблицу, возвращает индекс слота или -1 при неудаче
    public int put(String value)
    {
        // Проверяем, не пора ли увеличить таблицу
        if ((double) count / size >= loadFactor) {
            resize();
        }

        int index = seekSlot(value);
        if (index >= 0) {
            if (slots[index] == null) {
                slots[index] = value;
                count++;
            }
            return index;
        }
        return -1;
    }

    // 8-3; find; Time: O(n), Space: O(1)
    // Поиск значения в таблице, возвращает индекс слота или -1 при неудаче
    public int find(String value)
    {
        int index = hashFun(value);
        int startIndex = index;

        for (int i = 0; i < size; i++) {
            if (slots[index] == null) {
                return -1;
            }
            if (value.equals(slots[index])) {
                return index;
            }
            index = (index + step) % size;
            if (index == startIndex) {
                break;
            }
        }

        return -1;
    }

    // 8-3; size; Time: O(1), Space: O(1)
    public int size()
    {
        return size;
    }

    // 8-3; count; Time: O(1), Space: O(1)
    // Количество занятых слотов
    public int count()
    {
        return count;
    }

    // 8-3; getLoadFactor; Time: O(1), Space: O(1)
    // Текущий коэффициент загрузки
    public double getLoadFactor()
    {
        return (double) count / size;
    }

    // 8-3; getSlots; Time: O(1), Space: O(1)
    public String[] getSlots()
    {
        return slots;
    }
}
