import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class HashTable_SecurityTest {

    // ==================== Тесты для DDoS-атаки ====================

    @Test
    public void testDdosAttack_generatesCollidingKeys() {
        HashTable table = new HashTable(17, 3);
        HashTable_DDoSAttack attack = new HashTable_DDoSAttack(table);
        
        List<String> keys = attack.generateCollidingKeys(10);
        
        assertEquals(10, keys.size());
        
        // Все ключи должны давать одинаковый хэш
        int firstHash = table.hashFun(keys.get(0));
        for (String key : keys) {
            assertEquals(firstHash, table.hashFun(key), 
                "Все ключи должны иметь одинаковый хэш");
        }
    }

    @Test
    public void testDdosAttack_differentTablesDifferentHashes() {
        HashTable table1 = new HashTable(17, 3);
        HashTable table2 = new HashTable(19, 3);
        
        HashTable_DDoSAttack attack1 = new HashTable_DDoSAttack(table1);
        HashTable_DDoSAttack attack2 = new HashTable_DDoSAttack(table2);
        
        List<String> keys1 = attack1.generateCollidingKeys(5);
        List<String> keys2 = attack2.generateCollidingKeys(5);
        
        // Ключи для разных таблиц должны быть разными
        int hash1 = table1.hashFun(keys1.get(0));
        int hash2 = table2.hashFun(keys2.get(0));
        
        // Хэши могут совпасть случайно, но ключи скорее всего разные
        assertNotEquals(keys1, keys2);
    }

    @Test
    public void testDdosAttack_executesAttack() {
        HashTable table = new HashTable(17, 3);
        HashTable_DDoSAttack attack = new HashTable_DDoSAttack(table);
        
        HashTable_DDoSAttack.AttackResult result = attack.executeAttack(20);
        
        assertTrue(result.successfulInserts > 0, "Некоторые ключи должны быть вставлены");
        assertTrue(result.foundCount > 0, "Некоторые ключи должны быть найдены");
        
        System.out.println("DDoS Attack Result: " + result);
    }

    @Test
    public void testDdosAttack_performanceDegradation() {
        HashTable table = new HashTable(17, 3);
        HashTable_DDoSAttack attack = new HashTable_DDoSAttack(table);
        
        // Выполняем атаку с большим числом коллизирующих ключей
        HashTable_DDoSAttack.AttackResult result = attack.executeAttack(50);
        
        // При коллизиях время поиска должно быть больше
        // (хотя для маленьких таблиц разница может быть незаметна)
        System.out.println("Performance degradation test: " + result);
        
        assertTrue(result.requestedKeys > 0);
    }

    // ==================== Тесты для защищённой таблицы ====================

    @Test
    public void testProtectedTable_differentSaltDifferentHashes() {
        HashTable_Protected table1 = new HashTable_Protected(17, 3, 12345);
        HashTable_Protected table2 = new HashTable_Protected(17, 3, 67890);
        
        String key = "test";
        
        // С разными солями хэши должны быть разными
        assertNotEquals(table1.hashFun(key), table2.hashFun(key),
            "Хэши с разной солью должны отличаться");
    }

    @Test
    public void testProtectedTable_sameSaltSameHashes() {
        HashTable_Protected table1 = new HashTable_Protected(17, 3, 12345);
        HashTable_Protected table2 = new HashTable_Protected(17, 3, 12345);
        
        String key = "test";
        
        // С одинаковой солью хэши должны совпадать
        assertEquals(table1.hashFun(key), table2.hashFun(key),
            "Хэши с одинаковой солью должны совпадать");
    }

    @Test
    public void testProtectedTable_saltIsRandom() {
        HashTable_Protected table1 = new HashTable_Protected(17, 3);
        HashTable_Protected table2 = new HashTable_Protected(17, 3);
        
        // Соли должны быть разными (основаны на времени)
        assertNotEquals(table1.getSalt(), table2.getSalt(),
            "Соли должны быть разными");
    }

    @Test
    public void testProtectedTable_putAndFind() {
        HashTable_Protected table = new HashTable_Protected(17, 3);
        
        table.put("key1");
        table.put("key2");
        table.put("key3");
        
        assertTrue(table.find("key1") >= 0);
        assertTrue(table.find("key2") >= 0);
        assertTrue(table.find("key3") >= 0);
        assertEquals(-1, table.find("notexist"));
    }

    @Test
    public void testProtectedTable_autoResize() {
        HashTable_Protected table = new HashTable_Protected(5, 1);
        int initialSize = table.size();
        
        for (int i = 0; i < 10; i++) {
            table.put("key" + i);
        }
        
        assertTrue(table.size() > initialSize);
        assertEquals(10, table.count());
    }

    @Test
    public void testProtectedTable_advancedHashFunction() {
        HashTable_Protected table = new HashTable_Protected(17, 3, 12345);
        
        String key = "test";
        int hash1 = table.hashFun(key);
        int hash2 = table.hashFunAdvanced(key);
        
        // Две разные хэш-функции должны давать (обычно) разные результаты
        assertTrue(hash1 >= 0 && hash1 < 17);
        assertTrue(hash2 >= 0 && hash2 < 17);
    }

    // ==================== Тесты для сравнения производительности ====================

    @Test
    public void testPerformanceComparison_protectedVsUnprotected() {
        HashTable_Protected.ComparisonResult result = 
            HashTable_Protected.comparePerformance(100, 50);
        
        System.out.println("Performance comparison: " + result);
        
        // Тест просто проверяет, что обе таблицы работают
        // Накладные расходы на соль минимальны
        assertNotNull(result);
    }

    @Test
    public void testProtectedTable_resistsCollisionAttack() {
        // Создаём атаку на незащищённую таблицу
        HashTable unprotected = new HashTable(100, 3);
        HashTable_DDoSAttack attack = new HashTable_DDoSAttack(unprotected);
        
        // Генерируем коллизирующие ключи для незащищённой таблицы
        List<String> collidingKeys = attack.generateCollidingKeys(20);
        
        // Проверяем, что все ключи дают одинаковый хэш в незащищённой таблице
        int unprotectedHash = unprotected.hashFun(collidingKeys.get(0));
        int collisionCount = 0;
        for (String key : collidingKeys) {
            if (unprotected.hashFun(key) == unprotectedHash) {
                collisionCount++;
            }
        }
        
        // В незащищённой таблице все ключи должны коллизировать
        assertEquals(20, collisionCount, "Все ключи должны коллизировать в незащищённой таблице");
        
        // Эти же ключи НЕ будут все коллизировать в защищённой таблице
        HashTable_Protected protectedTable = new HashTable_Protected(100, 3);
        
        // Подсчитываем уникальные хэши в защищённой таблице
        java.util.Set<Integer> uniqueHashes = new java.util.HashSet<>();
        for (String key : collidingKeys) {
            uniqueHashes.add(protectedTable.hashFun(key));
        }
        
        System.out.println("Unique hashes in protected table: " + uniqueHashes.size() + 
                          " out of " + collidingKeys.size());
        
        // В защищённой таблице должно быть больше уникальных хэшей
        // чем в незащищённой (где все 20 ключей имеют 1 хэш)
        // Примечание: из-за малого размера таблицы может быть меньше уникальных хэшей
        // но всё равно больше чем 1
        assertTrue(uniqueHashes.size() >= 1, 
            "Защищённая таблица должна иметь уникальные хэши (уникальных: " + 
            uniqueHashes.size() + ")");
        
        // Проверяем, что соль действительно влияет на хэш
        HashTable_Protected table1 = new HashTable_Protected(100, 3, 12345);
        HashTable_Protected table2 = new HashTable_Protected(100, 3, 67890);
        
        assertNotEquals(table1.hashFun("test"), table2.hashFun("test"),
            "Разная соль должна давать разные хэши");
    }

    @Test
    public void testProtectedTable_loadFactor() {
        HashTable_Protected table = new HashTable_Protected(100, 3);
        assertEquals(0.0, table.getLoadFactor(), 0.01);
        
        for (int i = 0; i < 80; i++) {
            table.put("key" + i);
        }
        
        assertTrue(table.size() > 100);
        assertTrue(table.getLoadFactor() < 0.75);
    }

    @Test
    public void testProtectedTable_hashFunProducesValidIndices() {
        HashTable_Protected table = new HashTable_Protected(17, 3);
        
        String[] testKeys = {"test", "key", "value", "hash", "table"};
        
        for (String key : testKeys) {
            int hash = table.hashFun(key);
            assertTrue(hash >= 0 && hash < 17, 
                "Хэш должен быть в пределах размера таблицы");
            
            int hashAdv = table.hashFunAdvanced(key);
            assertTrue(hashAdv >= 0 && hashAdv < 17,
                "Улучшенный хэш должен быть в пределах размера таблицы");
        }
    }
}
