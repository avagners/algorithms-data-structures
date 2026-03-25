import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NativeDictionaryTest {

    // ==================== Тесты для put() ====================

    @Test
    public void testPut_newKey() {
        NativeDictionary<Integer> dict = new NativeDictionary<>(17, Integer.class);
        
        dict.put("key1", 100);
        
        assertEquals(100, dict.get("key1"));
        assertEquals(1, dict.count());
    }

    @Test
    public void testPut_existingKey_updatesValue() {
        NativeDictionary<Integer> dict = new NativeDictionary<>(17, Integer.class);
        
        dict.put("key1", 100);
        dict.put("key1", 200); // Обновление существующего ключа
        
        assertEquals(200, dict.get("key1"));
        assertEquals(1, dict.count()); // Количество не изменилось
    }

    @Test
    public void testPut_multipleKeys() {
        NativeDictionary<String> dict = new NativeDictionary<>(17, String.class);
        
        dict.put("first", "one");
        dict.put("second", "two");
        dict.put("third", "three");
        
        assertEquals("one", dict.get("first"));
        assertEquals("two", dict.get("second"));
        assertEquals("three", dict.get("third"));
        assertEquals(3, dict.count());
    }

    @Test
    public void testPut_mixedNewAndExisting() {
        NativeDictionary<Integer> dict = new NativeDictionary<>(17, Integer.class);
        
        dict.put("key1", 1);
        dict.put("key2", 2);
        dict.put("key1", 10); // Обновление
        dict.put("key3", 3);
        dict.put("key2", 20); // Обновление
        
        assertEquals(10, dict.get("key1"));
        assertEquals(20, dict.get("key2"));
        assertEquals(3, dict.get("key3"));
        assertEquals(3, dict.count());
    }

    // ==================== Тесты для isKey() ====================

    @Test
    public void testIsKey_existingKey() {
        NativeDictionary<Integer> dict = new NativeDictionary<>(17, Integer.class);
        dict.put("key1", 100);
        
        assertTrue(dict.isKey("key1"));
    }

    @Test
    public void testIsKey_nonExistingKey() {
        NativeDictionary<Integer> dict = new NativeDictionary<>(17, Integer.class);
        dict.put("key1", 100);
        
        assertFalse(dict.isKey("key2"));
        assertFalse(dict.isKey("nonexistent"));
    }

    @Test
    public void testIsKey_emptyDictionary() {
        NativeDictionary<Integer> dict = new NativeDictionary<>(17, Integer.class);
        
        assertFalse(dict.isKey("anykey"));
    }

    @Test
    public void testIsKey_afterRemove() {
        NativeDictionary<Integer> dict = new NativeDictionary<>(17, Integer.class);
        dict.put("key1", 100);
        assertTrue(dict.isKey("key1"));
        
        dict.remove("key1");
        assertFalse(dict.isKey("key1"));
    }

    // ==================== Тесты для get() ====================

    @Test
    public void testGet_existingKey() {
        NativeDictionary<String> dict = new NativeDictionary<>(17, String.class);
        dict.put("key1", "value1");
        
        assertEquals("value1", dict.get("key1"));
    }

    @Test
    public void testGet_nonExistingKey_returnsNull() {
        NativeDictionary<Integer> dict = new NativeDictionary<>(17, Integer.class);
        dict.put("key1", 100);
        
        assertNull(dict.get("key2"));
        assertNull(dict.get("nonexistent"));
    }

    @Test
    public void testGet_emptyDictionary() {
        NativeDictionary<Integer> dict = new NativeDictionary<>(17, Integer.class);
        
        assertNull(dict.get("anykey"));
    }

    @Test
    public void testGet_afterUpdate() {
        NativeDictionary<Integer> dict = new NativeDictionary<>(17, Integer.class);
        dict.put("key1", 100);
        dict.put("key1", 200);
        
        assertEquals(200, dict.get("key1"));
    }

    // ==================== Комплексные тесты ====================

    @Test
    public void testDictionary_fullWorkflow() {
        NativeDictionary<String> dict = new NativeDictionary<>(17, String.class);
        
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
    }

    @Test
    public void testDictionary_withNullValue() {
        NativeDictionary<String> dict = new NativeDictionary<>(17, String.class);
        
        // null как значение допустимо
        dict.put("key1", null);
        
        assertTrue(dict.isKey("key1"));
        assertNull(dict.get("key1"));
    }

    @Test
    public void testDictionary_hashCollisions() {
        NativeDictionary<Integer> dict = new NativeDictionary<>(17, Integer.class);
        
        // Добавляем много ключей в таблицу (будут коллизии)
        for (int i = 0; i < 10; i++) {
            dict.put("key" + i, i * 10);
        }
        
        // Все ключи должны быть доступны
        for (int i = 0; i < 10; i++) {
            assertTrue(dict.isKey("key" + i), "key" + i + " should exist");
            assertEquals(i * 10, dict.get("key" + i));
        }
        
        assertEquals(10, dict.count());
    }

    @Test
    public void testDictionary_differentValueTypes() {
        // Словарь с Integer
        NativeDictionary<Integer> intDict = new NativeDictionary<>(17, Integer.class);
        intDict.put("age", 25);
        assertEquals(25, intDict.get("age"));
        
        // Словарь с String
        NativeDictionary<String> strDict = new NativeDictionary<>(17, String.class);
        strDict.put("name", "Alice");
        assertEquals("Alice", strDict.get("name"));
        
        // Словарь с Double
        NativeDictionary<Double> doubleDict = new NativeDictionary<>(17, Double.class);
        doubleDict.put("pi", 3.14159);
        assertEquals(3.14159, doubleDict.get("pi"), 0.00001);
    }

    @Test
    public void testDictionary_largeDictionary() {
        NativeDictionary<Integer> dict = new NativeDictionary<>(100, Integer.class);
        
        // Добавляем 50 элементов
        for (int i = 0; i < 50; i++) {
            dict.put("key" + i, i);
        }
        
        assertEquals(50, dict.count());
        
        // Проверяем все элементы
        for (int i = 0; i < 50; i++) {
            assertTrue(dict.isKey("key" + i));
            assertEquals(i, dict.get("key" + i));
        }
    }
}
