import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NativeDictionary_2Test {

    // ==================== Тесты для put() ====================

    @Test
    public void testPut_newKey() {
        NativeDictionary_2<Integer> dict = new NativeDictionary_2<>();
        
        dict.put("key1", 100);
        
        assertEquals(100, dict.get("key1"));
        assertEquals(1, dict.count());
    }

    @Test
    public void testPut_existingKey_updatesValue() {
        NativeDictionary_2<Integer> dict = new NativeDictionary_2<>();
        
        dict.put("key1", 100);
        dict.put("key1", 200);
        
        assertEquals(200, dict.get("key1"));
        assertEquals(1, dict.count());
    }

    @Test
    public void testPut_maintainsSortedOrder() {
        NativeDictionary_2<Integer> dict = new NativeDictionary_2<>();
        
        // Добавляем ключи в случайном порядке
        dict.put("zebra", 1);
        dict.put("apple", 2);
        dict.put("mango", 3);
        dict.put("banana", 4);
        
        // Ключи должны быть отсортированы
        String[] keys = dict.getAllKeys();
        assertEquals("apple", keys[0]);
        assertEquals("banana", keys[1]);
        assertEquals("mango", keys[2]);
        assertEquals("zebra", keys[3]);
    }

    @Test
    public void testPut_multipleKeys() {
        NativeDictionary_2<String> dict = new NativeDictionary_2<>();
        
        dict.put("first", "one");
        dict.put("second", "two");
        dict.put("third", "three");
        
        assertEquals("one", dict.get("first"));
        assertEquals("two", dict.get("second"));
        assertEquals("three", dict.get("third"));
        assertEquals(3, dict.count());
    }

    // ==================== Тесты для isKey() ====================

    @Test
    public void testIsKey_existingKey() {
        NativeDictionary_2<Integer> dict = new NativeDictionary_2<>();
        dict.put("key1", 100);
        
        assertTrue(dict.isKey("key1"));
    }

    @Test
    public void testIsKey_nonExistingKey() {
        NativeDictionary_2<Integer> dict = new NativeDictionary_2<>();
        dict.put("key1", 100);
        
        assertFalse(dict.isKey("key2"));
        assertFalse(dict.isKey("nonexistent"));
    }

    @Test
    public void testIsKey_emptyDictionary() {
        NativeDictionary_2<Integer> dict = new NativeDictionary_2<>();
        
        assertFalse(dict.isKey("anykey"));
    }

    // ==================== Тесты для get() ====================

    @Test
    public void testGet_existingKey() {
        NativeDictionary_2<String> dict = new NativeDictionary_2<>();
        dict.put("key1", "value1");
        
        assertEquals("value1", dict.get("key1"));
    }

    @Test
    public void testGet_nonExistingKey_returnsNull() {
        NativeDictionary_2<Integer> dict = new NativeDictionary_2<>();
        dict.put("key1", 100);
        
        assertNull(dict.get("key2"));
        assertNull(dict.get("nonexistent"));
    }

    @Test
    public void testGet_emptyDictionary() {
        NativeDictionary_2<Integer> dict = new NativeDictionary_2<>();
        
        assertNull(dict.get("anykey"));
    }

    // ==================== Тесты для remove() ====================

    @Test
    public void testRemove_existingKey() {
        NativeDictionary_2<Integer> dict = new NativeDictionary_2<>();
        dict.put("key1", 100);
        
        assertTrue(dict.remove("key1"));
        assertEquals(0, dict.count());
        assertFalse(dict.isKey("key1"));
    }

    @Test
    public void testRemove_nonExistingKey() {
        NativeDictionary_2<Integer> dict = new NativeDictionary_2<>();
        dict.put("key1", 100);
        
        assertFalse(dict.remove("key2"));
        assertEquals(1, dict.count());
    }

    @Test
    public void testRemove_maintainsSortedOrder() {
        NativeDictionary_2<Integer> dict = new NativeDictionary_2<>();
        dict.put("apple", 1);
        dict.put("banana", 2);
        dict.put("cherry", 3);
        
        dict.remove("banana");
        
        String[] keys = dict.getAllKeys();
        assertEquals(2, keys.length);
        assertEquals("apple", keys[0]);
        assertEquals("cherry", keys[1]);
    }

    // ==================== Тесты для getMinKey() и getMaxKey() ====================

    @Test
    public void testGetMinKey() {
        NativeDictionary_2<Integer> dict = new NativeDictionary_2<>();
        dict.put("zebra", 1);
        dict.put("apple", 2);
        dict.put("mango", 3);
        
        assertEquals("apple", dict.getMinKey());
    }

    @Test
    public void testGetMaxKey() {
        NativeDictionary_2<Integer> dict = new NativeDictionary_2<>();
        dict.put("zebra", 1);
        dict.put("apple", 2);
        dict.put("mango", 3);
        
        assertEquals("zebra", dict.getMaxKey());
    }

    @Test
    public void testGetMinMaxKey_emptyDictionary() {
        NativeDictionary_2<Integer> dict = new NativeDictionary_2<>();
        
        assertNull(dict.getMinKey());
        assertNull(dict.getMaxKey());
    }

    // ==================== Тесты для getKeysInRange() ====================

    @Test
    public void testGetKeysInRange() {
        NativeDictionary_2<Integer> dict = new NativeDictionary_2<>();
        dict.put("apple", 1);
        dict.put("banana", 2);
        dict.put("cherry", 3);
        dict.put("date", 4);
        dict.put("elderberry", 5);
        
        String[] keys = dict.getKeysInRange("banana", "date");
        
        assertEquals(3, keys.length);
        assertEquals("banana", keys[0]);
        assertEquals("cherry", keys[1]);
        assertEquals("date", keys[2]);
    }

    @Test
    public void testGetKeysInRange_noMatches() {
        NativeDictionary_2<Integer> dict = new NativeDictionary_2<>();
        dict.put("apple", 1);
        dict.put("banana", 2);
        
        String[] keys = dict.getKeysInRange("cherry", "date");
        
        assertEquals(0, keys.length);
    }

    // ==================== Тесты для getAllKeys() и getAllValues() ====================

    @Test
    public void testGetAllKeys_sorted() {
        NativeDictionary_2<Integer> dict = new NativeDictionary_2<>();
        dict.put("c", 3);
        dict.put("a", 1);
        dict.put("b", 2);
        
        String[] keys = dict.getAllKeys();
        
        assertEquals(3, keys.length);
        assertEquals("a", keys[0]);
        assertEquals("b", keys[1]);
        assertEquals("c", keys[2]);
    }

    @Test
    public void testGetAllValues_inKeyOrder() {
        NativeDictionary_2<Integer> dict = new NativeDictionary_2<>();
        dict.put("c", 3);
        dict.put("a", 1);
        dict.put("b", 2);
        
        Integer[] values = dict.getAllValues(Integer.class);
        
        assertEquals(3, values.length);
        assertEquals(1, values[0]); // Значение для "a"
        assertEquals(2, values[1]); // Значение для "b"
        assertEquals(3, values[2]); // Значение для "c"
    }

    // ==================== Тесты для производительности ====================

    @Test
    public void testPerformance_largeDictionary() {
        NativeDictionary_2<Integer> dict = new NativeDictionary_2<>();
        
        // Добавляем 1000 элементов
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            dict.put("key" + i, i);
        }
        long insertTime = System.currentTimeMillis() - startTime;
        
        assertEquals(1000, dict.count());
        System.out.println("Insert time for 1000 elements: " + insertTime + " ms");
        
        // Измеряем время поиска (должно быть O(log n))
        startTime = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            dict.get("key" + i);
        }
        long searchTime = System.currentTimeMillis() - startTime;
        
        System.out.println("Search time for 1000 elements: " + searchTime + " ms");
        assertTrue(searchTime < 5000, "Search should complete in reasonable time");
    }

    // ==================== Тесты для разных типов значений ====================

    @Test
    public void testDifferentValueTypes() {
        NativeDictionary_2<String> strDict = new NativeDictionary_2<>();
        strDict.put("name", "Alice");
        assertEquals("Alice", strDict.get("name"));
        
        NativeDictionary_2<Integer> intDict = new NativeDictionary_2<>();
        intDict.put("age", 25);
        assertEquals(25, intDict.get("age"));
        
        NativeDictionary_2<Double> doubleDict = new NativeDictionary_2<>();
        doubleDict.put("pi", 3.14159);
        assertEquals(3.14159, doubleDict.get("pi"), 0.00001);
    }

    // ==================== Тест для комплексного сценария ====================

    @Test
    public void testDictionary_fullWorkflow() {
        NativeDictionary_2<String> dict = new NativeDictionary_2<>();
        
        // Добавление
        dict.put("name", "John");
        dict.put("city", "Moscow");
        dict.put("country", "Russia");
        
        // Проверка наличия
        assertTrue(dict.isKey("name"));
        assertTrue(dict.isKey("city"));
        assertTrue(dict.isKey("country"));
        assertFalse(dict.isKey("age"));
        
        // Получение значений
        assertEquals("John", dict.get("name"));
        assertEquals("Moscow", dict.get("city"));
        assertEquals("Russia", dict.get("country"));
        assertNull(dict.get("age"));
        
        // Обновление
        dict.put("city", "St. Petersburg");
        assertEquals("St. Petersburg", dict.get("city"));
        assertEquals(3, dict.count());
        
        // Удаление
        assertTrue(dict.remove("country"));
        assertFalse(dict.isKey("country"));
        assertNull(dict.get("country"));
        assertEquals(2, dict.count());
        
        // Проверка сортировки
        String[] keys = dict.getAllKeys();
        assertEquals("city", keys[0]);
        assertEquals("name", keys[1]);
    }
}
