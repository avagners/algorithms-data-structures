import java.util.ArrayList;
import java.util.List;

/**
 * Демонстрация DDoS-атаки на хэш-таблицу через коллизии.
 * Генерирует ключи, которые дают одинаковый хэш, вызывая деградацию
 * производительности с O(1) до O(n).
 */
public class HashTable_DDoSAttack
{
    private final HashTable targetTable;
    private final List<String> collidingKeys;

    // 8-5; HashTable_DDoSAttack; Time: O(1), Space: O(n)
    public HashTable_DDoSAttack(HashTable table)
    {
        this.targetTable = table;
        this.collidingKeys = new ArrayList<>();
    }

    // 8-5; generateCollidingKeys; Time: O(n × k), Space: O(n)
    // Генерирует n ключей, дающих одинаковый хэш (атака на hashFun)
    public List<String> generateCollidingKeys(int count)
    {
        collidingKeys.clear();
        
        // Находим хэш первого ключа
        String baseKey = "A";
        int targetHash = targetTable.hashFun(baseKey);
        collidingKeys.add(baseKey);
        
        // Генерируем ключи с тем же хэшем методом перебора
        // Для hashFun = сумма кодов % size, коллизии возникают когда
        // сумма кодов символов даёт одинаковый остаток от деления на size
        int found = 1;
        int candidate = 0;
        
        while (found < count)
        {
            // Генерируем строки из символов с одинаковой суммой кодов
            // Например: "A" (65), "!" (33) + "2" (50) = 83, но нам нужно 65 % size
            String key = generateKeyWithHash(targetHash, candidate);
            if (key != null && !collidingKeys.contains(key))
            {
                if (targetTable.hashFun(key) == targetHash)
                {
                    collidingKeys.add(key);
                    found++;
                }
            }
            candidate++;
            
            // Ограничиваем поиск для производительности
            if (candidate > 100000)
            {
                // Пробуем другой базовый хэш
                baseKey = "key" + found;
                targetHash = targetTable.hashFun(baseKey);
                collidingKeys.add(baseKey);
                found++;
                candidate = 0;
            }
        }
        
        return collidingKeys;
    }

    // 8-5; generateKeyWithHash; Time: O(k), Space: O(1)
    // Генерирует ключ с заданным хэшем
    private String generateKeyWithHash(int targetHash, int variant)
    {
        int size = targetTable.size;
        
        // Простой способ: используем символы, сумма которых даёт нужный хэш
        // Для size=17, hash=0: ключи с суммой кодов 0, 17, 34, 51, ...
        int baseSum = targetHash;
        int multiplier = variant % 10 + 1; // 1-10
        int targetSum = baseSum + multiplier * size;
        
        // Генерируем строку из двух символов с нужной суммой
        char c1 = (char) ('A' + (variant % 26));
        char c2 = (char) (targetSum - c1);
        
        if (c2 >= 32 && c2 < 127) {
            return "" + c1 + c2;
        }
        
        // Если не получилось, пробуем трёхсимвольную строку
        if (targetSum >= 96) {
            char c3 = (char) (targetSum - c1 - 'A');
            if (c3 >= 32 && c3 < 127) {
                return "" + c1 + 'A' + c3;
            }
        }
        
        return null;
    }

    // 8-5; executeAttack; Time: O(n²), Space: O(n)
    // Выполняет атаку: заполняет таблицу коллизирующими ключами
    public AttackResult executeAttack(int numKeys)
    {
        long startTime = System.currentTimeMillis();
        
        // Генерируем коллизирующие ключи
        List<String> keys = generateCollidingKeys(numKeys);
        
        // Вставляем все ключи в таблицу
        int successfulInserts = 0;
        for (String key : keys)
        {
            int index = targetTable.put(key);
            if (index >= 0)
            {
                successfulInserts++;
            }
        }
        
        long endTime = System.currentTimeMillis();
        long insertTime = endTime - startTime;
        
        // Измеряем время поиска (должно быть O(n) для каждого поиска)
        startTime = System.currentTimeMillis();
        int foundCount = 0;
        for (String key : keys)
        {
            int index = targetTable.find(key);
            if (index >= 0)
            {
                foundCount++;
            }
        }
        endTime = System.currentTimeMillis();
        long searchTime = endTime - startTime;
        
        return new AttackResult(
            numKeys,
            successfulInserts,
            foundCount,
            insertTime,
            searchTime,
            targetTable.size,
            targetTable.count()
        );
    }

    // 8-5; getCollidingKeys; Time: O(1), Space: O(1)
    public List<String> getCollidingKeys()
    {
        return new ArrayList<>(collidingKeys);
    }

    // 8-5; AttackResult; Класс для хранения результатов атаки
    public static class AttackResult
    {
        public final int requestedKeys;
        public final int successfulInserts;
        public final int foundCount;
        public final long insertTimeMs;
        public final long searchTimeMs;
        public final int tableSize;
        public final int tableCount;

        public AttackResult(int requestedKeys, int successfulInserts, int foundCount,
                           long insertTimeMs, long searchTimeMs, int tableSize, int tableCount)
        {
            this.requestedKeys = requestedKeys;
            this.successfulInserts = successfulInserts;
            this.foundCount = foundCount;
            this.insertTimeMs = insertTimeMs;
            this.searchTimeMs = searchTimeMs;
            this.tableSize = tableSize;
            this.tableCount = tableCount;
        }

        @Override
        public String toString()
        {
            return String.format(
                "AttackResult{" +
                "requestedKeys=%d, successfulInserts=%d, foundCount=%d, " +
                "insertTimeMs=%d, searchTimeMs=%d, " +
                "tableSize=%d, tableCount=%d, " +
                "avgInsertTime=%.4f ms/key, avgSearchTime=%.4f ms/key" +
                "}",
                requestedKeys, successfulInserts, foundCount,
                insertTimeMs, searchTimeMs,
                tableSize, tableCount,
                successfulInserts > 0 ? (double) insertTimeMs / successfulInserts : 0,
                foundCount > 0 ? (double) searchTimeMs / foundCount : 0
            );
        }
    }
}

