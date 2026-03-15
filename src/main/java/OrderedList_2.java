import java.util.*;

public class OrderedList_2
{
    // 7-9; merge; Time: O(n+m), Space: O(1)
    public static <T> OrderedList<T> merge(OrderedList<T> list1, OrderedList<T> list2)
    {
        OrderedList<T> result = new OrderedList<>(list1.isAscending());

        if (list1.head == null) {
            copyList(list2, result);
            return result;
        }
        if (list2.head == null) {
            copyList(list1, result);
            return result;
        }

        Node2<T> current1 = list1.head;
        Node2<T> current2 = list2.head;

        while (current1 != null && current2 != null) {
            int cmp = list1.compare(current1.value, current2.value);

            if (cmp <= 0) {
                result.add(current1.value);
                current1 = current1.next;
            } else {
                result.add(current2.value);
                current2 = current2.next;
            }
        }

        while (current1 != null) {
            result.add(current1.value);
            current1 = current1.next;
        }

        while (current2 != null) {
            result.add(current2.value);
            current2 = current2.next;
        }

        return result;
    }

    private static <T> void copyList(OrderedList<T> source, OrderedList<T> target)
    {
        Node2<T> current = source.head;
        while (current != null) {
            target.add(current.value);
            current = current.next;
        }
    }

    // 7-10; containsSublist; Time: O(n*m), Space: O(1)
    public static <T> boolean containsSublist(OrderedList<T> list, OrderedList<T> sublist)
    {
        if (sublist.head == null) {
            return true;
        }

        if (list.head == null) {
            return false;
        }

        int listLength = list.count();
        int sublistLength = sublist.count();

        if (sublistLength > listLength) {
            return false;
        }

        Node2<T> listCurrent = list.head;

        for (int i = 0; i <= listLength - sublistLength; i++) {
            if (startsWith(listCurrent, sublist.head, list)) {
                return true;
            }
            listCurrent = listCurrent.next;
        }

        return false;
    }

    private static <T> boolean startsWith(Node2<T> head1, Node2<T> head2, OrderedList<T> list)
    {
        Node2<T> current1 = head1;
        Node2<T> current2 = head2;

        while (current2 != null) {
            if (current1 == null) {
                return false;
            }

            int cmp = list.compare(current1.value, current2.value);
            if (cmp != 0) {
                return false;
            }

            current1 = current1.next;
            current2 = current2.next;
        }

        return true;
    }

    // 7-11; findMostFrequent; Time: O(n), Space: O(1)
    public static <T> T findMostFrequent(OrderedList<T> list)
    {
        if (list.head == null) {
            return null;
        }

        T mostFrequentValue = null;
        int maxCount = 0;

        T currentValue = null;
        int currentCount = 0;

        Node2<T> current = list.head;

        while (current != null) {
            if (currentValue == null || list.compare(currentValue, current.value) != 0) {
                if (currentCount > maxCount) {
                    maxCount = currentCount;
                    mostFrequentValue = currentValue;
                }

                currentValue = current.value;
                currentCount = 1;
            } else {
                currentCount++;
            }

            current = current.next;
        }

        if (currentCount > maxCount) {
            mostFrequentValue = currentValue;
        }

        return mostFrequentValue;
    }

    // 7-11; findMostFrequentWithCount; Time: O(n), Space: O(1)
    public static <T> java.util.Map.Entry<T, Integer> findMostFrequentWithCount(OrderedList<T> list)
    {
        if (list.head == null) {
            return null;
        }

        T mostFrequentValue = null;
        int maxCount = 0;

        T currentValue = null;
        int currentCount = 0;

        Node2<T> current = list.head;

        while (current != null) {
            if (currentValue == null || list.compare(currentValue, current.value) != 0) {
                if (currentCount > maxCount) {
                    maxCount = currentCount;
                    mostFrequentValue = currentValue;
                }

                currentValue = current.value;
                currentCount = 1;
            } else {
                currentCount++;
            }

            current = current.next;
        }

        if (currentCount > maxCount) {
            maxCount = currentCount;
            mostFrequentValue = currentValue;
        }

        return new java.util.AbstractMap.SimpleEntry<>(mostFrequentValue, maxCount);
    }
}

