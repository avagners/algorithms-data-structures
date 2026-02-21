import java.util.*;

// LinkedList with dummy nodes - eliminates special cases for head/tail operations
public class LinkedList2_Dummy
{
     private DummyNode dummyHead;
     private DummyNode dummyTail;

     public Node head;
     public Node tail;

     public LinkedList2_Dummy()
     {
       dummyHead = new DummyNode();
       dummyTail = new DummyNode();
       dummyHead.next = dummyTail;
       dummyHead.prev = null;
       dummyTail.prev = dummyHead;
       dummyTail.next = null;
       head = null;
       tail = null;
     }

     // 2-13; addInTail; Time: O(1), Space: O(1)
     public void addInTail(Node _item)
     {
       _item.next = dummyTail;
       _item.prev = dummyTail.prev;
       dummyTail.prev.next = _item;
       dummyTail.prev = _item;

       tail = _item;

       if (head == null) {
         head = _item;
       }
     }

     // 2-13; remove; Time: O(n), Space: O(1)
     public boolean remove(int _value)
     {
       Node current = dummyHead.next;

       while (current != dummyTail) {
         if (current.value == _value) {
           current.prev.next = current.next;
           current.next.prev = current.prev;

           if (current == head) {
             head = (head.next == dummyTail) ? null : head.next;
           }
           if (current == tail) {
             tail = (tail.prev == dummyHead) ? null : tail.prev;
           }

           current.next = null;
           current.prev = null;

           return true;
         }
         current = current.next;
       }

       return false;
     }

     // 2-13; removeAll; Time: O(n), Space: O(1)
     public void removeAll(int _value)
     {
       Node current = dummyHead.next;

       while (current != dummyTail) {
         if (current.value == _value) {
           Node nextNode = current.next;
           current.prev.next = current.next;
           current.next.prev = current.prev;
           current.next = null;
           current.prev = null;
           current = nextNode;
         } else {
           current = current.next;
         }
       }

       head = (dummyHead.next == dummyTail) ? null : dummyHead.next;
       tail = (dummyTail.prev == dummyHead) ? null : dummyTail.prev;
     }

     // 2-13; clear; Time: O(1), Space: O(1)
     public void clear()
     {
       dummyHead.next = dummyTail;
       dummyTail.prev = dummyHead;
       head = null;
       tail = null;
     }

     // 2-1; find; Time: O(n), Space: O(1)
     public Node find(int _value)
     {
       Node current = dummyHead.next;

       while (current != dummyTail) {
         if (current.value == _value) {
           return current;
         }
         current = current.next;
       }

       return null;
     }

     // 2-2; findAll; Time: O(n), Space: O(k)
     public ArrayList<Node> findAll(int _value)
     {
       ArrayList<Node> nodes = new ArrayList<>();
       Node current = dummyHead.next;

       while (current != dummyTail) {
         if (current.value == _value) {
           nodes.add(current);
         }
         current = current.next;
       }

       return nodes;
     }

     // 2-13; count; Time: O(n), Space: O(1)
     public int count()
     {
       int count = 0;
       Node current = dummyHead.next;

       while (current != dummyTail) {
         count++;
         current = current.next;
       }

       return count;
     }

     // 2-13; insertAfter; Time: O(1), Space: O(1)
     public void insertAfter(Node _nodeAfter, Node _nodeToInsert)
     {
       _nodeToInsert.next = null;
       _nodeToInsert.prev = null;

       if (_nodeAfter == null) {
         _nodeToInsert.prev = dummyHead;
         _nodeToInsert.next = dummyHead.next;
         dummyHead.next.prev = _nodeToInsert;
         dummyHead.next = _nodeToInsert;

         head = _nodeToInsert;

         if (tail == null) {
           tail = _nodeToInsert;
         }
       } else {
         _nodeToInsert.next = _nodeAfter.next;
         _nodeToInsert.prev = _nodeAfter;
         _nodeAfter.next.prev = _nodeToInsert;
         _nodeAfter.next = _nodeToInsert;

         if (_nodeAfter == tail) {
           tail = _nodeToInsert;
         }
       }
     }
}

// Dummy node - marks boundaries, not visible to user
class DummyNode extends Node
{
     public DummyNode()
     {
       super(Integer.MIN_VALUE);
     }

     @Override
     public String toString()
     {
       return "DummyNode";
     }
}

