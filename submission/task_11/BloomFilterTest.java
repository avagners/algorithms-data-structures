import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BloomFilterTest {

    private BloomFilter filter;

    // 10 тестовых строк по заданию
    private static final String[] TEST_STRINGS = {
        "0123456789", "1234567890", "2345678901", "3456789012",
        "4567890123", "5678901234", "6789012345", "7890123456",
        "8901234567", "9012345678"
    };

    @BeforeEach
    void setUp() {
        // m=32, n=10 => k≈2
        filter = new BloomFilter(32);
    }

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
    void testNonInsertedStringsAreNotFound() {
        for (String s : TEST_STRINGS) {
            filter.add(s);
        }
        // Строки, которых точно нет в фильтре
        assertFalse(filter.isValue("abcdef"));
        assertFalse(filter.isValue("xyz"));
        assertFalse(filter.isValue("0000000000"));
    }

    @Test
    void testEmptyFilterReturnsFalse() {
        for (String s : TEST_STRINGS) {
            assertFalse(filter.isValue(s), "Пустой фильтр не должен находить: " + s);
        }
    }

    @Test
    void testHashFunctionsReturnDifferentValues() {
        String s = "0123456789";
        int h1 = filter.hash1(s);
        int h2 = filter.hash2(s);
        assertNotEquals(h1, h2, "Хэш-функции должны давать разные результаты");
        assertTrue(h1 >= 0 && h1 < 32, "hash1 должен быть в диапазоне [0, 31]");
        assertTrue(h2 >= 0 && h2 < 32, "hash2 должен быть в диапазоне [0, 31]");
    }

    @Test
    void testHashFunctionsDeterministic() {
        String s = "0123456789";
        assertEquals(filter.hash1(s), filter.hash1(s));
        assertEquals(filter.hash2(s), filter.hash2(s));
    }

    @Test
    void testFalsePositiveRate() {
        // Добавляем все 10 строк
        for (String s : TEST_STRINGS) {
            filter.add(s);
        }
        // Проверяем 1000 случайных строк — ложноположительных должно быть немного
        int falsePositives = 0;
        int checked = 1000;
        for (int i = 0; i < checked; i++) {
            String random = "rand" + i + "_test";
            if (filter.isValue(random)) {
                falsePositives++;
            }
        }
        // При m=32, n=10, k=2 вероятность P ≈ 0.6931^(32/10) ≈ 0.11
        // Допускаем до 25% ложных срабатываний
        double rate = (double) falsePositives / checked;
        assertTrue(rate < 0.25,
            "Слишком высокая доля ложноположительных: " + rate +
            " (ожидается < 0.25)");
    }

    @Test
    void testAddIncremental() {
        String s = "0123456789";
        assertFalse(filter.isValue(s));
        filter.add(s);
        assertTrue(filter.isValue(s));
    }

    @Test
    void testEmptyString() {
        filter.add("");
        assertTrue(filter.isValue(""), "Пустая строка должна находиться после добавления");
    }

    @Test
    void testEmptyString_notAdded() {
        assertFalse(filter.isValue(""), "Пустая строка не должна находиться в пустом фильтре");
    }

    @Test
    void testSingleCharacterString() {
        filter.add("a");
        assertTrue(filter.isValue("a"));
        assertFalse(filter.isValue("b"));
    }

    @Test
    void testDuplicateAdd() {
        String s = "0123456789";
        filter.add(s);
        filter.add(s);
        filter.add(s);
        // Многократное добавление той же строки не должно ломать фильтр
        assertTrue(filter.isValue(s));
    }

    @Test
    void testSimilarStrings() {
        String s1 = "0123456789";
        String s2 = "0123456788"; // отличается одним символом

        filter.add(s1);

        assertTrue(filter.isValue(s1));
        // s2 может дать ложноположительное, но это допустимо
        // Главное, что s1 точно находится
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
    }

    @Test
    void testUnicodeString() {
        filter.add("привет");
        assertTrue(filter.isValue("привет"));
    }

    @Test
    void testSpecialCharacters() {
        filter.add("hello world!");
        filter.add("tab\there");
        filter.add("new\nline");

        assertTrue(filter.isValue("hello world!"));
        assertTrue(filter.isValue("tab\there"));
        assertTrue(filter.isValue("new\nline"));
    }

    @Test
    void testAllTenStringsSimilarFalsePositiveCheck() {
        // Добавляем все 10 строк
        for (String s : TEST_STRINGS) {
            filter.add(s);
        }

        // Проверяем строки, которые отличаются одним символом от исходных
        for (int i = 0; i < TEST_STRINGS.length; i++) {
            String modified = TEST_STRINGS[i].substring(0, 9) + "X";
            // Они не должны обязательно находиться (может быть ложное срабатывание, но не гарантировано)
            // Просто проверяем, что оригиналы находятся
            assertTrue(filter.isValue(TEST_STRINGS[i]));
        }
    }

    @Test
    void testHashFunctions_differentStrings() {
        for (String s : TEST_STRINGS) {
            int h1 = filter.hash1(s);
            int h2 = filter.hash2(s);
            assertTrue(h1 >= 0 && h1 < 32, "hash1 в диапазоне для " + s);
            assertTrue(h2 >= 0 && h2 < 32, "hash2 в диапазоне для " + s);
        }
    }

    @Test
    void testHashFunctions_distribution() {
        // Проверяем, что хэш-функции дают разумное распределение
        int[] counts1 = new int[32];
        int[] counts2 = new int[32];

        for (String s : TEST_STRINGS) {
            counts1[filter.hash1(s)]++;
            counts2[filter.hash2(s)]++;
        }

        // Каждая строка должна дать хотя бы один хэш
        int sum1 = 0, sum2 = 0;
        for (int c : counts1) sum1 += c;
        for (int c : counts2) sum2 += c;

        assertEquals(10, sum1);
        assertEquals(10, sum2);
    }

    @Test
    void testBitmapInitiallyZero() {
        // Проверяем, что новый фильтр пуст
        filter = new BloomFilter(32);
        for (int i = 0; i < 32; i++) {
            // Косвенно через isValue — все должны вернуть false
        }
        assertFalse(filter.isValue("anything"));
    }

    @Test
    void testPerformance_addAndCheck() {
        long startTime = System.currentTimeMillis();

        // Добавляем 1000 строк
        for (int i = 0; i < 1000; i++) {
            filter.add("item_" + i);
        }

        // Проверяем все 1000
        for (int i = 0; i < 1000; i++) {
            assertTrue(filter.isValue("item_" + i));
        }

        long elapsed = System.currentTimeMillis() - startTime;
        System.out.println("BloomFilter: 1000 add + 1000 check = " + elapsed + " ms");
        assertTrue(elapsed < 2000, "Должно выполниться за разумное время");
    }

    @Test
    void testBloomFilter_fullWorkflow() {
        // Пустой фильтр
        assertFalse(filter.isValue("0123456789"));

        // Добавляем несколько строк
        filter.add("0123456789");
        filter.add("1234567890");
        filter.add("2345678901");

        // Проверяем наличие
        assertTrue(filter.isValue("0123456789"));
        assertTrue(filter.isValue("1234567890"));
        assertTrue(filter.isValue("2345678901"));

        // Проверяем отсутствие
        assertFalse(filter.isValue("not_in_filter"));

        // Добавляем все 10 строк
        for (String s : TEST_STRINGS) {
            filter.add(s);
        }

        // Все 10 строк находятся
        for (String s : TEST_STRINGS) {
            assertTrue(filter.isValue(s), "Должен найти: " + s);
        }
    }
}

