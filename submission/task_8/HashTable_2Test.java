import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HashTable_2Test {

    @Test
    public void testHashFun_sameStringSameHash() {
        HashTable_2 table = new HashTable_2(17, 3);
        int hash1 = table.hashFun("test");
        int hash2 = table.hashFun("test");
        assertEquals(hash1, hash2);
    }

    @Test
    public void testHashFun_withinBounds() {
        HashTable_2 table = new HashTable_2(17, 3);
        int hash = table.hashFun("some string");
        assertTrue(hash >= 0 && hash < 17);
    }

    @Test
    public void testPutAndFind_singleElement() {
        HashTable_2 table = new HashTable_2(17, 3);
        int index = table.put("test");
        assertTrue(index >= 0);
        assertEquals(index, table.find("test"));
    }

    @Test
    public void testPutAndFind_multipleElements() {
        HashTable_2 table = new HashTable_2(17, 3);
        table.put("first");
        table.put("second");
        table.put("third");

        assertTrue(table.find("first") >= 0);
        assertTrue(table.find("second") >= 0);
        assertTrue(table.find("third") >= 0);
        assertEquals(-1, table.find("notexist"));
    }

    @Test
    public void testPut_duplicateValue() {
        HashTable_2 table = new HashTable_2(17, 3);
        int index1 = table.put("test");
        int index2 = table.put("test");

        assertEquals(index1, index2);
        assertEquals(1, table.count());
    }

    @Test
    public void testResize_autoResizeWhenFull() {
        HashTable_2 table = new HashTable_2(5, 1);
        int initialSize = table.size();

        // loadFactor = 0.75, size = 5
        // Проверка происходит ДО добавления, поэтому resize при 4/5 = 0.8 >= 0.75
        // т.е. на 5-м элементе
        table.put("a"); // count=0, 0/5=0 < 0.75, no resize
        table.put("b"); // count=1, 1/5=0.2 < 0.75, no resize
        table.put("c"); // count=2, 2/5=0.4 < 0.75, no resize
        table.put("d"); // count=3, 3/5=0.6 < 0.75, no resize
        table.put("e"); // count=4, 4/5=0.8 >= 0.75 → resize!

        assertTrue(table.size() > initialSize, "Размер таблицы должен увеличиться");
        assertEquals(initialSize * 2, table.size());
    }

    @Test
    public void testResize_elementsPreservedAfterResize() {
        HashTable_2 table = new HashTable_2(5, 1);

        table.put("a");
        table.put("b");
        table.put("c");
        table.put("d"); // Trigger resize

        // Все элементы должны остаться доступными
        assertTrue(table.find("a") >= 0);
        assertTrue(table.find("b") >= 0);
        assertTrue(table.find("c") >= 0);
        assertTrue(table.find("d") >= 0);
        assertEquals(4, table.count());
    }

    @Test
    public void testResize_multipleResizes() {
        HashTable_2 table = new HashTable_2(5, 1);
        int initialSize = table.size();

        // Добавляем много элементов, чтобы вызвать несколько resize
        for (int i = 0; i < 50; i++) {
            table.put("key" + i);
        }

        assertTrue(table.size() > initialSize);
        assertEquals(50, table.count());

        // Все элементы должны быть доступны
        for (int i = 0; i < 50; i++) {
            assertTrue(table.find("key" + i) >= 0, "Element key" + i + " not found");
        }
    }

    @Test
    public void testResize_loadFactorAfterResize() {
        HashTable_2 table = new HashTable_2(5, 1);

        // Заполняем до resize
        for (int i = 0; i < 10; i++) {
            table.put("key" + i);
        }

        // После resize коэффициент загрузки должен быть меньше порога
        assertTrue(table.getLoadFactor() < 0.75, "Load factor should be below threshold after resize");
    }

    @Test
    public void testResize_findAfterMultipleResizes() {
        HashTable_2 table = new HashTable_2(5, 1);

        // Добавляем элементы порциями
        for (int batch = 0; batch < 5; batch++) {
            for (int i = 0; i < 10; i++) {
                table.put("batch" + batch + "_key" + i);
            }

            // Проверяем, что все предыдущие элементы доступны
            for (int b = 0; b <= batch; b++) {
                for (int i = 0; i < 10; i++) {
                    assertTrue(table.find("batch" + b + "_key" + i) >= 0);
                }
            }
        }

        assertEquals(50, table.count());
    }

    @Test
    public void testSizeAndCount() {
        HashTable_2 table = new HashTable_2(17, 3);
        assertEquals(17, table.size());
        assertEquals(0, table.count());

        table.put("a");
        assertEquals(1, table.count());

        table.put("b");
        assertEquals(2, table.count());

        table.put("a"); // Дубликат
        assertEquals(2, table.count());
    }

    @Test
    public void testGetLoadFactor() {
        HashTable_2 table = new HashTable_2(10, 1);
        assertEquals(0.0, table.getLoadFactor(), 0.01);

        table.put("a");
        assertEquals(0.1, table.getLoadFactor(), 0.01);

        table.put("b");
        assertEquals(0.2, table.getLoadFactor(), 0.01);
    }

    @Test
    public void testSmallTable() {
        HashTable_2 table = new HashTable_2(5, 2);

        for (int i = 0; i < 20; i++) {
            table.put("key" + i);
        }

        assertTrue(table.size() > 5);
        assertEquals(20, table.count());

        for (int i = 0; i < 20; i++) {
            assertTrue(table.find("key" + i) >= 0);
        }
    }

    @Test
    public void testLargerTable() {
        HashTable_2 table = new HashTable_2(31, 7);

        for (int i = 0; i < 100; i++) {
            table.put("key" + i);
        }

        assertTrue(table.size() >= 31);
        assertEquals(100, table.count());

        for (int i = 0; i < 100; i++) {
            assertTrue(table.find("key" + i) >= 0);
        }
    }

    @Test
    public void testResizePerformance() {
        HashTable_2 table = new HashTable_2(17, 3);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
            table.put("key" + i);
        }

        long endTime = System.currentTimeMillis();

        assertEquals(1000, table.count());
        assertTrue(endTime - startTime < 5000, "Resize should complete in reasonable time");

        // Проверяем случайные элементы
        assertTrue(table.find("key0") >= 0);
        assertTrue(table.find("key500") >= 0);
        assertTrue(table.find("key999") >= 0);
    }
}

