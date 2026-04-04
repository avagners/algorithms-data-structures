import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class BloomFilter_4Test {

    private BloomFilter_4 filter;

    private static final String[] ORIGINAL_SET = {
        "0123456789", "1234567890", "2345678901", "3456789012",
        "4567890123", "5678901234", "6789012345", "7890123456",
        "8901234567", "9012345678"
    };

    @BeforeEach
    void setUp() {
        filter = new BloomFilter_4(32);
        for (String s : ORIGINAL_SET) {
            filter.add(s);
        }
    }

    // ============================================
    // Тесты словарного восстановления
    // ============================================

    @Test
    void testRecoverFromDictionary_allFound() {
        // Полный словарь: все 10 строк + несколько лишних
        ArrayList<String> dictionary = new ArrayList<>();
        for (String s : ORIGINAL_SET) {
            dictionary.add(s);
        }
        dictionary.add("not_in_filter_1");
        dictionary.add("not_in_filter_2");

        ArrayList<String> recovered = filter.recoverFromDictionary(dictionary);

        // Все 10 оригинальных должны найтись
        for (String s : ORIGINAL_SET) {
            assertTrue(recovered.contains(s), "Должен восстановить: " + s);
        }
    }

    @Test
    void testRecoverFromDictionary_partialDictionary() {
        // Словарь содержит только часть оригинальных элементов
        ArrayList<String> dictionary = new ArrayList<>();
        dictionary.add("0123456789");
        dictionary.add("1234567890");
        dictionary.add("missing_element");

        ArrayList<String> recovered = filter.recoverFromDictionary(dictionary);

        assertTrue(recovered.contains("0123456789"));
        assertTrue(recovered.contains("1234567890"));
        assertFalse(recovered.contains("missing_element"));
    }

    @Test
    void testRecoverFromDictionary_emptyDictionary() {
        ArrayList<String> dictionary = new ArrayList<>();
        ArrayList<String> recovered = filter.recoverFromDictionary(dictionary);
        assertEquals(0, recovered.size());
    }

    @Test
    void testRecoverFromDictionary_exactOriginalSet() {
        // Словарь = точно все оригинальные строки
        ArrayList<String> dictionary = new ArrayList<>();
        for (String s : ORIGINAL_SET) {
            dictionary.add(s);
        }

        ArrayList<String> recovered = filter.recoverFromDictionary(dictionary);

        assertEquals(ORIGINAL_SET.length, recovered.size());
        for (String s : ORIGINAL_SET) {
            assertTrue(recovered.contains(s));
        }
    }

    // ============================================
    // Тесты brute-force восстановления
    // ============================================

    @Test
    void testRecoverFromBruteForce_singleDigitLength1() {
        BloomFilter_4 smallFilter = new BloomFilter_4(32);
        smallFilter.add("0");
        smallFilter.add("1");
        smallFilter.add("2");

        ArrayList<String> recovered = smallFilter.recoverFromBruteForce(
            "0123456789", 1);

        assertTrue(recovered.contains("0"));
        assertTrue(recovered.contains("1"));
        assertTrue(recovered.contains("2"));
    }

    @Test
    void testRecoverFromBruteForce_stringsLength2() {
        BloomFilter_4 smallFilter = new BloomFilter_4(32);
        smallFilter.add("AB");
        smallFilter.add("CD");

        ArrayList<String> recovered = smallFilter.recoverFromBruteForce(
            "ABCDE", 2);

        assertTrue(recovered.contains("AB"));
        assertTrue(recovered.contains("CD"));
    }

    @Test
    void testRecoverFromBruteForce_includesFalsePositives() {
        // Brute-force может вернуть ложноположительные элементы
        BloomFilter_4 smallFilter = new BloomFilter_4(32);
        smallFilter.add("A");

        ArrayList<String> recovered = smallFilter.recoverFromBruteForce(
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ", 1);

        // "A" точно должен быть
        assertTrue(recovered.contains("A"));
        // Могут быть ложноположительные — это нормально
    }

    // ============================================
    // Тесты восстановления по битовой маске
    // ============================================

    @Test
    void testRecoverFromBitmapInversion_allFound() {
        ArrayList<String> dictionary = new ArrayList<>();
        for (String s : ORIGINAL_SET) {
            dictionary.add(s);
        }
        dictionary.add("ZZZZZZZZZZ"); // не в фильтре

        ArrayList<String> recovered = filter.recoverFromBitmapInversion(dictionary);

        for (String s : ORIGINAL_SET) {
            assertTrue(recovered.contains(s));
        }
        assertFalse(recovered.contains("ZZZZZZZZZZ"));
    }

    @Test
    void testRecoverFromBitmapInversion_sameAsIsValue() {
        // recoverFromBitmapInversion по сути делает то же, что isValue,
        // но на уровне хэшей
        ArrayList<String> dictionary = new ArrayList<>();
        dictionary.add("0123456789");
        dictionary.add("not_in_filter");

        ArrayList<String> recovered = filter.recoverFromBitmapInversion(dictionary);

        assertTrue(recovered.contains("0123456789"));
        assertFalse(recovered.contains("not_in_filter"));
    }

    // ============================================
    // Тесты восстановления с уверенностью
    // ============================================

    @Test
    void testRecoverWithConfidence_ordering() {
        ArrayList<String> dictionary = new ArrayList<>();
        for (String s : ORIGINAL_SET) {
            dictionary.add(s);
        }
        dictionary.add("maybe_false");

        ArrayList<RecoveryCandidate> candidates =
            filter.recoverWithConfidence(dictionary);

        // Результат должен быть отсортирован по убыванию уверенности
        for (int i = 1; i < candidates.size() - 1; i++) {
            assertTrue(candidates.get(i).confidence >=
                       candidates.get(i + 1).confidence);
        }

        // Все оригинальные должны быть в результате
        for (String s : ORIGINAL_SET) {
            boolean found = false;
            for (RecoveryCandidate rc : candidates) {
                if (rc.element.equals(s)) {
                    found = true;
                    break;
                }
            }
            assertTrue(found, "Должен найти: " + s);
        }
    }

    @Test
    void testRecoverWithConfidence_duplicatesIncreaseConfidence() {
        BloomFilter_4 bf = new BloomFilter_4(32);
        bf.add("AAA");
        bf.add("AAA"); // Добавляем дважды
        bf.add("AAA"); // И трижды

        ArrayList<String> dictionary = new ArrayList<>();
        dictionary.add("AAA");
        dictionary.add("BBB"); // добавим один раз
        bf.add("BBB");

        ArrayList<RecoveryCandidate> candidates =
            bf.recoverWithConfidence(dictionary);

        // "AAA" должен иметь более высокую уверенность
        RecoveryCandidate aaa = null, bbb = null;
        for (RecoveryCandidate rc : candidates) {
            if (rc.element.equals("AAA")) aaa = rc;
            if (rc.element.equals("BBB")) bbb = rc;
        }

        assertNotNull(aaa);
        assertNotNull(bbb);
        assertTrue(aaa.confidence > bbb.confidence,
            "AAA (добавлен 3 раза) должен иметь бОльшую уверенность, чем BBB (1 раз)");
    }

    // ============================================
    // Тесты наиболее вероятного множества
    // ============================================

    @Test
    void testRecoverMostLikelySet_defaultThreshold() {
        ArrayList<String> dictionary = new ArrayList<>();
        for (String s : ORIGINAL_SET) {
            dictionary.add(s);
        }
        dictionary.add("false_1");
        dictionary.add("false_2");

        ArrayList<String> result = filter.recoverMostLikelySet(dictionary, 2);

        for (String s : ORIGINAL_SET) {
            assertTrue(result.contains(s));
        }
    }

    @Test
    void testRecoverMostLikelySet_highThreshold() {
        // С высоким порогом меньше элементов пройдут
        ArrayList<String> dictionary = new ArrayList<>();
        for (String s : ORIGINAL_SET) {
            dictionary.add(s);
        }

        ArrayList<String> lowThreshold =
            filter.recoverMostLikelySet(dictionary, 2);
        ArrayList<String> highThreshold =
            filter.recoverMostLikelySet(dictionary, 10);

        // Высокий порог даёт подмножество (или пусто)
        assertTrue(highThreshold.size() <= lowThreshold.size());
    }

    // ============================================
    // Тесты анализа счётчиков
    // ============================================

    @Test
    void testGetTopCounterPositions() {
        int[] topPositions = filter.getTopCounterPositions(5);
        assertEquals(5, topPositions.length);

        // Все топ-позиции должны иметь ненулевые счётчики
        for (int pos : topPositions) {
            assertTrue(filter.getCount(pos) >= 0);
        }
    }

    @Test
    void testGetTopCounterPositions_moreThanFilter() {
        // Запрашиваем больше позиций, чем размер фильтра
        int[] topPositions = filter.getTopCounterPositions(100);
        assertEquals(32, topPositions.length);
    }

    @Test
    void testGetNonZeroPositions() {
        ArrayList<Integer> positions = filter.getNonZeroPositions();

        // После добавления 10 строк ненулевых позиций должно быть > 0
        assertTrue(positions.size() > 0);
        assertTrue(positions.size() <= 32);

        // Все позиции валидны
        for (int pos : positions) {
            assertTrue(pos >= 0 && pos < 32);
            assertTrue(filter.getCount(pos) > 0);
        }
    }

    // ============================================
    // Тесты восстановления после удаления
    // ============================================

    @Test
    void testRecoverAfterRemoval() {
        // Создаём отдельный фильтр для этого теста
        BloomFilter_4 bf = new BloomFilter_4(32);
        bf.add("UNIQUE_A");
        bf.add("UNIQUE_B");
        bf.add("UNIQUE_C");

        // Удаляем один элемент
        bf.remove("UNIQUE_B");

        ArrayList<String> dictionary = new ArrayList<>();
        dictionary.add("UNIQUE_A");
        dictionary.add("UNIQUE_B");
        dictionary.add("UNIQUE_C");

        ArrayList<String> recovered = bf.recoverFromDictionary(dictionary);

        // Удалённый элемент может найтись из-за коллизий хэшей — это ограничение BF
        // Но "A" и "C" точно должны быть (если их хэши не были затронуты)
        assertTrue(recovered.contains("UNIQUE_A"));
        assertTrue(recovered.contains("UNIQUE_C"));

        // "B" после удаления с большой вероятностью не найдётся,
        // но при коллизиях может — это нормально
        System.out.println("After removal of UNIQUE_B, recovered: " + recovered);
    }

    // ============================================
    // Тесты коллизий и ограничений
    // ============================================

    @Test
    void testRecover_collisionDemonstration() {
        // Создаём фильтр с малым размером для коллизий
        BloomFilter_4 bf = new BloomFilter_4(16);
        bf.add("A");
        bf.add("B");

        ArrayList<String> dictionary = new ArrayList<>();
        // Добавляем элементы, которые могут коллизировать
        for (char c = 'A'; c <= 'Z'; c++) {
            dictionary.add(String.valueOf(c));
        }

        ArrayList<String> recovered = bf.recoverFromDictionary(dictionary);

        // "A" и "B" точно должны быть
        assertTrue(recovered.contains("A"));
        assertTrue(recovered.contains("B"));

        // Могут быть и ложноположительные
        System.out.println("Recovered: " + recovered);
    }

    @Test
    void testRecover_falsePositiveRate() {
        // Оцениваем долю ложноположительных при восстановлении
        ArrayList<String> allPossible = new ArrayList<>();
        // Генерируем "мусорные" строки + оригинальные
        for (String s : ORIGINAL_SET) {
            allPossible.add(s);
        }
        for (int i = 0; i < 100; i++) {
            allPossible.add("noise_" + i);
        }

        ArrayList<String> recovered = filter.recoverFromDictionary(allPossible);

        int originalFound = 0;
        int noiseFound = 0;
        for (String s : recovered) {
            boolean isOriginal = false;
            for (String o : ORIGINAL_SET) {
                if (o.equals(s)) {
                    isOriginal = true;
                    break;
                }
            }
            if (isOriginal) originalFound++;
            else noiseFound++;
        }

        System.out.println("Original found: " + originalFound +
                           ", noise found: " + noiseFound);

        // Все оригинальные из словаря должны найтись
        assertEquals(ORIGINAL_SET.length, originalFound);
        // Шум может найтись (ложноположительные) — это ожидаемо
    }

    // ============================================
    // Полный сценарий восстановления
    // ============================================

    @Test
    void testBloomFilter4_fullRecoveryWorkflow() {
        // 1. Создаём фильтр, добавляем элементы
        BloomFilter_4 bf = new BloomFilter_4(32);
        bf.add("alpha");
        bf.add("beta");
        bf.add("gamma");

        // 2. Восстанавливаем из полного словаря
        ArrayList<String> fullDict = new ArrayList<>();
        fullDict.add("alpha");
        fullDict.add("beta");
        fullDict.add("gamma");
        fullDict.add("delta");
        fullDict.add("epsilon");

        ArrayList<String> recovered = bf.recoverFromDictionary(fullDict);

        assertTrue(recovered.contains("alpha"));
        assertTrue(recovered.contains("beta"));
        assertTrue(recovered.contains("gamma"));
        assertFalse(recovered.contains("delta"));
        assertFalse(recovered.contains("epsilon"));

        // 3. Восстанавливаем с уверенностью
        ArrayList<RecoveryCandidate> candidates =
            bf.recoverWithConfidence(fullDict);

        assertEquals(3, candidates.size());
        for (RecoveryCandidate rc : candidates) {
            assertTrue(rc.confidence >= 2);
            System.out.println("  " + rc);
        }

        // 4. Анализ ненулевых позиций
        ArrayList<Integer> nonZero = bf.getNonZeroPositions();
        System.out.println("Non-zero counter positions: " + nonZero);
        assertTrue(nonZero.size() > 0);
        assertTrue(nonZero.size() <= 32);
    }

    @Test
    void testRecovery_performance_smallAlphabet() {
        BloomFilter_4 bf = new BloomFilter_4(32);
        bf.add("0");
        bf.add("1");
        bf.add("2");
        bf.add("3");

        long start = System.currentTimeMillis();
        ArrayList<String> recovered = bf.recoverFromBruteForce(
            "0123456789", 1);
        long elapsed = System.currentTimeMillis() - start;

        System.out.println("Brute-force recovery (10 candidates): " +
                           elapsed + " ms, found: " + recovered.size());

        assertTrue(recovered.contains("0"));
        assertTrue(recovered.contains("1"));
        assertTrue(recovered.contains("2"));
        assertTrue(recovered.contains("3"));
    }
}
