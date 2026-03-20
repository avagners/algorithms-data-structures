import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HashTableTest {

    // ==================== Тесты для hashFun() ====================

    @Test
    public void testHashFun_sameStringSameHash() {
        HashTable table = new HashTable(17, 3);
        int hash1 = table.hashFun("test");
        int hash2 = table.hashFun("test");
        assertEquals(hash1, hash2);
    }

    @Test
    public void testHashFun_differentStringsDifferentHash() {
        HashTable table = new HashTable(17, 3);
        int hash1 = table.hashFun("abc");
        int hash2 = table.hashFun("def");
        assertNotEquals(hash1, hash2);
    }

    @Test
    public void testHashFun_withinBounds() {
        HashTable table = new HashTable(17, 3);
        int hash = table.hashFun("some string");
        assertTrue(hash >= 0 && hash < 17);
    }

    @Test
    public void testHashFun_emptyString() {
        HashTable table = new HashTable(17, 3);
        int hash = table.hashFun("");
        assertEquals(0, hash);
    }

    // ==================== Тесты для seekSlot() ====================

    @Test
    public void testSeekSlot_emptyTable() {
        HashTable table = new HashTable(17, 3);
        int slot = table.seekSlot("test");
        assertEquals(table.hashFun("test"), slot);
    }

    @Test
    public void testSeekSlot_withCollisions() {
        HashTable table = new HashTable(17, 3);
        table.put("a");
        table.put("b");
        table.put("c");
        
        int slot = table.seekSlot("new value");
        assertTrue(slot >= 0 && slot < 17);
    }

    @Test
    public void testSeekSlot_fullTable() {
        HashTable table = new HashTable(5, 1);
        for (int i = 0; i < 5; i++) {
            table.put("value" + i);
        }
        
        int slot = table.seekSlot("new");
        assertEquals(-1, slot);
    }

    // ==================== Тесты для put() ====================

    @Test
    public void testPut_singleElement() {
        HashTable table = new HashTable(17, 3);
        int index = table.put("test");
        assertTrue(index >= 0 && index < 17);
        assertEquals("test", table.slots[index]);
    }

    @Test
    public void testPut_multipleElements() {
        HashTable table = new HashTable(17, 3);
        int index1 = table.put("first");
        int index2 = table.put("second");
        int index3 = table.put("third");
        
        assertTrue(index1 >= 0);
        assertTrue(index2 >= 0);
        assertTrue(index3 >= 0);
        
        assertEquals("first", table.slots[index1]);
        assertEquals("second", table.slots[index2]);
        assertEquals("third", table.slots[index3]);
    }

    @Test
    public void testPut_duplicateValue() {
        HashTable table = new HashTable(17, 3);
        int index1 = table.put("test");
        int index2 = table.put("test");
        
        assertEquals(index1, index2);
        assertEquals(1, table.count());
    }

    @Test
    public void testPut_fullTable() {
        HashTable table = new HashTable(5, 1);
        for (int i = 0; i < 5; i++) {
            int index = table.put("value" + i);
            assertTrue(index >= 0);
        }
        
        int newIndex = table.put("new");
        assertEquals(-1, newIndex);
    }

    @Test
    public void testPut_withCollisions() {
        HashTable table = new HashTable(17, 3);
        for (int i = 0; i < 10; i++) {
            int index = table.put("key" + i);
            assertTrue(index >= 0, "Failed to put key" + i);
        }
        
        assertEquals(10, table.count());
    }

    // ==================== Тесты для find() ====================

    @Test
    public void testFind_existingValue() {
        HashTable table = new HashTable(17, 3);
        int index = table.put("test");
        int foundIndex = table.find("test");
        assertEquals(index, foundIndex);
    }

    @Test
    public void testFind_nonExistingValue() {
        HashTable table = new HashTable(17, 3);
        table.put("test");
        int foundIndex = table.find("notexist");
        assertEquals(-1, foundIndex);
    }

    @Test
    public void testFind_emptyTable() {
        HashTable table = new HashTable(17, 3);
        int foundIndex = table.find("test");
        assertEquals(-1, foundIndex);
    }

    @Test
    public void testFind_afterCollisions() {
        HashTable table = new HashTable(17, 3);
        table.put("a");
        table.put("b");
        table.put("c");
        table.put("d");
        
        assertNotEquals(-1, table.find("a"));
        assertNotEquals(-1, table.find("b"));
        assertNotEquals(-1, table.find("c"));
        assertNotEquals(-1, table.find("d"));
    }

    @Test
    public void testFind_withStep() {
        HashTable table = new HashTable(19, 5);
        table.put("first");
        table.put("second");
        table.put("third");
        
        assertNotEquals(-1, table.find("first"));
        assertNotEquals(-1, table.find("second"));
        assertNotEquals(-1, table.find("third"));
        assertEquals(-1, table.find("notexist"));
    }

    // ==================== Тесты для size и count() ====================

    @Test
    public void testSizeAndCount() {
        HashTable table = new HashTable(17, 3);
        assertEquals(17, table.size);
        assertEquals(0, table.count());
        
        table.put("a");
        assertEquals(1, table.count());
        
        table.put("b");
        assertEquals(2, table.count());
        
        table.put("a");
        assertEquals(2, table.count());
    }

    @Test
    public void testSmallTable() {
        HashTable table = new HashTable(5, 2);
        assertEquals(5, table.size);
        
        table.put("a");
        table.put("b");
        table.put("c");
        
        assertEquals(3, table.count());
        assertNotEquals(-1, table.find("a"));
        assertNotEquals(-1, table.find("b"));
        assertNotEquals(-1, table.find("c"));
    }

    @Test
    public void testLargerTable() {
        HashTable table = new HashTable(31, 7);
        assertEquals(31, table.size);
        
        for (int i = 0; i < 20; i++) {
            int index = table.put("key" + i);
            assertTrue(index >= 0);
        }
        
        assertEquals(20, table.count());
        
        for (int i = 0; i < 20; i++) {
            assertNotEquals(-1, table.find("key" + i));
        }
    }

    @Test
    public void testExampleFromTask() {
        HashTable table = new HashTable(17, 3);
        
        int index1 = table.put("f1");
        int index2 = table.put("t1");
        int index3 = table.put("f2");
        int index4 = table.put("t2");
        
        assertTrue(index1 >= 0);
        assertTrue(index2 >= 0);
        assertTrue(index3 >= 0);
        assertTrue(index4 >= 0);
        
        assertEquals(index1, table.find("f1"));
        assertEquals(index2, table.find("t1"));
        assertEquals(index3, table.find("f2"));
        assertEquals(index4, table.find("t2"));
        
        assertEquals(4, table.count());
    }
}

