import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BagTest {

    // ==================== Тесты для add() ====================

    @Test
    public void testAdd_singleElement() {
        Bag bag = new Bag();
        
        bag.add("apple");
        
        assertTrue(bag.contains("apple"));
        assertEquals(1, bag.getCount("apple"));
        assertEquals(1, bag.size());
        assertEquals(1, bag.uniqueCount());
    }

    @Test
    public void testAdd_multipleSameElements() {
        Bag bag = new Bag();
        
        bag.add("apple");
        bag.add("apple");
        bag.add("apple");
        
        assertTrue(bag.contains("apple"));
        assertEquals(3, bag.getCount("apple"));
        assertEquals(3, bag.size());
        assertEquals(1, bag.uniqueCount());
    }

    @Test
    public void testAdd_differentElements() {
        Bag bag = new Bag();
        
        bag.add("apple");
        bag.add("banana");
        bag.add("apple");
        bag.add("cherry");
        bag.add("banana");
        bag.add("apple");
        
        assertEquals(3, bag.getCount("apple"));
        assertEquals(2, bag.getCount("banana"));
        assertEquals(1, bag.getCount("cherry"));
        assertEquals(6, bag.size());
        assertEquals(3, bag.uniqueCount());
    }

    @Test
    public void testAdd_nullValue() {
        Bag bag = new Bag();
        
        bag.add(null);
        
        assertEquals(0, bag.size());
        assertFalse(bag.contains(null));
    }

    // ==================== Тесты для addMultiple() ====================

    @Test
    public void testAddMultiple() {
        Bag bag = new Bag();
        
        bag.addMultiple("apple", 5);
        
        assertEquals(5, bag.getCount("apple"));
        assertEquals(5, bag.size());
        assertEquals(1, bag.uniqueCount());
    }

    @Test
    public void testAddMultiple_invalidCount() {
        Bag bag = new Bag();
        
        bag.addMultiple("apple", 0);
        bag.addMultiple("banana", -5);
        
        assertEquals(0, bag.size());
    }

    // ==================== Тесты для remove() ====================

    @Test
    public void testRemove_existingElement() {
        Bag bag = new Bag();
        bag.add("apple");
        bag.add("apple");
        bag.add("apple");
        
        assertTrue(bag.remove("apple"));
        assertEquals(2, bag.getCount("apple"));
        assertEquals(2, bag.size());
    }

    @Test
    public void testRemove_lastElement() {
        Bag bag = new Bag();
        bag.add("apple");
        
        assertTrue(bag.remove("apple"));
        assertEquals(0, bag.getCount("apple"));
        assertFalse(bag.contains("apple"));
        assertEquals(0, bag.size());
    }

    @Test
    public void testRemove_nonExistingElement() {
        Bag bag = new Bag();
        bag.add("apple");
        
        assertFalse(bag.remove("banana"));
        assertEquals(1, bag.size());
    }

    @Test
    public void testRemove_nullValue() {
        Bag bag = new Bag();
        bag.add("apple");
        
        assertFalse(bag.remove(null));
    }

    // ==================== Тесты для removeMultiple() ====================

    @Test
    public void testRemoveMultiple() {
        Bag bag = new Bag();
        bag.addMultiple("apple", 10);
        
        int removed = bag.removeMultiple("apple", 3);
        
        assertEquals(3, removed);
        assertEquals(7, bag.getCount("apple"));
        assertEquals(7, bag.size());
    }

    @Test
    public void testRemoveMultiple_moreThanAvailable() {
        Bag bag = new Bag();
        bag.addMultiple("apple", 5);
        
        int removed = bag.removeMultiple("apple", 10);
        
        assertEquals(5, removed);  // Удалили только то, что было
        assertEquals(0, bag.getCount("apple"));
        assertEquals(0, bag.size());
    }

    @Test
    public void testRemoveMultiple_nonExistingElement() {
        Bag bag = new Bag();
        bag.add("apple");
        
        int removed = bag.removeMultiple("banana", 5);
        
        assertEquals(0, removed);
        assertEquals(1, bag.size());
    }

    // ==================== Тесты для getCount() и contains() ====================

    @Test
    public void testGetCount() {
        Bag bag = new Bag();
        bag.add("apple");
        bag.add("apple");
        
        assertEquals(2, bag.getCount("apple"));
        assertEquals(0, bag.getCount("banana"));
        assertEquals(0, bag.getCount(null));
    }

    @Test
    public void testContains() {
        Bag bag = new Bag();
        bag.add("apple");
        
        assertTrue(bag.contains("apple"));
        assertFalse(bag.contains("banana"));
    }

    // ==================== Тесты для size() и uniqueCount() ====================

    @Test
    public void testSizeAndUniqueCount() {
        Bag bag = new Bag();
        
        assertEquals(0, bag.size());
        assertEquals(0, bag.uniqueCount());
        assertTrue(bag.isEmpty());
        
        bag.add("a");
        bag.add("a");
        bag.add("b");
        bag.add("b");
        bag.add("b");
        
        assertEquals(5, bag.size());       // Всего элементов
        assertEquals(2, bag.uniqueCount()); // Уникальных: "a", "b"
        assertFalse(bag.isEmpty());
    }

    // ==================== Тесты для getAllWithFrequencies() ====================

    @Test
    public void testGetAllWithFrequencies() {
        Bag bag = new Bag();
        bag.add("apple");
        bag.add("apple");
        bag.add("banana");
        bag.add("banana");
        bag.add("banana");
        bag.add("cherry");
        
        List<BagElement> elements = bag.getAllWithFrequencies();
        
        assertEquals(3, elements.size());
        
        // Проверяем наличие всех элементов с правильными частотами
        boolean foundApple = false, foundBanana = false, foundCherry = false;
        
        for (BagElement elem : elements) {
            if (elem.value.equals("apple")) {
                assertEquals(2, elem.count);
                foundApple = true;
            } else if (elem.value.equals("banana")) {
                assertEquals(3, elem.count);
                foundBanana = true;
            } else if (elem.value.equals("cherry")) {
                assertEquals(1, elem.count);
                foundCherry = true;
            }
        }
        
        assertTrue(foundApple);
        assertTrue(foundBanana);
        assertTrue(foundCherry);
    }

    @Test
    public void testGetAllWithFrequencies_emptyBag() {
        Bag bag = new Bag();
        
        List<BagElement> elements = bag.getAllWithFrequencies();
        
        assertEquals(0, elements.size());
    }

    // ==================== Тесты для getAllElements() ====================

    @Test
    public void testGetAllElements() {
        Bag bag = new Bag();
        bag.add("a");
        bag.add("a");
        bag.add("b");
        
        List<String> elements = bag.getAllElements();
        
        assertEquals(3, elements.size());
        assertTrue(elements.contains("a"));
        assertTrue(elements.contains("b"));
    }

    // ==================== Тесты для getUniqueElements() ====================

    @Test
    public void testGetUniqueElements() {
        Bag bag = new Bag();
        bag.add("a");
        bag.add("a");
        bag.add("b");
        bag.add("b");
        bag.add("b");
        
        List<String> unique = bag.getUniqueElements();
        
        assertEquals(2, unique.size());
        assertTrue(unique.contains("a"));
        assertTrue(unique.contains("b"));
    }

    // ==================== Тесты для clear() и isEmpty() ====================

    @Test
    public void testClear() {
        Bag bag = new Bag();
        bag.add("a");
        bag.add("b");
        bag.add("c");
        
        bag.clear();
        
        assertEquals(0, bag.size());
        assertEquals(0, bag.uniqueCount());
        assertTrue(bag.isEmpty());
        assertFalse(bag.contains("a"));
    }

    @Test
    public void testIsEmpty() {
        Bag bag = new Bag();
        
        assertTrue(bag.isEmpty());
        
        bag.add("a");
        assertFalse(bag.isEmpty());
        
        bag.remove("a");
        assertTrue(bag.isEmpty());
    }

    // ==================== Тесты для toPowerSet() ====================

    @Test
    public void testToPowerSet() {
        Bag bag = new Bag();
        bag.add("apple");
        bag.add("apple");
        bag.add("banana");
        bag.add("cherry");
        bag.add("cherry");
        bag.add("cherry");
        
        PowerSet set = bag.toPowerSet();
        
        assertEquals(3, set.size());
        assertTrue(set.get("apple"));
        assertTrue(set.get("banana"));
        assertTrue(set.get("cherry"));
    }

    // ==================== Тесты для быстродействия ====================

    @Test
    public void testPerformance_largeBag() {
        Bag bag = new Bag();
        
        long startTime = System.currentTimeMillis();
        
        // Добавляем 10,000 элементов
        for (int i = 0; i < 10000; i++) {
            bag.add("item" + (i % 100));  // 100 уникальных элементов
        }
        
        long addTime = System.currentTimeMillis() - startTime;
        
        assertEquals(10000, bag.size());
        assertEquals(100, bag.uniqueCount());
        
        startTime = System.currentTimeMillis();
        
        // Получаем все элементы с частотами
        List<BagElement> elements = bag.getAllWithFrequencies();
        
        long getAllTime = System.currentTimeMillis() - startTime;
        
        System.out.println("Add 10,000 elements: " + addTime + " ms");
        System.out.println("Get all with frequencies: " + getAllTime + " ms");
        
        assertTrue(addTime < 1000, "Add should complete in reasonable time");
        assertTrue(getAllTime < 1000, "GetAll should complete in reasonable time");
    }

    @Test
    public void testPerformance_removeOperations() {
        Bag bag = new Bag();
        
        // Добавляем 5,000 элементов
        for (int i = 0; i < 5000; i++) {
            bag.add("item" + (i % 50));
        }
        
        long startTime = System.currentTimeMillis();
        
        // Удаляем 1,000 элементов
        for (int i = 0; i < 1000; i++) {
            bag.remove("item" + (i % 50));
        }
        
        long removeTime = System.currentTimeMillis() - startTime;
        
        assertEquals(4000, bag.size());
        
        System.out.println("Remove 1,000 elements: " + removeTime + " ms");
        
        assertTrue(removeTime < 500, "Remove should complete in reasonable time");
    }

    // ==================== Комплексный тест ====================

    @Test
    public void testBag_fullWorkflow() {
        Bag bag = new Bag();
        
        // Добавление элементов
        bag.add("red");
        bag.add("red");
        bag.add("red");
        bag.add("green");
        bag.add("green");
        bag.add("blue");
        
        assertEquals(6, bag.size());
        assertEquals(3, bag.uniqueCount());
        assertEquals(3, bag.getCount("red"));
        assertEquals(2, bag.getCount("green"));
        assertEquals(1, bag.getCount("blue"));
        
        // Удаление одного экземпляра
        assertTrue(bag.remove("red"));
        assertEquals(5, bag.size());
        assertEquals(2, bag.getCount("red"));
        
        // Удаление нескольких экземпляров
        int removed = bag.removeMultiple("green", 5);
        assertEquals(2, removed);  // Удалили только 2, т.к. было 2
        assertEquals(3, bag.size());
        assertEquals(0, bag.getCount("green"));
        assertFalse(bag.contains("green"));
        
        // Получение элементов с частотами
        List<BagElement> elements = bag.getAllWithFrequencies();
        assertEquals(2, elements.size());  // "red" и "blue"
        
        // Преобразование в множество
        PowerSet set = bag.toPowerSet();
        assertEquals(2, set.size());
        assertTrue(set.get("red"));
        assertTrue(set.get("blue"));
        assertFalse(set.get("green"));
        
        // Очистка
        bag.clear();
        assertEquals(0, bag.size());
        assertTrue(bag.isEmpty());
    }
}
