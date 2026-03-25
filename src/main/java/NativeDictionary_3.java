/**
 * Словарь с ключами в виде битовых строк фиксированной длины.
 * Использует битовые операции для ускорения поиска, вставки и удаления.
 * 
 * Реализация основана на бите (битовом дереве), где каждый уровень
 * соответствует одному биту ключа.
 */
public class NativeDictionary_3<T>
{
    // Узел битового дерева
    private static class BitNode<V>
    {
        public V value;           // Значение (null если промежуточный узел)
        public BitNode<V> zero;   // Дочерний узел для бита 0
        public BitNode<V> one;    // Дочерний узел для бита 1
        public boolean hasValue;  // Флаг наличия значения в этом узле

        public BitNode()
        {
            this.value = null;
            this.zero = null;
            this.one = null;
            this.hasValue = false;
        }
    }

    private final int bitLength;      // Длина битовой строки
    private BitNode<T> root;          // Корень дерева
    private int count;                // Количество элементов

    public NativeDictionary_3(int bitLength)
    {
        if (bitLength <= 0 || bitLength > 64) {
            throw new IllegalArgumentException("bitLength must be between 1 and 64");
        }
        this.bitLength = bitLength;
        this.root = new BitNode<>();
        this.count = 0;
    }

    // 9-6; getBit; Time: O(1), Space: O(1)
    // Получает бит на позиции pos из ключа (0 = старший бит)
    private int getBit(long key, int pos)
    {
        return (int)((key >>> (bitLength - 1 - pos)) & 1);
    }

    // 9-6; put; Time: O(k), Space: O(1) где k = bitLength
    // Вставка пары ключ-значение
    public void put(long key, T value)
    {
        if (key < 0 || key >= (1L << bitLength)) {
            throw new IllegalArgumentException("Key out of range for bit length " + bitLength);
        }

        BitNode<T> current = root;

        // Проходим по всем битам ключа
        for (int i = 0; i < bitLength; i++) {
            int bit = getBit(key, i);

            if (bit == 0) {
                if (current.zero == null) {
                    current.zero = new BitNode<>();
                }
                current = current.zero;
            } else {
                if (current.one == null) {
                    current.one = new BitNode<>();
                }
                current = current.one;
            }
        }

        // Обновляем или добавляем значение
        if (!current.hasValue) {
            count++;
        }
        current.value = value;
        current.hasValue = true;
    }

    // 9-6; get; Time: O(k), Space: O(1) где k = bitLength
    // Получение значения по ключу
    public T get(long key)
    {
        if (key < 0 || key >= (1L << bitLength)) {
            return null;
        }

        BitNode<T> current = root;

        for (int i = 0; i < bitLength; i++) {
            int bit = getBit(key, i);

            if (bit == 0) {
                if (current.zero == null) return null;
                current = current.zero;
            } else {
                if (current.one == null) return null;
                current = current.one;
            }
        }

        return current.hasValue ? current.value : null;
    }

    // 9-6; isKey; Time: O(k), Space: O(1) где k = bitLength
    // Проверка наличия ключа
    public boolean isKey(long key)
    {
        if (key < 0 || key >= (1L << bitLength)) {
            return false;
        }

        BitNode<T> current = root;

        for (int i = 0; i < bitLength; i++) {
            int bit = getBit(key, i);

            if (bit == 0) {
                if (current.zero == null) return false;
                current = current.zero;
            } else {
                if (current.one == null) return false;
                current = current.one;
            }
        }

        return current.hasValue;
    }

    // 9-6; remove; Time: O(k), Space: O(1) где k = bitLength
    // Удаление пары по ключу
    public boolean remove(long key)
    {
        if (key < 0 || key >= (1L << bitLength)) {
            return false;
        }

        boolean removed = removeRecursive(root, key, 0);
        if (removed) {
            count--;
        }
        return removed;
    }

    // 9-6; removeRecursive; Time: O(k), Space: O(k) где k = bitLength
    // Рекурсивное удаление с очисткой пустых узлов
    private boolean removeRecursive(BitNode<T> node, long key, int depth)
    {
        if (depth == bitLength) {
            if (node.hasValue) {
                node.hasValue = false;
                node.value = null;
                return true;
            }
            return false;
        }

        int bit = getBit(key, depth);
        BitNode<T> child = (bit == 0) ? node.zero : node.one;

        if (child == null) {
            return false;
        }

        boolean removed = removeRecursive(child, key, depth + 1);

        // Очищаем пустые узлы
        if (removed && child.zero == null && child.one == null && !child.hasValue) {
            if (bit == 0) {
                node.zero = null;
            } else {
                node.one = null;
            }
        }

        return removed;
    }

    // 9-6; count; Time: O(1), Space: O(1)
    // Количество пар ключ-значение
    public int count()
    {
        return count;
    }

    // 9-6; clear; Time: O(1), Space: O(1)
    public void clear()
    {
        root = new BitNode<>();
        count = 0;
    }

    // 9-6; isEmpty; Time: O(1), Space: O(1)
    public boolean isEmpty()
    {
        return count == 0;
    }

    // 9-6; getBitLength; Time: O(1), Space: O(1)
    public int getBitLength()
    {
        return bitLength;
    }

    // 9-6; getAllKeys; Time: O(2^k), Space: O(2^k) где k = bitLength
    // Возвращает все ключи
    public long[] getAllKeys()
    {
        long[] keys = new long[count];
        int[] index = {0};
        collectKeys(root, 0, 0, keys, index);
        return keys;
    }

    // 9-6; collectKeys; Time: O(2^k), Space: O(k)
    private void collectKeys(BitNode<T> node, long key, int depth, long[] keys, int[] index)
    {
        if (node == null) return;

        if (node.hasValue) {
            keys[index[0]++] = key;
        }

        if (depth < bitLength) {
            // Добавляем бит 0
            collectKeys(node.zero, key << 1, depth + 1, keys, index);
            // Добавляем бит 1
            collectKeys(node.one, (key << 1) | 1, depth + 1, keys, index);
        }
    }

    // 9-6; getAllValues; Time: O(2^k), Space: O(2^k)
    @SuppressWarnings("unchecked")
    public T[] getAllValues(Class<T> clazz)
    {
        T[] values = (T[]) java.lang.reflect.Array.newInstance(clazz, count);
        int[] index = {0};
        collectValues(root, values, index);
        return values;
    }

    // 9-6; collectValues; Time: O(2^k), Space: O(k)
    @SuppressWarnings("unchecked")
    private void collectValues(BitNode<T> node, T[] values, int[] index)
    {
        if (node == null) return;

        if (node.hasValue) {
            values[index[0]++] = node.value;
        }

        collectValues(node.zero, values, index);
        collectValues(node.one, values, index);
    }

    // 9-6; findByPrefix; Time: O(p), Space: O(1) где p = длина префикса
    // Проверяет, есть ли ключи с заданным префиксом
    public boolean hasKeyWithPrefix(long prefix, int prefixLength)
    {
        if (prefixLength <= 0 || prefixLength > bitLength) {
            return false;
        }

        BitNode<T> current = root;

        for (int i = 0; i < prefixLength; i++) {
            int bit = getBit(prefix, i);

            if (bit == 0) {
                if (current.zero == null) return false;
                current = current.zero;
            } else {
                if (current.one == null) return false;
                current = current.one;
            }
        }

        return true;
    }

    // 9-6; countWithPrefix; Time: O(2^(k-p)), Space: O(k-p)
    // Подсчитывает количество ключей с заданным префиксом
    public int countWithPrefix(long prefix, int prefixLength)
    {
        if (prefixLength <= 0 || prefixLength > bitLength) {
            return 0;
        }

        BitNode<T> current = root;

        for (int i = 0; i < prefixLength; i++) {
            int bit = getBit(prefix, i);

            if (bit == 0) {
                if (current.zero == null) return 0;
                current = current.zero;
            } else {
                if (current.one == null) return 0;
                current = current.one;
            }
        }

        return countSubtree(current);
    }

    // 9-6; countSubtree; Time: O(n), Space: O(k)
    private int countSubtree(BitNode<T> node)
    {
        if (node == null) return 0;

        int count = node.hasValue ? 1 : 0;
        count += countSubtree(node.zero);
        count += countSubtree(node.one);
        return count;
    }
}
