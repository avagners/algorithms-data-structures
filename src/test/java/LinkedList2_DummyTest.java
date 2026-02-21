import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class LinkedList2_DummyTest {

    @Test
    public void testAddInTailEmptyList() {
        LinkedList2_Dummy list = new LinkedList2_Dummy();
        Node node = new Node(1);

        list.addInTail(node);

        assertEquals(node, list.head);
        assertEquals(node, list.tail);
        assertEquals(1, list.head.value);
    }

    @Test
    public void testAddInTailMultipleNodes() {
        LinkedList2_Dummy list = new LinkedList2_Dummy();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        assertEquals(node1, list.head);
        assertEquals(node3, list.tail);
        assertEquals(node2, node1.next);
        assertEquals(node1, node2.prev);
        assertEquals(node3, node2.next);
        assertEquals(node2, node3.prev);
    }

    @Test
    public void testRemoveFromEmptyList() {
        LinkedList2_Dummy list = new LinkedList2_Dummy();

        boolean result = list.remove(1);

        assertFalse(result);
        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testRemoveSingleElement() {
        LinkedList2_Dummy list = new LinkedList2_Dummy();
        Node node = new Node(1);
        list.addInTail(node);

        boolean result = list.remove(1);

        assertTrue(result);
        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testRemoveFromHeadNoSpecialCase() {
        LinkedList2_Dummy list = new LinkedList2_Dummy();
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
    }

    @Test
    public void testRemoveFromTailNoSpecialCase() {
        LinkedList2_Dummy list = new LinkedList2_Dummy();
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
    }

    @Test
    public void testRemoveFromMiddle() {
        LinkedList2_Dummy list = new LinkedList2_Dummy();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        boolean result = list.remove(2);

        assertTrue(result);
        assertEquals(node1, list.head);
        assertEquals(node3, list.tail);
        assertEquals(node3, node1.next);
        assertEquals(node1, node3.prev);
    }

    @Test
    public void testRemoveAllFromEmptyList() {
        LinkedList2_Dummy list = new LinkedList2_Dummy();

        list.removeAll(1);

        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testRemoveAllElements() {
        LinkedList2_Dummy list = new LinkedList2_Dummy();
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
    public void testRemoveAllFromHeadAndMiddle() {
        LinkedList2_Dummy list = new LinkedList2_Dummy();
        Node node1 = new Node(1);
        Node node2 = new Node(1);
        Node node3 = new Node(2);
        Node node4 = new Node(1);
        Node node5 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);
        list.addInTail(node4);
        list.addInTail(node5);

        list.removeAll(1);

        assertEquals(node3, list.head);
        assertEquals(node5, list.tail);
        assertEquals(2, list.count());
    }

    @Test
    public void testClearEmptyList() {
        LinkedList2_Dummy list = new LinkedList2_Dummy();

        list.clear();

        assertNull(list.head);
        assertNull(list.tail);
    }

    @Test
    public void testClearMultipleElements() {
        LinkedList2_Dummy list = new LinkedList2_Dummy();
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
    public void testClearAndReUse() {
        LinkedList2_Dummy list = new LinkedList2_Dummy();
        Node node1 = new Node(1);
        list.addInTail(node1);

        list.clear();

        Node newNode = new Node(10);
        list.addInTail(newNode);

        assertEquals(newNode, list.head);
        assertEquals(newNode, list.tail);
    }

    @Test
    public void testFindInEmptyList() {
        LinkedList2_Dummy list = new LinkedList2_Dummy();

        Node result = list.find(1);

        assertNull(result);
    }

    @Test
    public void testFindExistingElement() {
        LinkedList2_Dummy list = new LinkedList2_Dummy();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        Node result = list.find(2);

        assertEquals(node2, result);
    }

    @Test
    public void testFindAllInEmptyList() {
        LinkedList2_Dummy list = new LinkedList2_Dummy();

        ArrayList<Node> result = list.findAll(1);

        assertTrue(result.isEmpty());
    }

    @Test
    public void testFindAllMultipleOccurrences() {
        LinkedList2_Dummy list = new LinkedList2_Dummy();
        Node node1 = new Node(2);
        Node node2 = new Node(1);
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
        assertEquals(node1, result.get(0));
        assertEquals(node3, result.get(1));
        assertEquals(node5, result.get(2));
    }

    @Test
    public void testCountEmptyList() {
        LinkedList2_Dummy list = new LinkedList2_Dummy();

        assertEquals(0, list.count());
    }

    @Test
    public void testCountMultipleElements() {
        LinkedList2_Dummy list = new LinkedList2_Dummy();
        list.addInTail(new Node(1));
        list.addInTail(new Node(2));
        list.addInTail(new Node(3));

        assertEquals(3, list.count());
    }

    @Test
    public void testInsertAfterNullInEmptyList() {
        LinkedList2_Dummy list = new LinkedList2_Dummy();
        Node node = new Node(1);

        list.insertAfter(null, node);

        assertEquals(node, list.head);
        assertEquals(node, list.tail);
    }

    @Test
    public void testInsertAfterNullInNonEmptyList() {
        LinkedList2_Dummy list = new LinkedList2_Dummy();
        Node node1 = new Node(1);
        Node node2 = new Node(2);

        list.addInTail(node1);
        list.addInTail(node2);

        Node newNode = new Node(0);
        list.insertAfter(null, newNode);

        assertEquals(newNode, list.head);
        assertEquals(node1, newNode.next);
        assertEquals(newNode, node1.prev);
    }

    @Test
    public void testInsertAfterMiddle() {
        LinkedList2_Dummy list = new LinkedList2_Dummy();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        Node newNode = new Node(5);
        list.insertAfter(node2, newNode);

        assertEquals(newNode, node2.next);
        assertEquals(node3, newNode.next);
        assertEquals(node2, newNode.prev);
        assertEquals(newNode, node3.prev);
    }

    @Test
    public void testDummyNodesNotExposed() {
        LinkedList2_Dummy list = new LinkedList2_Dummy();

        assertNull(list.head);
        assertNull(list.tail);

        list.addInTail(new Node(1));

        assertTrue(list.head instanceof Node);
        assertFalse(list.head instanceof DummyNode);
        assertTrue(list.tail instanceof Node);
        assertFalse(list.tail instanceof DummyNode);
    }

    @Test
    public void testNoSpecialCaseForHeadRemoval() {
        LinkedList2_Dummy list = new LinkedList2_Dummy();
        list.addInTail(new Node(1));
        list.addInTail(new Node(2));
        list.addInTail(new Node(3));

        boolean result = list.remove(1);

        assertTrue(result);
        assertEquals(2, list.head.value);
        assertEquals(2, list.count());
    }

    @Test
    public void testNoSpecialCaseForTailRemoval() {
        LinkedList2_Dummy list = new LinkedList2_Dummy();
        list.addInTail(new Node(1));
        list.addInTail(new Node(2));
        list.addInTail(new Node(3));

        boolean result = list.remove(3);

        assertTrue(result);
        assertEquals(2, list.tail.value);
        assertEquals(2, list.count());
    }

    @Test
    public void testNoSpecialCaseForOnlyElementRemoval() {
        LinkedList2_Dummy list = new LinkedList2_Dummy();
        list.addInTail(new Node(1));

        boolean result = list.remove(1);

        assertTrue(result);
        assertNull(list.head);
        assertNull(list.tail);
        assertEquals(0, list.count());
    }

    @Test
    public void testNoSpecialCaseForInsertAtBeginning() {
        LinkedList2_Dummy list = new LinkedList2_Dummy();
        list.addInTail(new Node(1));
        list.addInTail(new Node(2));

        Node newNode = new Node(0);
        list.insertAfter(null, newNode);

        assertEquals(0, list.head.value);
        assertEquals(3, list.count());
    }

    @Test
    public void testPrevLinksConsistentWithDummyNodes() {
        LinkedList2_Dummy list = new LinkedList2_Dummy();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        assertTrue(node1.prev instanceof DummyNode);
        assertEquals(node1, node2.prev);
        assertEquals(node2, node3.prev);
    }

    @Test
    public void testNextLinksConsistentWithDummyNodes() {
        LinkedList2_Dummy list = new LinkedList2_Dummy();
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);

        list.addInTail(node1);
        list.addInTail(node2);
        list.addInTail(node3);

        assertEquals(node2, node1.next);
        assertEquals(node3, node2.next);
        assertTrue(node3.next instanceof DummyNode);
    }
}
