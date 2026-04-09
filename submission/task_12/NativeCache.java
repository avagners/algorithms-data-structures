import java.lang.reflect.Array;

/**
 * 12-1 NativeCache - кэш на основе хэш-таблицы с вытеснением наименее используемых элементов (LFU).
 *
 * Когда таблица заполнена и нужно добавить новый ключ, вытесняется элемент
 * с наименьшим количеством обращений (hits). При равных hits вытесняется первый найденный.
 */
public class NativeCache<T>
{
    public int size;
    public int step;
    public String[] slots;
    public T[] values;
    public int[] hits;
    private int count;

    public NativeCache(int sz, Class<T> clazz)
    {
        this(sz, clazz, 1);
    }

    @SuppressWarnings("unchecked")
    public NativeCache(int sz, Class<T> clazz, int step)
    {
        size = sz;
        this.step = step;
        slots = new String[size];
        values = (T[]) Array.newInstance(clazz, this.size);
        hits = new int[size];
        count = 0;
    }

    // 12-1; hashFun; Time: O(L), Space: O(1), L - длина ключа
    public int hashFun(String key)
    {
        int sum = 0;
        for (int i = 0; i < key.length(); i++) {
            sum += (int) key.charAt(i);
        }
        return Math.abs(sum) % size;
    }

    // 12-1; seekSlot; Time: O(n), Space: O(1)
    // Поиск слота для ключа (существующего или свободного)
    public int seekSlot(String key)
    {
        int index = hashFun(key);
        int startIndex = index;

        for (int i = 0; i < size; i++) {
            if (slots[index] == null || key.equals(slots[index])) {
                return index;
            }
            index = (index + step) % size;
            if (index == startIndex) {
                break;
            }
        }

        return -1;
    }

    // 12-1; findMinHitsSlot; Time: O(n), Space: O(1)
    // Находит индекс элемента с наименьшим количеством обращений
    private int findMinHitsSlot()
    {
        int minHits = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int i = 0; i < size; i++) {
            if (slots[i] != null && hits[i] < minHits) {
                minHits = hits[i];
                minIndex = i;
            }
        }

        return minIndex;
    }

    // 12-1; evict; Time: O(n), Space: O(1)
    // Вытеснение элемента с наименьшим количеством обращений
    private void evict()
    {
        int index = findMinHitsSlot();
        if (index >= 0) {
            slots[index] = null;
            values[index] = null;
            hits[index] = 0;
            count--;
        }
    }

    // 12-1; put; Time: O(n), Space: O(1)
    // Сохранение пары ключ-значение. При заполнении вытесняет наименее используемый.
    public void put(String key, T value)
    {
        int index = seekSlot(key);

        if (index >= 0) {
            if (slots[index] == null) {
                // Нашли свободный слот
                slots[index] = key;
                values[index] = value;
                hits[index] = 0;
                count++;
            } else {
                // Обновление существующего ключа
                values[index] = value;
                hits[index]++;
            }
            return;
        }

        // Слот не найден — таблица заполнена, нужно вытеснить
        evict();

        // После eviction освободился слот, ищем снова
        index = seekSlot(key);
        if (index >= 0 && slots[index] == null) {
            slots[index] = key;
            values[index] = value;
            hits[index] = 0;
            count++;
        }
    }

    // 12-1; isKey; Time: O(n), Space: O(1)
    // Проверка наличия ключа (не увеличивает счётчик обращений)
    public boolean isKey(String key)
    {
        int index = seekSlot(key);
        return index >= 0 && slots[index] != null;
    }

    // 12-1; get; Time: O(n), Space: O(1)
    // Получение значения. Увеличивает счётчик обращений при успешном поиске.
    public T get(String key)
    {
        int index = seekSlot(key);
        if (index >= 0 && slots[index] != null && key.equals(slots[index])) {
            hits[index]++;
            return values[index];
        }
        return null;
    }

    // 12-1; remove; Time: O(n), Space: O(1)
    public boolean remove(String key)
    {
        int index = seekSlot(key);
        if (index >= 0 && slots[index] != null && key.equals(slots[index])) {
            slots[index] = null;
            values[index] = null;
            hits[index] = 0;
            count--;
            return true;
        }
        return false;
    }

    // 12-1; count; Time: O(1), Space: O(1)
    public int count()
    {
        return count;
    }

    // 12-1; size; Time: O(1), Space: O(1)
    public int size()
    {
        return size;
    }

    // 12-1; getHits; Time: O(1), Space: O(1)
    // Возвращает количество обращений к ключу
    public int getHits(String key)
    {
        int index = seekSlot(key);
        if (index >= 0 && slots[index] != null && key.equals(slots[index])) {
            return hits[index];
        }
        return -1;
    }

    // 12-1; isFull; Time: O(1), Space: O(1)
    public boolean isFull()
    {
        return count >= size;
    }
}

