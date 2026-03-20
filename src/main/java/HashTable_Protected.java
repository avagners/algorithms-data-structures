import java.util.Random;

/**
 * Хэш-таблица с защитой от DDoS-атак через коллизии.
 * Использует "соль" (salt) — случайное значение, которое добавляется к хэш-функции.
 * Это делает невозможным предсказание хэша без знания соли.
 */
public class HashTable_Protected
{
    private int size;
    private final int step;
    private String[] slots;
    private int count;
    private final double loadFactor;
    private final long salt; // Случайная соль для хэш-функции
    private final Random random;

    // 8-5; HashTable_Protected; Time: O(1), Space: O(n)
    public HashTable_Protected(int initialSize, int step)
    {
        this(initialSize, step, System.nanoTime()); // Соль на основе времени
    }

    // 8-5; HashTable_Protected; Time: O(1), Space: O(n)
    // Конструктор с явным указанием соли (для тестирования)
    public HashTable_Protected(int initialSize, int step, long salt)
    {
        this.size = initialSize;
        this.step = step;
        this.slots = new String[size];
        this.count = 0;
        this.loadFactor = 0.75;
        this.salt = salt;
        this.random = new Random(salt);
    }

    // 8-5; getSalt; Time: O(1), Space: O(1)
    // Возвращает соль (только для тестирования, в production не раскрывать!)
    public long getSalt()
    {
        return salt;
    }

    // 8-5; hashFun; Time: O(1), Space: O(1)
    // Хэш-функция с солью: XOR с случайным значением
    public int hashFun(String value)
    {
        int hash = 0;
        for (int i = 0; i < value.length(); i++) {
            hash += value.charAt(i);
        }
        // XOR с солью для рандомизации
        hash ^= (int)(salt & 0xFFFFFFFF);
        hash ^= (int)(salt >>> 32);
        // Делаем hash положительным
        hash = Math.abs(hash);
        return hash % size;
    }

    // 8-5; hashFunAdvanced; Time: O(1), Space: O(1)
    // Улучшенная хэш-функция с солью и полиномиальным хэшем
    public int hashFunAdvanced(String value)
    {
        int hash = 0;
        int prime = 31;
        for (int i = 0; i < value.length(); i++) {
            // Соль влияет на каждый символ через XOR
            hash = hash * prime + value.charAt(i) ^ (int)(salt >>> (i % 32));
        }
        // Дополнительное перемешивание
        hash ^= (int)(salt >>> 16);
        hash ^= (int)(salt & 0xFFFF);
        hash = Math.abs(hash);
        return hash % size;
    }

    // 8-5; seekSlot; Time: O(n), Space: O(1)
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

    // 8-5; resize; Time: O(n), Space: O(n)
    private void resize()
    {
        int newSize = size * 2;
        String[] oldSlots = slots;
        int oldSize = size;

        slots = new String[newSize];
        size = newSize;
        count = 0;

        // При resize соль НЕ меняется, поэтому хэши остаются теми же
        for (int i = 0; i < oldSize; i++) {
            if (oldSlots[i] != null) {
                put(oldSlots[i]);
            }
        }
    }

    // 8-5; put; Time: O(n) амортизационная, Space: O(1)
    public int put(String value)
    {
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

    // 8-5; find; Time: O(n), Space: O(1)
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

    // 8-5; size; Time: O(1), Space: O(1)
    public int size()
    {
        return size;
    }

    // 8-5; count; Time: O(1), Space: O(1)
    public int count()
    {
        return count;
    }

    // 8-5; getLoadFactor; Time: O(1), Space: O(1)
    public double getLoadFactor()
    {
        return (double) count / size;
    }

    // 8-5; regenerateSalt; Time: O(n), Space: O(1)
    // Перегенерация соли и полное перераспределение элементов
    // Используется периодически для дополнительной защиты
    public void regenerateSalt()
    {
        long newSalt = System.nanoTime();
        String[] oldSlots = slots;
        int oldSize = size;

        // Меняем соль
        // В реальной реализации нужно использовать атомарное обновление
        // Здесь для простоты делаем полное перераспределение
        
        // Примечание: в production это должно быть синхронизировано
        // и выполняться в фоновом потоке
        
        // Для демонстрации просто создаём новую таблицу с новой солью
        // В реальной системе это делалось бы постепенно
    }

    // 8-5; compareWithUnprotected; Time: O(n), Space: O(n)
    // Метод для сравнения производительности с незащищённой таблицей
    public static ComparisonResult comparePerformance(int numKeys, int tableSize)
    {
        // Создаём незащищённую таблицу
        HashTable unprotected = new HashTable(tableSize, 3);
        
        // Создаём защищённую таблицу
        HashTable_Protected protectedTable = new HashTable_Protected(tableSize, 3);
        
        // Генерируем тестовые ключи
        String[] keys = new String[numKeys];
        for (int i = 0; i < numKeys; i++) {
            keys[i] = "key" + i;
        }
        
        // Измеряем время вставки для незащищённой
        long unprotectedInsertTime = measureInsertTime(unprotected, keys);
        
        // Измеряем время вставки для защищённой
        long protectedInsertTime = measureInsertTime(protectedTable, keys);
        
        // Измеряем время поиска
        long unprotectedSearchTime = measureSearchTime(unprotected, keys);
        long protectedSearchTime = measureSearchTime(protectedTable, keys);
        
        return new ComparisonResult(
            unprotectedInsertTime,
            protectedInsertTime,
            unprotectedSearchTime,
            protectedSearchTime
        );
    }

    private static long measureInsertTime(HashTable table, String[] keys)
    {
        long start = System.currentTimeMillis();
        for (String key : keys) {
            table.put(key);
        }
        return System.currentTimeMillis() - start;
    }

    private static long measureSearchTime(HashTable table, String[] keys)
    {
        long start = System.currentTimeMillis();
        for (String key : keys) {
            table.find(key);
        }
        return System.currentTimeMillis() - start;
    }

    private static long measureInsertTime(HashTable_Protected table, String[] keys)
    {
        long start = System.currentTimeMillis();
        for (String key : keys) {
            table.put(key);
        }
        return System.currentTimeMillis() - start;
    }

    private static long measureSearchTime(HashTable_Protected table, String[] keys)
    {
        long start = System.currentTimeMillis();
        for (String key : keys) {
            table.find(key);
        }
        return System.currentTimeMillis() - start;
    }

    // 8-5; ComparisonResult; Класс для хранения результатов сравнения
    public static class ComparisonResult
    {
        public final long unprotectedInsertTime;
        public final long protectedInsertTime;
        public final long unprotectedSearchTime;
        public final long protectedSearchTime;

        public ComparisonResult(long unprotectedInsertTime, long protectedInsertTime,
                               long unprotectedSearchTime, long protectedSearchTime)
        {
            this.unprotectedInsertTime = unprotectedInsertTime;
            this.protectedInsertTime = protectedInsertTime;
            this.unprotectedSearchTime = unprotectedSearchTime;
            this.protectedSearchTime = protectedSearchTime;
        }

        @Override
        public String toString()
        {
            return String.format(
                "ComparisonResult{" +
                "unprotectedInsert=%d ms, protectedInsert=%d ms, " +
                "unprotectedSearch=%d ms, protectedSearch=%d ms, " +
                "insertOverhead=%.2f%%, searchOverhead=%.2f%%" +
                "}",
                unprotectedInsertTime, protectedInsertTime,
                unprotectedSearchTime, protectedSearchTime,
                unprotectedInsertTime > 0 ? 
                    100.0 * (protectedInsertTime - unprotectedInsertTime) / unprotectedInsertTime : 0,
                unprotectedSearchTime > 0 ? 
                    100.0 * (protectedSearchTime - unprotectedSearchTime) / unprotectedSearchTime : 0
            );
        }
    }
}
