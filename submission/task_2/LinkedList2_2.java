public class LinkedList2_2
{
     public Node head;
     public Node tail;

     public LinkedList2_2()
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

     // 2-9; reverse; Time: O(n), Space: O(1)
     public void reverse()
     {
       if (head == null || head.next == null) {
         return;
       }

       Node temp = head;
       head = tail;
       tail = temp;

       Node current = head;
       while (current != null) {
         temp = current.next;
         current.next = current.prev;
         current.prev = temp;
         current = current.next;
       }
     }

     // 2-10; hasCycle; Time: O(n), Space: O(1)
     public boolean hasCycle()
     {
       if (head == null || head.next == null) {
         return false;
       }

       // Floyd's cycle detection algorithm
       Node slow = head;
       Node fast = head;

       while (fast != null && fast.next != null) {
         slow = slow.next;
         fast = fast.next.next;

         if (slow == fast) {
           return true;
         }
       }

       return false;
     }

     // 2-11; sort; Time: O(n log n), Space: O(log n)
     public void sort()
     {
       if (head == null || head.next == null) {
         return;
       }

       head = mergeSort(head);
       head.prev = null;

       // Update tail
       tail = head;
       while (tail.next != null) {
         tail = tail.next;
       }
     }

     private Node mergeSort(Node node)
     {
       if (node == null || node.next == null) {
         return node;
       }

       Node mid = getMiddle(node);
       Node nextOfMid = mid.next;
       mid.next = null;

       Node left = mergeSort(node);
       Node right = mergeSort(nextOfMid);

       return merge(left, right);
     }

     private Node getMiddle(Node node)
     {
       if (node == null) {
         return node;
       }

       Node slow = node;
       Node fast = node;

       while (fast.next != null && fast.next.next != null) {
         slow = slow.next;
         fast = fast.next.next;
       }

       return slow;
     }

     private Node merge(Node left, Node right)
     {
       if (left == null) {
         return right;
       }
       if (right == null) {
         return left;
       }

       Node result;

       if (left.value <= right.value) {
         result = left;
         result.next = merge(left.next, right);
         if (result.next != null) {
           result.next.prev = result;
         }
       } else {
         result = right;
         result.next = merge(left, right.next);
         if (result.next != null) {
           result.next.prev = result;
         }
       }

       return result;
     }

     // 2-12; mergeSortedLists; Time: O(n+m), Space: O(1)
     public static LinkedList2_2 mergeSortedLists(LinkedList2_2 list1, LinkedList2_2 list2)
     {
       LinkedList2_2 result = new LinkedList2_2();

       Node current1 = list1.head;
       Node current2 = list2.head;

       while (current1 != null && current2 != null) {
         if (current1.value <= current2.value) {
           result.addInTail(new Node(current1.value));
           current1 = current1.next;
         } else {
           result.addInTail(new Node(current2.value));
           current2 = current2.next;
         }
       }

       while (current1 != null) {
         result.addInTail(new Node(current1.value));
         current1 = current1.next;
       }

       while (current2 != null) {
         result.addInTail(new Node(current2.value));
         current2 = current2.next;
       }

       return result;
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

