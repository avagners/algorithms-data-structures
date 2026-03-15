import java.util.*;

class Node<T>
{
    public T value;
    public Node<T> next, prev;

    public Node(T _value)
    {
        value = _value;
        next = null;
        prev = null;
    }
}

public class OrderedList<T>
{
    public Node<T> head, tail;
    private boolean _ascending;

    public OrderedList(boolean asc)
    {
        head = null;
        tail = null;
        _ascending = asc;
    }

    // 7-0; isAscending; Time: O(1), Space: O(1)
    public boolean isAscending()
    {
        return _ascending;
    }

    // 7-5; compare; Time: O(1), Space: O(1)
    @SuppressWarnings("unchecked")
    public int compare(T v1, T v2)
    {
        int result;
        
        if (v1 instanceof Number && v2 instanceof Number) {
            double n1 = ((Number) v1).doubleValue();
            double n2 = ((Number) v2).doubleValue();
            result = Double.compare(n1, n2);
        }
        else if (v1 instanceof String && v2 instanceof String) {
            String s1 = ((String) v1).trim();
            String s2 = ((String) v2).trim();
            result = s1.compareTo(s2);
        }
        else if (v1 instanceof Comparable && v2 instanceof Comparable) {
            result = ((Comparable<T>) v1).compareTo(v2);
        }
        else {
            throw new ClassCastException("Неподдерживаемый тип для сравнения");
        }
        
        return _ascending ? result : -result;
        // -1 если v1 < v2
        // 0 если v1 == v2
        // +1 если v1 > v2
    }

    // 7-3; add; Time: O(n), Space: O(1)
    public void add(T value)
    {
        Node<T> newNode = new Node<>(value);
        
        // Если список пуст
        if (head == null) {
            head = newNode;
            tail = newNode;
            return;
        }
        
        if (compare(value, head.value) <= 0) {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
            return;
        }
        
        if (compare(value, tail.value) >= 0) {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
            return;
        }
        
        Node<T> current = head.next;
        while (current != null) {
            if (compare(value, current.value) <= 0) {
                // Вставляем перед current
                newNode.prev = current.prev;
                newNode.next = current;
                current.prev.next = newNode;
                current.prev = newNode;
                return;
            }
            current = current.next;
        }
    }

	// 7-6; find; Time: O(n), Space: O(1)
    public Node<T> find(T val)
    {
        Node<T> current = head;
        while (current != null) {
            int cmp = compare(current.value, val);
            
            if (cmp == 0) {
                // Нашли искомый элемент
                return current;
            }
            
            // Раннее прерывание: если текущий элемент больше искомого,
            // дальше искать нет смысла (список упорядочен)
            if (cmp > 0) {
                return null;
            }
            
            current = current.next;
        }
        return null;
    }

	// 7-4; delete; Time: O(n), Space: O(1)
    public void delete(T val)
        {
        Node<T> current = head;
        while (current != null) {
            if (compare(current.value, val) == 0) {
                // Нашли элемент для удаления
                
                // Обновляем prev.next
                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    // Удаляем head
                    head = current.next;
                }
                
                // Обновляем next.prev
                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    // Удаляем tail
                    tail = current.prev;
                }
                
                // Очищаем ссылки удаляемого узла
                current.next = null;
                current.prev = null;
                
                return; // Удаляем только первый найденный элемент
            }
            current = current.next;
        }
    }

    public void clear(boolean asc)
    {
        _ascending = asc;
        head = null;
        tail = null;
    }

    public int count()
    {
        int count = 0;
        Node<T> current = head;
        while (current != null)
        {
            count++;
            current = current.next;
        }
        return count;
    }

    public ArrayList<Node<T>> getAll()
    {
        ArrayList<Node<T>> r = new ArrayList<Node<T>>();
        Node<T> node = head;
        while (node != null)
        {
            r.add(node);
            node = node.next;
        }
        return r;
    }

    // 7-8; removeAllDuplicates; Time: O(n), Space: O(1)
    public void removeAllDuplicates()
    {
        if (head == null || head.next == null) {
            return;
        }

        Node<T> current = head;
        while (current != null && current.next != null) {
            if (compare(current.value, current.next.value) == 0) {
                Node<T> duplicate = current.next;
                current.next = duplicate.next;

                if (duplicate == tail) {
                    tail = current;
                }

                duplicate.next = null;
                duplicate.prev = null;
            } else {
                current = current.next;
            }
        }
    }
}

