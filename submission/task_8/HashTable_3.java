/**
 * Хэш-таблица с несколькими хэш-функциями для уменьшения коллизий.
 * Использует подход, похожий на cuckoo hashing: каждый элемент может быть размещён
 * в одном из нескольких возможных слотов (по числу хэш-функций).
 */
public class HashTable_3
{
    private int size;
    private String[] slots;
    private int count;
    private final double loadFactor;
    private final int numHashFunctions;

    // 8-4; HashTable_3; Time: O(1), Space: O(n)
    public HashTable_3(int initialSize, int numHashFunctions)
    {
        this.size = initialSize;
        this.slots = new String[size];
        this.count = 0;
        this.loadFactor = 0.75;
        this.numHashFunctions = numHashFunctions;
    }

    // 8-4; hashFun1; Time: O(1), Space: O(1)
    // Первая хэш-функция: сумма кодов символов
    public int hashFun1(String value)
    {
        int hash = 0;
        for (int i = 0; i < value.length(); i++) {
            hash += value.charAt(i);
        }
        return hash % size;
    }

    // 8-4; hashFun2; Time: O(1), Space: O(1)
    // Вторая хэш-функция: сумма кодов с умножением на позицию
    public int hashFun2(String value)
    {
        int hash = 0;
        for (int i = 0; i < value.length(); i++) {
            hash += value.charAt(i) * (i + 1);
        }
        return hash % size;
    }

    // 8-4; hashFun3; Time: O(1), Space: O(1)
    // Третья хэш-функция: полиномиальный хэш
    public int hashFun3(String value)
    {
        int hash = 0;
        int prime = 31;
        for (int i = 0; i < value.length(); i++) {
            hash = hash * prime + value.charAt(i);
        }
        return Math.abs(hash) % size;
    }

    // 8-4; getHashFunctions; Time: O(1), Space: O(1)
    // Возвращает массив хэш-функций для использования
    public int[] getHashFunctions(String value)
    {
        int[] hashes = new int[numHashFunctions];
        hashes[0] = hashFun1(value);
        if (numHashFunctions > 1) {
            hashes[1] = hashFun2(value);
        }
        if (numHashFunctions > 2) {
            hashes[2] = hashFun3(value);
        }
        return hashes;
    }

    // 8-4; seekSlot; Time: O(k), Space: O(1) где k = numHashFunctions
    // Поиск свободного слота среди всех хэш-функций
    public int seekSlot(String value)
    {
        int[] hashes = getHashFunctions(value);

        // Проверяем каждый слот от каждой хэш-функции
        for (int hash : hashes) {
            if (slots[hash] == null) {
                return hash;
            }
            if (value.equals(slots[hash])) {
                return hash; // Уже есть такое значение
            }
        }

        // Если все слоты заняты, используем линейную пробу от первого хэша
        int startIndex = hashes[0];
        for (int i = 0; i < size; i++) {
            int index = (startIndex + i) % size;
            if (slots[index] == null) {
                return index;
            }
            if (value.equals(slots[index])) {
                return index;
            }
        }

        return -1;
    }

    // 8-4; resize; Time: O(n), Space: O(n)
    // Увеличивает размер таблицы и перераспределяет все элементы
    private void resize()
    {
        int newSize = size * 2;
        String[] oldSlots = slots;
        int oldSize = size;

        slots = new String[newSize];
        size = newSize;
        count = 0;

        // Перераспределяем все элементы
        for (int i = 0; i < oldSize; i++) {
            if (oldSlots[i] != null) {
                put(oldSlots[i]);
            }
        }
    }

    // 8-4; put; Time: O(k) амортизационная, Space: O(1)
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

    // 8-4; find; Time: O(k), Space: O(1) где k = numHashFunctions
    // Поиск значения в таблице
    public int find(String value)
    {
        int[] hashes = getHashFunctions(value);

        // Проверяем каждый слот от каждой хэш-функции
        for (int hash : hashes) {
            if (slots[hash] == null) {
                continue;
            }
            if (value.equals(slots[hash])) {
                return hash;
            }
        }

        // Если не найдено, используем линейную пробу от первого хэша
        int startIndex = hashes[0];
        for (int i = 0; i < size; i++) {
            int index = (startIndex + i) % size;
            if (slots[index] == null) {
                return -1;
            }
            if (value.equals(slots[index])) {
                return index;
            }
        }

        return -1;
    }

    // 8-4; size; Time: O(1), Space: O(1)
    public int size()
    {
        return size;
    }

    // 8-4; count; Time: O(1), Space: O(1)
    public int count()
    {
        return count;
    }

    // 8-4; getLoadFactor; Time: O(1), Space: O(1)
    public double getLoadFactor()
    {
        return (double) count / size;
    }

    // 8-4; getNumHashFunctions; Time: O(1), Space: O(1)
    public int getNumHashFunctions()
    {
        return numHashFunctions;
    }

    // 8-4; getCollisionStats; Time: O(n), Space: O(1)
    // Возвращает количество коллизий (слотов с более чем одним возможным размещением)
    public int countCollisions()
    {
        int collisions = 0;
        for (int i = 0; i < size; i++) {
            if (slots[i] != null) {
                int[] hashes = getHashFunctions(slots[i]);
                for (int hash : hashes) {
                    if (hash != i && slots[hash] != null) {
                        collisions++;
                        break;
                    }
                }
            }
        }
        return collisions;
    }
}

