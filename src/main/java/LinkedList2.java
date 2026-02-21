import java.util.*;

public class LinkedList2
{
     public Node head;
     public Node tail;

     public LinkedList2()
     {
       head = null;
       tail = null;
     }

     public void addInTail(Node _item)
     {
       if (head == null) {
        this.head = _item;
        this.head.next = null;
        this.head.prev = null;
       } else {
        this.tail.next = _item;
        _item.prev = tail;
       }
       this.tail = _item;
     }

     // 2-1; find; Time: O(n), Space: O(1)
     public Node find(int _value)
     {
       Node current = head;
       while (current != null) {
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
       ArrayList<Node> nodes = new ArrayList<Node>();
       Node current = head;
       while (current != null) {
         if (current.value == _value) {
           nodes.add(current);
         }
         current = current.next;
       }
       return nodes;
     }

     // 2-3; remove; Time: O(n), Space: O(1)
     public boolean remove(int _value)
     {
       if (head == null) {
         return false;
       }

       if (head.value == _value) {
         head = head.next;
         if (head != null) {
           head.prev = null;
         } else {
           tail = null;
         }
         return true;
       }

       Node current = head;
       while (current.next != null) {
         if (current.next.value == _value) {
           Node nodeToRemove = current.next;
           current.next = nodeToRemove.next;
           if (nodeToRemove.next != null) {
             nodeToRemove.next.prev = current;
           } else {
             tail = current;
           }
           return true;
         }
         current = current.next;
       }
       return false;
     }

     // 2-4; removeAll; Time: O(n), Space: O(1)
     public void removeAll(int _value)
     {
       while (head != null && head.value == _value) {
         head = head.next;
         if (head != null) {
           head.prev = null;
         }
       }

       if (head == null) {
         tail = null;
         return;
       }

       Node current = head;
       while (current.next != null) {
         if (current.next.value == _value) {
           Node nodeToRemove = current.next;
           current.next = nodeToRemove.next;
           if (nodeToRemove.next != null) {
             nodeToRemove.next.prev = current;
           } else {
             tail = current;
           }
         } else {
           current = current.next;
         }
       }
     }

     // 2-7; clear; Time: O(1), Space: O(1)
     public void clear()
     {
       head = null;
       tail = null;
     }

     public int count()
     {
       int count = 0;
       Node node = this.head;
       while (node != null) {
           count++;
           node = node.next;
       }
       return count;
     }

     // 2-5,6; insertAfter; Time: O(1), Space: O(1)
     public void insertAfter(Node _nodeAfter, Node _nodeToInsert)
     {
       _nodeToInsert.next = null;
       _nodeToInsert.prev = null;

       if (_nodeAfter == null) {
         _nodeToInsert.next = head;
         if (head != null) {
           head.prev = _nodeToInsert;
         }
         head = _nodeToInsert;
         if (tail == null) {
           tail = _nodeToInsert;
         }
       } else {
         _nodeToInsert.next = _nodeAfter.next;
         _nodeToInsert.prev = _nodeAfter;
         _nodeAfter.next = _nodeToInsert;
         if (_nodeToInsert.next != null) {
           _nodeToInsert.next.prev = _nodeToInsert;
         } else {
           tail = _nodeToInsert;
         }
       }
     }
}

class Node
{
     public int value;
     public Node next;
     public Node prev;

     public Node(int _value)
     {
       value = _value;
       next = null;
       prev = null;
     }
}

