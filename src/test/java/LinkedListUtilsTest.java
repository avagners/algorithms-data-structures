import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LinkedListUtilsTest {

    @Test
    public void testSumListsEmptyLists() {
        LinkedList list1 = new LinkedList();
        LinkedList list2 = new LinkedList();

        LinkedList result = LinkedListUtils.sumLists(list1, list2);

        assertNotNull(result);
        assertEquals(0, result.count());
        assertNull(result.head);
        assertNull(result.tail);
    }

    @Test
    public void testSumListsSingleElement() {
        LinkedList list1 = new LinkedList();
        LinkedList list2 = new LinkedList();

        list1.addInTail(new Node(5));
        list2.addInTail(new Node(3));

        LinkedList result = LinkedListUtils.sumLists(list1, list2);

        assertNotNull(result);
        assertEquals(1, result.count());
        assertEquals(8, result.head.value);
        assertEquals(8, result.tail.value);
    }

    @Test
    public void testSumListsMultipleElements() {
        LinkedList list1 = new LinkedList();
        LinkedList list2 = new LinkedList();

        list1.addInTail(new Node(1));
        list1.addInTail(new Node(2));
        list1.addInTail(new Node(3));

        list2.addInTail(new Node(4));
        list2.addInTail(new Node(5));
        list2.addInTail(new Node(6));

        LinkedList result = LinkedListUtils.sumLists(list1, list2);

        assertNotNull(result);
        assertEquals(3, result.count());
        assertEquals(5, result.head.value);
        assertEquals(7, result.head.next.value);
        assertEquals(9, result.tail.value);
    }

    @Test
    public void testSumListsDifferentLengths() {
        LinkedList list1 = new LinkedList();
        LinkedList list2 = new LinkedList();

        list1.addInTail(new Node(1));
        list1.addInTail(new Node(2));

        list2.addInTail(new Node(4));
        list2.addInTail(new Node(5));
        list2.addInTail(new Node(6));

        LinkedList result = LinkedListUtils.sumLists(list1, list2);

        assertNull(result);
    }

    @Test
    public void testSumListsWithNegativeValues() {
        LinkedList list1 = new LinkedList();
        LinkedList list2 = new LinkedList();

        list1.addInTail(new Node(-1));
        list1.addInTail(new Node(-2));
        list1.addInTail(new Node(3));

        list2.addInTail(new Node(1));
        list2.addInTail(new Node(2));
        list2.addInTail(new Node(-3));

        LinkedList result = LinkedListUtils.sumLists(list1, list2);

        assertNotNull(result);
        assertEquals(3, result.count());
        assertEquals(0, result.head.value);
        assertEquals(0, result.head.next.value);
        assertEquals(0, result.tail.value);
    }

    @Test
    public void testSumListsWithZero() {
        LinkedList list1 = new LinkedList();
        LinkedList list2 = new LinkedList();

        list1.addInTail(new Node(0));
        list1.addInTail(new Node(0));
        list1.addInTail(new Node(0));

        list2.addInTail(new Node(5));
        list2.addInTail(new Node(10));
        list2.addInTail(new Node(15));

        LinkedList result = LinkedListUtils.sumLists(list1, list2);

        assertNotNull(result);
        assertEquals(3, result.count());
        assertEquals(5, result.head.value);
        assertEquals(10, result.head.next.value);
        assertEquals(15, result.tail.value);
    }

    @Test
    public void testSumListsOriginalListsUnchanged() {
        LinkedList list1 = new LinkedList();
        LinkedList list2 = new LinkedList();

        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);

        list1.addInTail(node1);
        list1.addInTail(node2);

        list2.addInTail(node3);
        list2.addInTail(node4);

        LinkedList result = LinkedListUtils.sumLists(list1, list2);

        assertNotNull(result);
        assertEquals(2, list1.count());
        assertEquals(2, list2.count());
        assertEquals(node1, list1.head);
        assertEquals(node2, list1.tail);
        assertEquals(node3, list2.head);
        assertEquals(node4, list2.tail);
    }

    @Test
    public void testSumListsWithLargeValues() {
        LinkedList list1 = new LinkedList();
        LinkedList list2 = new LinkedList();

        list1.addInTail(new Node(1000000));
        list1.addInTail(new Node(2000000));

        list2.addInTail(new Node(3000000));
        list2.addInTail(new Node(4000000));

        LinkedList result = LinkedListUtils.sumLists(list1, list2);

        assertNotNull(result);
        assertEquals(2, result.count());
        assertEquals(4000000, result.head.value);
        assertEquals(6000000, result.tail.value);
    }
}

