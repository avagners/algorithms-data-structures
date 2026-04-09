import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NativeCacheTest {

    private NativeCache<String> cache;

    @BeforeEach
    void setUp() {
        cache = new NativeCache<>(5, String.class);
    }

    @Test
    void testPutAndGet() {
        cache.put("key1", "value1");
        assertEquals("value1", cache.get("key1"));
    }

    @Test
    void testGetNonExistent() {
        assertNull(cache.get("nonexistent"));
    }

    @Test
    void testIsKey() {
        cache.put("key1", "value1");
        assertTrue(cache.isKey("key1"));
        assertFalse(cache.isKey("nonexistent"));
    }

    @Test
    void testCount() {
        assertEquals(0, cache.count());
        cache.put("a", "1");
        assertEquals(1, cache.count());
        cache.put("b", "2");
        assertEquals(2, cache.count());
    }

    @Test
    void testRemove() {
        cache.put("key1", "value1");
        assertTrue(cache.remove("key1"));
        assertEquals(0, cache.count());
        assertNull(cache.get("key1"));
        assertFalse(cache.remove("key1"));
    }

    @Test
    void testUpdateExistingKey() {
        cache.put("key1", "value1");
        cache.put("key1", "updated");
        assertEquals("updated", cache.get("key1"));
        assertEquals(1, cache.count());
    }

    @Test
    void testHits_incrementOnGet() {
        cache.put("key1", "value1");
        assertEquals(0, cache.getHits("key1"));

        cache.get("key1");
        assertEquals(1, cache.getHits("key1"));

        cache.get("key1");
        cache.get("key1");
        assertEquals(3, cache.getHits("key1"));
    }

    @Test
    void testHits_notIncrementOnIsKey() {
        cache.put("key1", "value1");
        cache.isKey("key1");
        cache.isKey("key1");
        // isKey не должен увеличивать счётчик
        assertEquals(0, cache.getHits("key1"));
    }

    @Test
    void testHits_incrementOnPut_existing() {
        cache.put("key1", "value1");
        cache.put("key1", "value2"); // Обновление — тоже обращение
        assertEquals(1, cache.getHits("key1"));
    }

    @Test
    void testHits_newKeyStartsAtZero() {
        cache.put("key1", "value1");
        assertEquals(0, cache.getHits("key1"));
    }

    @Test
    void testHits_nonExistentKey() {
        assertEquals(-1, cache.getHits("nonexistent"));
    }

    @Test
    void testEviction_fillAndAddMore() {
        // Заполняем кэш полностью
        cache.put("a", "1");
        cache.put("b", "2");
        cache.put("c", "3");
        cache.put("d", "4");
        cache.put("e", "5");

        assertEquals(5, cache.count());
        assertTrue(cache.isFull());

        // Добавляем новый ключ — должен произойти eviction
        cache.put("f", "6");

        // Количество должно остаться 5
        assertEquals(5, cache.count());

        // "f" должен быть в кэше
        assertEquals("6", cache.get("f"));
    }

    @Test
    void testEviction_removesLeastFrequentlyUsed() {
        // Заполняем кэш
        cache.put("a", "1");
        cache.put("b", "2");
        cache.put("c", "3");
        cache.put("d", "4");
        cache.put("e", "5");

        // Делаем много обращений к некоторым ключам
        cache.get("a");
        cache.get("a");
        cache.get("a");
        cache.get("b");
        cache.get("b");
        // "c", "d", "e" — hits = 0

        // Добавляем новый ключ — должен вытесниться один из c/d/e (hits=0)
        cache.put("f", "6");

        // "a" и "b" точно должны остаться
        assertTrue(cache.isKey("a"), "'a' с hits=3 не должен вытесниться");
        assertTrue(cache.isKey("b"), "'b' с hits=2 не должен вытесниться");

        // "f" должен быть в кэше
        assertTrue(cache.isKey("f"));

        // Один из c/d/e должен быть вытеснен
        int remainingCount = 0;
        if (cache.isKey("c")) remainingCount++;
        if (cache.isKey("d")) remainingCount++;
        if (cache.isKey("e")) remainingCount++;
        assertEquals(2, remainingCount, "Один из c/d/e должен быть вытеснен");
    }

    @Test
    void testEviction_popularKeySurvives() {
        // Создаём кэш маленького размера
        NativeCache<String> smallCache = new NativeCache<>(3, String.class);

        smallCache.put("hot", "hot_value");
        smallCache.put("cold1", "cold1_value");
        smallCache.put("cold2", "cold2_value");

        // "hot" — популярный ключ
        smallCache.get("hot");
        smallCache.get("hot");
        smallCache.get("hot");

        // Добавляем два новых ключа
        smallCache.put("new1", "new1_value");
        smallCache.put("new2", "new2_value");

        // "hot" должен остаться
        assertTrue(smallCache.isKey("hot"), "Популярный ключ 'hot' должен выжить");
        assertEquals("hot_value", smallCache.get("hot"));
    }

    @Test
    void testEviction_equalHits_evictsFirst() {
        // Все ключи с hits = 0
        cache.put("a", "1");
        cache.put("b", "2");
        cache.put("c", "3");
        cache.put("d", "4");
        cache.put("e", "5");

        // Ни один не использовался — у всех hits = 0
        cache.put("f", "6");

        // Первый найденный с минимальным hits должен вытесниться
        // (обычно это "a", но зависит от порядка обхода)
        assertEquals(5, cache.count());
        assertTrue(cache.isKey("f"));
    }

    @Test
    void testEviction_afterRemove() {
        cache.put("a", "1");
        cache.put("b", "2");
        cache.put("c", "3");

        cache.get("a");
        cache.get("a");

        // Удаляем "b"
        cache.remove("b");
        assertEquals(2, cache.count());

        // Заполняем обратно
        cache.put("d", "4");
        cache.put("e", "5");
        cache.put("f", "6"); // Теперь должен вытесниться наименее используемый

        assertEquals(5, cache.count());
        assertTrue(cache.isKey("a")); // hits=2, должен выжить
    }

    @Test
    void testCollision_manyKeys_sameHash() {
        // Создаём кэш с размером 3
        NativeCache<String> smallCache = new NativeCache<>(3, String.class);

        // hashFun("abc") = (97+98+99) % 3 = 294 % 3 = 0
        // hashFun("bca") = (98+99+97) % 3 = 294 % 3 = 0
        // hashFun("cab") = (99+97+98) % 3 = 294 % 3 = 0
        // Все три имеют одинаковый хэш — коллизия

        smallCache.put("abc", "1");
        smallCache.put("bca", "2");
        smallCache.put("cab", "3");

        assertEquals(3, smallCache.count());
        assertEquals("1", smallCache.get("abc"));
        assertEquals("2", smallCache.get("bca"));
        assertEquals("3", smallCache.get("cab"));

        // Добавляем четвёртый — должен произойти eviction
        smallCache.get("abc"); // abc становится популярным

        smallCache.put("dac", "4");

        assertTrue(smallCache.isKey("abc"), "abc с hits > 0 должен выжить");
        assertEquals(3, smallCache.count());
    }

    @Test
    void testCollision_extreme_allSameHash() {
        // Маленький кэш, все ключи коллизируют
        NativeCache<String> tinyCache = new NativeCache<>(2, String.class);

        tinyCache.put("ab", "1"); // 97+98 = 195 % 2 = 1
        tinyCache.put("ba", "2"); // 98+97 = 195 % 2 = 1

        assertEquals(2, tinyCache.count());

        // Делаем "ab" популярным
        tinyCache.get("ab");
        tinyCache.get("ab");
        tinyCache.get("ab");

        // Добавляем ещё один с тем же хэшем
        tinyCache.put("ab", "3"); // Обновление
        assertEquals("3", tinyCache.get("ab"));

        tinyCache.put("cd", "4"); // 99+100 = 199 % 2 = 1, коллизия!

        // "ab" должен выжить (hits=4), "ba" — кандидат на вытеснение
        assertTrue(tinyCache.isKey("ab"));
        assertEquals(2, tinyCache.count());
    }

    @Test
    void testEviction_withCollisions_verifyHits() {
        NativeCache<String> small = new NativeCache<>(3, String.class);

        small.put("aaa", "v1");
        small.put("bbb", "v2");
        small.put("ccc", "v3");

        // aaa — 5 обращений
        for (int i = 0; i < 5; i++) small.get("aaa");
        // bbb — 2 обращения
        for (int i = 0; i < 2; i++) small.get("bbb");
        // ccc — 0 обращений

        small.put("ddd", "v4");

        // ccc должен вытесниться (hits=0)
        assertFalse(small.isKey("ccc"), "ccc с hits=0 должен вытесниться");
        assertTrue(small.isKey("aaa"));
        assertTrue(small.isKey("bbb"));
        assertTrue(small.isKey("ddd"));

        assertEquals(5, small.getHits("aaa")); // 5 get вызовов
        assertEquals(2, small.getHits("bbb")); // 2 get вызова
    }

    @Test
    void testStress_manyInserts() {
        NativeCache<String> small = new NativeCache<>(10, String.class);

        // Вставляем 100 ключей в кэш на 10 мест
        for (int i = 0; i < 100; i++) {
            small.put("key_" + i, "value_" + i);
        }

        // Кэш должен быть полным
        assertEquals(10, small.count());
        assertTrue(small.isFull());
    }

    @Test
    void testStress_hotKeys_survive() {
        NativeCache<String> small = new NativeCache<>(5, String.class);

        // Заполняем
        for (int i = 0; i < 5; i++) {
            small.put("key_" + i, "value_" + i);
        }

        // Делаем первые 2 ключа очень популярными
        for (int access = 0; access < 50; access++) {
            small.get("key_0");
            small.get("key_1");
        }

        // Вставляем ещё 20 ключей
        for (int i = 5; i < 25; i++) {
            small.put("key_" + i, "value_" + i);
        }

        // Популярные ключи должны выжить
        assertTrue(small.isKey("key_0"), "Популярный key_0 должен выжить");
        assertTrue(small.isKey("key_1"), "Популярный key_1 должен выжить");
    }

    @Test
    void testEmptyCache() {
        assertEquals(0, cache.count());
        assertFalse(cache.isFull());
        assertNull(cache.get("anything"));
        assertEquals(-1, cache.getHits("anything"));
    }

    @Test
    void testSingleSlotCache() {
        NativeCache<String> single = new NativeCache<>(1, String.class);

        single.put("only", "value");
        assertEquals(1, single.count());
        assertTrue(single.isFull());

        // Добавляем ещё один — eviction
        single.put("new", "new_value");
        assertEquals(1, single.count());

        // "only" был с hits=0, "new" — единственный оставшийся
        // Один из них должен остаться
        assertTrue(single.isKey("only") || single.isKey("new"));
    }

    @Test
    void testUpdateDoesNotIncreaseCount() {
        cache.put("a", "1");
        cache.put("b", "2");
        int countBefore = cache.count();

        cache.put("a", "updated");
        assertEquals(countBefore, cache.count());
    }

    @Test
    void testNativeCache_fullWorkflow() {
        // Добавляем элементы
        cache.put("user_1", "Alice");
        cache.put("user_2", "Bob");
        cache.put("user_3", "Charlie");
        cache.put("user_4", "Dave");
        cache.put("user_5", "Eve");

        assertTrue(cache.isFull());

        // "user_1" и "user_2" — активно используются
        assertEquals("Alice", cache.get("user_1"));
        assertEquals("Alice", cache.get("user_1"));
        assertEquals("Alice", cache.get("user_1"));
        assertEquals("Bob", cache.get("user_2"));
        assertEquals("Bob", cache.get("user_2"));

        // "user_5" не использовался ни разу

        // Добавляем нового пользователя
        cache.put("user_6", "Frank");

        // "user_5" (hits=0) должен вытесниться
        assertFalse(cache.isKey("user_5"), "Неиспользуемый user_5 должен вытесниться");

        // Популярные должны остаться
        assertTrue(cache.isKey("user_1"));
        assertTrue(cache.isKey("user_2"));
        assertTrue(cache.isKey("user_6"));

        System.out.println("Hits after workflow:");
        System.out.println("  user_1: " + cache.getHits("user_1"));
        System.out.println("  user_2: " + cache.getHits("user_2"));
        System.out.println("  user_6: " + cache.getHits("user_6"));
    }
}

