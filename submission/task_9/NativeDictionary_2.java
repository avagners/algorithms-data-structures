import java.util.ArrayList;

/**
 * Словарь на основе упорядоченного списка пар ключ-значение.
 * Ключи хранятся в отсортированном виде, что позволяет использовать
 * двоичный поиск для оптимизации операций поиска.
 */
public class NativeDictionary_2<T>
{
    // Класс для хранения пары ключ-значение
    private static class KeyValue<K extends Comparable<K>, V> implements Comparable<KeyValue<K, V>>
    {
        public K key;
        public V value;

        public KeyValue(K key, V value)
        {
            this.key = key;
            this.value = value;
        }

        @Override
        public int compareTo(KeyValue<K, V> other)
        {
            return this.key.compareTo(other.key);
        }
    }

    private final ArrayList<KeyValue<String, T>> items;
    private int count;

    public NativeDictionary_2()
    {
        this.items = new ArrayList<>();
        this.count = 0;
    }

    // 9-5; binarySearch; Time: O(log n), Space: O(1)
    // Двоичный поиск ключа в отсортированном списке
    // Возвращает индекс ключа или -(точка вставки) - 1 если не найден
    private int binarySearch(String key)
    {
        int left = 0;
        int right = count - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int cmp = items.get(mid).key.compareTo(key);

            if (cmp == 0) {
                return mid; // Ключ найден
            } else if (cmp < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        // Ключ не найден, возвращаем точку вставки
        return -left - 1;
    }

    // 9-5; put; Time: O(n), Space: O(1)
    // Вставка пары ключ-значение с сохранением порядка
    public void put(String key, T value)
    {
        int index = binarySearch(key);

        if (index >= 0) {
            // Ключ уже существует - обновляем значение
            items.get(index).value = value;
        } else {
            // Ключ не найден - вставляем в правильную позицию
            int insertIndex = -index - 1;
            items.add(insertIndex, new KeyValue<>(key, value));
            count++;
        }
    }

    // 9-5; isKey; Time: O(log n), Space: O(1)
    // Проверка наличия ключа (двоичный поиск)
    public boolean isKey(String key)
    {
        return binarySearch(key) >= 0;
    }

    // 9-5; get; Time: O(log n), Space: O(1)
    // Получение значения по ключу (двоичный поиск)
    public T get(String key)
    {
        int index = binarySearch(key);
        if (index >= 0) {
            return items.get(index).value;
        }
        return null;
    }

    // 9-5; remove; Time: O(n), Space: O(1)
    // Удаление пары по ключу
    public boolean remove(String key)
    {
        int index = binarySearch(key);
        if (index >= 0) {
            items.remove(index);
            count--;
            return true;
        }
        return false;
    }

    // 9-5; count; Time: O(1), Space: O(1)
    // Количество пар ключ-значение
    public int count()
    {
        return count;
    }

    // 9-5; clear; Time: O(1), Space: O(1)
    public void clear()
    {
        items.clear();
        count = 0;
    }

    // 9-5; getAllKeys; Time: O(n), Space: O(n)
    // Возвращает все ключи в отсортированном порядке
    public String[] getAllKeys()
    {
        String[] keys = new String[count];
        for (int i = 0; i < count; i++) {
            keys[i] = items.get(i).key;
        }
        return keys;
    }

    // 9-5; getAllValues; Time: O(n), Space: O(n)
    // Возвращает все значения в порядке ключей
    @SuppressWarnings("unchecked")
    public T[] getAllValues(Class<T> clazz)
    {
        T[] values = (T[]) java.lang.reflect.Array.newInstance(clazz, count);
        for (int i = 0; i < count; i++) {
            values[i] = items.get(i).value;
        }
        return values;
    }

    // 9-5; getMinKey; Time: O(1), Space: O(1)
    // Возвращает минимальный ключ (первый в отсортированном списке)
    public String getMinKey()
    {
        if (count > 0) {
            return items.get(0).key;
        }
        return null;
    }

    // 9-5; getMaxKey; Time: O(1), Space: O(1)
    // Возвращает максимальный ключ (последний в отсортированном списке)
    public String getMaxKey()
    {
        if (count > 0) {
            return items.get(count - 1).key;
        }
        return null;
    }

    // 9-5; getKeysInRange; Time: O(log n + k), Space: O(k)
    // Возвращает ключи в диапазоне [fromKey, toKey]
    public String[] getKeysInRange(String fromKey, String toKey)
    {
        ArrayList<String> result = new ArrayList<>();
        
        // Находим начальную позицию (двоичный поиск)
        int startIndex = binarySearch(fromKey);
        if (startIndex < 0) {
            startIndex = -startIndex - 1;
        }
        
        // Собираем все ключи в диапазоне
        for (int i = startIndex; i < count; i++) {
            String key = items.get(i).key;
            if (key.compareTo(toKey) > 0) {
                break;
            }
            result.add(key);
        }
        
        return result.toArray(new String[0]);
    }
}
