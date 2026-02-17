import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class LinkedListTest {

    @Test
    public void testAddInTailEmptyList() {
        LinkedList list = new LinkedList();
        Node node = new Node(1);
        
        list.addInTail(node);
        
        assertEquals(node, list.head);
        assertEquals(node, list.tail);
        assertEquals(1, list.head.value);
    }
    
    @Test
    public void testAddInTailNonEmptyList() {
        LinkedList list = new LinkedList();
        Node firstNode = new Node(1);
        Node secondNode = new Node(2);
        
        list.addInTail(firstNode);
        list.addInTail(secondNode);
        
        assertEquals(firstNode, list.head);
        assertEquals(secondNode, list.tail);
        assertEquals(1, list.head.value);
        assertEquals(2, list.tail.value);
        assertEquals(secondNode, list.head.next);
    }
    
    @Test
    public void testAddInTailMultipleNodes() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        
        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        
        assertEquals(node1, list.head);
        assertEquals(node3, list.tail);
        assertEquals(1, list.head.value);
        assertEquals(3, list.tail.value);
        assertEquals(node2, list.head.next);
        assertEquals(node3, list.head.next.next);
    }
    
    @Test
    public void testFindExistingElementAtHead() {
        LinkedList list = new LinkedList();
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
        LinkedList list = new LinkedList();
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
        LinkedList list = new LinkedList();
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
        LinkedList list = new LinkedList();
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
    public void testFindElementInEmptyList() {
        LinkedList list = new LinkedList();
        
        Node result = list.find(1);
        assertNull(result);
    }
    
    @Test
    public void testFindDuplicateElements() {
        LinkedList list = new LinkedList();
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
        LinkedList list = new LinkedList();
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
    public void testRemoveFromEmptyList() {
        LinkedList list = new LinkedList();
        
        boolean result = list.remove(1);
        
        assertFalse(result);
        assertNull(list.head);
        assertNull(list.tail);
    }
    
    @Test
    public void testRemoveSingleElement() {
        LinkedList list = new LinkedList();
        Node node = new Node(1);
        
        list.addInTail(node);
        
        boolean result = list.remove(1);
        
        assertTrue(result);
        assertNull(list.head);
        assertNull(list.tail);
    }
    
    @Test
    public void testRemoveNonExistingElement() {
        LinkedList list = new LinkedList();
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
        assertEquals(1, list.head.value);
        assertEquals(3, list.tail.value);
    }
    
    @Test
    public void testRemoveElementFromHead() {
        LinkedList list = new LinkedList();
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
        assertEquals(2, list.head.value);
        assertEquals(3, list.tail.value);
    }
    
    @Test
    public void testRemoveElementFromTail() {
        LinkedList list = new LinkedList();
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
        assertEquals(1, list.head.value);
        assertEquals(2, list.tail.value);
    }
    
    @Test
    public void testRemoveElementFromMiddle() {
        LinkedList list = new LinkedList();
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
        assertEquals(1, list.head.value);
        assertEquals(4, list.tail.value);
        assertEquals(node3, list.head.next);
        assertEquals(node4, list.head.next.next);
        assertNull(list.head.next.next.next);
    }
    
    @Test
    public void testRemoveDuplicateElements() {
        LinkedList list = new LinkedList();
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
        assertEquals(1, list.head.value);
        assertEquals(3, list.tail.value);
        assertEquals(node3, list.head.next);
    }
    
    @Test
    public void testRemoveAllOccurrences() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(2);
        Node node4 = new Node(2);
        Node node5 = new Node(3);
        
        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);
        list.addInTail(node5);
        
        boolean result1 = list.remove(2);
        boolean result2 = list.remove(2);
        boolean result3 = list.remove(2);
        
        assertTrue(result1);
        assertTrue(result2);
        assertTrue(result3);
        assertEquals(node1, list.head);
        assertEquals(node5, list.tail);
        assertEquals(1, list.head.value);
        assertEquals(3, list.tail.value);
        assertEquals(node5, list.head.next);
        assertNull(list.head.next.next);
    }
    
    @Test
    public void testRemoveAfterOperations() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        
        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        Node found = list.find(2);
        assertEquals(node2, found);
        
        boolean result = list.remove(2);
        assertTrue(result);

        Node notFound = list.find(2);
        assertNull(notFound);

        assertEquals(node1, list.head);
        assertEquals(node3, list.tail);
        assertEquals(1, list.head.value);
        assertEquals(3, list.tail.value);
    }

    @Test
    public void testRemoveAllFromEmptyList() {
        LinkedList list = new LinkedList();

        list.removeAll(1);

        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testRemoveAllNonExistingElements() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        list.removeAll(5);

        assertEquals(node1, list.head);
        assertEquals(node3, list.tail);
        assertEquals(1, list.head.value);
        assertEquals(3, list.tail.value);
    }

    @Test
    public void testRemoveAllFromMiddle() {
        LinkedList list = new LinkedList();
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
        assertEquals(1, list.head.value);
        assertEquals(3, list.tail.value);
        assertEquals(node4, list.head.next);
        assertNull(list.head.next.next);
    }

    @Test
    public void testRemoveAllFromHead() {
        LinkedList list = new LinkedList();
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
        assertEquals(2, list.head.value);
        assertEquals(3, list.tail.value);
    }

    @Test
    public void testRemoveAllFromTail() {
        LinkedList list = new LinkedList();
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
        assertEquals(1, list.head.value);
        assertEquals(2, list.tail.value);
        assertEquals(node2, list.head.next);
        assertNull(list.head.next.next);
    }

    @Test
    public void testRemoveAllElements() {
        LinkedList list = new LinkedList();
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
        LinkedList list = new LinkedList();
        Node node = new Node(1);

        list.addInTail(node);

        list.removeAll(1);

        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testRemoveAllConsecutiveDuplicates() {
        LinkedList list = new LinkedList();
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
        assertEquals(1, list.head.value);
        assertEquals(4, list.tail.value);
        assertEquals(node5, list.head.next);
        assertEquals(node8, list.head.next.next);
        assertNull(list.head.next.next.next);
    }

    @Test
    public void testRemoveAllWithNegativeValues() {
        LinkedList list = new LinkedList();
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
        assertEquals(-2, list.head.value);
        assertEquals(-3, list.tail.value);
    }

    @Test
    public void testClearEmptyList() {
        LinkedList list = new LinkedList();

        list.clear();

        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testClearSingleElement() {
        LinkedList list = new LinkedList();
        Node node = new Node(1);

        list.addInTail(node);

        list.clear();

        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testClearMultipleElements() {
        LinkedList list = new LinkedList();
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
    public void testClearAfterOperations() {
        LinkedList list = new LinkedList();
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
    public void testFindAllInEmptyList() {
        LinkedList list = new LinkedList();

        ArrayList<Node> result = list.findAll(1);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindAllNonExistingElements() {
        LinkedList list = new LinkedList();
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
        LinkedList list = new LinkedList();
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
        LinkedList list = new LinkedList();
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
        LinkedList list = new LinkedList();
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
        LinkedList list = new LinkedList();
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
        LinkedList list = new LinkedList();
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
        LinkedList list = new LinkedList();
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
    public void testCountEmptyList() {
        LinkedList list = new LinkedList();

        assertEquals(0, list.count());
    }

    @Test
    public void testCountSingleElement() {
        LinkedList list = new LinkedList();
        Node node = new Node(1);

        list.addInTail(node);

        assertEquals(1, list.count());
    }

    @Test
    public void testCountMultipleElements() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        assertEquals(3, list.count());
    }

    @Test
    public void testCountAfterRemove() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        list.remove(2);

        assertEquals(2, list.count());
    }

    @Test
    public void testCountAfterRemoveAll() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(2);
        Node node4 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);

        list.removeAll(2);

        assertEquals(2, list.count());
    }

    @Test
    public void testCountAfterClear() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        list.clear();

        assertEquals(0, list.count());
    }

    @Test
    public void testInsertAfterNullInEmptyList() {
        LinkedList list = new LinkedList();
        Node node = new Node(1);

        list.insertAfter(null, node);

        assertEquals(node, list.head);
        assertEquals(node, list.tail);
        assertEquals(1, list.head.value);
    }

    @Test
    public void testInsertAfterNullInNonEmptyList() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        Node newNode = new Node(0);
        list.insertAfter(null, newNode);

        assertEquals(newNode, list.head);
        assertEquals(node1, list.head.next);
        assertEquals(0, list.head.value);
        assertEquals(1, list.head.next.value);
    }

    @Test
    public void testInsertAfterMiddle() {
        LinkedList list = new LinkedList();
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
        assertEquals(node2, list.head.next);
        assertEquals(newNode, list.head.next.next);
        assertEquals(node3, list.head.next.next.next);
    }

    @Test
    public void testInsertAfterTail() {
        LinkedList list = new LinkedList();
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
        assertEquals(node3, list.head.next.next);
        assertEquals(newNode, list.head.next.next.next);
    }

    @Test
    public void testInsertAfterSingleElement() {
        LinkedList list = new LinkedList();
        Node node1 = new Node(1);

        list.addInTail(node1);

        Node newNode = new Node(2);
        list.insertAfter(node1, newNode);

        assertEquals(node1, list.head);
        assertEquals(newNode, list.tail);
        assertEquals(newNode, list.head.next);
    }

    @Test
    public void testInsertMultipleAfterDifferentNodes() {
        LinkedList list = new LinkedList();
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

        assertEquals(5, list.count());
        assertEquals(node1, list.head);
        assertEquals(newNode1, list.head.next);
        assertEquals(node2, list.head.next.next);
        assertEquals(node3, list.head.next.next.next);
        assertEquals(newNode2, list.tail);
    }
}

