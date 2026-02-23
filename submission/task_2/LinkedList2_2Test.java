import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LinkedList2_2Test {

    @Test
    public void testReverseEmptyList() {
        LinkedList2_2 list = new LinkedList2_2();

        list.reverse();

        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testReverseSingleElement() {
        LinkedList2_2 list = new LinkedList2_2();
        Node node = new Node(1);

        list.addInTail(node);

        list.reverse();

        assertEquals(node, list.head);
        assertEquals(node, list.tail);
        assertNull(node.prev);
        assertNull(node.next);
    }

    @Test
    public void testReverseTwoElements() {
        LinkedList2_2 list = new LinkedList2_2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);

        list.addInTail(node1);
        list.addInTail(node2);

        list.reverse();

        assertEquals(node2, list.head);
        assertEquals(node1, list.tail);
        assertNull(node2.prev);
        assertEquals(node1, node2.next);
        assertEquals(node2, node1.prev);
        assertNull(node1.next);
    }

    @Test
    public void testReverseMultipleElements() {
        LinkedList2_2 list = new LinkedList2_2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);
        list.addInTail(node5);

        list.reverse();

        assertEquals(node5, list.head);
        assertEquals(node1, list.tail);
        
        // Check all links
        assertNull(node5.prev);
        assertEquals(node4, node5.next);
        
        assertEquals(node5, node4.prev);
        assertEquals(node3, node4.next);
        
        assertEquals(node4, node3.prev);
        assertEquals(node2, node3.next);
        
        assertEquals(node3, node2.prev);
        assertEquals(node1, node2.next);
        
        assertEquals(node2, node1.prev);
        assertNull(node1.next);
    }

    @Test
    public void testReverseThreeElements() {
        LinkedList2_2 list = new LinkedList2_2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        list.reverse();

        assertEquals(node3, list.head);
        assertEquals(node1, list.tail);
        assertEquals(node2, node3.next);
        assertEquals(node1, node2.next);
        assertEquals(node2, node1.prev);
    }

    @Test
    public void testReverseWithNegativeValues() {
        LinkedList2_2 list = new LinkedList2_2();
        Node node1 = new Node(-1);
        Node node2 = new Node(-2);
        Node node3 = new Node(-3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        list.reverse();

        assertEquals(-3, list.head.value);
        assertEquals(-1, list.tail.value);
        assertEquals(-2, list.head.next.value);
        assertEquals(-1, list.head.next.next.value);
    }

    @Test
    public void testReverseWithZero() {
        LinkedList2_2 list = new LinkedList2_2();
        Node node1 = new Node(0);
        Node node2 = new Node(1);
        Node node3 = new Node(2);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        list.reverse();

        assertEquals(2, list.head.value);
        assertEquals(0, list.tail.value);
    }

    @Test
    public void testReverseTwice() {
        LinkedList2_2 list = new LinkedList2_2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        list.reverse();
        list.reverse();

        assertEquals(node1, list.head);
        assertEquals(node3, list.tail);
        assertEquals(node2, node1.next);
        assertEquals(node3, node2.next);
    }

    @Test
    public void testReversePreservesValues() {
        LinkedList2_2 list = new LinkedList2_2();
        Node node1 = new Node(10);
        Node node2 = new Node(20);
        Node node3 = new Node(30);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        list.reverse();

        assertEquals(30, list.head.value);
        assertEquals(20, list.head.next.value);
        assertEquals(10, list.tail.value);
    }

    @Test
    public void testReverseAllLinksIntegrity() {
        LinkedList2_2 list = new LinkedList2_2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);

        list.reverse();

        // Forward traversal
        Node current = list.head;
        assertEquals(4, current.value);
        current = current.next;
        assertEquals(3, current.value);
        current = current.next;
        assertEquals(2, current.value);
        current = current.next;
        assertEquals(1, current.value);
        assertNull(current.next);

        // Backward traversal
        current = list.tail;
        assertEquals(1, current.value);
        current = current.prev;
        assertEquals(2, current.value);
        current = current.prev;
        assertEquals(3, current.value);
        current = current.prev;
        assertEquals(4, current.value);
        assertNull(current.prev);
    }

    @Test
    public void testHasCycleEmptyList() {
        LinkedList2_2 list = new LinkedList2_2();

        assertFalse(list.hasCycle());
    }

    @Test
    public void testHasCycleSingleElement() {
        LinkedList2_2 list = new LinkedList2_2();
        Node node = new Node(1);

        list.addInTail(node);

        assertFalse(list.hasCycle());
    }

    @Test
    public void testHasCycleNoCycle() {
        LinkedList2_2 list = new LinkedList2_2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        assertFalse(list.hasCycle());
    }

    @Test
    public void testHasCycleTwoElements() {
        LinkedList2_2 list = new LinkedList2_2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);

        list.addInTail(node1);
        list.addInTail(node2);

        assertFalse(list.hasCycle());
    }

    @Test
    public void testHasCycleSelfLoopOnHead() {
        LinkedList2_2 list = new LinkedList2_2();
        Node node1 = new Node(1);

        list.addInTail(node1);
        node1.next = node1; // Создаём цикл на самого себя

        assertTrue(list.hasCycle());
    }

    @Test
    public void testHasCycleSelfLoopOnTail() {
        LinkedList2_2 list = new LinkedList2_2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);

        list.addInTail(node1);
        list.addInTail(node2);
        node2.next = node2; // Цикл на хвосте

        assertTrue(list.hasCycle());
    }

    @Test
    public void testHasCycleTailPointsToHead() {
        LinkedList2_2 list = new LinkedList2_2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        node3.next = node1; // Хвост указывает на голову

        assertTrue(list.hasCycle());
    }

    @Test
    public void testHasCycleTailPointsToMiddle() {
        LinkedList2_2 list = new LinkedList2_2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);
        node4.next = node2; // Хвост указывает на средний узел

        assertTrue(list.hasCycle());
    }

    @Test
    public void testHasCycleMiddlePointsToHead() {
        LinkedList2_2 list = new LinkedList2_2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        node2.next = node1; // Средний узел указывает на голову

        assertTrue(list.hasCycle());
    }

    @Test
    public void testHasCycleLargeListNoCycle() {
        LinkedList2_2 list = new LinkedList2_2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);
        list.addInTail(node5);
        list.addInTail(node6);

        assertFalse(list.hasCycle());
    }

    @Test
    public void testHasCycleLargeListWithCycle() {
        LinkedList2_2 list = new LinkedList2_2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);
        list.addInTail(node5);
        node5.next = node3; // Цикл

        assertTrue(list.hasCycle());
    }

    @Test
    public void testSortEmptyList() {
        LinkedList2_2 list = new LinkedList2_2();

        list.sort();

        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testSortSingleElement() {
        LinkedList2_2 list = new LinkedList2_2();
        Node node = new Node(5);

        list.addInTail(node);

        list.sort();

        assertEquals(node, list.head);
        assertEquals(node, list.tail);
        assertEquals(5, list.head.value);
    }

    @Test
    public void testSortAlreadySorted() {
        LinkedList2_2 list = new LinkedList2_2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        list.sort();

        assertEquals(1, list.head.value);
        assertEquals(2, list.head.next.value);
        assertEquals(3, list.tail.value);
    }

    @Test
    public void testSortReverseOrder() {
        LinkedList2_2 list = new LinkedList2_2();
        Node node1 = new Node(3);
        Node node2 = new Node(2);
        Node node3 = new Node(1);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        list.sort();

        assertEquals(1, list.head.value);
        assertEquals(2, list.head.next.value);
        assertEquals(3, list.tail.value);
    }

    @Test
    public void testSortRandomOrder() {
        LinkedList2_2 list = new LinkedList2_2();
        Node node1 = new Node(5);
        Node node2 = new Node(2);
        Node node3 = new Node(8);
        Node node4 = new Node(1);
        Node node5 = new Node(9);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);
        list.addInTail(node5);

        list.sort();

        assertEquals(1, list.head.value);
        assertEquals(2, list.head.next.value);
        assertEquals(5, list.head.next.next.value);
        assertEquals(8, list.head.next.next.next.value);
        assertEquals(9, list.tail.value);
    }

    @Test
    public void testSortWithDuplicates() {
        LinkedList2_2 list = new LinkedList2_2();
        Node node1 = new Node(3);
        Node node2 = new Node(1);
        Node node3 = new Node(3);
        Node node4 = new Node(1);
        Node node5 = new Node(2);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);
        list.addInTail(node5);

        list.sort();

        assertEquals(1, list.head.value);
        assertEquals(1, list.head.next.value);
        assertEquals(2, list.head.next.next.value);
        assertEquals(3, list.head.next.next.next.value);
        assertEquals(3, list.tail.value);
    }

    @Test
    public void testSortWithNegativeValues() {
        LinkedList2_2 list = new LinkedList2_2();
        Node node1 = new Node(3);
        Node node2 = new Node(-1);
        Node node3 = new Node(0);
        Node node4 = new Node(-5);
        Node node5 = new Node(2);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);
        list.addInTail(node5);

        list.sort();

        assertEquals(-5, list.head.value);
        assertEquals(-1, list.head.next.value);
        assertEquals(0, list.head.next.next.value);
        assertEquals(2, list.head.next.next.next.value);
        assertEquals(3, list.tail.value);
    }

    @Test
    public void testSortTwoElements() {
        LinkedList2_2 list = new LinkedList2_2();
        Node node1 = new Node(2);
        Node node2 = new Node(1);

        list.addInTail(node1);
        list.addInTail(node2);

        list.sort();

        assertEquals(1, list.head.value);
        assertEquals(2, list.tail.value);
    }

    @Test
    public void testSortPrevPointers() {
        LinkedList2_2 list = new LinkedList2_2();
        Node node1 = new Node(3);
        Node node2 = new Node(1);
        Node node3 = new Node(2);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        list.sort();

        // After sort: 1 -> 2 -> 3
        // Check prev pointers
        assertNull(list.head.prev);
        assertNotNull(list.head.next);
        assertEquals(list.head, list.head.next.prev);
        assertNotNull(list.tail.prev);
        assertEquals(list.head.next, list.tail.prev);
        assertNull(list.tail.next);
    }

    @Test
    public void testSortAllSameValues() {
        LinkedList2_2 list = new LinkedList2_2();
        Node node1 = new Node(5);
        Node node2 = new Node(5);
        Node node3 = new Node(5);
        Node node4 = new Node(5);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);

        list.sort();

        assertEquals(5, list.head.value);
        assertEquals(5, list.tail.value);

        // Verify all nodes are still in the list
        int count = 0;
        Node current = list.head;
        while (current != null) {
            assertEquals(5, current.value);
            count++;
            current = current.next;
        }
        assertEquals(4, count);
    }

    @Test
    public void testSortLargeList() {
        LinkedList2_2 list = new LinkedList2_2();
        int[] values = {64, 34, 25, 12, 22, 11, 90, 1, 100, 50};

        for (int value : values) {
            list.addInTail(new Node(value));
        }

        list.sort();

        int[] expected = {1, 11, 12, 22, 25, 34, 50, 64, 90, 100};
        Node current = list.head;
        for (int exp : expected) {
            assertEquals(exp, current.value);
            current = current.next;
        }
        assertNull(current);
    }

    @Test
    public void testSortTailUpdated() {
        LinkedList2_2 list = new LinkedList2_2();
        Node node1 = new Node(3);
        Node node2 = new Node(1);
        Node node3 = new Node(2);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        list.sort();

        // After sort: 1 -> 2 -> 3, tail should have value 3
        assertEquals(3, list.tail.value);
        assertNull(list.tail.next);
    }

    @Test
    public void testMergeSortedListsEmptyLists() {
        LinkedList2_2 list1 = new LinkedList2_2();
        LinkedList2_2 list2 = new LinkedList2_2();

        LinkedList2_2 result = LinkedList2_2.mergeSortedLists(list1, list2);

        assertNull(result.head);
        assertNull(result.tail);
    }

    @Test
    public void testMergeSortedListsFirstEmpty() {
        LinkedList2_2 list1 = new LinkedList2_2();
        LinkedList2_2 list2 = new LinkedList2_2();
        list2.addInTail(new Node(1));
        list2.addInTail(new Node(2));
        list2.addInTail(new Node(3));

        LinkedList2_2 result = LinkedList2_2.mergeSortedLists(list1, list2);

        assertEquals(1, result.head.value);
        assertEquals(2, result.head.next.value);
        assertEquals(3, result.tail.value);
    }

    @Test
    public void testMergeSortedListsSecondEmpty() {
        LinkedList2_2 list1 = new LinkedList2_2();
        list1.addInTail(new Node(1));
        list1.addInTail(new Node(2));
        list1.addInTail(new Node(3));
        LinkedList2_2 list2 = new LinkedList2_2();

        LinkedList2_2 result = LinkedList2_2.mergeSortedLists(list1, list2);

        assertEquals(1, result.head.value);
        assertEquals(2, result.head.next.value);
        assertEquals(3, result.tail.value);
    }

    @Test
    public void testMergeSortedListsSingleElements() {
        LinkedList2_2 list1 = new LinkedList2_2();
        list1.addInTail(new Node(1));
        LinkedList2_2 list2 = new LinkedList2_2();
        list2.addInTail(new Node(2));

        LinkedList2_2 result = LinkedList2_2.mergeSortedLists(list1, list2);

        assertEquals(1, result.head.value);
        assertEquals(2, result.tail.value);
    }

    @Test
    public void testMergeSortedListsNonOverlapping() {
        LinkedList2_2 list1 = new LinkedList2_2();
        list1.addInTail(new Node(1));
        list1.addInTail(new Node(3));
        list1.addInTail(new Node(5));

        LinkedList2_2 list2 = new LinkedList2_2();
        list2.addInTail(new Node(2));
        list2.addInTail(new Node(4));
        list2.addInTail(new Node(6));

        LinkedList2_2 result = LinkedList2_2.mergeSortedLists(list1, list2);

        assertEquals(1, result.head.value);
        assertEquals(2, result.head.next.value);
        assertEquals(3, result.head.next.next.value);
        assertEquals(4, result.head.next.next.next.value);
        assertEquals(5, result.head.next.next.next.next.value);
        assertEquals(6, result.tail.value);
    }

    @Test
    public void testMergeSortedListsOverlapping() {
        LinkedList2_2 list1 = new LinkedList2_2();
        list1.addInTail(new Node(1));
        list1.addInTail(new Node(3));
        list1.addInTail(new Node(5));

        LinkedList2_2 list2 = new LinkedList2_2();
        list2.addInTail(new Node(3));
        list2.addInTail(new Node(4));
        list2.addInTail(new Node(5));

        LinkedList2_2 result = LinkedList2_2.mergeSortedLists(list1, list2);

        assertEquals(1, result.head.value);
        assertEquals(3, result.head.next.value);
        assertEquals(3, result.head.next.next.value);
        assertEquals(4, result.head.next.next.next.value);
        assertEquals(5, result.head.next.next.next.next.value);
        assertEquals(5, result.tail.value);
    }

    @Test
    public void testMergeSortedListsDifferentSizes() {
        LinkedList2_2 list1 = new LinkedList2_2();
        list1.addInTail(new Node(1));
        list1.addInTail(new Node(2));

        LinkedList2_2 list2 = new LinkedList2_2();
        list2.addInTail(new Node(3));
        list2.addInTail(new Node(4));
        list2.addInTail(new Node(5));
        list2.addInTail(new Node(6));

        LinkedList2_2 result = LinkedList2_2.mergeSortedLists(list1, list2);

        assertEquals(1, result.head.value);
        assertEquals(2, result.head.next.value);
        assertEquals(3, result.head.next.next.value);
        assertEquals(4, result.head.next.next.next.value);
        assertEquals(5, result.head.next.next.next.next.value);
        assertEquals(6, result.tail.value);
    }

    @Test
    public void testMergeSortedListsWithNegativeValues() {
        LinkedList2_2 list1 = new LinkedList2_2();
        list1.addInTail(new Node(-5));
        list1.addInTail(new Node(0));
        list1.addInTail(new Node(5));

        LinkedList2_2 list2 = new LinkedList2_2();
        list2.addInTail(new Node(-3));
        list2.addInTail(new Node(2));
        list2.addInTail(new Node(7));

        LinkedList2_2 result = LinkedList2_2.mergeSortedLists(list1, list2);

        assertEquals(-5, result.head.value);
        assertEquals(-3, result.head.next.value);
        assertEquals(0, result.head.next.next.value);
        assertEquals(2, result.head.next.next.next.value);
        assertEquals(5, result.head.next.next.next.next.value);
        assertEquals(7, result.tail.value);
    }

    @Test
    public void testMergeSortedListsAllSameValues() {
        LinkedList2_2 list1 = new LinkedList2_2();
        list1.addInTail(new Node(5));
        list1.addInTail(new Node(5));

        LinkedList2_2 list2 = new LinkedList2_2();
        list2.addInTail(new Node(5));
        list2.addInTail(new Node(5));

        LinkedList2_2 result = LinkedList2_2.mergeSortedLists(list1, list2);

        int count = 0;
        Node current = result.head;
        while (current != null) {
            assertEquals(5, current.value);
            count++;
            current = current.next;
        }
        assertEquals(4, count);
    }

    @Test
    public void testMergeSortedListsOriginalListsUnchanged() {
        LinkedList2_2 list1 = new LinkedList2_2();
        list1.addInTail(new Node(1));
        list1.addInTail(new Node(3));

        LinkedList2_2 list2 = new LinkedList2_2();
        list2.addInTail(new Node(2));
        list2.addInTail(new Node(4));

        LinkedList2_2.mergeSortedLists(list1, list2);

        // Verify original lists are unchanged
        assertEquals(1, list1.head.value);
        assertEquals(3, list1.tail.value);
        assertEquals(2, list2.head.value);
        assertEquals(4, list2.tail.value);
    }

    @Test
    public void testMergeSortedListsPrevPointers() {
        LinkedList2_2 list1 = new LinkedList2_2();
        list1.addInTail(new Node(1));
        list1.addInTail(new Node(3));

        LinkedList2_2 list2 = new LinkedList2_2();
        list2.addInTail(new Node(2));
        list2.addInTail(new Node(4));

        LinkedList2_2 result = LinkedList2_2.mergeSortedLists(list1, list2);

        // Check prev pointers
        assertNull(result.head.prev);
        assertNotNull(result.head.next);
        assertEquals(result.head, result.head.next.prev);
        assertNotNull(result.head.next.next);
        assertEquals(result.head.next, result.head.next.next.prev);
        assertNotNull(result.tail.prev);
        assertEquals(result.head.next.next, result.tail.prev);
        assertNull(result.tail.next);
    }

    @Test
    public void testMergeSortedListsLargeLists() {
        LinkedList2_2 list1 = new LinkedList2_2();
        int[] values1 = {1, 3, 5, 7, 9};
        for (int v : values1) {
            list1.addInTail(new Node(v));
        }

        LinkedList2_2 list2 = new LinkedList2_2();
        int[] values2 = {2, 4, 6, 8, 10};
        for (int v : values2) {
            list2.addInTail(new Node(v));
        }

        LinkedList2_2 result = LinkedList2_2.mergeSortedLists(list1, list2);

        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Node current = result.head;
        for (int exp : expected) {
            assertEquals(exp, current.value);
            current = current.next;
        }
        assertNull(current);
    }
}
