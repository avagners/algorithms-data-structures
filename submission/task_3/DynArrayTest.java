import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DynArrayTest {

    @Test
    public void testMakeArrayInitialCapacity() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        assertEquals(16, array.capacity);
        assertEquals(0, array.count);
        assertNotNull(array.array);
    }

    @Test
    public void testMakeArrayExpandCapacity() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        array.makeArray(32);

        assertEquals(32, array.capacity);
        assertEquals(0, array.count);
        assertNotNull(array.array);
    }

    @Test
    public void testMakeArrayPreservesElements() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        array.array[0] = 1;
        array.array[1] = 2;
        array.array[2] = 3;
        array.count = 3;

        array.makeArray(32);

        assertEquals(32, array.capacity);
        assertEquals(3, array.count);
        assertEquals(1, array.array[0]);
        assertEquals(2, array.array[1]);
        assertEquals(3, array.array[2]);
    }

    @Test
    public void testMakeArrayShrinkCapacity() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        array.makeArray(32);
        array.makeArray(8);

        assertEquals(8, array.capacity);
    }

    @Test
    public void testMakeArrayWithStrings() {
        DynArray<String> array = new DynArray<>(String.class);

        array.makeArray(20);

        assertEquals(20, array.capacity);
        assertNotNull(array.array);
    }

    @Test
    public void testMakeArrayPreservesElementsAfterShrink() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        array.makeArray(32);
        array.array[0] = 10;
        array.array[1] = 20;
        array.array[2] = 30;
        array.count = 3;

        array.makeArray(16);

        assertEquals(16, array.capacity);
        assertEquals(3, array.count);
        assertEquals(10, array.array[0]);
        assertEquals(20, array.array[1]);
        assertEquals(30, array.array[2]);
    }

    @Test
    public void testMakeArrayTruncatesElementsIfSmaller() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        array.makeArray(32);
        for (int i = 0; i < 20; i++) {
            array.array[i] = i;
        }
        array.count = 20;

        array.makeArray(10);

        assertEquals(10, array.capacity);
        assertEquals(20, array.count);
        for (int i = 0; i < 10; i++) {
            assertEquals(i, array.array[i]);
        }
    }

    @Test
    public void testMakeArrayMinimumCapacity() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        array.makeArray(16);

        assertEquals(16, array.capacity);
    }

    @Test
    public void testMakeArrayWithCustomObjects() {
        DynArray<MyObject> array = new DynArray<>(MyObject.class);

        array.makeArray(25);

        assertEquals(25, array.capacity);
        assertNotNull(array.array);
    }

    @Test
    public void testMakeArrayDoubleCapacity() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        int initialCapacity = array.capacity;
        array.makeArray(initialCapacity * 2);

        assertEquals(initialCapacity * 2, array.capacity);
    }

    @Test
    public void testMakeArrayNullElementsAfterResize() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        array.makeArray(32);
        array.array[0] = 1;
        array.array[1] = 2;
        array.count = 2;

        array.makeArray(64);

        assertEquals(64, array.capacity);
        assertEquals(2, array.count);
        assertEquals(1, array.array[0]);
        assertEquals(2, array.array[1]);
        assertNull(array.array[2]);
        assertNull(array.array[63]);
    }

    @Test
    public void testGetItemValidIndex() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        array.append(10);
        array.append(20);
        array.append(30);

        assertEquals(10, array.getItem(0));
        assertEquals(20, array.getItem(1));
        assertEquals(30, array.getItem(2));
    }

    @Test
    public void testGetItemNegativeIndex() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        array.append(10);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            array.getItem(-1);
        });
    }

    @Test
    public void testGetItemOutOfBounds() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        array.append(10);
        array.append(20);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            array.getItem(2);
        });
    }

    @Test
    public void testGetItemEmptyArray() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            array.getItem(0);
        });
    }

    @Test
    public void testInsertAtBeginning() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        array.append(1);
        array.append(2);
        array.append(3);

        array.insert(0, 0);

        assertEquals(4, array.count);
        assertEquals(0, array.getItem(0));
        assertEquals(1, array.getItem(1));
        assertEquals(2, array.getItem(2));
        assertEquals(3, array.getItem(3));
    }

    @Test
    public void testInsertWithoutExpansion() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        for (int i = 0; i < 10; i++) {
            array.append(i);
        }

        int capacityBefore = array.capacity;
        assertEquals(16, capacityBefore);

        array.insert(99, 5);

        assertEquals(11, array.count);
        assertEquals(capacityBefore, array.capacity);
        assertEquals(99, array.getItem(5));
    }

    @Test
    public void testInsertAtMiddle() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        array.append(1);
        array.append(2);
        array.append(3);

        array.insert(99, 1);

        assertEquals(4, array.count);
        assertEquals(1, array.getItem(0));
        assertEquals(99, array.getItem(1));
        assertEquals(2, array.getItem(2));
        assertEquals(3, array.getItem(3));
    }

    @Test
    public void testInsertAtEnd() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        array.append(1);
        array.append(2);
        array.append(3);

        array.insert(4, 3);

        assertEquals(4, array.count);
        assertEquals(1, array.getItem(0));
        assertEquals(2, array.getItem(1));
        assertEquals(3, array.getItem(2));
        assertEquals(4, array.getItem(3));
    }

    @Test
    public void testInsertIntoEmptyArray() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        array.insert(42, 0);

        assertEquals(1, array.count);
        assertEquals(42, array.getItem(0));
    }

    @Test
    public void testInsertInvalidIndexNegative() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        array.append(1);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            array.insert(99, -1);
        });
    }

    @Test
    public void testInsertInvalidIndexTooLarge() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        array.append(1);
        array.append(2);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            array.insert(99, 3);
        });
    }

    @Test
    public void testInsertTriggersExpansion() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        for (int i = 0; i < 16; i++) {
            array.append(i);
        }

        assertEquals(16, array.capacity);

        array.insert(99, 0);

        assertEquals(17, array.count);
        assertEquals(32, array.capacity);
        assertEquals(99, array.getItem(0));
    }

    @Test
    public void testInsertMultipleTimes() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        array.insert(1, 0);
        array.insert(2, 1);
        array.insert(3, 1);
        array.insert(4, 0);

        assertEquals(4, array.count);
        assertEquals(4, array.getItem(0));
        assertEquals(1, array.getItem(1));
        assertEquals(3, array.getItem(2));
        assertEquals(2, array.getItem(3));
    }

    @Test
    public void testRemoveFromBeginning() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        array.append(1);
        array.append(2);
        array.append(3);

        array.remove(0);

        assertEquals(2, array.count);
        assertEquals(2, array.getItem(0));
        assertEquals(3, array.getItem(1));
    }

    @Test
    public void testRemoveWithoutShrink() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        for (int i = 0; i < 20; i++) {
            array.append(i);
        }

        int capacityBefore = array.capacity;
        assertTrue(capacityBefore >= 32);

        array.remove(10);

        assertEquals(19, array.count);
        assertEquals(capacityBefore, array.capacity);
    }

    @Test
    public void testRemoveFromMiddle() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        array.append(1);
        array.append(2);
        array.append(3);
        array.append(4);

        array.remove(2);

        assertEquals(3, array.count);
        assertEquals(1, array.getItem(0));
        assertEquals(2, array.getItem(1));
        assertEquals(4, array.getItem(2));
    }

    @Test
    public void testRemoveFromEnd() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        array.append(1);
        array.append(2);
        array.append(3);

        array.remove(2);

        assertEquals(2, array.count);
        assertEquals(1, array.getItem(0));
        assertEquals(2, array.getItem(1));
    }

    @Test
    public void testRemoveInvalidIndexNegative() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        array.append(1);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            array.remove(-1);
        });
    }

    @Test
    public void testRemoveInvalidIndexOutOfBounds() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        array.append(1);
        array.append(2);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            array.remove(2);
        });
    }

    @Test
    public void testRemoveSingleElement() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        array.append(42);
        array.remove(0);

        assertEquals(0, array.count);
        assertEquals(16, array.capacity);
    }

    @Test
    public void testRemoveTriggersShrink() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        for (int i = 0; i < 17; i++) {
            array.append(i);
        }

        assertEquals(32, array.capacity);

        for (int i = 0; i < 2; i++) {
            array.remove(0);
        }

        assertEquals(15, array.count);
        assertEquals(21, array.capacity);
    }

    @Test
    public void testRemoveDoesNotShrinkBelowMinimum() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        assertEquals(16, array.capacity);

        array.append(1);
        array.remove(0);

        assertEquals(16, array.capacity);
    }

    @Test
    public void testRemoveAllElements() {
        DynArray<Integer> array = new DynArray<>(Integer.class);

        for (int i = 0; i < 10; i++) {
            array.append(i);
        }

        for (int i = 0; i < 10; i++) {
            array.remove(0);
        }

        assertEquals(0, array.count);
        assertEquals(16, array.capacity);
    }

    @Test
    public void testRemoveWithNullCleanup() {
        DynArray<String> array = new DynArray<>(String.class);

        array.append("A");
        array.append("B");
        array.append("C");

        array.remove(1);

        assertEquals(2, array.count);
        assertEquals("A", array.getItem(0));
        assertEquals("C", array.getItem(1));
    }

    static class MyObject {
        int value;

        MyObject(int value) {
            this.value = value;
        }
    }
}

