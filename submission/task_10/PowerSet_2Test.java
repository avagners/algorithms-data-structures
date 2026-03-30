import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class PowerSet_2Test {

    @Test
    public void testIntersectionManySets_threeSets() {
        PowerSet set1 = new PowerSet();
        set1.put("a");
        set1.put("b");
        set1.put("c");
        set1.put("d");

        PowerSet set2 = new PowerSet();
        set2.put("b");
        set2.put("c");
        set2.put("d");
        set2.put("e");

        PowerSet set3 = new PowerSet();
        set3.put("c");
        set3.put("d");
        set3.put("e");
        set3.put("f");

        List<PowerSet> sets = new ArrayList<>();
        sets.add(set1);
        sets.add(set2);
        sets.add(set3);

        PowerSet_2 ps2 = new PowerSet_2();
        PowerSet result = ps2.intersectionManySets(sets);

        // Пересечение: только "c" и "d" есть во всех трёх множествах
        assertEquals(2, result.size());
        assertTrue(result.get("c"));
        assertTrue(result.get("d"));
        assertFalse(result.get("a"));
        assertFalse(result.get("b"));
        assertFalse(result.get("e"));
    }

    @Test
    public void testIntersectionManySets_fourSets() {
        PowerSet set1 = new PowerSet();
        set1.put("1");
        set1.put("2");
        set1.put("3");
        set1.put("4");
        set1.put("5");

        PowerSet set2 = new PowerSet();
        set2.put("2");
        set2.put("3");
        set2.put("4");
        set2.put("5");
        set2.put("6");

        PowerSet set3 = new PowerSet();
        set3.put("3");
        set3.put("4");
        set3.put("5");
        set3.put("6");
        set3.put("7");

        PowerSet set4 = new PowerSet();
        set4.put("4");
        set4.put("5");
        set4.put("6");
        set4.put("7");
        set4.put("8");

        List<PowerSet> sets = new ArrayList<>();
        sets.add(set1);
        sets.add(set2);
        sets.add(set3);
        sets.add(set4);

        PowerSet_2 ps2 = new PowerSet_2();
        PowerSet result = ps2.intersectionManySets(sets);

        // Пересечение: только "4" и "5" есть во всех четырёх множествах
        assertEquals(2, result.size());
        assertTrue(result.get("4"));
        assertTrue(result.get("5"));
    }

    @Test
    public void testIntersectionManySets_fiveSets() {
        List<PowerSet> sets = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            PowerSet set = new PowerSet();
            for (int j = i; j < 10; j++) {
                set.put("key" + j);
            }
            sets.add(set);
        }

        PowerSet_2 ps2 = new PowerSet_2();
        PowerSet result = ps2.intersectionManySets(sets);

        // Пересечение: key4..key9 (элементы, которые есть во всех 5 множествах)
        assertEquals(6, result.size());
        for (int i = 4; i < 10; i++) {
            assertTrue(result.get("key" + i));
        }
    }

    @Test
    public void testIntersectionManySets_emptyResult() {
        PowerSet set1 = new PowerSet();
        set1.put("a");
        set1.put("b");

        PowerSet set2 = new PowerSet();
        set2.put("c");
        set2.put("d");

        PowerSet set3 = new PowerSet();
        set3.put("e");
        set3.put("f");

        List<PowerSet> sets = new ArrayList<>();
        sets.add(set1);
        sets.add(set2);
        sets.add(set3);

        PowerSet_2 ps2 = new PowerSet_2();
        PowerSet result = ps2.intersectionManySets(sets);

        // Нет общих элементов
        assertEquals(0, result.size());
    }

    @Test
    public void testIntersectionManySets_withEmptySet() {
        PowerSet set1 = new PowerSet();
        set1.put("a");
        set1.put("b");

        PowerSet set2 = new PowerSet();
        set2.put("a");

        PowerSet set3 = new PowerSet();  // Пустое множество

        List<PowerSet> sets = new ArrayList<>();
        sets.add(set1);
        sets.add(set2);
        sets.add(set3);

        PowerSet_2 ps2 = new PowerSet_2();
        PowerSet result = ps2.intersectionManySets(sets);

        // Пересечение с пустым = пустое
        assertEquals(0, result.size());
    }

    @Test
    public void testIntersectionManySets_lessThanThreeSets() {
        PowerSet set1 = new PowerSet();
        set1.put("a");

        PowerSet set2 = new PowerSet();
        set2.put("a");

        List<PowerSet> sets = new ArrayList<>();
        sets.add(set1);
        sets.add(set2);

        PowerSet_2 ps2 = new PowerSet_2();
        PowerSet result = ps2.intersectionManySets(sets);

        // Меньше 3 множеств - возвращаем пустое
        assertEquals(0, result.size());
    }

    @Test
    public void testIntersectionManySets_nullList() {
        PowerSet_2 ps2 = new PowerSet_2();
        PowerSet result = ps2.intersectionManySets(null);
        assertEquals(0, result.size());
    }

    @Test
    public void testIntersectionManySets_withNullInList() {
        PowerSet set1 = new PowerSet();
        set1.put("a");

        PowerSet set2 = new PowerSet();
        set2.put("a");

        List<PowerSet> sets = new ArrayList<>();
        sets.add(set1);
        sets.add(null);  // null в списке
        sets.add(set2);

        PowerSet_2 ps2 = new PowerSet_2();
        PowerSet result = ps2.intersectionManySets(sets);

        // null в списке = пустое множество
        assertEquals(0, result.size());
    }

    @Test
    public void testIntersectionManySets_allSameElements() {
        PowerSet set1 = new PowerSet();
        set1.put("x");
        set1.put("y");
        set1.put("z");

        PowerSet set2 = new PowerSet();
        set2.put("x");
        set2.put("y");
        set2.put("z");

        PowerSet set3 = new PowerSet();
        set3.put("x");
        set3.put("y");
        set3.put("z");

        List<PowerSet> sets = new ArrayList<>();
        sets.add(set1);
        sets.add(set2);
        sets.add(set3);

        PowerSet_2 ps2 = new PowerSet_2();
        PowerSet result = ps2.intersectionManySets(sets);

        // Все множества одинаковые - пересечение равно каждому из них
        assertEquals(3, result.size());
        assertTrue(result.get("x"));
        assertTrue(result.get("y"));
        assertTrue(result.get("z"));
    }

    @Test
    public void testIntersectionManySets_usingAsList() {
        PowerSet set1 = new PowerSet();
        set1.put("a");
        set1.put("b");
        set1.put("c");

        PowerSet set2 = new PowerSet();
        set2.put("b");
        set2.put("c");
        set2.put("d");

        PowerSet set3 = new PowerSet();
        set3.put("c");
        set3.put("d");
        set3.put("e");

        PowerSet_2 ps2 = new PowerSet_2();

        // Используем вспомогательный метод asList
        PowerSet result = ps2.intersectionManySets(
            PowerSet_2.asList(set1, set2, set3)
        );

        // Только "c" есть во всех трёх множествах
        assertEquals(1, result.size());
        assertTrue(result.get("c"));
    }

    @Test
    public void testPerformance_intersectionManySets() {
        List<PowerSet> sets = new ArrayList<>();

        // Создаём 5 множеств по 1000 элементов каждое
        for (int i = 0; i < 5; i++) {
            PowerSet set = new PowerSet();
            for (int j = i * 100; j < i * 100 + 1000; j++) {
                set.put("key" + j);
            }
            sets.add(set);
        }

        PowerSet_2 ps2 = new PowerSet_2();

        long startTime = System.currentTimeMillis();
        PowerSet result = ps2.intersectionManySets(sets);
        long intersectionTime = System.currentTimeMillis() - startTime;

        System.out.println("Intersection of 5 sets (1000 elements each): " + intersectionTime + " ms");
        System.out.println("Result size: " + result.size());

        // Должно выполниться за разумное время (< 5 секунд)
        assertTrue(intersectionTime < 5000, "Should complete in reasonable time");
    }

    @Test
    public void testPerformance_largeNumberOfSets() {
        List<PowerSet> sets = new ArrayList<>();

        // Создаём 10 множеств по 500 элементов
        for (int i = 0; i < 10; i++) {
            PowerSet set = new PowerSet();
            for (int j = 0; j < 500; j++) {
                set.put("item" + (i * 50 + j));
            }
            sets.add(set);
        }

        PowerSet_2 ps2 = new PowerSet_2();

        long startTime = System.currentTimeMillis();
        PowerSet result = ps2.intersectionManySets(sets);
        long intersectionTime = System.currentTimeMillis() - startTime;

        System.out.println("Intersection of 10 sets (500 elements each): " + intersectionTime + " ms");
        System.out.println("Result size: " + result.size());

        assertTrue(intersectionTime < 5000, "Should complete in reasonable time");
    }

    @Test
    public void testIntersectionManySets_fullWorkflow() {
        // Создаём 4 множества с разными элементами
        PowerSet fruits = new PowerSet();
        fruits.put("apple");
        fruits.put("banana");
        fruits.put("cherry");
        fruits.put("date");

        PowerSet tropical = new PowerSet();
        tropical.put("banana");
        tropical.put("cherry");
        tropical.put("mango");
        tropical.put("papaya");

        PowerSet red = new PowerSet();
        red.put("cherry");
        red.put("apple");
        red.put("strawberry");

        PowerSet sweet = new PowerSet();
        sweet.put("cherry");
        sweet.put("date");
        sweet.put("mango");

        List<PowerSet> sets = new ArrayList<>();
        sets.add(fruits);
        sets.add(tropical);
        sets.add(red);
        sets.add(sweet);

        PowerSet_2 ps2 = new PowerSet_2();
        PowerSet result = ps2.intersectionManySets(sets);

        // Только "cherry" есть во всех четырёх множествах
        assertEquals(1, result.size());
        assertTrue(result.get("cherry"));
    }
}

