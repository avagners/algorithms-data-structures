/**
 * Хэш-таблица с разрешением коллизий методом последовательных проб.
 */
public class HashTable
{
    public int size;
    public int step;
    public String[] slots;

    public HashTable(int sz, int stp)
    {
        size = sz;
        step = stp;
        slots = new String[size];
        for (int i = 0; i < size; i++) {
            slots[i] = null;
        }
    }

    // 8-1; hashFun; Time: O(1), Space: O(1)
    public int hashFun(String value)
    {
        int hash = 0;
        for (int i = 0; i < value.length(); i++) {
            hash += value.charAt(i);
        }
        return hash % size;
    }

    // 8-1; seekSlot; Time: O(n), Space: O(1)
    public int seekSlot(String value)
    {
        int index = hashFun(value);
        int startIndex = index;

        // Ищем свободный слот, делая шаг step позиций
        for (int i = 0; i < size; i++) {
            if (slots[index] == null) {
                return index;
            }
            // Если слот занят этим же значением, возвращаем его
            if (value.equals(slots[index])) {
                return index;
            }
            // Переходим к следующему слоту
            index = (index + step) % size;
            // Если вернулись к началу - таблица заполнена
            if (index == startIndex) {
                break;
            }
        }

        return -1; // Не удалось найти свободный слот
    }

    // 8-1; put; Time: O(n), Space: O(1)
    // Помещает значение в таблицу, возвращает индекс слота или -1 при неудаче
    public int put(String value)
    {
        int index = seekSlot(value);
        if (index >= 0) {
            slots[index] = value;
            return index;
        }
        return -1;
    }

    // 8-1; find; Time: O(n), Space: O(1)
    // Поиск значения в таблице, возвращает индекс слота или -1 при неудаче
    public int find(String value)
    {
        int index = hashFun(value);
        int startIndex = index;

        for (int i = 0; i < size; i++) {
            if (slots[index] == null) {
                // Пустой слот - значения нет
                return -1;
            }
            if (value.equals(slots[index])) {
                return index;
            }
            // Переходим к следующему слоту
            index = (index + step) % size;
            // Если вернулись к началу - значения нет
            if (index == startIndex) {
                break;
            }
        }

        return -1; // Значение не найдено
    }

    // 8-1; count; Time: O(n), Space: O(1)
    // Количество занятых слотов
    public int count()
    {
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (slots[i] != null) {
                count++;
            }
        }
        return count;
    }
}

