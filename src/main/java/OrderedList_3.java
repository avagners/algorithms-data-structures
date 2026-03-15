import java.util.*;

/**
 * Упорядоченный список на основе динамического массива с поддержкой
 * двоичного поиска для нахождения индекса элемента за O(log N).
 */
public class OrderedList_3<T extends Comparable<T>>
{
    private final ArrayList<T> elements;
    private final boolean ascending;

    public OrderedList_3(boolean asc)
    {
        elements = new ArrayList<>();
        ascending = asc;
    }

    // 7-12; compare; Time: O(1), Space: O(1)
    public int compare(T v1, T v2)
    {
        int result = v1.compareTo(v2);
        return ascending ? result : -result;
    }

    // 7-12; add; Time: O(n), Space: O(1)
    public void add(T value)
    {
        int index = binarySearchForInsertion(value);
        elements.add(index, value);
    }

    // 7-12; binarySearchForInsertion; Time: O(log n), Space: O(1)
    private int binarySearchForInsertion(T value)
    {
        int left = 0;
        int right = elements.size();

        while (left < right) {
            int mid = left + (right - left) / 2;
            int cmp = compare(elements.get(mid), value);

            if (cmp < 0) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }

    // 7-12; findIndex; Time: O(log n), Space: O(1)
    public int findIndex(T value)
    {
        int left = 0;
        int right = elements.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int cmp = compare(elements.get(mid), value);

            if (cmp == 0) {
                return mid;
            } else if (cmp < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }

    // 7-12; findFirstIndex; Time: O(log n), Space: O(1)
    public int findFirstIndex(T value)
    {
        int left = 0;
        int right = elements.size() - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int cmp = compare(elements.get(mid), value);

            if (cmp == 0) {
                result = mid;
                right = mid - 1;
            } else if (cmp < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }

    // 7-12; findLastIndex; Time: O(log n), Space: O(1)
    public int findLastIndex(T value)
    {
        int left = 0;
        int right = elements.size() - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int cmp = compare(elements.get(mid), value);

            if (cmp == 0) {
                result = mid;
                left = mid + 1;
            } else if (cmp < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }

    // 7-12; get; Time: O(1), Space: O(1)
    public T get(int index)
    {
        if (index < 0 || index >= elements.size()) {
            return null;
        }
        return elements.get(index);
    }

    // 7-12; size; Time: O(1), Space: O(1)
    public int size()
    {
        return elements.size();
    }

    // 7-12; getAll; Time: O(n), Space: O(n)
    public ArrayList<T> getAll()
    {
        return new ArrayList<>(elements);
    }

    // 7-12; delete; Time: O(n), Space: O(1)
    public boolean delete(T value)
    {
        int index = findIndex(value);
        if (index >= 0) {
            elements.remove(index);
            return true;
        }
        return false;
    }

    // 7-12; clear; Time: O(1), Space: O(1)
    public void clear()
    {
        elements.clear();
    }

    // 7-12; isAscending; Time: O(1), Space: O(1)
    public boolean isAscending()
    {
        return ascending;
    }
}

