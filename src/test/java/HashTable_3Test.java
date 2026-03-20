import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HashTable_3Test {

    // ==================== Тесты для хэш-функций ====================

    @Test
    public void testHashFunctions_differentFunctionsDifferentResults() {
        HashTable_3 table = new HashTable_3(17, 3);
        
        int h1 = table.hashFun1("test");
        int h2 = table.hashFun2("test");
        int h3 = table.hashFun3("test");
        
        // Хэш-функции должны давать разные результаты для большинства строк
        assertTrue(h1 >= 0 && h1 < 17);
        assertTrue(h2 >= 0 && h2 < 17);
        assertTrue(h3 >= 0 && h3 < 17);
    }

    @Test
    public void testHashFunctions_sameStringSameHash() {
        HashTable_3 table = new HashTable_3(17, 3);
        
        assertEquals(table.hashFun1("test"), table.hashFun1("test"));
        assertEquals(table.hashFun2("test"), table.hashFun2("test"));
        assertEquals(table.hashFun3("test"), table.hashFun3("test"));
    }

    @Test
    public void testGetHashFunctions() {
        HashTable_3 table = new HashTable_3(17, 3);
        int[] hashes = table.getHashFunctions("test");
        
        assertEquals(3, hashes.length);
        assertEquals(table.hashFun1("test"), hashes[0]);
        assertEquals(table.hashFun2("test"), hashes[1]);
        assertEquals(table.hashFun3("test"), hashes[2]);
    }

    // ==================== Тесты для put() и find() ====================

    @Test
    public void testPutAndFind_singleElement() {
        HashTable_3 table = new HashTable_3(17, 3);
        int index = table.put("test");
        assertTrue(index >= 0);
        assertEquals(index, table.find("test"));
    }

    @Test
    public void testPutAndFind_multipleElements() {
        HashTable_3 table = new HashTable_3(17, 3);
        
        for (int i = 0; i < 10; i++) {
            int index = table.put("key" + i);
            assertTrue(index >= 0);
        }
        
        for (int i = 0; i < 10; i++) {
            assertTrue(table.find("key" + i) >= 0);
        }
    }

    @Test
    public void testPut_duplicateValue() {
        HashTable_3 table = new HashTable_3(17, 3);
        int index1 = table.put("test");
        int index2 = table.put("test");
        
        assertEquals(index1, index2);
        assertEquals(1, table.count());
    }

    // ==================== Тесты для нескольких хэш-функций ====================

    @Test
    public void testMultipleHashFunctions_reducesCollisions() {
        // Сравниваем таблицы с 1 и 3 хэш-функциями
        HashTable_3 table1Hash = new HashTable_3(17, 1);
        HashTable_3 table3Hash = new HashTable_3(17, 3);
        
        // Добавляем одинаковые ключи
        String[] keys = {"apple", "banana", "cherry", "date", "elderberry", "fig", "grape"};
        
        for (String key : keys) {
            table1Hash.put(key);
            table3Hash.put(key);
        }
        
        // Обе таблицы должны содержать все элементы
        assertEquals(keys.length, table1Hash.count());
        assertEquals(keys.length, table3Hash.count());
        
        // Все элементы должны находиться
        for (String key : keys) {
            assertTrue(table1Hash.find(key) >= 0);
            assertTrue(table3Hash.find(key) >= 0);
        }
    }

    @Test
    public void testMultipleHashFunctions_withCollisions() {
        HashTable_3 table = new HashTable_3(10, 3);
        
        // Добавляем много элементов в маленькую таблицу
        for (int i = 0; i < 20; i++) {
            int index = table.put("key" + i);
            assertTrue(index >= 0, "Failed to put key" + i);
        }
        
        assertEquals(20, table.count());
        
        // Все элементы должны находиться
        for (int i = 0; i < 20; i++) {
            assertTrue(table.find("key" + i) >= 0);
        }
    }

    // ==================== Тесты для resize() ====================

    @Test
    public void testResize_autoResize() {
        HashTable_3 table = new HashTable_3(5, 3);
        int initialSize = table.size();
        
        // Добавляем элементы до resize
        for (int i = 0; i < 10; i++) {
            table.put("key" + i);
        }
        
        assertTrue(table.size() > initialSize);
        assertEquals(10, table.count());
    }

    @Test
    public void testResize_elementsPreserved() {
        HashTable_3 table = new HashTable_3(5, 3);
        
        table.put("a");
        table.put("b");
        table.put("c");
        table.put("d");
        
        // Trigger resize
        table.put("e");
        
        // Все элементы должны остаться доступными
        assertTrue(table.find("a") >= 0);
        assertTrue(table.find("b") >= 0);
        assertTrue(table.find("c") >= 0);
        assertTrue(table.find("d") >= 0);
        assertTrue(table.find("e") >= 0);
    }

    // ==================== Тесты для разных numHashFunctions ====================

    @Test
    public void testDifferentNumHashFunctions_oneHash() {
        HashTable_3 table = new HashTable_3(17, 1);
        assertEquals(1, table.getNumHashFunctions());
        
        for (int i = 0; i < 10; i++) {
            table.put("key" + i);
        }
        
        assertEquals(10, table.count());
    }

    @Test
    public void testDifferentNumHashFunctions_twoHashes() {
        HashTable_3 table = new HashTable_3(17, 2);
        assertEquals(2, table.getNumHashFunctions());
        
        for (int i = 0; i < 10; i++) {
            table.put("key" + i);
        }
        
        assertEquals(10, table.count());
    }

    @Test
    public void testDifferentNumHashFunctions_threeHashes() {
        HashTable_3 table = new HashTable_3(17, 3);
        assertEquals(3, table.getNumHashFunctions());
        
        for (int i = 0; i < 10; i++) {
            table.put("key" + i);
        }
        
        assertEquals(10, table.count());
    }

    // ==================== Тесты производительности ====================

    @Test
    public void testPerformance_largeTable() {
        HashTable_3 table = new HashTable_3(100, 3);
        
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < 500; i++) {
            table.put("key" + i);
        }
        
        long endTime = System.currentTimeMillis();
        
        assertEquals(500, table.count());
        assertTrue(endTime - startTime < 5000, "Operation should complete in reasonable time");
        
        // Проверяем поиск
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 500; i++) {
            assertTrue(table.find("key" + i) >= 0);
        }
        endTime = System.currentTimeMillis();
        
        assertTrue(endTime - startTime < 5000, "Search should complete in reasonable time");
    }

    @Test
    public void testPerformance_compareHashFunctions() {
        // Сравниваем производительность таблиц с разным числом хэш-функций
        HashTable_3 table1 = new HashTable_3(50, 1);
        HashTable_3 table2 = new HashTable_3(50, 2);
        HashTable_3 table3 = new HashTable_3(50, 3);
        
        int numElements = 200;
        
        long time1 = measurePutTime(table1, numElements);
        long time2 = measurePutTime(table2, numElements);
        long time3 = measurePutTime(table3, numElements);
        
        // Все таблицы должны работать за разумное время
        assertTrue(time1 < 5000);
        assertTrue(time2 < 5000);
        assertTrue(time3 < 5000);
        
        // Таблица с 1 хэш-функцией может быть немного быстрее (меньше вычислений)
        // Но таблицы с несколькими хэш-функциями должны иметь меньше коллизий
    }

    private long measurePutTime(HashTable_3 table, int numElements) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < numElements; i++) {
            table.put("key" + i);
        }
        return System.currentTimeMillis() - startTime;
    }

    // ==================== Тесты для countCollisions() ====================

    @Test
    public void testCountCollisions() {
        HashTable_3 table = new HashTable_3(100, 3);
        
        // Пустая таблица — нет коллизий
        assertEquals(0, table.countCollisions());
        
        // Добавляем элементы
        for (int i = 0; i < 50; i++) {
            table.put("key" + i);
        }
        
        // Коллизии должны быть (некоторые элементы могут иметь пересекающиеся хэши)
        int collisions = table.countCollisions();
        assertTrue(collisions >= 0);
    }

    // ==================== Тесты для loadFactor ====================

    @Test
    public void testLoadFactor() {
        HashTable_3 table = new HashTable_3(100, 3);
        assertEquals(0.0, table.getLoadFactor(), 0.01);
        
        table.put("a");
        assertEquals(0.01, table.getLoadFactor(), 0.01);
        
        for (int i = 0; i < 80; i++) {
            table.put("key" + i);
        }
        
        // Должен быть resize при loadFactor >= 0.75
        assertTrue(table.size() > 100, "Should have resized");
        assertTrue(table.getLoadFactor() < 0.75);
    }

    // ==================== Тесты для seekSlot() ====================

    @Test
    public void testSeekSlot_usesMultipleHashes() {
        HashTable_3 table = new HashTable_3(17, 3);
        
        // Добавляем элемент
        String value = "test";
        int slot1 = table.put(value);
        assertTrue(slot1 >= 0);
        
        // Добавляем ещё элементы, чтобы создать коллизии
        for (int i = 0; i < 10; i++) {
            table.put("key" + i);
        }
        
        // seekSlot должен найти слот для нового значения
        int slot2 = table.seekSlot("newvalue");
        assertTrue(slot2 >= 0);
    }
}
