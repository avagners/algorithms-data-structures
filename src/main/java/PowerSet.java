import java.util.ArrayList;
import java.util.List;

public class PowerSet
{
    private static final int TABLE_SIZE = 20000;
    private static final int STEP = 3;
    
    private String[] slots;
    private int size;  // количество элементов в множестве

    public PowerSet()
    {
        slots = new String[TABLE_SIZE];
        size = 0;
    }

    // 10-1; hashFun; Time: O(k), Space: O(1) где k = длина строки
    private int hashFun(String value)
    {
        int sum = 0;
        for (int i = 0; i < value.length(); i++) {
            sum += (int) value.charAt(i);
        }
        return Math.abs(sum) % TABLE_SIZE;
    }

    // 10-1; seekSlot; Time: O(n), Space: O(1)
    private int seekSlot(String value)
    {
        int index = hashFun(value);
        int startIndex = index;

        for (int i = 0; i < TABLE_SIZE; i++) {
            if (slots[index] == null || slots[index].equals(value)) {
                return index;
            }
            index = (index + STEP) % TABLE_SIZE;
            if (index == startIndex) {
                break;
            }
        }

        return -1;
    }

    // 10-1; size; Time: O(1), Space: O(1)
    public int size()
    {
        return size;
    }

    // 10-2; put; Time: O(n), Space: O(1)
    public void put(String value)
    {
        int index = seekSlot(value);
        if (index >= 0 && slots[index] == null) {
            slots[index] = value;
            size++;
        }
        // Если элемент уже существует - ничего не делаем
    }

    // 10-2; get; Time: O(n), Space: O(1)
    public boolean get(String value)
    {
        int index = seekSlot(value);
        return index >= 0 && slots[index] != null && slots[index].equals(value);
    }

    // 10-2; remove; Time: O(n), Space: O(1)
    public boolean remove(String value)
    {
        int index = seekSlot(value);
        if (index >= 0 && slots[index] != null && slots[index].equals(value)) {
            slots[index] = null;
            size--;
            
            // Перекомпактируем таблицу: перераспределяем все элементы после удалённого
            rehashFrom(index);
            
            return true;
        }
        return false;
    }

    // Перекомпактировка таблицы после удаления
    private void rehashFrom(int startIndex)
    {
        // Собираем все элементы после удалённого
        List<String> elementsToRehash = new ArrayList<>();
        int index = (startIndex + STEP) % TABLE_SIZE;
        
        while (index != startIndex && slots[index] != null) {
            elementsToRehash.add(slots[index]);
            slots[index] = null;
            size--;
            index = (index + STEP) % TABLE_SIZE;
        }
        
        // Перераспределяем элементы
        for (String value : elementsToRehash) {
            int newIndex = seekSlot(value);
            if (newIndex >= 0) {
                slots[newIndex] = value;
                size++;
            }
        }
    }

    // 10-2; getAll; Time: O(n), Space: O(n)
    protected String[] getAll()
    {
        String[] result = new String[size];
        int index = 0;
        for (String slot : slots) {
            if (slot != null) {
                result[index++] = slot;
            }
        }
        return result;
    }

    // 10-2; intersection; Time: O(n*m), Space: O(n)
    public PowerSet intersection(PowerSet set2)
    {
        PowerSet result = new PowerSet();
        
        // Проходим по меньшему множеству для оптимизации
        PowerSet smaller = this.size <= set2.size ? this : set2;
        PowerSet larger = this.size <= set2.size ? set2 : this;
        
        for (String value : smaller.getAll()) {
            if (larger.get(value)) {
                result.put(value);
            }
        }
        
        return result;
    }

    // 10-2; union; Time: O(n+m), Space: O(n+m)
    public PowerSet union(PowerSet set2)
    {
        PowerSet result = new PowerSet();
        
        // Добавляем все элементы из текущего множества
        for (String value : this.getAll()) {
            result.put(value);
        }
        
        // Добавляем все элементы из другого множества
        for (String value : set2.getAll()) {
            result.put(value);
        }
        
        return result;
    }

    // 10-2; difference; Time: O(n*m), Space: O(n)
    public PowerSet difference(PowerSet set2)
    {
        PowerSet result = new PowerSet();
        
        for (String value : this.getAll()) {
            if (!set2.get(value)) {
                result.put(value);
            }
        }
        
        return result;
    }

    // 10-2; isSubset; Time: O(n*m), Space: O(1)
    public boolean isSubset(PowerSet set2)
    {
        // Пустое множество является подмножеством любого
        if (set2.size() == 0) {
            return true;
        }
        
        // Если set2 больше текущего, не может быть подмножеством
        if (set2.size() > this.size) {
            return false;
        }
        
        // Проверяем каждый элемент set2
        for (String value : set2.getAll()) {
            if (!this.get(value)) {
                return false;
            }
        }
        
        return true;
    }

    // 10-2; equals; Time: O(n*m), Space: O(1)
    public boolean equals(PowerSet set2)
    {
        // Разные размеры - не равны
        if (this.size() != set2.size()) {
            return false;
        }

        // Одинаковые размеры - проверяем, является ли set2 подмножеством
        return this.isSubset(set2);
    }

    // 10-4; cartesianProduct; Time: O(n*m), Space: O(n*m)
    // Декартово произведение двух множеств
    // Возвращает множество пар в формате "(a; b)"
    public PowerSet cartesianProduct(PowerSet set2)
    {
        PowerSet result = new PowerSet();

        // Для каждой пары (a, b) где a ∈ this, b ∈ set2
        for (String a : this.getAll()) {
            for (String b : set2.getAll()) {
                // Представляем пару как строку "(a; b)"
                String pair = "(" + a + "; " + b + ")";
                result.put(pair);
            }
        }

        return result;
    }
}

