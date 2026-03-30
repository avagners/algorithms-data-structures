import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * Мульти-множество (Bag) — структура данных, где каждый элемент
 * может присутствовать несколько раз.
 */
public class Bag
{
    // Хранение элементов с их частотами
    private Map<String, Integer> elements;
    private int totalCount;  // Общее количество элементов (с учётом повторений)

    public Bag()
    {
        elements = new HashMap<>();
        totalCount = 0;
    }

    // 10-6; add; Time: O(1), Space: O(1)
    // Добавление элемента в мульти-множество
    public void add(String value)
    {
        if (value == null) return;

        int count = elements.getOrDefault(value, 0);
        elements.put(value, count + 1);
        totalCount++;
    }

    // 10-6; addMultiple; Time: O(1), Space: O(1)
    // Добавление нескольких экземпляров элемента
    public void addMultiple(String value, int count)
    {
        if (value == null || count <= 0) return;

        int currentCount = elements.getOrDefault(value, 0);
        elements.put(value, currentCount + count);
        totalCount += count;
    }

    // 10-6; remove; Time: O(1), Space: O(1)
    // Удаление одного экземпляра элемента
    // Возвращает true если элемент был удалён, false если элемента не было
    public boolean remove(String value)
    {
        if (value == null) return false;

        Integer count = elements.get(value);
        if (count == null || count == 0) {
            return false;
        }

        if (count == 1) {
            elements.remove(value);
        } else {
            elements.put(value, count - 1);
        }
        totalCount--;
        return true;
    }

    // 10-6; removeMultiple; Time: O(1), Space: O(1)
    // Удаление нескольких экземпляров элемента
    // Возвращает количество фактически удалённых элементов
    public int removeMultiple(String value, int count)
    {
        if (value == null || count <= 0) return 0;

        Integer currentCount = elements.get(value);
        if (currentCount == null || currentCount == 0) {
            return 0;
        }

        int removed = Math.min(currentCount, count);
        
        if (currentCount <= removed) {
            elements.remove(value);
        } else {
            elements.put(value, currentCount - removed);
        }
        totalCount -= removed;
        return removed;
    }

    // 10-6; getCount; Time: O(1), Space: O(1)
    // Получение количества экземпляров элемента
    public int getCount(String value)
    {
        if (value == null) return 0;
        return elements.getOrDefault(value, 0);
    }

    // 10-6; contains; Time: O(1), Space: O(1)
    // Проверка наличия элемента (хотя бы одного экземпляра)
    public boolean contains(String value)
    {
        return getCount(value) > 0;
    }

    // 10-6; size; Time: O(1), Space: O(1)
    // Общее количество элементов (с учётом повторений)
    public int size()
    {
        return totalCount;
    }

    // 10-6; uniqueCount; Time: O(1), Space: O(1)
    // Количество уникальных элементов
    public int uniqueCount()
    {
        return elements.size();
    }

    // 10-6; getAllWithFrequencies; Time: O(n), Space: O(n)
    // Получение списка всех элементов с их частотами
    public List<BagElement> getAllWithFrequencies()
    {
        List<BagElement> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : elements.entrySet()) {
            result.add(new BagElement(entry.getKey(), entry.getValue()));
        }
        return result;
    }

    // 10-6; getAllElements; Time: O(n), Space: O(n)
    // Получение списка всех элементов (с повторениями)
    public List<String> getAllElements()
    {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : elements.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    // 10-6; getUniqueElements; Time: O(n), Space: O(n)
    // Получение списка уникальных элементов
    public List<String> getUniqueElements()
    {
        return new ArrayList<>(elements.keySet());
    }

    // 10-6; clear; Time: O(1), Space: O(1)
    // Очистка мульти-множества
    public void clear()
    {
        elements.clear();
        totalCount = 0;
    }

    // 10-6; isEmpty; Time: O(1), Space: O(1)
    // Проверка на пустоту
    public boolean isEmpty()
    {
        return totalCount == 0;
    }

    // 10-6; getTotalCountForValues; Time: O(n), Space: O(1)
    // Подсчёт общего количества элементов для заданных значений
    public int getTotalCountForValues(List<String> values)
    {
        int total = 0;
        for (String value : values) {
            total += getCount(value);
        }
        return total;
    }

    // 10-6; toPowerSet; Time: O(n), Space: O(n)
    // Преобразование в обычное множество (уникальные элементы)
    public PowerSet toPowerSet()
    {
        PowerSet result = new PowerSet();
        for (String value : elements.keySet()) {
            result.put(value);
        }
        return result;
    }
}
