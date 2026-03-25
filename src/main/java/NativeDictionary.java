import java.lang.reflect.Array;

public class NativeDictionary<T>
{
    public int size;
    public int step;
    public String[] slots;
    public T[] values;
    private int count;

    public NativeDictionary(int sz, Class clazz)
    {
        this(sz, clazz, 1); // По умолчанию шаг 1
    }

    public NativeDictionary(int sz, Class clazz, int step)
    {
        size = sz;
        this.step = step;
        slots = new String[size];
        values = (T[]) Array.newInstance(clazz, this.size);
        count = 0;
    }

    // 9-2; hashFun; Time: O(n), Space: O(1)
    // Хэш-функция: сумма кодов символов по модулю размера таблицы
    public int hashFun(String key)
    {
        int sum = 0;
        for (int i = 0; i < key.length(); i++) {
            sum += (int) key.charAt(i);
        }
        return Math.abs(sum) % size;
    }

    // 9-2; seekSlot; Time: O(n), Space: O(1)
    // Поиск слота для ключа (существующего или свободного)
    public int seekSlot(String key)
    {
        int index = hashFun(key);
        int startIndex = index;

        for (int i = 0; i < size; i++) {
            // Пустой слот или найден ключ
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

    // 9-3; put; Time: O(n), Space: O(1)
    // Сохранение пары ключ-значение в словаре
    public void put(String key, T value)
    {
        int index = seekSlot(key);
        if (index >= 0) {
            if (slots[index] == null) {
                // Новый ключ
                slots[index] = key;
                values[index] = value;
                count++;
            } else {
                // Обновление существующего ключа
                values[index] = value;
            }
        }
    }

    // 9-3; isKey; Time: O(n), Space: O(1)
    // Проверка наличия ключа в словаре
    public boolean isKey(String key)
    {
        return seekSlot(key) >= 0 && slots[seekSlot(key)] != null;
    }

    // 9-3; get; Time: O(n), Space: O(1)
    // Получение значения по ключу, или null если ключ не найден
    public T get(String key)
    {
        int index = seekSlot(key);
        if (index >= 0 && slots[index] != null && key.equals(slots[index])) {
            return values[index];
        }
        return null;
    }

    // 9-2; remove; Time: O(n), Space: O(1)
    // Удаление пары по ключу
    public boolean remove(String key)
    {
        int index = seekSlot(key);
        if (index >= 0 && slots[index] != null && key.equals(slots[index])) {
            slots[index] = null;
            values[index] = null;
            count--;
            return true;
        }
        return false;
    }

    // 9-2; count; Time: O(1), Space: O(1)
    // Количество пар ключ-значение
    public int count()
    {
        return count;
    }

    // 9-2; size; Time: O(1), Space: O(1)
    public int size()
    {
        return size;
    }
}

