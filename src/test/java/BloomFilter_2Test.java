import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BloomFilter_2Test {

    private BloomFilter_2 filter1;
    private BloomFilter_2 filter2;
    private BloomFilter_2 filter3;

    private static final String[] SET1 = {"0123456789", "1234567890", "2345678901"};
    private static final String[] SET2 = {"3456789012", "4567890123", "5678901234"};
    private static final String[] SET3 = {"6789012345", "7890123456", "8901234567"};

    @BeforeEach
    void setUp() {
        filter1 = new BloomFilter_2(32);
        filter2 = new BloomFilter_2(32);
        filter3 = new BloomFilter_2(32);

        for (String s : SET1) filter1.add(s);
        for (String s : SET2) filter2.add(s);
        for (String s : SET3) filter3.add(s);
    }

    // ==================== Тесты слияния двух фильтров ====================

    @Test
    void testMerge_twoFilters_allElementsFound() {
        BloomFilter_2 merged = new BloomFilter_2(32);
        for (String s : SET1) merged.add(s);
        merged.merge(filter2);

        // Все элементы из обоих фильтров должны находиться
        for (String s : SET1) {
            assertTrue(merged.isValue(s), "Должен найти из SET1: " + s);
        }
        for (String s : SET2) {
            assertTrue(merged.isValue(s), "Должен найти из SET2: " + s);
        }
    }

    @Test
    void testMerge_inPlace() {
        int bitsBefore = filter1.countSetBits();
        filter1.merge(filter2);
        int bitsAfter = filter1.countSetBits();

        // После слияния битов должно стать больше или остаться тем же
        assertTrue(bitsAfter >= bitsBefore);
    }

    @Test
    void testMerge_differentSizesThrows() {
        BloomFilter_2 small = new BloomFilter_2(16);
        assertThrows(IllegalArgumentException.class, () -> filter1.merge(small));
    }

    @Test
    void testMerge_commute() {
        BloomFilter_2 m1 = new BloomFilter_2(32);
        for (String s : SET1) m1.add(s);
        m1.merge(filter2);

        BloomFilter_2 m2 = new BloomFilter_2(32);
        for (String s : SET2) m2.add(s);
        m2.merge(filter1);

        // Оба объединённых фильтра должны содержать все элементы
        for (String s : SET1) {
            assertTrue(m1.isValue(s));
            assertTrue(m2.isValue(s));
        }
        for (String s : SET2) {
            assertTrue(m1.isValue(s));
            assertTrue(m2.isValue(s));
        }
    }

    @Test
    void testMerge_withEmptyFilter() {
        BloomFilter_2 empty = new BloomFilter_2(32);
        BloomFilter_2 merged = new BloomFilter_2(32);
        for (String s : SET1) merged.add(s);

        int bitsBefore = merged.countSetBits();
        merged.merge(empty);
        int bitsAfter = merged.countSetBits();

        // Слияние с пустым фильтром не меняет битовую маску
        assertEquals(bitsBefore, bitsAfter);

        for (String s : SET1) {
            assertTrue(merged.isValue(s));
        }
    }

    @Test
    void testMerge_emptyWithEmpty() {
        BloomFilter_2 e1 = new BloomFilter_2(32);
        BloomFilter_2 e2 = new BloomFilter_2(32);
        e1.merge(e2);

        assertEquals(0, e1.countSetBits());
        assertFalse(e1.isValue("anything"));
    }

    // ==================== Тесты слияния множества фильтров ====================

    @Test
    void testMergeMany_threeFilters() {
        BloomFilter_2[] filters = {filter1, filter2, filter3};
        BloomFilter_2 merged = BloomFilter_2.mergeMany(filters);

        for (String s : SET1) assertTrue(merged.isValue(s));
        for (String s : SET2) assertTrue(merged.isValue(s));
        for (String s : SET3) assertTrue(merged.isValue(s));
    }

    @Test
    void testMergeMany_singleFilter() {
        BloomFilter_2[] filters = {filter1};
        BloomFilter_2 merged = BloomFilter_2.mergeMany(filters);

        for (String s : SET1) assertTrue(merged.isValue(s));
        assertEquals(filter1.countSetBits(), merged.countSetBits());
    }

    @Test
    void testMergeMany_emptyArray() {
        assertThrows(IllegalArgumentException.class,
            () -> BloomFilter_2.mergeMany(new BloomFilter_2[0]));
    }

    @Test
    void testMergeMany_nullArray() {
        assertThrows(IllegalArgumentException.class,
            () -> BloomFilter_2.mergeMany(null));
    }

    @Test
    void testMergeMany_mixedSizesThrows() {
        BloomFilter_2[] filters = {
            filter1,
            new BloomFilter_2(16) // фильтр другого размера
        };
        assertThrows(IllegalArgumentException.class,
            () -> BloomFilter_2.mergeMany(filters));
    }

    // ==================== Тесты битовых операций ====================

    @Test
    void testGetSetBitmap() {
        filter1.add("test");
        int bm = filter1.getBitmap();
        assertTrue(bm > 0);

        BloomFilter_2 restored = new BloomFilter_2(32);
        restored.setBitmap(bm);
        assertTrue(restored.isValue("test"));
    }

    @Test
    void testCountSetBits() {
        BloomFilter_2 empty = new BloomFilter_2(32);
        assertEquals(0, empty.countSetBits());

        filter1.add("test");
        int bits = filter1.countSetBits();
        assertTrue(bits >= 1); // минимум 1-2 бита установлено
        assertTrue(bits <= 32);
    }

    // ==================== Оценка вероятности ложного срабатывания ====================

    @Test
    void testEstimatedFalsePositiveRate_empty() {
        BloomFilter_2 empty = new BloomFilter_2(32);
        assertEquals(0.0, empty.estimatedFalsePositiveRate());
    }

    @Test
    void testEstimatedFalsePositiveRate_afterAdds() {
        double rate1 = filter1.estimatedFalsePositiveRate();
        assertTrue(rate1 > 0);
        assertTrue(rate1 < 1);

        // После слияния вероятность должна вырасти
        filter1.merge(filter2);
        double rateMerged = filter1.estimatedFalsePositiveRate();
        assertTrue(rateMerged >= rate1,
            "После слияния вероятность ложного срабатывания должна расти");
    }

    @Test
    void testFalsePositiveRate_increasesAfterMerge() {
        // Оцениваем FP rate до и после слияния
        double rate1 = filter1.estimatedFalsePositiveRate();
        double rate2 = filter2.estimatedFalsePositiveRate();

        filter1.merge(filter2);
        double rateMerged = filter1.estimatedFalsePositiveRate();

        // Вероятность растёт после слияния
        assertTrue(rateMerged >= rate1);
        assertTrue(rateMerged >= rate2);
        assertTrue(rateMerged < 1.0); // не должна быть 100%
    }

    // ==================== Практическая проверка ложных срабатываний ====================

    @Test
    void testFalsePositives_afterMerge() {
        BloomFilter_2 merged = new BloomFilter_2(32);
        for (String s : SET1) merged.add(s);
        for (String s : SET2) merged.add(s);

        // Альтернатива: через слияние
        BloomFilter_2 viaMerge = new BloomFilter_2(32);
        for (String s : SET1) viaMerge.add(s);
        viaMerge.merge(filter2);

        // Оба подхода должны давать одинаковый результат
        assertEquals(viaMerge.countSetBits(), merged.countSetBits());

        // Считаем ложные срабатывания
        int falsePositivesDirect = 0;
        int falsePositivesMerged = 0;
        int checked = 500;

        for (int i = 0; i < checked; i++) {
            String random = "rand_" + i;
            if (merged.isValue(random)) falsePositivesDirect++;
            if (viaMerge.isValue(random)) falsePositivesMerged++;
        }

        // Одинаковый bitmap => одинаковые ложные срабатывания
        assertEquals(falsePositivesDirect, falsePositivesMerged);
    }

    // ==================== Производительность ====================

    @Test
    void testPerformance_mergeMany() {
        BloomFilter_2[] filters = new BloomFilter_2[100];
        for (int i = 0; i < 100; i++) {
            filters[i] = new BloomFilter_2(32);
            for (int j = 0; j < 10; j++) {
                filters[i].add("filter" + i + "_item" + j);
            }
        }

        long start = System.currentTimeMillis();
        BloomFilter_2 merged = BloomFilter_2.mergeMany(filters);
        long elapsed = System.currentTimeMillis() - start;

        System.out.println("Merge 100 filters: " + elapsed + " ms, bits: " + merged.countSetBits());
        assertTrue(elapsed < 1000);
        assertTrue(merged.countSetBits() > 0);
    }

    // ==================== Полный сценарий ====================

    @Test
    void testBloomFilter2_fullWorkflow() {
        // Создаём локальные фильтры (как на разных узлах)
        BloomFilter_2 node1 = new BloomFilter_2(32);
        BloomFilter_2 node2 = new BloomFilter_2(32);

        node1.add("user_A");
        node1.add("user_B");

        node2.add("user_C");
        node2.add("user_D");

        // До слияния каждый узел видит только свои элементы
        assertTrue(node1.isValue("user_A"));
        assertFalse(node1.isValue("user_C"));
        assertTrue(node2.isValue("user_C"));
        assertFalse(node2.isValue("user_A"));

        // Сливаем фильтры
        node1.merge(node2);

        // Теперь node1 видит все элементы
        assertTrue(node1.isValue("user_A"));
        assertTrue(node1.isValue("user_B"));
        assertTrue(node1.isValue("user_C"));
        assertTrue(node1.isValue("user_D"));
    }
}
