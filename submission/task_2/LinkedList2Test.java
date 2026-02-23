import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class LinkedList2Test {

    @Test
    public void testFindInEmptyList() {
        LinkedList2 list = new LinkedList2();

        Node result = list.find(1);
        assertNull(result);
    }

    @Test
    public void testFindExistingElementAtHead() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        Node result = list.find(1);
        assertEquals(node1, result);
        assertEquals(1, result.value);
    }

    @Test
    public void testFindExistingElementInMiddle() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        Node result = list.find(2);
        assertEquals(node2, result);
        assertEquals(2, result.value);
    }

    @Test
    public void testFindExistingElementAtTail() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        Node result = list.find(3);
        assertEquals(node3, result);
        assertEquals(3, result.value);
    }

    @Test
    public void testFindNonExistingElement() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        Node result = list.find(4);
        assertNull(result);
    }

    @Test
    public void testFindDuplicateElements() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(2);
        Node node4 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);

        Node result = list.find(2);
        assertEquals(node2, result);
        assertEquals(2, result.value);
    }

    @Test
    public void testFindWithNegativeValues() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(-1);
        Node node2 = new Node(-2);
        Node node3 = new Node(0);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        Node result = list.find(-2);
        assertEquals(node2, result);
        assertEquals(-2, result.value);
    }

    @Test
    public void testFindSingleElement() {
        LinkedList2 list = new LinkedList2();
        Node node = new Node(42);

        list.addInTail(node);

        Node result = list.find(42);
        assertEquals(node, result);
        assertEquals(42, result.value);
    }

    @Test
    public void testFindWithZero() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(-1);
        Node node2 = new Node(0);
        Node node3 = new Node(1);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        Node result = list.find(0);
        assertEquals(node2, result);
        assertEquals(0, result.value);
    }

    @Test
    public void testFindAllInEmptyList() {
        LinkedList2 list = new LinkedList2();

        ArrayList<Node> result = list.findAll(1);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindAllNonExistingElements() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        ArrayList<Node> result = list.findAll(5);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindAllSingleOccurrence() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        ArrayList<Node> result = list.findAll(2);

        assertEquals(1, result.size());
        assertEquals(node2, result.get(0));
    }

    @Test
    public void testFindAllMultipleOccurrences() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(2);
        Node node4 = new Node(3);
        Node node5 = new Node(2);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);
        list.addInTail(node5);

        ArrayList<Node> result = list.findAll(2);

        assertEquals(3, result.size());
        assertEquals(node2, result.get(0));
        assertEquals(node3, result.get(1));
        assertEquals(node5, result.get(2));
    }

    @Test
    public void testFindAllAtHead() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(1);
        Node node3 = new Node(2);
        Node node4 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);

        ArrayList<Node> result = list.findAll(1);

        assertEquals(2, result.size());
        assertEquals(node1, result.get(0));
        assertEquals(node2, result.get(1));
    }

    @Test
    public void testFindAllAtTail() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);

        ArrayList<Node> result = list.findAll(3);

        assertEquals(2, result.size());
        assertEquals(node3, result.get(0));
        assertEquals(node4, result.get(1));
    }

    @Test
    public void testFindAllWithNegativeValues() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(-1);
        Node node2 = new Node(-2);
        Node node3 = new Node(-1);
        Node node4 = new Node(-3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);

        ArrayList<Node> result = list.findAll(-1);

        assertEquals(2, result.size());
        assertEquals(node1, result.get(0));
        assertEquals(node3, result.get(1));
    }

    @Test
    public void testFindAllWithZero() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(0);
        Node node2 = new Node(1);
        Node node3 = new Node(0);
        Node node4 = new Node(2);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);

        ArrayList<Node> result = list.findAll(0);

        assertEquals(2, result.size());
        assertEquals(node1, result.get(0));
        assertEquals(node3, result.get(1));
    }

    @Test
    public void testRemoveFromEmptyList() {
        LinkedList2 list = new LinkedList2();

        boolean result = list.remove(1);

        assertFalse(result);
        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testRemoveSingleElement() {
        LinkedList2 list = new LinkedList2();
        Node node = new Node(1);

        list.addInTail(node);

        boolean result = list.remove(1);

        assertTrue(result);
        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testRemoveNonExistingElement() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        boolean result = list.remove(4);

        assertFalse(result);
        assertEquals(node1, list.head);
        assertEquals(node3, list.tail);
    }

    @Test
    public void testRemoveElementFromHead() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        boolean result = list.remove(1);

        assertTrue(result);
        assertEquals(node2, list.head);
        assertEquals(node3, list.tail);
        assertNull(list.head.prev);
    }

    @Test
    public void testRemoveElementFromTail() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        boolean result = list.remove(3);

        assertTrue(result);
        assertEquals(node1, list.head);
        assertEquals(node2, list.tail);
        assertNull(list.tail.next);
    }

    @Test
    public void testRemoveElementFromMiddle() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);

        boolean result = list.remove(2);

        assertTrue(result);
        assertEquals(node1, list.head);
        assertEquals(node4, list.tail);
        assertEquals(node3, node1.next);
        assertEquals(node1, node3.prev);
    }

    @Test
    public void testRemoveDuplicateElements() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(2);
        Node node4 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);

        boolean result = list.remove(2);

        assertTrue(result);
        assertEquals(node1, list.head);
        assertEquals(node4, list.tail);
        assertEquals(node3, node1.next);
    }

    @Test
    public void testRemoveWithNegativeValues() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(-1);
        Node node2 = new Node(-2);
        Node node3 = new Node(-3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        boolean result = list.remove(-2);

        assertTrue(result);
        assertEquals(node1, list.head);
        assertEquals(node3, list.tail);
        assertEquals(node3, node1.next);
    }

    @Test
    public void testAddInTailSingleElementPrevNull() {
        LinkedList2 list = new LinkedList2();
        Node node = new Node(1);

        list.addInTail(node);

        assertEquals(node, list.head);
        assertEquals(node, list.tail);
        assertNull(node.prev);
        assertNull(node.next);
    }

    @Test
    public void testAddInTailMultipleElementsPrevLinks() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        assertEquals(node1, list.head);
        assertEquals(node3, list.tail);
        assertNull(node1.prev);
        assertEquals(node2, node1.next);
        assertEquals(node1, node2.prev);
        assertEquals(node3, node2.next);
        assertEquals(node2, node3.prev);
        assertNull(node3.next);
    }

    @Test
    public void testRemoveOnlyElement() {
        LinkedList2 list = new LinkedList2();
        Node node = new Node(5);
        list.addInTail(node);

        boolean result = list.remove(5);

        assertTrue(result);
        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testRemoveFromTwoElementsListHead() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);

        list.addInTail(node1);
        list.addInTail(node2);

        boolean result = list.remove(1);

        assertTrue(result);
        assertEquals(node2, list.head);
        assertEquals(node2, list.tail);
        assertNull(node2.prev);
        assertNull(node2.next);
    }

    @Test
    public void testRemoveFromTwoElementsListTail() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);

        list.addInTail(node1);
        list.addInTail(node2);

        boolean result = list.remove(2);

        assertTrue(result);
        assertEquals(node1, list.head);
        assertEquals(node1, list.tail);
        assertNull(node1.prev);
        assertNull(node1.next);
    }

    @Test
    public void testRemoveMiddleElementPrevNextLinks() {
        LinkedList2 list = new LinkedList2();
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

        boolean result = list.remove(3);

        assertTrue(result);
        assertEquals(node1, list.head);
        assertEquals(node5, list.tail);
        assertEquals(node4, node2.next);
        assertEquals(node2, node4.prev);
    }

    @Test
    public void testFindAllEmptyResult() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);

        list.addInTail(node1);
        list.addInTail(node2);

        ArrayList<Node> result = list.findAll(999);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindAllSingleElementMatch() {
        LinkedList2 list = new LinkedList2();
        Node node = new Node(42);
        list.addInTail(node);

        ArrayList<Node> result = list.findAll(42);

        assertEquals(1, result.size());
        assertEquals(node, result.get(0));
    }

    @Test
    public void testFindAllSingleElementNoMatch() {
        LinkedList2 list = new LinkedList2();
        Node node = new Node(42);
        list.addInTail(node);

        ArrayList<Node> result = list.findAll(100);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testRemoveAllElementsOneByOne() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        list.remove(1);
        list.remove(2);
        list.remove(3);

        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testAddInTailAfterRemove() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);

        list.addInTail(node1);
        list.addInTail(node2);
        list.remove(1);

        Node node3 = new Node(3);
        list.addInTail(node3);

        assertEquals(node2, list.head);
        assertEquals(node3, list.tail);
        assertNull(node2.prev);
        assertEquals(node3, node2.next);
        assertEquals(node2, node3.prev);
        assertNull(node3.next);
    }

    @Test
    public void testRemoveAllFromEmptyList() {
        LinkedList2 list = new LinkedList2();

        list.removeAll(1);

        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testRemoveAllNonExistingElements() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        list.removeAll(5);

        assertEquals(node1, list.head);
        assertEquals(node3, list.tail);
        assertEquals(3, list.count());
    }

    @Test
    public void testRemoveAllFromMiddle() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(2);
        Node node4 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);

        list.removeAll(2);

        assertEquals(node1, list.head);
        assertEquals(node4, list.tail);
        assertEquals(node4, node1.next);
        assertEquals(node1, node4.prev);
    }

    @Test
    public void testRemoveAllFromHead() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(1);
        Node node3 = new Node(2);
        Node node4 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);

        list.removeAll(1);

        assertEquals(node3, list.head);
        assertEquals(node4, list.tail);
        assertNull(node3.prev);
        assertEquals(node4, node3.next);
        assertEquals(node3, node4.prev);
    }

    @Test
    public void testRemoveAllFromTail() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);

        list.removeAll(3);

        assertEquals(node1, list.head);
        assertEquals(node2, list.tail);
        assertNull(node2.next);
        assertEquals(node2, node1.next);
        assertEquals(node1, node2.prev);
    }

    @Test
    public void testRemoveAllElements() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(2);
        Node node2 = new Node(2);
        Node node3 = new Node(2);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        list.removeAll(2);

        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testRemoveAllSingleElement() {
        LinkedList2 list = new LinkedList2();
        Node node = new Node(1);

        list.addInTail(node);

        list.removeAll(1);

        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testRemoveAllConsecutiveDuplicates() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(2);
        Node node4 = new Node(2);
        Node node5 = new Node(3);
        Node node6 = new Node(2);
        Node node7 = new Node(2);
        Node node8 = new Node(4);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);
        list.addInTail(node5);
        list.addInTail(node6);
        list.addInTail(node7);
        list.addInTail(node8);

        list.removeAll(2);

        assertEquals(node1, list.head);
        assertEquals(node8, list.tail);
        assertEquals(node5, node1.next);
        assertEquals(node8, node5.next);
        assertEquals(node1, node5.prev);
        assertEquals(node5, node8.prev);
        assertNull(node8.next);
    }

    @Test
    public void testRemoveAllWithNegativeValues() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(-1);
        Node node2 = new Node(-2);
        Node node3 = new Node(-1);
        Node node4 = new Node(-3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);

        list.removeAll(-1);

        assertEquals(node2, list.head);
        assertEquals(node4, list.tail);
        assertEquals(node4, node2.next);
        assertEquals(node2, node4.prev);
    }

    @Test
    public void testRemoveAllAlternatingValues() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(1);
        Node node4 = new Node(2);
        Node node5 = new Node(1);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);
        list.addInTail(node5);

        list.removeAll(1);

        assertEquals(node2, list.head);
        assertEquals(node4, list.tail);
        assertEquals(node4, node2.next);
        assertEquals(node2, node4.prev);
        assertNull(node2.prev);
        assertNull(node4.next);
    }

    @Test
    public void testRemoveAllFromTwoElementsBothMatch() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(5);
        Node node2 = new Node(5);

        list.addInTail(node1);
        list.addInTail(node2);

        list.removeAll(5);

        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testRemoveAllFromTwoElementsOneMatch() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(5);
        Node node2 = new Node(10);

        list.addInTail(node1);
        list.addInTail(node2);

        list.removeAll(5);

        assertEquals(node2, list.head);
        assertEquals(node2, list.tail);
        assertNull(node2.prev);
        assertNull(node2.next);
    }

    @Test
    public void testInsertAfterNullInEmptyList() {
        LinkedList2 list = new LinkedList2();
        Node node = new Node(1);

        list.insertAfter(null, node);

        assertEquals(node, list.head);
        assertEquals(node, list.tail);
        assertNull(node.prev);
        assertNull(node.next);
    }

    @Test
    public void testInsertAfterNullInNonEmptyList() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        Node newNode = new Node(0);
        list.insertAfter(null, newNode);

        assertEquals(newNode, list.head);
        assertEquals(node1, newNode.next);
        assertEquals(newNode, node1.prev);
        assertNull(newNode.prev);
    }

    @Test
    public void testInsertAfterMiddle() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        Node newNode = new Node(5);
        list.insertAfter(node2, newNode);

        assertEquals(node1, list.head);
        assertEquals(node3, list.tail);
        assertEquals(newNode, node2.next);
        assertEquals(node3, newNode.next);
        assertEquals(node2, newNode.prev);
        assertEquals(newNode, node3.prev);
    }

    @Test
    public void testInsertAfterTail() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        Node newNode = new Node(4);
        list.insertAfter(node3, newNode);

        assertEquals(node1, list.head);
        assertEquals(newNode, list.tail);
        assertEquals(newNode, node3.next);
        assertEquals(node3, newNode.prev);
        assertNull(newNode.next);
    }

    @Test
    public void testInsertAfterSingleElement() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);

        list.addInTail(node1);

        Node newNode = new Node(2);
        list.insertAfter(node1, newNode);

        assertEquals(node1, list.head);
        assertEquals(newNode, list.tail);
        assertEquals(newNode, node1.next);
        assertEquals(node1, newNode.prev);
        assertNull(newNode.next);
    }

    @Test
    public void testInsertMultipleAfterDifferentNodes() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        Node newNode1 = new Node(10);
        Node newNode2 = new Node(20);

        list.insertAfter(node1, newNode1);
        list.insertAfter(node3, newNode2);

        assertEquals(node1, list.head);
        assertEquals(newNode2, list.tail);
        assertEquals(newNode1, node1.next);
        assertEquals(node2, newNode1.next);
        assertEquals(newNode1, node2.prev);
        assertEquals(newNode2, node3.next);
        assertEquals(node3, newNode2.prev);
        assertNull(newNode2.next);
    }

    @Test
    public void testInsertAfterNullToEmptyListPrevLinks() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);

        list.insertAfter(null, node1);
        list.insertAfter(null, node2);

        assertEquals(node2, list.head);
        assertEquals(node1, list.tail);
        assertEquals(node1, node2.next);
        assertEquals(node2, node1.prev);
        assertNull(node2.prev);
        assertNull(node1.next);
    }

    @Test
    public void testInsertAfterAllLinksIntegrity() {
        LinkedList2 list = new LinkedList2();
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

        Node newNode = new Node(99);
        list.insertAfter(node3, newNode);

        // Проверяем все связи
        assertEquals(node1, list.head);
        assertEquals(node5, list.tail);
        
        // node1 <-> node2
        assertEquals(node2, node1.next);
        assertEquals(node1, node2.prev);
        
        // node2 <-> node3
        assertEquals(node3, node2.next);
        assertEquals(node2, node3.prev);
        
        // node3 <-> newNode
        assertEquals(newNode, node3.next);
        assertEquals(node3, newNode.prev);

        // newNode <-> node4
        assertEquals(node4, newNode.next);
        assertEquals(newNode, node4.prev);

        // node4 <-> node5
        assertEquals(node5, node4.next);
        assertEquals(node4, node5.prev);

        assertNull(node1.prev);
        assertNull(node5.next);
    }

    @Test
    public void testClearEmptyList() {
        LinkedList2 list = new LinkedList2();

        list.clear();

        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testClearSingleElement() {
        LinkedList2 list = new LinkedList2();
        Node node = new Node(1);

        list.addInTail(node);

        list.clear();

        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testClearMultipleElements() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        list.clear();

        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testClearAfterRemove() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        list.remove(2);

        list.clear();

        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testClearAfterRemoveAll() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(2);
        Node node4 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);

        list.removeAll(2);

        list.clear();

        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testClearAfterInsertAfter() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        Node newNode = new Node(99);
        list.insertAfter(node2, newNode);

        list.clear();

        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testClearAndReUse() {
        LinkedList2 list = new LinkedList2();
        Node node1 = new Node(1);
        Node node2 = new Node(2);

        list.addInTail(node1);
        list.addInTail(node2);

        list.clear();

        Node newNode1 = new Node(10);
        Node newNode2 = new Node(20);
        list.addInTail(newNode1);
        list.addInTail(newNode2);

        assertEquals(newNode1, list.head);
        assertEquals(newNode2, list.tail);
        assertNull(newNode1.prev);
        assertEquals(newNode1, newNode2.prev);
        assertNull(newNode2.next);
    }
}

