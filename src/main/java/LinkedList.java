import java.util.*;

public class LinkedList
{
     public Node head;
     public Node tail;

     public LinkedList()
     {
       head = null;
       tail = null;
     }

    public void addInTail(Node item) {
        if (this.head == null)
            this.head = item;
        else
            this.tail.next = item;
        this.tail = item;
    }

    public Node find(int value) {
        Node node = this.head;
        while (node != null) {
            if (node.value == value)
                return node;
            node = node.next;
        }
        return null;
    }

    public ArrayList<Node> findAll(int _value) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        Node node = this.head;
        while (node != null) {
            if (node.value == _value) {
                nodes.add(node);
            }
            node = node.next;
        }
        return nodes;
     }

     public boolean remove(int _value)
     {
       if (this.head == null) {
           return false;
       }
       
       if (this.head.value == _value) {
           this.head = this.head.next;
           if (this.head == null) {
               this.tail = null;
           }
           return true;
       }

       Node currentNode = this.head;
       while (currentNode.next != null) {
           if (currentNode.next.value == _value) {
               Node nodeToRemove = currentNode.next;
               currentNode.next = nodeToRemove.next;

               if (nodeToRemove == this.tail) {
                   this.tail = currentNode;
               }
               
               return true;
           }
           currentNode = currentNode.next;
       }
       return false;
     }

     public void removeAll(int _value)
     {
        while (this.head != null && this.head.value == _value) {
           this.head = this.head.next;
       }

       if (this.head == null) {
           this.tail = null;
           return;
       }

       Node currentNode = this.head;
       while (currentNode.next != null) {
           if (currentNode.next.value == _value) {
               currentNode.next = currentNode.next.next;
           } else {
               currentNode = currentNode.next;
           }
       }

       this.tail = currentNode;
     }

     public void clear()
     {
       this.head = null;
       this.tail = null;
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

     public void insertAfter(Node _nodeAfter, Node _nodeToInsert)
     {
       if (_nodeAfter == null) {
           _nodeToInsert.next = this.head;
           this.head = _nodeToInsert;
           if (this.tail == null) {
               this.tail = _nodeToInsert;
           }
           return;
       }

       _nodeToInsert.next = _nodeAfter.next;
       _nodeAfter.next = _nodeToInsert;

       if (_nodeAfter == this.tail) {
           this.tail = _nodeToInsert;
       }
     }
}

class Node
{
     public int value;
     public Node next;
     public Node(int _value) 
     {  
       value = _value;
       next = null;
     }
}