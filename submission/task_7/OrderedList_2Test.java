import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class OrderedList_2Test {

    @Test
    public void testMerge_bothEmpty() {
        OrderedList<Integer> list1 = new OrderedList<>(true);
        OrderedList<Integer> list2 = new OrderedList<>(true);
        
        OrderedList<Integer> result = OrderedList_2.merge(list1, list2);
        
        assertEquals(0, result.count());

        assertEquals(0, list1.count());
        assertEquals(0, list2.count());
    }

    @Test
    public void testMerge_firstEmpty() {
        OrderedList<Integer> list1 = new OrderedList<>(true);
        OrderedList<Integer> list2 = new OrderedList<>(true);
        list2.add(1);
        list2.add(2);
        list2.add(3);
        
        OrderedList<Integer> result = OrderedList_2.merge(list1, list2);
        
        assertEquals(3, result.count());
        assertEquals(0, list1.count());
        assertEquals(3, list2.count());
    }

    @Test
    public void testMerge_secondEmpty() {
        OrderedList<Integer> list1 = new OrderedList<>(true);
        list1.add(1);
        list1.add(2);
        list1.add(3);
        OrderedList<Integer> list2 = new OrderedList<>(true);
        
        OrderedList<Integer> result = OrderedList_2.merge(list1, list2);
        
        assertEquals(3, result.count());
        assertEquals(3, list1.count());
        assertEquals(0, list2.count());
    }

    @Test
    public void testMerge_nonOverlapping_ascending() {
        OrderedList<Integer> list1 = new OrderedList<>(true);
        list1.add(1);
        list1.add(2);
        list1.add(3);
        
        OrderedList<Integer> list2 = new OrderedList<>(true);
        list2.add(4);
        list2.add(5);
        list2.add(6);
        
        OrderedList<Integer> result = OrderedList_2.merge(list1, list2);
        
        assertEquals(6, result.count());
        ArrayList<Node<Integer>> all = result.getAll();
        assertEquals(1, all.get(0).value);
        assertEquals(2, all.get(1).value);
        assertEquals(3, all.get(2).value);
        assertEquals(4, all.get(3).value);
        assertEquals(5, all.get(4).value);
        assertEquals(6, all.get(5).value);
    }

    @Test
    public void testMerge_nonOverlapping_descending() {
        OrderedList<Integer> list1 = new OrderedList<>(false);
        list1.add(6);
        list1.add(5);
        list1.add(4);
        
        OrderedList<Integer> list2 = new OrderedList<>(false);
        list2.add(3);
        list2.add(2);
        list2.add(1);
        
        OrderedList<Integer> result = OrderedList_2.merge(list1, list2);
        
        assertEquals(6, result.count());
        ArrayList<Node<Integer>> all = result.getAll();
        assertEquals(6, all.get(0).value);
        assertEquals(5, all.get(1).value);
        assertEquals(4, all.get(2).value);
        assertEquals(3, all.get(3).value);
        assertEquals(2, all.get(4).value);
        assertEquals(1, all.get(5).value);
    }

    @Test
    public void testMerge_overlapping_ascending() {
        OrderedList<Integer> list1 = new OrderedList<>(true);
        list1.add(1);
        list1.add(3);
        list1.add(5);
        
        OrderedList<Integer> list2 = new OrderedList<>(true);
        list2.add(2);
        list2.add(4);
        list2.add(6);
        
        OrderedList<Integer> result = OrderedList_2.merge(list1, list2);
        
        assertEquals(6, result.count());
        ArrayList<Node<Integer>> all = result.getAll();
        assertEquals(1, all.get(0).value);
        assertEquals(2, all.get(1).value);
        assertEquals(3, all.get(2).value);
        assertEquals(4, all.get(3).value);
        assertEquals(5, all.get(4).value);
        assertEquals(6, all.get(5).value);
    }

    @Test
    public void testMerge_withDuplicates() {
        OrderedList<Integer> list1 = new OrderedList<>(true);
        list1.add(1);
        list1.add(3);
        list1.add(3);
        list1.add(5);
        
        OrderedList<Integer> list2 = new OrderedList<>(true);
        list2.add(2);
        list2.add(3);
        list2.add(4);
        
        OrderedList<Integer> result = OrderedList_2.merge(list1, list2);
        
        assertEquals(7, result.count());
        ArrayList<Node<Integer>> all = result.getAll();
        assertEquals(1, all.get(0).value);
        assertEquals(2, all.get(1).value);
        assertEquals(3, all.get(2).value);
        assertEquals(3, all.get(3).value);
        assertEquals(3, all.get(4).value);
        assertEquals(4, all.get(5).value);
        assertEquals(5, all.get(6).value);
    }

    @Test
    public void testMerge_differentSizes() {
        OrderedList<Integer> list1 = new OrderedList<>(true);
        list1.add(1);
        
        OrderedList<Integer> list2 = new OrderedList<>(true);
        list2.add(2);
        list2.add(3);
        list2.add(4);
        list2.add(5);
        list2.add(6);
        
        OrderedList<Integer> result = OrderedList_2.merge(list1, list2);
        
        assertEquals(6, result.count());
        ArrayList<Node<Integer>> all = result.getAll();
        assertEquals(1, all.get(0).value);
        assertEquals(2, all.get(1).value);
        assertEquals(3, all.get(2).value);
        assertEquals(4, all.get(3).value);
        assertEquals(5, all.get(4).value);
        assertEquals(6, all.get(5).value);
    }

    @Test
    public void testMerge_strings() {
        OrderedList<String> list1 = new OrderedList<>(true);
        list1.add("apple");
        list1.add("cherry");
        
        OrderedList<String> list2 = new OrderedList<>(true);
        list2.add("banana");
        list2.add("date");
        
        OrderedList<String> result = OrderedList_2.merge(list1, list2);
        
        assertEquals(4, result.count());
        ArrayList<Node<String>> all = result.getAll();
        assertEquals("apple", all.get(0).value);
        assertEquals("banana", all.get(1).value);
        assertEquals("cherry", all.get(2).value);
        assertEquals("date", all.get(3).value);
    }

    @Test
    public void testMerge_doubles() {
        OrderedList<Double> list1 = new OrderedList<>(true);
        list1.add(1.5);
        list1.add(3.5);
        
        OrderedList<Double> list2 = new OrderedList<>(true);
        list2.add(2.5);
        list2.add(4.5);
        
        OrderedList<Double> result = OrderedList_2.merge(list1, list2);
        
        assertEquals(4, result.count());
        ArrayList<Node<Double>> all = result.getAll();
        assertEquals(1.5, all.get(0).value);
        assertEquals(2.5, all.get(1).value);
        assertEquals(3.5, all.get(2).value);
        assertEquals(4.5, all.get(3).value);
    }

    @Test
    public void testContainsSublist_emptySublist() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(2);
        list.add(3);
        OrderedList<Integer> sublist = new OrderedList<>(true);

        assertTrue(OrderedList_2.containsSublist(list, sublist));
    }

    @Test
    public void testContainsSublist_emptyList() {
        OrderedList<Integer> list = new OrderedList<>(true);
        OrderedList<Integer> sublist = new OrderedList<>(true);
        sublist.add(1);

        assertFalse(OrderedList_2.containsSublist(list, sublist));
    }

    @Test
    public void testContainsSublist_bothEmpty() {
        OrderedList<Integer> list = new OrderedList<>(true);
        OrderedList<Integer> sublist = new OrderedList<>(true);

        assertTrue(OrderedList_2.containsSublist(list, sublist));
    }

    @Test
    public void testContainsSublist_sublistLonger() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(2);
        OrderedList<Integer> sublist = new OrderedList<>(true);
        sublist.add(1);
        sublist.add(2);
        sublist.add(3);

        assertFalse(OrderedList_2.containsSublist(list, sublist));
    }

    @Test
    public void testContainsSublist_exactMatch() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(2);
        list.add(3);
        OrderedList<Integer> sublist = new OrderedList<>(true);
        sublist.add(1);
        sublist.add(2);
        sublist.add(3);

        assertTrue(OrderedList_2.containsSublist(list, sublist));
    }

    @Test
    public void testContainsSublist_sublistAtStart() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        OrderedList<Integer> sublist = new OrderedList<>(true);
        sublist.add(1);
        sublist.add(2);

        assertTrue(OrderedList_2.containsSublist(list, sublist));
    }

    @Test
    public void testContainsSublist_sublistAtEnd() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        OrderedList<Integer> sublist = new OrderedList<>(true);
        sublist.add(4);
        sublist.add(5);

        assertTrue(OrderedList_2.containsSublist(list, sublist));
    }

    @Test
    public void testContainsSublist_sublistInMiddle() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        OrderedList<Integer> sublist = new OrderedList<>(true);
        sublist.add(2);
        sublist.add(3);
        sublist.add(4);

        assertTrue(OrderedList_2.containsSublist(list, sublist));
    }

    @Test
    public void testContainsSublist_notContained() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        OrderedList<Integer> sublist = new OrderedList<>(true);
        sublist.add(6);
        sublist.add(7);

        assertFalse(OrderedList_2.containsSublist(list, sublist));
    }

    @Test
    public void testContainsSublist_partialMatch() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        OrderedList<Integer> sublist = new OrderedList<>(true);
        sublist.add(2);
        sublist.add(3);
        sublist.add(6);

        assertFalse(OrderedList_2.containsSublist(list, sublist));
    }

    @Test
    public void testContainsSublist_singleElement() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(2);
        list.add(3);
        OrderedList<Integer> sublist = new OrderedList<>(true);
        sublist.add(2);

        assertTrue(OrderedList_2.containsSublist(list, sublist));
    }

    @Test
    public void testContainsSublist_singleElementNotFound() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(2);
        list.add(3);
        OrderedList<Integer> sublist = new OrderedList<>(true);
        sublist.add(5);

        assertFalse(OrderedList_2.containsSublist(list, sublist));
    }

    @Test
    public void testContainsSublist_withDuplicates() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(3);
        OrderedList<Integer> sublist = new OrderedList<>(true);
        sublist.add(2);
        sublist.add(2);

        assertTrue(OrderedList_2.containsSublist(list, sublist));
    }

    @Test
    public void testContainsSublist_withDuplicates_notFound() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(2);
        list.add(3);
        OrderedList<Integer> sublist = new OrderedList<>(true);
        sublist.add(2);
        sublist.add(2);

        assertFalse(OrderedList_2.containsSublist(list, sublist));
    }

    @Test
    public void testContainsSublist_strings() {
        OrderedList<String> list = new OrderedList<>(true);
        list.add("apple");
        list.add("banana");
        list.add("cherry");
        list.add("date");
        OrderedList<String> sublist = new OrderedList<>(true);
        sublist.add("banana");
        sublist.add("cherry");

        assertTrue(OrderedList_2.containsSublist(list, sublist));
    }

    @Test
    public void testContainsSublist_descending() {
        OrderedList<Integer> list = new OrderedList<>(false);
        list.add(5);
        list.add(4);
        list.add(3);
        list.add(2);
        list.add(1);
        OrderedList<Integer> sublist = new OrderedList<>(false);
        sublist.add(4);
        sublist.add(3);
        sublist.add(2);

        assertTrue(OrderedList_2.containsSublist(list, sublist));
    }

    @Test
    public void testFindMostFrequent_emptyList() {
        OrderedList<Integer> list = new OrderedList<>(true);
        assertNull(OrderedList_2.findMostFrequent(list));
    }

    @Test
    public void testFindMostFrequent_singleElement() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(5);
        assertEquals(Integer.valueOf(5), OrderedList_2.findMostFrequent(list));
    }

    @Test
    public void testFindMostFrequent_allUnique() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        assertEquals(Integer.valueOf(1), OrderedList_2.findMostFrequent(list));
    }

    @Test
    public void testFindMostFrequent_allSame() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(7);
        list.add(7);
        list.add(7);
        list.add(7);
        assertEquals(Integer.valueOf(7), OrderedList_2.findMostFrequent(list));
    }

    @Test
    public void testFindMostFrequent_oneMostFrequent() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(2);
        list.add(3);
        list.add(4);
        assertEquals(Integer.valueOf(2), OrderedList_2.findMostFrequent(list));
    }

    @Test
    public void testFindMostFrequent_mostFrequentAtEnd() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(4);
        list.add(4);
        list.add(4);
        assertEquals(Integer.valueOf(4), OrderedList_2.findMostFrequent(list));
    }

    @Test
    public void testFindMostFrequent_mostFrequentAtStart() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(5);
        list.add(5);
        list.add(5);
        list.add(5);
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals(Integer.valueOf(5), OrderedList_2.findMostFrequent(list));
    }

    @Test
    public void testFindMostFrequent_multipleWithSameCount() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(1);
        list.add(3);
        list.add(3);
        list.add(5);
        list.add(5);

        assertEquals(Integer.valueOf(1), OrderedList_2.findMostFrequent(list));
    }

    @Test
    public void testFindMostFrequent_withDuplicates() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(2);
        list.add(3);
        list.add(3);
        assertEquals(Integer.valueOf(2), OrderedList_2.findMostFrequent(list));
    }

    @Test
    public void testFindMostFrequent_strings() {
        OrderedList<String> list = new OrderedList<>(true);
        list.add("apple");
        list.add("banana");
        list.add("banana");
        list.add("banana");
        list.add("cherry");
        assertEquals("banana", OrderedList_2.findMostFrequent(list));
    }

    @Test
    public void testFindMostFrequent_descending() {
        OrderedList<Integer> list = new OrderedList<>(false);
        list.add(5);
        list.add(4);
        list.add(4);
        list.add(4);
        list.add(4);
        list.add(3);
        list.add(2);
        assertEquals(Integer.valueOf(4), OrderedList_2.findMostFrequent(list));
    }

    @Test
    public void testFindMostFrequentWithCount_emptyList() {
        OrderedList<Integer> list = new OrderedList<>(true);
        assertNull(OrderedList_2.findMostFrequentWithCount(list));
    }

    @Test
    public void testFindMostFrequentWithCount_singleElement() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(5);
        java.util.Map.Entry<Integer, Integer> result = OrderedList_2.findMostFrequentWithCount(list);
        assertEquals(Integer.valueOf(5), result.getKey());
        assertEquals(Integer.valueOf(1), result.getValue());
    }

    @Test
    public void testFindMostFrequentWithCount_withCount() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(2);
        list.add(3);
        java.util.Map.Entry<Integer, Integer> result = OrderedList_2.findMostFrequentWithCount(list);
        assertEquals(Integer.valueOf(2), result.getKey());
        assertEquals(Integer.valueOf(3), result.getValue());
    }

    @Test
    public void testFindMostFrequentWithCount_allSame() {
        OrderedList<Integer> list = new OrderedList<>(true);
        list.add(7);
        list.add(7);
        list.add(7);
        list.add(7);
        java.util.Map.Entry<Integer, Integer> result = OrderedList_2.findMostFrequentWithCount(list);
        assertEquals(Integer.valueOf(7), result.getKey());
        assertEquals(Integer.valueOf(4), result.getValue());
    }
}

