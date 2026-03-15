import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class OrderedListTest {

    @Test
    public void testAdd_emptyList_ascending() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(5);
        
        assertEquals(1, list.count());
        assertEquals(5, list.head.value);
        assertEquals(5, list.tail.value);
    }

    @Test
    public void testAdd_emptyList_descending() {
        OrderedList<Integer> list = new OrderedList<>(false);
        list.add(5);
        
        assertEquals(1, list.count());
        assertEquals(5, list.head.value);
        assertEquals(5, list.tail.value);
    }

    @Test
    public void testAdd_ascending_inOrder() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(2);
        list.add(3);
        
        assertEquals(3, list.count());
        assertEquals(1, list.head.value);
        assertEquals(3, list.tail.value);
        
        ArrayList<Node2<Integer>> all = list.getAll();
        assertEquals(1, all.get(0).value);
        assertEquals(2, all.get(1).value);
        assertEquals(3, all.get(2).value);
    }

    @Test
    public void testAdd_ascending_reverseOrder() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(3);
        list.add(2);
        list.add(1);
        
        assertEquals(3, list.count());
        assertEquals(1, list.head.value);
        assertEquals(3, list.tail.value);
        
        ArrayList<Node2<Integer>> all = list.getAll();
        assertEquals(1, all.get(0).value);
        assertEquals(2, all.get(1).value);
        assertEquals(3, all.get(2).value);
    }

    @Test
    public void testAdd_ascending_mixedOrder() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(5);
        list.add(1);
        list.add(9);
        list.add(3);
        list.add(7);
        
        assertEquals(5, list.count());
        
        ArrayList<Node2<Integer>> all = list.getAll();
        assertEquals(1, all.get(0).value);
        assertEquals(3, all.get(1).value);
        assertEquals(5, all.get(2).value);
        assertEquals(7, all.get(3).value);
        assertEquals(9, all.get(4).value);
    }

    @Test
    public void testAdd_descending_inOrder() {
        OrderedList<Integer> list = new OrderedList<>(false);
        list.add(3);
        list.add(2);
        list.add(1);
        
        assertEquals(3, list.count());
        assertEquals(3, list.head.value);
        assertEquals(1, list.tail.value);
        
        ArrayList<Node2<Integer>> all = list.getAll();
        assertEquals(3, all.get(0).value);
        assertEquals(2, all.get(1).value);
        assertEquals(1, all.get(2).value);
    }

    @Test
    public void testAdd_descending_reverseOrder() {
        OrderedList<Integer> list = new OrderedList<>(false);
        list.add(1);
        list.add(2);
        list.add(3);
        
        assertEquals(3, list.count());
        assertEquals(3, list.head.value);
        assertEquals(1, list.tail.value);
        
        ArrayList<Node2<Integer>> all = list.getAll();
        assertEquals(3, all.get(0).value);
        assertEquals(2, all.get(1).value);
        assertEquals(1, all.get(2).value);
    }

    @Test
    public void testAdd_descending_mixedOrder() {
        OrderedList<Integer> list = new OrderedList<>(false);
        list.add(5);
        list.add(1);
        list.add(9);
        list.add(3);
        list.add(7);
        
        assertEquals(5, list.count());
        
        ArrayList<Node2<Integer>> all = list.getAll();
        assertEquals(9, all.get(0).value);
        assertEquals(7, all.get(1).value);
        assertEquals(5, all.get(2).value);
        assertEquals(3, all.get(3).value);
        assertEquals(1, all.get(4).value);
    }

    @Test
    public void testAdd_duplicates_ascending() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(5);
        list.add(3);
        list.add(5);
        list.add(5);
        list.add(3);
        
        assertEquals(5, list.count());
        
        ArrayList<Node2<Integer>> all = list.getAll();
        assertEquals(3, all.get(0).value);
        assertEquals(3, all.get(1).value);
        assertEquals(5, all.get(2).value);
        assertEquals(5, all.get(3).value);
        assertEquals(5, all.get(4).value);
    }

    @Test
    public void testAdd_duplicates_descending() {
        OrderedList<Integer> list = new OrderedList<>(false);
        list.add(5);
        list.add(3);
        list.add(5);
        list.add(5);
        list.add(3);
        
        assertEquals(5, list.count());
        
        ArrayList<Node2<Integer>> all = list.getAll();
        assertEquals(5, all.get(0).value);
        assertEquals(5, all.get(1).value);
        assertEquals(5, all.get(2).value);
        assertEquals(3, all.get(3).value);
        assertEquals(3, all.get(4).value);
    }

    @Test
    public void testAdd_strings_ascending() {
        OrderedList<String> list = new OrderedList<>(true);
        list.add("banana");
        list.add("apple");
        list.add("cherry");
        
        ArrayList<Node2<String>> all = list.getAll();
        assertEquals("apple", all.get(0).value);
        assertEquals("banana", all.get(1).value);
        assertEquals("cherry", all.get(2).value);
    }

    @Test
    public void testAdd_stringsWithSpaces_ascending() {
        OrderedList<String> list = new OrderedList<>(true);
        list.add("  banana  ");
        list.add("apple");
        list.add(" cherry");
        
        ArrayList<Node2<String>> all = list.getAll();
        assertEquals("apple", all.get(0).value);
        assertEquals("  banana  ", all.get(1).value);
        assertEquals(" cherry", all.get(2).value);
    }

    @Test
    public void testAdd_doubles_ascending() {
        OrderedList<Double> list = new OrderedList<>(true);
        list.add(3.14);
        list.add(2.71);
        list.add(1.41);
        
        ArrayList<Node2<Double>> all = list.getAll();
        assertEquals(1.41, all.get(0).value);
        assertEquals(2.71, all.get(1).value);
        assertEquals(3.14, all.get(2).value);
    }

    // ==================== Тесты для delete() ====================

    @Test
    public void testDelete_emptyList() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.delete(5);
        
        assertEquals(0, list.count());
        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testDelete_notFound_ascending() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(3);
        list.add(5);
        
        list.delete(4);
        
        assertEquals(3, list.count());
        assertEquals(1, list.head.value);
        assertEquals(5, list.tail.value);
    }

    @Test
    public void testDelete_notFound_descending() {
        OrderedList<Integer> list = new OrderedList<>(false);
        list.add(5);
        list.add(3);
        list.add(1);
        
        list.delete(4);
        
        assertEquals(3, list.count());
        assertEquals(5, list.head.value);
        assertEquals(1, list.tail.value);
    }

    @Test
    public void testDelete_head_ascending() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(3);
        list.add(5);
        
        list.delete(1);
        
        assertEquals(2, list.count());
        assertEquals(3, list.head.value);
        assertEquals(5, list.tail.value);
    }

    @Test
    public void testDelete_head_descending() {
        OrderedList<Integer> list = new OrderedList<>(false);
        list.add(5);
        list.add(3);
        list.add(1);
        
        list.delete(5);
        
        assertEquals(2, list.count());
        assertEquals(3, list.head.value);
        assertEquals(1, list.tail.value);
    }

    @Test
    public void testDelete_tail_ascending() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(3);
        list.add(5);
        
        list.delete(5);
        
        assertEquals(2, list.count());
        assertEquals(1, list.head.value);
        assertEquals(3, list.tail.value);
    }

    @Test
    public void testDelete_tail_descending() {
        OrderedList<Integer> list = new OrderedList<>(false);
        list.add(5);
        list.add(3);
        list.add(1);
        
        list.delete(1);
        
        assertEquals(2, list.count());
        assertEquals(5, list.head.value);
        assertEquals(3, list.tail.value);
    }

    @Test
    public void testDelete_middle_ascending() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(3);
        list.add(5);
        list.add(7);
        
        list.delete(3);
        
        assertEquals(3, list.count());
        assertEquals(1, list.head.value);
        assertEquals(7, list.tail.value);
        
        ArrayList<Node2<Integer>> all = list.getAll();
        assertEquals(1, all.get(0).value);
        assertEquals(5, all.get(1).value);
        assertEquals(7, all.get(2).value);
    }

    @Test
    public void testDelete_middle_descending() {
        OrderedList<Integer> list = new OrderedList<>(false);
        list.add(7);
        list.add(5);
        list.add(3);
        list.add(1);
        
        list.delete(5);
        
        assertEquals(3, list.count());
        assertEquals(7, list.head.value);
        assertEquals(1, list.tail.value);
        
        ArrayList<Node2<Integer>> all = list.getAll();
        assertEquals(7, all.get(0).value);
        assertEquals(3, all.get(1).value);
        assertEquals(1, all.get(2).value);
    }

    @Test
    public void testDelete_firstDuplicate_ascending() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(5);
        list.add(3);
        list.add(5);
        list.add(5);
        
        list.delete(5);
        
        assertEquals(3, list.count());
        
        ArrayList<Node2<Integer>> all = list.getAll();
        assertEquals(3, all.get(0).value);
        assertEquals(5, all.get(1).value);
        assertEquals(5, all.get(2).value);
    }

    @Test
    public void testDelete_firstDuplicate_descending() {
        OrderedList<Integer> list = new OrderedList<>(false);
        list.add(5);
        list.add(3);
        list.add(5);
        list.add(5);
        
        list.delete(5);
        
        assertEquals(3, list.count());
        
        ArrayList<Node2<Integer>> all = list.getAll();
        assertEquals(5, all.get(0).value);
        assertEquals(5, all.get(1).value);
        assertEquals(3, all.get(2).value);
    }

    @Test
    public void testDelete_allElements_ascending() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(2);
        list.add(3);
        
        list.delete(2);
        list.delete(1);
        list.delete(3);
        
        assertEquals(0, list.count());
        assertNull(list.head);
        assertNull(list.tail);
    }

    // ==================== Тесты для find() ====================

    @Test
    public void testFind_emptyList() {
        OrderedList<Integer> list = new OrderedList<>(true);
        Node2<Integer> found = list.find(5);
        
        assertNull(found);
    }

    @Test
    public void testFind_found_ascending() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(3);
        list.add(5);
        list.add(7);
        
        Node2<Integer> found = list.find(5);
        
        assertNotNull(found);
        assertEquals(5, found.value);
    }

    @Test
    public void testFind_found_descending() {
        OrderedList<Integer> list = new OrderedList<>(false);
        list.add(7);
        list.add(5);
        list.add(3);
        list.add(1);
        
        Node2<Integer> found = list.find(5);
        
        assertNotNull(found);
        assertEquals(5, found.value);
    }

    @Test
    public void testFind_notFound_greater_ascending() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(3);
        list.add(5);
        
        Node2<Integer> found = list.find(10);
        
        assertNull(found);
    }

    @Test
    public void testFind_notFound_greater_descending() {
        OrderedList<Integer> list = new OrderedList<>(false);
        list.add(5);
        list.add(3);
        list.add(1);
        
        Node2<Integer> found = list.find(10);
        
        assertNull(found);
    }

    @Test
    public void testFind_notFound_between_ascending() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(5);
        list.add(9);
        
        Node2<Integer> found = list.find(3);
        
        assertNull(found);
    }

    @Test
    public void testFind_notFound_between_descending() {
        OrderedList<Integer> list = new OrderedList<>(false);
        list.add(9);
        list.add(5);
        list.add(1);
        
        Node2<Integer> found = list.find(3);
        
        assertNull(found);
    }

    @Test
    public void testFind_firstElement_ascending() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(3);
        list.add(5);
        
        Node2<Integer> found = list.find(1);
        
        assertNotNull(found);
        assertEquals(1, found.value);
    }

    @Test
    public void testFind_lastElement_ascending() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(3);
        list.add(5);
        
        Node2<Integer> found = list.find(5);
        
        assertNotNull(found);
        assertEquals(5, found.value);
    }

    @Test
    public void testFind_duplicate_ascending() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(3);
        list.add(5);
        list.add(5);
        list.add(5);
        list.add(7);
        
        Node2<Integer> found = list.find(5);
        
        assertNotNull(found);
        assertEquals(5, found.value);
    }

    @Test
    public void testFind_strings_ascending() {
        OrderedList<String> list = new OrderedList<>(true);
        list.add("banana");
        list.add("apple");
        list.add("cherry");
        
        Node2<String> found = list.find("banana");
        
        assertNotNull(found);
        assertEquals("banana", found.value);
    }

    @Test
    public void testFind_stringsWithSpaces_ascending() {
        OrderedList<String> list = new OrderedList<>(true);
        list.add("  banana  ");
        list.add("apple");
        list.add(" cherry");
        
        // Поиск с trim
        Node2<String> found = list.find("banana");
        
        assertNotNull(found);
        assertEquals("  banana  ", found.value);
    }

    @Test
    public void testFind_earlyTermination_ascending() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(3);
        list.add(5);
        list.add(7);
        list.add(9);

        // Поиск 4 должен прерваться на 5 (ранее прерывание)
        Node2<Integer> found = list.find(4);

        assertNull(found);
    }

    @Test
    public void testFind_earlyTermination_descending() {
        OrderedList<Integer> list = new OrderedList<>(false);
        list.add(9);
        list.add(7);
        list.add(5);
        list.add(3);
        list.add(1);

        // Поиск 6 должен прерваться на 5 (ранее прерывание)
        Node2<Integer> found = list.find(6);

        assertNull(found);
    }

    // ==================== Тесты для removeAllDuplicates() ====================

    @Test
    public void testRemoveAllDuplicates_emptyList() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.removeAllDuplicates();

        assertEquals(0, list.count());
        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testRemoveAllDuplicates_singleElement() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(5);

        list.removeAllDuplicates();

        assertEquals(1, list.count());
        assertEquals(5, list.head.value);
        assertEquals(5, list.tail.value);
    }

    @Test
    public void testRemoveAllDuplicates_noDuplicates_ascending() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        list.removeAllDuplicates();

        assertEquals(5, list.count());

        ArrayList<Node2<Integer>> all = list.getAll();
        assertEquals(1, all.get(0).value);
        assertEquals(2, all.get(1).value);
        assertEquals(3, all.get(2).value);
        assertEquals(4, all.get(3).value);
        assertEquals(5, all.get(4).value);
    }

    @Test
    public void testRemoveAllDuplicates_noDuplicates_descending() {
        OrderedList<Integer> list = new OrderedList<>(false);
        list.add(5);
        list.add(4);
        list.add(3);
        list.add(2);
        list.add(1);

        list.removeAllDuplicates();

        assertEquals(5, list.count());

        ArrayList<Node2<Integer>> all = list.getAll();
        assertEquals(5, all.get(0).value);
        assertEquals(4, all.get(1).value);
        assertEquals(3, all.get(2).value);
        assertEquals(2, all.get(3).value);
        assertEquals(1, all.get(4).value);
    }

    @Test
    public void testRemoveAllDuplicates_allDuplicates_ascending() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(5);
        list.add(5);
        list.add(5);
        list.add(5);

        list.removeAllDuplicates();

        assertEquals(1, list.count());
        assertEquals(5, list.head.value);
        assertEquals(5, list.tail.value);
    }

    @Test
    public void testRemoveAllDuplicates_allDuplicates_descending() {
        OrderedList<Integer> list = new OrderedList<>(false);
        list.add(5);
        list.add(5);
        list.add(5);
        list.add(5);

        list.removeAllDuplicates();

        assertEquals(1, list.count());
        assertEquals(5, list.head.value);
        assertEquals(5, list.tail.value);
    }

    @Test
    public void testRemoveAllDuplicates_someDuplicates_ascending() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(3);
        list.add(3);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(5);

        list.removeAllDuplicates();

        assertEquals(5, list.count());

        ArrayList<Node2<Integer>> all = list.getAll();
        assertEquals(1, all.get(0).value);
        assertEquals(2, all.get(1).value);
        assertEquals(3, all.get(2).value);
        assertEquals(4, all.get(3).value);
        assertEquals(5, all.get(4).value);
    }

    @Test
    public void testRemoveAllDuplicates_someDuplicates_descending() {
        OrderedList<Integer> list = new OrderedList<>(false);
        list.add(5);
        list.add(5);
        list.add(4);
        list.add(3);
        list.add(3);
        list.add(3);
        list.add(2);
        list.add(2);
        list.add(1);

        list.removeAllDuplicates();

        assertEquals(5, list.count());

        ArrayList<Node2<Integer>> all = list.getAll();
        assertEquals(5, all.get(0).value);
        assertEquals(4, all.get(1).value);
        assertEquals(3, all.get(2).value);
        assertEquals(2, all.get(3).value);
        assertEquals(1, all.get(4).value);
    }

    @Test
    public void testRemoveAllDuplicates_duplicatesAtHead_ascending() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(2);
        list.add(3);

        list.removeAllDuplicates();

        assertEquals(3, list.count());

        ArrayList<Node2<Integer>> all = list.getAll();
        assertEquals(1, all.get(0).value);
        assertEquals(2, all.get(1).value);
        assertEquals(3, all.get(2).value);
    }

    @Test
    public void testRemoveAllDuplicates_duplicatesAtTail_ascending() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(3);
        list.add(3);

        list.removeAllDuplicates();

        assertEquals(3, list.count());
        assertEquals(1, list.head.value);
        assertEquals(3, list.tail.value);

        ArrayList<Node2<Integer>> all = list.getAll();
        assertEquals(1, all.get(0).value);
        assertEquals(2, all.get(1).value);
        assertEquals(3, all.get(2).value);
    }

    @Test
    public void testRemoveAllDuplicates_duplicatesAtTail_descending() {
        OrderedList<Integer> list = new OrderedList<>(false);
        list.add(3);
        list.add(2);
        list.add(1);
        list.add(1);
        list.add(1);

        list.removeAllDuplicates();

        assertEquals(3, list.count());
        assertEquals(3, list.head.value);
        assertEquals(1, list.tail.value);

        ArrayList<Node2<Integer>> all = list.getAll();
        assertEquals(3, all.get(0).value);
        assertEquals(2, all.get(1).value);
        assertEquals(1, all.get(2).value);
    }

    @Test
    public void testRemoveAllDuplicates_consecutiveGroups_ascending() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(2);
        list.add(3);
        list.add(3);
        list.add(4);
        list.add(4);
        list.add(4);
        list.add(4);

        list.removeAllDuplicates();

        assertEquals(4, list.count());

        ArrayList<Node2<Integer>> all = list.getAll();
        assertEquals(1, all.get(0).value);
        assertEquals(2, all.get(1).value);
        assertEquals(3, all.get(2).value);
        assertEquals(4, all.get(3).value);
    }

    @Test
    public void testRemoveAllDuplicates_strings_ascending() {
        OrderedList<String> list = new OrderedList<>(true);
        list.add("apple");
        list.add("banana");
        list.add("banana");
        list.add("cherry");
        list.add("cherry");
        list.add("cherry");

        list.removeAllDuplicates();

        assertEquals(3, list.count());

        ArrayList<Node2<String>> all = list.getAll();
        assertEquals("apple", all.get(0).value);
        assertEquals("banana", all.get(1).value);
        assertEquals("cherry", all.get(2).value);
    }

    @Test
    public void testRemoveAllDuplicates_tailUpdated() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(3);
        list.add(3);

        list.removeAllDuplicates();

        // Проверяем, что tail обновлён корректно
        assertEquals(3, list.tail.value);
        assertNull(list.tail.next);

        // Добавляем новый элемент после удаления дубликатов
        list.add(4);
        assertEquals(4, list.tail.value);
        assertEquals(4, list.count());
    }

    @Test
    public void testRemoveAllDuplicates_linksConsistency() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(3);

        list.removeAllDuplicates();

        // Проверяем целостность ссылок
        ArrayList<Node2<Integer>> all = list.getAll();

        // head.prev должен быть null
        assertNull(list.head.prev);

        // tail.next должен быть null
        assertNull(list.tail.next);

        // Проверяем значения prev/next для каждого элемента
        for (int i = 0; i < all.size(); i++) {
            Node2<Integer> node = all.get(i);
            if (i > 0) {
                assertEquals(all.get(i - 1).value, node.prev.value);
            } else {
                assertNull(node.prev);
            }

            if (i < all.size() - 1) {
                assertEquals(all.get(i + 1).value, node.next.value);
            } else {
                assertNull(node.next);
            }
        }
    }
}
