import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
        Node node3 = new Node(2); // Duplicate value
        Node node4 = new Node(3);
        
        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);
        
        // Should return the first occurrence of value 2
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
}
