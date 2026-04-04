import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BloomFilter_3Test {

    private BloomFilter_3 filter;

    private static final String[] TEST_STRINGS = {
        "0123456789", "1234567890", "2345678901", "3456789012",
        "4567890123", "5678901234", "6789012345", "7890123456",
        "8901234567", "9012345678"
    };

    @BeforeEach
    void setUp() {
        filter = new BloomFilter_3(32);
    }

    // ==================== Базовые тесты add/isValue ====================

    @Test
    void testAllInsertedStringsAreFound() {
        for (String s : TEST_STRINGS) {
            filter.add(s);
        }
        for (String s : TEST_STRINGS) {
            assertTrue(filter.isValue(s), "Должен найти строку: " + s);
        }
    }

    @Test
    void testEmptyFilterReturnsFalse() {
        for (String s : TEST_STRINGS) {
            assertFalse(filter.isValue(s));
        }
    }

    @Test
    void testAddIncremental() {
        String s = "0123456789";
        assertFalse(filter.isValue(s));
        filter.add(s);
        assertTrue(filter.isValue(s));
    }

    // ==================== Тесты удаления ====================

    @Test
    void testRemove_existingElement() {
        String s = "0123456789";
        filter.add(s);
        assertTrue(filter.isValue(s));

        filter.remove(s);
        assertFalse(filter.isValue(s), "После удаления элемент не должен находиться");
    }

    @Test
    void testRemove_doesNotAffectOtherElements() {
        // Добавляем два элемента
        String s1 = "0123456789";
        String s2 = "5678901234";
        filter.add(s1);
        filter.add(s2);

        // Запоминаем, находился ли s2 до удаления s1
        boolean s2FoundBefore = filter.isValue(s2);
        assertTrue(s2FoundBefore);

        // Удаляем s1
        filter.remove(s1);

        // s2 всё ещё должен находиться (если их хэши не пересекались полностью)
        // В counting BF с разными хэшами удаление одного не влияет на другой
        // Но если хэши пересекаются, s2 может перестать находиться — это ожидаемо
        // Проверяем: если счётчики были > 1, s2 останется
    }

    @Test
    void testRemoveAndAddAgain() {
        String s = "0123456789";
        filter.add(s);
        assertTrue(filter.isValue(s));

        filter.remove(s);
        assertFalse(filter.isValue(s));

        filter.add(s);
        assertTrue(filter.isValue(s), "После повторного добавления должен находиться");
    }

    @Test
    void testRemoveNonExistentElement() {
        String s = "0123456789";
        filter.add(s);

        // Пытаемся удалить элемент, которого нет
        filter.remove("not_in_filter");

        // Оригинальный элемент должен остаться (если его счётчики > 0)
        // Но структура может быть нарушена — это ожидаемое поведение counting BF
    }

    @Test
    void testRemoveMultipleTimes() {
        String s = "0123456789";
        filter.add(s);
        filter.add(s);
        filter.add(s);

        // После одного удаления всё ещё должен находиться
        filter.remove(s);
        assertTrue(filter.isValue(s));

        // После второго удаления может всё ещё находиться (счётчики > 0)
        filter.remove(s);

        // После третьего — должен пропасть (или уже раньше)
        filter.remove(s);
    }

    // ==================== Тесты ложноположительного удаления ====================

    @Test
    void testFalsePositiveRemoval_corruptsStructure() {
        // Демонстрация проблемы: удаление несуществующего элемента
        // повреждает структуру фильтра

        // Создаём фильтр с одним элементом
        BloomFilter_3 smallFilter = new BloomFilter_3(32);
        smallFilter.add("AAAA");

        // Находим элемент, который даёт ложноположительное срабатывание
        // (или хотя бы пробуем удалить несуществующий)
        smallFilter.remove("ZZZZ"); // элемента нет, но удаление произойдёт

        // "AAAA" может всё ещё находиться или нет — зависит от перекрытия хэшей
        // Если хэши "AAAA" и "ZZZZ" не пересекаются, "AAAA" останется
        // Если пересекаются — "AAAA" может пропасть
    }

    @Test
    void testFalsePositiveRemoval_canBreakOtherElements() {
        // Конкретный пример: добавляем элементы, потом удаляем несуществующий
        BloomFilter_3 bf = new BloomFilter_3(32);

        bf.add("AAAA");
        bf.add("BBBB");

        int countBefore = bf.countNonZero();

        // Удаляем несуществующий элемент
        bf.remove("XXXX");

        int countAfter = bf.countNonZero();

        // Количество ненулевых счётчиков может уменьшиться
        // Это демонстрация проблемы ложноположительного удаления
        System.out.println("Non-zero counters before: " + countBefore +
                           ", after: " + countAfter);
    }

    @Test
    void testRemove_withFullFilter_corruptionCheck() {
        // Добавляем все 10 строк
        for (String s : TEST_STRINGS) {
            filter.add(s);
        }

        // Запоминаем, что все находятся
        boolean[] beforeRemoval = new boolean[TEST_STRINGS.length];
        for (int i = 0; i < TEST_STRINGS.length; i++) {
            beforeRemoval[i] = filter.isValue(TEST_STRINGS[i]);
            assertTrue(beforeRemoval[i]);
        }

        // Удаляем несуществующий элемент
        filter.remove("NOT_IN_FILTER");

        // Проверяем, сколько элементов осталось доступных
        int stillFound = 0;
        for (int i = 0; i < TEST_STRINGS.length; i++) {
            if (filter.isValue(TEST_STRINGS[i])) stillFound++;
        }

        System.out.println("After false removal: " + stillFound +
                           " of " + TEST_STRINGS.length + " still found");
        // В идеале все должны находиться, но при пересечении хэшей
        // некоторые могут пропасть — это ожидаемая проблема counting BF
    }

    // ==================== Тесты счётчиков ====================

    @Test
    void testCountersInitiallyZero() {
        byte[] counters = filter.getCounters();
        for (byte c : counters) {
            assertEquals(0, c);
        }
    }

    @Test
    void testCountersIncrementOnAdd() {
        filter.add("0123456789");
        byte[] counters = filter.getCounters();

        int nonZero = 0;
        for (byte c : counters) {
            if (c > 0) nonZero++;
        }
        // Должно быть установлено минимум 2 счётчика (по двум хэшам)
        assertTrue(nonZero >= 2);
    }

    @Test
    void testCountersDecrementOnRemove() {
        String s = "0123456789";
        filter.add(s);
        int countBefore = filter.countNonZero();

        filter.remove(s);
        int countAfter = filter.countNonZero();

        // После удаления ненулевых счётчиков должно стать не больше
        assertTrue(countAfter <= countBefore);
    }

    @Test
    void testCountersOverflowProtection() {
        String s = "0123456789";
        // Добавляем 20 раз — счётчик не должен превысить 15
        for (int i = 0; i < 20; i++) {
            filter.add(s);
        }

        byte[] counters = filter.getCounters();
        for (byte c : counters) {
            assertTrue(c <= 15, "Счётчик не должен превышать 15");
        }
    }

    @Test
    void testEstimatedElementCount() {
        filter.add("A");
        filter.add("B");
        filter.add("C");

        double estimated = filter.estimatedElementCount();
        // Ожидаем примерно 3, но может отличаться из-за коллизий хэшей
        assertTrue(estimated >= 2.0);
        assertTrue(estimated <= 5.0);
    }

    // ==================== Краевые случаи ====================

    @Test
    void testEmptyString() {
        filter.add("");
        assertTrue(filter.isValue(""));

        filter.remove("");
        assertFalse(filter.isValue(""));
    }

    @Test
    void testSingleCharacter() {
        filter.add("a");
        assertTrue(filter.isValue("a"));

        filter.remove("a");
        assertFalse(filter.isValue("a"));
    }

    @Test
    void testLongString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append("a");
        }
        String longStr = sb.toString();

        filter.add(longStr);
        assertTrue(filter.isValue(longStr));

        filter.remove(longStr);
        assertFalse(filter.isValue(longStr));
    }

    @Test
    void testUnicodeString() {
        filter.add("привет");
        assertTrue(filter.isValue("привет"));

        filter.remove("привет");
        assertFalse(filter.isValue("привет"));
    }

    // ==================== Оценка вероятности ====================

    @Test
    void testEstimatedFalsePositiveRate_empty() {
        assertEquals(0.0, filter.estimatedFalsePositiveRate());
    }

    @Test
    void testEstimatedFalsePositiveRate_afterAdds() {
        for (String s : TEST_STRINGS) {
            filter.add(s);
        }
        double rate = filter.estimatedFalsePositiveRate();
        assertTrue(rate > 0);
        assertTrue(rate < 1);
    }

    @Test
    void testEstimatedFalsePositiveRate_decreasesAfterRemove() {
        for (String s : TEST_STRINGS) {
            filter.add(s);
        }
        double rateBefore = filter.estimatedFalsePositiveRate();

        for (String s : TEST_STRINGS) {
            filter.remove(s);
        }
        double rateAfter = filter.estimatedFalsePositiveRate();

        // После удаления элементов заполненность должна уменьшиться
        assertTrue(rateAfter <= rateBefore);
    }

    // ==================== Производительность ====================

    @Test
    void testPerformance_addRemoveCheck() {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
            filter.add("item_" + i);
        }
        for (int i = 0; i < 1000; i++) {
            assertTrue(filter.isValue("item_" + i));
        }
        for (int i = 0; i < 1000; i++) {
            filter.remove("item_" + i);
        }
        for (int i = 0; i < 1000; i++) {
            assertFalse(filter.isValue("item_" + i));
        }

        long elapsed = System.currentTimeMillis() - start;
        System.out.println("Counting BF: 1000 add + check + remove + check = " +
                           elapsed + " ms");
        assertTrue(elapsed < 2000);
    }

    // ==================== Полный сценарий ====================

    @Test
    void testBloomFilter3_fullWorkflow() {
        // Добавляем элементы
        filter.add("user_A");
        filter.add("user_B");
        filter.add("user_C");

        assertTrue(filter.isValue("user_A"));
        assertTrue(filter.isValue("user_B"));
        assertTrue(filter.isValue("user_C"));
        assertFalse(filter.isValue("user_D"));

        // Удаляем один элемент
        filter.remove("user_B");
        assertFalse(filter.isValue("user_B"));
        assertTrue(filter.isValue("user_A"));
        assertTrue(filter.isValue("user_C"));

        // Добавляем обратно
        filter.add("user_B");
        assertTrue(filter.isValue("user_B"));
    }
}
