import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PowerSetTest {

    // ==================== Тесты для put() ====================

    @Test
    public void testPut_newElement_added() {
        PowerSet set = new PowerSet();
        
        set.put("element1");
        
        assertTrue(set.get("element1"));
        assertEquals(1, set.size());
    }

    @Test
    public void testPut_existingElement_notAdded() {
        PowerSet set = new PowerSet();
        
        set.put("element1");
        set.put("element1");  // Попытка добавить дубликат
        
        assertEquals(1, set.size());
        assertTrue(set.get("element1"));
    }

    @Test
    public void testPut_multipleElements() {
        PowerSet set = new PowerSet();
        
        set.put("a");
        set.put("b");
        set.put("c");
        set.put("a");  // Дубликат
        
        assertEquals(3, set.size());
        assertTrue(set.get("a"));
        assertTrue(set.get("b"));
        assertTrue(set.get("c"));
    }

    // ==================== Тесты для get() ====================

    @Test
    public void testGet_existingElement() {
        PowerSet set = new PowerSet();
        set.put("test");
        
        assertTrue(set.get("test"));
    }

    @Test
    public void testGet_nonExistingElement() {
        PowerSet set = new PowerSet();
        set.put("test");
        
        assertFalse(set.get("notexist"));
    }

    @Test
    public void testGet_emptySet() {
        PowerSet set = new PowerSet();
        
        assertFalse(set.get("anything"));
    }

    // ==================== Тесты для remove() ====================

    @Test
    public void testRemove_existingElement() {
        PowerSet set = new PowerSet();
        set.put("element");
        
        assertTrue(set.remove("element"));
        assertEquals(0, set.size());
        assertFalse(set.get("element"));
    }

    @Test
    public void testRemove_nonExistingElement() {
        PowerSet set = new PowerSet();
        set.put("element");
        
        assertFalse(set.remove("notexist"));
        assertEquals(1, set.size());
    }

    @Test
    public void testRemove_emptySet() {
        PowerSet set = new PowerSet();
        
        assertFalse(set.remove("anything"));
    }

    // ==================== Тесты для intersection() ====================

    @Test
    public void testIntersection_nonEmpty() {
        PowerSet set1 = new PowerSet();
        set1.put("a");
        set1.put("b");
        set1.put("c");
        
        PowerSet set2 = new PowerSet();
        set2.put("b");
        set2.put("c");
        set2.put("d");
        
        PowerSet result = set1.intersection(set2);
        
        assertEquals(2, result.size());
        assertTrue(result.get("b"));
        assertTrue(result.get("c"));
        assertFalse(result.get("a"));
        assertFalse(result.get("d"));
    }

    @Test
    public void testIntersection_empty() {
        PowerSet set1 = new PowerSet();
        set1.put("a");
        set1.put("b");
        
        PowerSet set2 = new PowerSet();
        set2.put("c");
        set2.put("d");
        
        PowerSet result = set1.intersection(set2);
        
        assertEquals(0, result.size());
    }

    @Test
    public void testIntersection_withEmptySet() {
        PowerSet set1 = new PowerSet();
        set1.put("a");
        set1.put("b");
        
        PowerSet set2 = new PowerSet();
        
        PowerSet result = set1.intersection(set2);
        
        assertEquals(0, result.size());
    }

    @Test
    public void testIntersection_sameSets() {
        PowerSet set1 = new PowerSet();
        set1.put("a");
        set1.put("b");
        
        PowerSet set2 = new PowerSet();
        set2.put("a");
        set2.put("b");
        
        PowerSet result = set1.intersection(set2);
        
        assertEquals(2, result.size());
        assertTrue(result.get("a"));
        assertTrue(result.get("b"));
    }

    // ==================== Тесты для union() ====================

    @Test
    public void testUnion_nonEmpty() {
        PowerSet set1 = new PowerSet();
        set1.put("a");
        set1.put("b");
        
        PowerSet set2 = new PowerSet();
        set2.put("b");
        set2.put("c");
        
        PowerSet result = set1.union(set2);
        
        assertEquals(3, result.size());
        assertTrue(result.get("a"));
        assertTrue(result.get("b"));
        assertTrue(result.get("c"));
    }

    @Test
    public void testUnion_withEmptySet() {
        PowerSet set1 = new PowerSet();
        set1.put("a");
        set1.put("b");
        
        PowerSet set2 = new PowerSet();
        
        PowerSet result = set1.union(set2);
        
        assertEquals(2, result.size());
        assertTrue(result.get("a"));
        assertTrue(result.get("b"));
    }

    @Test
    public void testUnion_bothEmpty() {
        PowerSet set1 = new PowerSet();
        PowerSet set2 = new PowerSet();
        
        PowerSet result = set1.union(set2);
        
        assertEquals(0, result.size());
    }

    // ==================== Тесты для difference() ====================

    @Test
    public void testDifference_nonEmpty() {
        PowerSet set1 = new PowerSet();
        set1.put("a");
        set1.put("b");
        set1.put("c");
        
        PowerSet set2 = new PowerSet();
        set2.put("b");
        set2.put("d");
        
        PowerSet result = set1.difference(set2);
        
        assertEquals(2, result.size());
        assertTrue(result.get("a"));
        assertFalse(result.get("b"));
        assertTrue(result.get("c"));
    }

    @Test
    public void testDifference_empty() {
        PowerSet set1 = new PowerSet();
        set1.put("a");
        set1.put("b");
        
        PowerSet set2 = new PowerSet();
        set2.put("a");
        set2.put("b");
        
        PowerSet result = set1.difference(set2);
        
        assertEquals(0, result.size());
    }

    @Test
    public void testDifference_withEmptySet() {
        PowerSet set1 = new PowerSet();
        set1.put("a");
        set1.put("b");
        
        PowerSet set2 = new PowerSet();
        
        PowerSet result = set1.difference(set2);
        
        assertEquals(2, result.size());
        assertTrue(result.get("a"));
        assertTrue(result.get("b"));
    }

    // ==================== Тесты для isSubset() ====================

    @Test
    public void testIsSubset_allElementsInCurrent() {
        PowerSet set1 = new PowerSet();
        set1.put("a");
        set1.put("b");
        set1.put("c");
        
        PowerSet set2 = new PowerSet();
        set2.put("a");
        set2.put("b");
        
        assertTrue(set1.isSubset(set2));
    }

    @Test
    public void testIsSubset_allCurrentInParameter() {
        PowerSet set1 = new PowerSet();
        set1.put("a");
        set1.put("b");
        
        PowerSet set2 = new PowerSet();
        set2.put("a");
        set2.put("b");
        set2.put("c");
        
        assertTrue(set2.isSubset(set1));  // set1 является подмножеством set2
    }

    @Test
    public void testIsSubset_notAllElementsInCurrent() {
        PowerSet set1 = new PowerSet();
        set1.put("a");
        set1.put("b");
        
        PowerSet set2 = new PowerSet();
        set2.put("a");
        set2.put("c");
        
        assertFalse(set1.isSubset(set2));
    }

    @Test
    public void testIsSubset_emptySetIsSubset() {
        PowerSet set1 = new PowerSet();
        set1.put("a");
        set1.put("b");
        
        PowerSet set2 = new PowerSet();
        
        assertTrue(set1.isSubset(set2));  // Пустое множество - подмножество любого
    }

    @Test
    public void testIsSubset_sameSets() {
        PowerSet set1 = new PowerSet();
        set1.put("a");
        set1.put("b");
        
        PowerSet set2 = new PowerSet();
        set2.put("a");
        set2.put("b");
        
        assertTrue(set1.isSubset(set2));
        assertTrue(set2.isSubset(set1));
    }

    // ==================== Тесты для equals() ====================

    @Test
    public void testEquals_sameElements() {
        PowerSet set1 = new PowerSet();
        set1.put("a");
        set1.put("b");
        set1.put("c");
        
        PowerSet set2 = new PowerSet();
        set2.put("c");
        set2.put("b");
        set2.put("a");  // Порядок не важен
        
        assertTrue(set1.equals(set2));
    }

    @Test
    public void testEquals_differentSizes() {
        PowerSet set1 = new PowerSet();
        set1.put("a");
        set1.put("b");
        
        PowerSet set2 = new PowerSet();
        set2.put("a");
        
        assertFalse(set1.equals(set2));
    }

    @Test
    public void testEquals_differentElements() {
        PowerSet set1 = new PowerSet();
        set1.put("a");
        set1.put("b");
        
        PowerSet set2 = new PowerSet();
        set2.put("c");
        set2.put("d");
        
        assertFalse(set1.equals(set2));
    }

    @Test
    public void testEquals_bothEmpty() {
        PowerSet set1 = new PowerSet();
        PowerSet set2 = new PowerSet();
        
        assertTrue(set1.equals(set2));
    }

    // ==================== Тесты для быстродействия ====================

    @Test
    public void testPerformance_largeSets() {
        PowerSet set1 = new PowerSet();
        PowerSet set2 = new PowerSet();
        
        // Добавляем 10,000 элементов в каждое множество
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < 10000; i++) {
            set1.put("key" + i);
            set2.put("key" + (i + 5000));  // Частичное пересечение
        }
        
        long insertTime = System.currentTimeMillis() - startTime;
        
        assertEquals(10000, set1.size());
        assertEquals(10000, set2.size());
        
        // Тестируем операции
        startTime = System.currentTimeMillis();
        
        PowerSet intersection = set1.intersection(set2);
        PowerSet union = set1.union(set2);
        PowerSet difference = set1.difference(set2);
        boolean isSubset = set1.isSubset(set2);
        boolean equals = set1.equals(set2);
        
        long operationsTime = System.currentTimeMillis() - startTime;
        
        System.out.println("Insert time for 20,000 elements: " + insertTime + " ms");
        System.out.println("Operations time: " + operationsTime + " ms");
        
        // Все операции должны выполниться за разумное время (< 2 секунд)
        assertTrue(insertTime < 5000, "Insert should complete in reasonable time");
        assertTrue(operationsTime < 5000, "Operations should complete in reasonable time");
        
        // Проверка результатов
        assertEquals(5000, intersection.size());  // key5000..key9999
        assertTrue(union.size() > 10000);  // key0..key14999
        assertEquals(5000, difference.size());  // key0..key4999
        assertFalse(isSubset);
        assertFalse(equals);
    }

    @Test
    public void testPerformance_contains() {
        PowerSet set = new PowerSet();
        
        // Добавляем 20,000 элементов
        for (int i = 0; i < 20000; i++) {
            set.put("key" + i);
        }
        
        // Тестируем поиск
        long startTime = System.currentTimeMillis();
        
        for (int i = 0; i < 1000; i++) {
            set.get("key" + i);
            set.get("notexist" + i);
        }
        
        long searchTime = System.currentTimeMillis() - startTime;
        
        System.out.println("Search time for 2,000 lookups in 20,000 element set: " + searchTime + " ms");
        
        assertTrue(searchTime < 5000, "Search should complete in reasonable time");
    }

    // ==================== Тест для комплексного сценария ====================

    @Test
    public void testPowerSet_fullWorkflow() {
        PowerSet set1 = new PowerSet();
        set1.put("apple");
        set1.put("banana");
        set1.put("cherry");
        
        PowerSet set2 = new PowerSet();
        set2.put("banana");
        set2.put("cherry");
        set2.put("date");
        
        // Проверка наличия
        assertTrue(set1.get("apple"));
        assertTrue(set1.get("banana"));
        assertFalse(set1.get("date"));
        
        // Пересечение
        PowerSet intersection = set1.intersection(set2);
        assertEquals(2, intersection.size());
        assertTrue(intersection.get("banana"));
        assertTrue(intersection.get("cherry"));
        
        // Объединение
        PowerSet union = set1.union(set2);
        assertEquals(4, union.size());
        assertTrue(union.get("apple"));
        assertTrue(union.get("banana"));
        assertTrue(union.get("cherry"));
        assertTrue(union.get("date"));
        
        // Разность
        PowerSet difference = set1.difference(set2);
        assertEquals(1, difference.size());
        assertTrue(difference.get("apple"));
        
        // Подмножество
        assertFalse(set1.isSubset(set2));
        assertFalse(set2.isSubset(set1));
        
        PowerSet set3 = new PowerSet();
        set3.put("banana");
        assertTrue(set1.isSubset(set3));
        
        // Равенство
        assertFalse(set1.equals(set2));
        
        set2.remove("date");
        set2.put("apple");
        assertTrue(set1.equals(set2));
        
        // Удаление
        assertTrue(set1.remove("apple"));
        assertEquals(2, set1.size());
        assertFalse(set1.get("apple"));
    }

    // ==================== Тесты для cartesianProduct() ====================

    @Test
    public void testCartesianProduct_basic() {
        PowerSet set1 = new PowerSet();
        set1.put("1");
        set1.put("2");
        set1.put("3");

        PowerSet set2 = new PowerSet();
        set2.put("3");
        set2.put("5");

        PowerSet result = set1.cartesianProduct(set2);

        // Ожидаем 3 * 2 = 6 пар
        assertEquals(6, result.size());

        // Проверяем наличие всех пар
        assertTrue(result.get("(1; 3)"));
        assertTrue(result.get("(1; 5)"));
        assertTrue(result.get("(2; 3)"));
        assertTrue(result.get("(2; 5)"));
        assertTrue(result.get("(3; 3)"));
        assertTrue(result.get("(3; 5)"));
    }

    @Test
    public void testCartesianProduct_withEmptySet() {
        PowerSet set1 = new PowerSet();
        set1.put("a");
        set1.put("b");

        PowerSet set2 = new PowerSet();

        PowerSet result1 = set1.cartesianProduct(set2);
        PowerSet result2 = set2.cartesianProduct(set1);

        // Декартово произведение с пустым множеством = пустое множество
        assertEquals(0, result1.size());
        assertEquals(0, result2.size());
    }

    @Test
    public void testCartesianProduct_sameSets() {
        PowerSet set = new PowerSet();
        set.put("a");
        set.put("b");

        PowerSet result = set.cartesianProduct(set);

        // Ожидаем 2 * 2 = 4 пары
        assertEquals(4, result.size());
        assertTrue(result.get("(a; a)"));
        assertTrue(result.get("(a; b)"));
        assertTrue(result.get("(b; a)"));
        assertTrue(result.get("(b; b)"));
    }

    @Test
    public void testCartesianProduct_orderMatters() {
        PowerSet set1 = new PowerSet();
        set1.put("1");
        set1.put("2");

        PowerSet set2 = new PowerSet();
        set2.put("a");
        set2.put("b");

        PowerSet result1 = set1.cartesianProduct(set2);
        PowerSet result2 = set2.cartesianProduct(set1);

        // Декартово произведение не коммутативно
        assertEquals(4, result1.size());
        assertEquals(4, result2.size());

        // Пары разные
        assertTrue(result1.get("(1; a)"));
        assertTrue(result2.get("(a; 1)"));
        assertFalse(result1.get("(a; 1)"));
        assertFalse(result2.get("(1; a)"));
    }

    @Test
    public void testCartesianProduct_singleElements() {
        PowerSet set1 = new PowerSet();
        set1.put("x");

        PowerSet set2 = new PowerSet();
        set2.put("y");

        PowerSet result = set1.cartesianProduct(set2);

        assertEquals(1, result.size());
        assertTrue(result.get("(x; y)"));
    }

    @Test
    public void testCartesianProduct_performance() {
        PowerSet set1 = new PowerSet();
        PowerSet set2 = new PowerSet();

        // Добавляем по 100 элементов в каждое множество
        for (int i = 0; i < 100; i++) {
            set1.put("a" + i);
            set2.put("b" + i);
        }

        long startTime = System.currentTimeMillis();
        PowerSet result = set1.cartesianProduct(set2);
        long cartesianTime = System.currentTimeMillis() - startTime;

        // Ожидаем 100 * 100 = 10,000 пар
        assertEquals(10000, result.size());

        System.out.println("Cartesian product time for 100x100 elements: " + cartesianTime + " ms");

        // Должно выполниться за разумное время
        assertTrue(cartesianTime < 5000, "Cartesian product should complete in reasonable time");

        // Проверяем наличие некоторых пар
        assertTrue(result.get("(a0; b0)"));
        assertTrue(result.get("(a50; b50)"));
        assertTrue(result.get("(a99; b99)"));
    }
}
