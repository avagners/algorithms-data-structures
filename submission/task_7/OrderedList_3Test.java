import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class OrderedList_3Test {

    @Test
    public void testAddAndFindIndex_emptyList() {
        OrderedList_3<Integer> list = new OrderedList_3<>(true);
        assertEquals(-1, list.findIndex(5));
    }

    @Test
    public void testAddAndFindIndex_singleElement() {
        OrderedList_3<Integer> list = new OrderedList_3<>(true);
        list.add(5);
        assertEquals(0, list.findIndex(5));
        assertEquals(-1, list.findIndex(3));
    }

    @Test
    public void testAddAndFindIndex_ascending() {
        OrderedList_3<Integer> list = new OrderedList_3<>(true);
        list.add(3);
        list.add(1);
        list.add(5);
        list.add(2);
        list.add(4);

        assertEquals(0, list.findIndex(1));
        assertEquals(1, list.findIndex(2));
        assertEquals(2, list.findIndex(3));
        assertEquals(3, list.findIndex(4));
        assertEquals(4, list.findIndex(5));
        assertEquals(-1, list.findIndex(6));
    }

    @Test
    public void testAddAndFindIndex_descending() {
        OrderedList_3<Integer> list = new OrderedList_3<>(false);
        list.add(1);
        list.add(3);
        list.add(5);
        list.add(2);
        list.add(4);

        assertEquals(4, list.findIndex(1));
        assertEquals(3, list.findIndex(2));
        assertEquals(2, list.findIndex(3));
        assertEquals(1, list.findIndex(4));
        assertEquals(0, list.findIndex(5));
    }

    @Test
    public void testAddAndFindIndex_withDuplicates() {
        OrderedList_3<Integer> list = new OrderedList_3<>(true);
        list.add(2);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(2);

        int index = list.findIndex(2);
        assertTrue(index >= 0 && index <= 4);
        assertEquals(0, list.findIndex(1));
        assertEquals(4, list.findIndex(3));
    }

    @Test
    public void testFindFirstAndLastIndex_noDuplicates() {
        OrderedList_3<Integer> list = new OrderedList_3<>(true);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        for (int i = 1; i <= 5; i++) {
            assertEquals(i - 1, list.findFirstIndex(i));
            assertEquals(i - 1, list.findLastIndex(i));
        }
    }

    @Test
    public void testFindFirstAndLastIndex_withDuplicates() {
        OrderedList_3<Integer> list = new OrderedList_3<>(true);
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(2);
        list.add(3);
        list.add(4);

        assertEquals(0, list.findFirstIndex(1));
        assertEquals(0, list.findLastIndex(1));

        assertEquals(1, list.findFirstIndex(2));
        assertEquals(3, list.findLastIndex(2));

        assertEquals(4, list.findFirstIndex(3));
        assertEquals(4, list.findLastIndex(3));

        assertEquals(5, list.findFirstIndex(4));
        assertEquals(5, list.findLastIndex(4));
    }

    @Test
    public void testFindFirstAndLastIndex_notFound() {
        OrderedList_3<Integer> list = new OrderedList_3<>(true);
        list.add(1);
        list.add(2);
        list.add(3);

        assertEquals(-1, list.findFirstIndex(5));
        assertEquals(-1, list.findLastIndex(5));
    }

    @Test
    public void testGetAndSize() {
        OrderedList_3<Integer> list = new OrderedList_3<>(true);
        assertEquals(0, list.size());

        list.add(3);
        list.add(1);
        list.add(2);

        assertEquals(3, list.size());
        assertEquals(Integer.valueOf(1), list.get(0));
        assertEquals(Integer.valueOf(2), list.get(1));
        assertEquals(Integer.valueOf(3), list.get(2));
        assertNull(list.get(-1));
        assertNull(list.get(3));
    }

    @Test
    public void testGetAll_ascending() {
        OrderedList_3<Integer> list = new OrderedList_3<>(true);
        list.add(5);
        list.add(1);
        list.add(3);
        list.add(2);
        list.add(4);

        ArrayList<Integer> all = list.getAll();
        assertEquals(5, all.size());
        for (int i = 0; i < 5; i++) {
            assertEquals(Integer.valueOf(i + 1), all.get(i));
        }
    }

    @Test
    public void testGetAll_descending() {
        OrderedList_3<Integer> list = new OrderedList_3<>(false);
        list.add(1);
        list.add(5);
        list.add(3);

        ArrayList<Integer> all = list.getAll();
        assertEquals(3, all.size());
        assertEquals(Integer.valueOf(5), all.get(0));
        assertEquals(Integer.valueOf(3), all.get(1));
        assertEquals(Integer.valueOf(1), all.get(2));
    }

    @Test
    public void testDelete_found() {
        OrderedList_3<Integer> list = new OrderedList_3<>(true);
        list.add(1);
        list.add(2);
        list.add(3);

        assertTrue(list.delete(2));
        assertEquals(2, list.size());
        assertEquals(-1, list.findIndex(2));
        assertEquals(0, list.findIndex(1));
        assertEquals(1, list.findIndex(3));
    }

    @Test
    public void testDelete_notFound() {
        OrderedList_3<Integer> list = new OrderedList_3<>(true);
        list.add(1);
        list.add(2);
        list.add(3);

        assertFalse(list.delete(5));
        assertEquals(3, list.size());
    }

    @Test
    public void testDelete_firstDuplicate() {
        OrderedList_3<Integer> list = new OrderedList_3<>(true);
        list.add(2);
        list.add(2);
        list.add(2);

        assertTrue(list.delete(2));
        assertEquals(2, list.size());
        assertEquals(0, list.findIndex(2));
    }

    @Test
    public void testClear() {
        OrderedList_3<Integer> list = new OrderedList_3<>(true);
        list.add(1);
        list.add(2);
        list.add(3);

        list.clear();

        assertEquals(0, list.size());
        assertEquals(-1, list.findIndex(1));
    }

    @Test
    public void testStrings_ascending() {
        OrderedList_3<String> list = new OrderedList_3<>(true);
        list.add("banana");
        list.add("apple");
        list.add("cherry");
        list.add("date");

        assertEquals(0, list.findIndex("apple"));
        assertEquals(1, list.findIndex("banana"));
        assertEquals(2, list.findIndex("cherry"));
        assertEquals(3, list.findIndex("date"));
        assertEquals(-1, list.findIndex("elderberry"));
    }

    @Test
    public void testStrings_descending() {
        OrderedList_3<String> list = new OrderedList_3<>(false);
        list.add("apple");
        list.add("cherry");
        list.add("banana");

        assertEquals(0, list.findIndex("cherry"));
        assertEquals(1, list.findIndex("banana"));
        assertEquals(2, list.findIndex("apple"));
    }

    @Test
    public void testDoubles() {
        OrderedList_3<Double> list = new OrderedList_3<>(true);
        list.add(3.14);
        list.add(2.71);
        list.add(1.41);
        list.add(0.5);

        assertEquals(0, list.findIndex(0.5));
        assertEquals(1, list.findIndex(1.41));
        assertEquals(2, list.findIndex(2.71));
        assertEquals(3, list.findIndex(3.14));
    }

    @Test
    public void testBinarySearch_largeList() {
        OrderedList_3<Integer> list = new OrderedList_3<>(true);

        for (int i = 1000; i > 0; i--) {
            list.add(i);
        }

        assertEquals(1000, list.size());

        assertEquals(0, list.findIndex(1));
        assertEquals(499, list.findIndex(500));
        assertEquals(999, list.findIndex(1000));
        assertEquals(-1, list.findIndex(1001));
    }

    @Test
    public void testBinarySearch_withDuplicates_largeList() {
        OrderedList_3<Integer> list = new OrderedList_3<>(true);

        for (int i = 0; i < 1000; i++) {
            list.add(i / 10);
        }

        assertEquals(1000, list.size());

        assertEquals(0, list.findFirstIndex(0));
        assertEquals(9, list.findLastIndex(0));

        assertEquals(10, list.findFirstIndex(1));
        assertEquals(19, list.findLastIndex(1));

        assertEquals(990, list.findFirstIndex(99));
        assertEquals(999, list.findLastIndex(99));
    }
}

