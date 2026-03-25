import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NativeDictionary_3Test {

    // ==================== Тесты для put() и get() ====================

    @Test
    public void testPutAndGet_basic() {
        NativeDictionary_3<Integer> dict = new NativeDictionary_3<>(8);
        
        dict.put(0b00000001L, 100);
        dict.put(0b00000010L, 200);
        dict.put(0b00000100L, 300);
        
        assertEquals(100, dict.get(0b00000001L));
        assertEquals(200, dict.get(0b00000010L));
        assertEquals(300, dict.get(0b00000100L));
        assertEquals(3, dict.count());
    }

    @Test
    public void testPut_updateExistingKey() {
        NativeDictionary_3<String> dict = new NativeDictionary_3<>(8);
        
        dict.put(0b00000001L, "first");
        dict.put(0b00000001L, "updated");
        
        assertEquals("updated", dict.get(0b00000001L));
        assertEquals(1, dict.count());
    }

    @Test
    public void testPut_allPossibleKeys() {
        NativeDictionary_3<Integer> dict = new NativeDictionary_3<>(4);
        
        // Добавляем все 16 возможных ключей для 4 бит
        for (long i = 0; i < 16; i++) {
            dict.put(i, (int)i * 10);
        }
        
        assertEquals(16, dict.count());
        
        for (long i = 0; i < 16; i++) {
            assertEquals((int)(i * 10), dict.get(i));
        }
    }

    @Test
    public void testGet_nonExistentKey() {
        NativeDictionary_3<Integer> dict = new NativeDictionary_3<>(8);
        
        dict.put(0b00000001L, 100);
        
        assertNull(dict.get(0b00000010L));
        assertNull(dict.get(0b11111111L));
    }

    @Test
    public void testPut_keyOutOfRange() {
        NativeDictionary_3<Integer> dict = new NativeDictionary_3<>(4);
        
        assertThrows(IllegalArgumentException.class, () -> {
            dict.put(16L, 100); // 16 = 0b10000, требует 5 бит
        });
    }

    // ==================== Тесты для isKey() ====================

    @Test
    public void testIsKey_existingKey() {
        NativeDictionary_3<Integer> dict = new NativeDictionary_3<>(8);
        dict.put(0b00000101L, 100);
        
        assertTrue(dict.isKey(0b00000101L));
    }

    @Test
    public void testIsKey_nonExistentKey() {
        NativeDictionary_3<Integer> dict = new NativeDictionary_3<>(8);
        dict.put(0b00000101L, 100);
        
        assertFalse(dict.isKey(0b00000110L));
        assertFalse(dict.isKey(0b11111111L));
    }

    @Test
    public void testIsKey_emptyDictionary() {
        NativeDictionary_3<Integer> dict = new NativeDictionary_3<>(8);
        
        assertFalse(dict.isKey(0b00000000L));
        assertFalse(dict.isKey(0b11111111L));
    }

    // ==================== Тесты для remove() ====================

    @Test
    public void testRemove_existingKey() {
        NativeDictionary_3<Integer> dict = new NativeDictionary_3<>(8);
        dict.put(0b00000001L, 100);
        
        assertTrue(dict.remove(0b00000001L));
        assertEquals(0, dict.count());
        assertFalse(dict.isKey(0b00000001L));
        assertNull(dict.get(0b00000001L));
    }

    @Test
    public void testRemove_nonExistentKey() {
        NativeDictionary_3<Integer> dict = new NativeDictionary_3<>(8);
        dict.put(0b00000001L, 100);
        
        assertFalse(dict.remove(0b00000010L));
        assertEquals(1, dict.count());
    }

    @Test
    public void testRemove_multipleKeys() {
        NativeDictionary_3<Integer> dict = new NativeDictionary_3<>(8);
        dict.put(0b00000001L, 1);
        dict.put(0b00000010L, 2);
        dict.put(0b00000100L, 3);
        
        assertTrue(dict.remove(0b00000010L));
        assertEquals(2, dict.count());
        
        assertNull(dict.get(0b00000010L));
        assertEquals(1, dict.get(0b00000001L));
        assertEquals(3, dict.get(0b00000100L));
    }

    @Test
    public void testRemove_clearsEmptyNodes() {
        NativeDictionary_3<Integer> dict = new NativeDictionary_3<>(8);
        
        // Добавляем ключи с общим префиксом
        dict.put(0b00000000L, 1);
        dict.put(0b00000001L, 2);
        
        dict.remove(0b00000000L);
        dict.remove(0b00000001L);
        
        assertEquals(0, dict.count());
        assertTrue(dict.isEmpty());
    }

    // ==================== Тесты для findByPrefix() ====================

    @Test
    public void testFindByPrefix_exists() {
        NativeDictionary_3<Integer> dict = new NativeDictionary_3<>(8);
        dict.put(0b00000000L, 1);
        dict.put(0b00000001L, 2);
        dict.put(0b00000010L, 3);
        
        // Префикс 0000000 (7 бит) должен найти ключи 0b00000000 и 0b00000001
        assertTrue(dict.hasKeyWithPrefix(0b00000000L, 7));
    }

    @Test
    public void testFindByPrefix_notExists() {
        NativeDictionary_3<Integer> dict = new NativeDictionary_3<>(8);
        dict.put(0b00000000L, 1);
        
        assertFalse(dict.hasKeyWithPrefix(0b11111111L, 8));
    }

    // ==================== Тесты для countWithPrefix() ====================

    @Test
    public void testCountWithPrefix() {
        NativeDictionary_3<Integer> dict = new NativeDictionary_3<>(8);
        dict.put(0b00000000L, 1);
        dict.put(0b00000001L, 2);
        dict.put(0b00000010L, 3);
        dict.put(0b00000011L, 4);
        
        // Префикс 000000 (6 бит) должен найти 4 ключа
        assertEquals(4, dict.countWithPrefix(0b00000000L, 6));
        
        // Префикс 0000000 (7 бит) должен найти 2 ключа (0b00000000, 0b00000001)
        assertEquals(2, dict.countWithPrefix(0b00000000L, 7));
    }

    // ==================== Тесты для getAllKeys() и getAllValues() ====================

    @Test
    public void testGetAllKeys() {
        NativeDictionary_3<Integer> dict = new NativeDictionary_3<>(4);
        
        dict.put(0b0001L, 1);
        dict.put(0b0101L, 5);
        dict.put(0b1001L, 9);
        
        long[] keys = dict.getAllKeys();
        
        assertEquals(3, keys.length);
        // Ключи могут быть в любом порядке
        assertTrue(contains(keys, 0b0001L));
        assertTrue(contains(keys, 0b0101L));
        assertTrue(contains(keys, 0b1001L));
    }

    @Test
    public void testGetAllValues() {
        NativeDictionary_3<String> dict = new NativeDictionary_3<>(4);
        
        dict.put(0b0001L, "one");
        dict.put(0b0101L, "five");
        
        String[] values = dict.getAllValues(String.class);
        
        assertEquals(2, values.length);
        assertTrue(contains(values, "one"));
        assertTrue(contains(values, "five"));
    }

    // ==================== Тесты для clear() и isEmpty() ====================

    @Test
    public void testClear() {
        NativeDictionary_3<Integer> dict = new NativeDictionary_3<>(8);
        dict.put(0b00000001L, 1);
        dict.put(0b00000010L, 2);
        
        dict.clear();
        
        assertEquals(0, dict.count());
        assertTrue(dict.isEmpty());
        assertFalse(dict.isKey(0b00000001L));
    }

    @Test
    public void testIsEmpty() {
        NativeDictionary_3<Integer> dict = new NativeDictionary_3<>(8);
        
        assertTrue(dict.isEmpty());
        
        dict.put(0b00000001L, 1);
        assertFalse(dict.isEmpty());
        
        dict.remove(0b00000001L);
        assertTrue(dict.isEmpty());
    }

    // ==================== Тесты для разных длин битовых строк ====================

    @Test
    public void testDifferentBitLengths() {
        // 4 бита = 16 возможных ключей
        NativeDictionary_3<Integer> dict4 = new NativeDictionary_3<>(4);
        for (long i = 0; i < 16; i++) {
            dict4.put(i, (int)i);
        }
        assertEquals(16, dict4.count());

        // 8 бит = 256 возможных ключей
        NativeDictionary_3<Integer> dict8 = new NativeDictionary_3<>(8);
        for (long i = 0; i < 256; i++) {
            dict8.put(i, (int)i);
        }
        assertEquals(256, dict8.count());

        // 16 бит
        NativeDictionary_3<Integer> dict16 = new NativeDictionary_3<>(16);
        for (long i = 0; i < 1000; i++) {
            dict16.put(i, (int)i);
        }
        assertEquals(1000, dict16.count());
    }

    @Test
    public void testInvalidBitLength() {
        assertThrows(IllegalArgumentException.class, () -> {
            new NativeDictionary_3<>(0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new NativeDictionary_3<>(65);
        });
    }

    // ==================== Тесты производительности ====================

    @Test
    public void testPerformance_largeDictionary() {
        NativeDictionary_3<Integer> dict = new NativeDictionary_3<>(16);
        
        long startTime = System.currentTimeMillis();
        
        // Добавляем 10000 элементов
        for (int i = 0; i < 10000; i++) {
            dict.put(i, i * 10);
        }
        
        long insertTime = System.currentTimeMillis() - startTime;
        assertEquals(10000, dict.count());
        
        // Измеряем время поиска
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            dict.get(i);
        }
        long searchTime = System.currentTimeMillis() - startTime;
        
        System.out.println("Insert time for 10000 elements: " + insertTime + " ms");
        System.out.println("Search time for 10000 elements: " + searchTime + " ms");
        
        assertTrue(insertTime < 5000, "Insert should complete in reasonable time");
        assertTrue(searchTime < 5000, "Search should complete in reasonable time");
    }

    @Test
    public void testPerformance_bitwiseOperations() {
        NativeDictionary_3<Integer> dict = new NativeDictionary_3<>(32);
        
        // Используем битовые маски для ключей
        long mask = 0xFFFFFFFFL;
        
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < 1000; i++) {
            long key = i & mask;
            dict.put(key, i);
        }
        
        long insertTime = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            long key = i & mask;
            dict.get(key);
        }
        long searchTime = System.currentTimeMillis() - startTime;
        
        System.out.println("Bitwise operations - Insert: " + insertTime + " ms, Search: " + searchTime + " ms");
        
        assertTrue(insertTime < 1000);
        assertTrue(searchTime < 1000);
    }

    // ==================== Вспомогательные методы ====================

    private boolean contains(long[] array, long value) {
        for (long v : array) {
            if (v == value) return true;
        }
        return false;
    }

    private boolean contains(String[] array, String value) {
        for (String v : array) {
            if (v.equals(value)) return true;
        }
        return false;
    }

    // ==================== Тест для комплексного сценария ====================

    @Test
    public void testDictionary_fullWorkflow() {
        NativeDictionary_3<String> dict = new NativeDictionary_3<>(8);
        
        // Добавление
        dict.put(0b00000001L, "one");
        dict.put(0b00000010L, "two");
        dict.put(0b00000100L, "four");
        
        // Проверка наличия
        assertTrue(dict.isKey(0b00000001L));
        assertTrue(dict.isKey(0b00000010L));
        assertFalse(dict.isKey(0b00000011L));
        
        // Получение
        assertEquals("one", dict.get(0b00000001L));
        assertEquals("two", dict.get(0b00000010L));
        assertNull(dict.get(0b00000011L));
        
        // Обновление
        dict.put(0b00000001L, "ONE");
        assertEquals("ONE", dict.get(0b00000001L));
        assertEquals(3, dict.count());
        
        // Удаление
        assertTrue(dict.remove(0b00000010L));
        assertEquals(2, dict.count());
        assertFalse(dict.isKey(0b00000010L));
        
        // Префиксный поиск
        assertTrue(dict.hasKeyWithPrefix(0b00000000L, 7));
        
        // Очистка
        dict.clear();
        assertEquals(0, dict.count());
        assertTrue(dict.isEmpty());
    }
}
