import java.util.ArrayList;
import java.util.List;

/**
 * Расширенный класс множества с поддержкой пересечения трёх и более множеств.
 */
public class PowerSet_2 extends PowerSet
{
    public PowerSet_2()
    {
        super();
    }

    // 10-5; intersectionManySets; Time: O(n*m*k*...), Space: O(n)
    // Пересечение трёх и более множеств
    // Принимает список множеств для пересечения
    public PowerSet intersectionManySets(List<PowerSet> list)
    {
        PowerSet result = new PowerSet();

        // Проверка: нужно минимум 3 множества
        if (list == null || list.size() < 3) {
            return result;
        }

        // Проверка на наличие пустых множеств или null
        PowerSet first = list.get(0);
        if (first == null || first.size() == 0) {
            return result;
        }

        for (PowerSet set : list) {
            if (set == null || set.size() == 0) {
                return result;  // Пересечение с пустым = пустое
            }
        }

        // Проходим по элементам первого множества
        for (String el : first.getAll()) {
            if (el == null) continue;

            boolean everywhere = true;
            // Проверяем, содержится ли элемент во всех остальных множествах
            for (int i = 1; i < list.size(); i++) {
                if (!list.get(i).get(el)) {
                    everywhere = false;
                    break;
                }
            }

            if (everywhere) {
                result.put(el);
            }
        }

        return result;
    }

    // Вспомогательный метод для создания списка множеств
    public static List<PowerSet> asList(PowerSet... sets)
    {
        List<PowerSet> list = new ArrayList<>();
        for (PowerSet set : sets) {
            list.add(set);
        }
        return list;
    }
}

