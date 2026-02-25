import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NDynArrayTest {

    @Test
    public void testConstructor1D() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 5);

        assertEquals(1, array.getRank());
        assertEquals(5, array.getDimensionSize(0));
    }

    @Test
    public void testConstructor2D() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 3, 4);

        assertEquals(2, array.getRank());
        assertEquals(3, array.getDimensionSize(0));
        assertEquals(4, array.getDimensionSize(1));
    }

    @Test
    public void testConstructor3D() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 2, 3, 4);

        assertEquals(3, array.getRank());
        assertEquals(2, array.getDimensionSize(0));
        assertEquals(3, array.getDimensionSize(1));
        assertEquals(4, array.getDimensionSize(2));
    }

    @Test
    public void testConstructorEmptyDimensions() {
        assertThrows(IllegalArgumentException.class, () -> {
            new NDynArray<>(Integer.class);
        });
    }

    @Test
    public void testConstructorZeroDimension() {
        assertThrows(IllegalArgumentException.class, () -> {
            new NDynArray<>(Integer.class, 0);
        });
    }

    @Test
    public void testConstructorNegativeDimension() {
        assertThrows(IllegalArgumentException.class, () -> {
            new NDynArray<>(Integer.class, -5);
        });
    }

    @Test
    public void testSetAndGet1D() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 5);

        array.set(42, 0);
        array.set(100, 2);
        array.set(200, 4);

        assertEquals(42, array.get(0));
        assertEquals(100, array.get(2));
        assertEquals(200, array.get(4));
    }

    @Test
    public void testGetUninitialized1D() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 5);

        assertNull(array.get(0));
        assertNull(array.get(2));
    }

    @Test
    public void testGetOutOfBounds1D() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 5);

        assertNull(array.get(10));
    }

    @Test
    public void testSetAndGet2D() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 3, 3);

        array.set(1, 0, 0);
        array.set(2, 0, 1);
        array.set(3, 1, 0);
        array.set(4, 2, 2);

        assertEquals(1, array.get(0, 0));
        assertEquals(2, array.get(0, 1));
        assertEquals(3, array.get(1, 0));
        assertEquals(4, array.get(2, 2));
    }

    @Test
    public void testGetUninitialized2D() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 3, 3);

        assertNull(array.get(0, 0));
        assertNull(array.get(1, 1));
        assertNull(array.get(2, 2));
    }

    @Test
    public void testGetOutOfBounds2D() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 3, 3);

        assertNull(array.get(10, 0));
        assertNull(array.get(0, 10));
        assertNull(array.get(10, 10));
    }

    @Test
    public void testSetAndGet3D() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 2, 2, 2);

        array.set(1, 0, 0, 0);
        array.set(2, 0, 0, 1);
        array.set(3, 0, 1, 0);
        array.set(4, 0, 1, 1);
        array.set(5, 1, 0, 0);
        array.set(6, 1, 0, 1);
        array.set(7, 1, 1, 0);
        array.set(8, 1, 1, 1);

        assertEquals(1, array.get(0, 0, 0));
        assertEquals(2, array.get(0, 0, 1));
        assertEquals(3, array.get(0, 1, 0));
        assertEquals(4, array.get(0, 1, 1));
        assertEquals(5, array.get(1, 0, 0));
        assertEquals(6, array.get(1, 0, 1));
        assertEquals(7, array.get(1, 1, 0));
        assertEquals(8, array.get(1, 1, 1));
    }

    @Test
    public void testGetUninitialized3D() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 2, 2, 2);

        assertNull(array.get(0, 0, 0));
        assertNull(array.get(1, 1, 1));
    }

    @Test
    public void testSetExpands1D() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 3);

        array.set(42, 5);

        assertEquals(42, array.get(5));
        assertEquals(6, array.getDimensionSize(0));
    }

    @Test
    public void testSetExpands2D() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 3, 3);

        array.set(42, 5, 5);

        assertEquals(42, array.get(5, 5));
        assertEquals(6, array.getDimensionSize(0));
        assertEquals(6, array.getDimensionSize(1));
    }

    @Test
    public void testSetExpands3D() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 2, 2, 2);

        array.set(42, 3, 4, 5);

        assertEquals(42, array.get(3, 4, 5));
        assertEquals(4, array.getDimensionSize(0));
        assertEquals(5, array.getDimensionSize(1));
        assertEquals(6, array.getDimensionSize(2));
    }

    @Test
    public void testSetExpandsPartialIndices() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 3, 3);

        array.set(1, 0, 0);
        array.set(2, 5, 0);

        assertEquals(1, array.get(0, 0));
        assertEquals(2, array.get(5, 0));
        assertEquals(6, array.getDimensionSize(0));
    }

    @Test
    public void testGetNegativeIndex() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 5);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            array.get(-1);
        });
    }

    @Test
    public void testSetNegativeIndex() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 5);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            array.set(42, -1);
        });
    }

    @Test
    public void testGetNegativeIndex2D() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 3, 3);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            array.get(-1, 0);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            array.get(0, -1);
        });
    }

    @Test
    public void testGetWrongNumberOfIndices() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 3, 3);

        assertThrows(IllegalArgumentException.class, () -> {
            array.get(0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            array.get(0, 1, 2);
        });
    }

    @Test
    public void testSetWrongNumberOfIndices() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 3, 3);

        assertThrows(IllegalArgumentException.class, () -> {
            array.set(42, 0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            array.set(42, 0, 1, 2);
        });
    }

    @Test
    public void testContains1D() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 5);

        assertFalse(array.contains(0));

        array.set(42, 0);
        assertTrue(array.contains(0));

        assertFalse(array.contains(1));
    }

    @Test
    public void testContains2D() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 3, 3);

        assertFalse(array.contains(0, 0));

        array.set(42, 0, 0);
        assertTrue(array.contains(0, 0));

        assertFalse(array.contains(0, 1));
    }

    @Test
    public void testContains3D() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 2, 2, 2);

        assertFalse(array.contains(0, 0, 0));

        array.set(42, 0, 0, 0);
        assertTrue(array.contains(0, 0, 0));
    }

    @Test
    public void testRemove1D() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 5);

        array.set(42, 0);
        assertTrue(array.contains(0));

        array.remove(0);
        assertFalse(array.contains(0));
        assertNull(array.get(0));
    }

    @Test
    public void testRemove2D() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 3, 3);

        array.set(42, 0, 0);
        assertTrue(array.contains(0, 0));

        array.remove(0, 0);
        assertFalse(array.contains(0, 0));
        assertNull(array.get(0, 0));
    }

    @Test
    public void testRemove3D() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 2, 2, 2);

        array.set(42, 0, 0, 0);
        assertTrue(array.contains(0, 0, 0));

        array.remove(0, 0, 0);
        assertFalse(array.contains(0, 0, 0));
        assertNull(array.get(0, 0, 0));
    }

    @Test
    public void testNavigator1D() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 5);

        array.getAt(0).setValue(42);
        array.getAt(2).setValue(100);

        assertEquals(42, array.getAt(0).getValue());
        assertEquals(100, array.getAt(2).getValue());
    }

    @Test
    public void testNavigator2D() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 3, 3);

        array.getAt(0).get(0).setValue(1);
        array.getAt(0).get(1).setValue(2);
        array.getAt(1).get(0).setValue(3);
        array.getAt(2).get(2).setValue(4);

        assertEquals(1, array.getAt(0).get(0).getValue());
        assertEquals(2, array.getAt(0).get(1).getValue());
        assertEquals(3, array.getAt(1).get(0).getValue());
        assertEquals(4, array.getAt(2).get(2).getValue());
    }

    @Test
    public void testNavigator3D() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 2, 2, 2);

        array.getAt(0).get(0).get(0).setValue(1);
        array.getAt(0).get(0).get(1).setValue(2);
        array.getAt(1).get(1).get(1).setValue(8);

        assertEquals(1, array.getAt(0).get(0).get(0).getValue());
        assertEquals(2, array.getAt(0).get(0).get(1).getValue());
        assertEquals(8, array.getAt(1).get(1).get(1).getValue());
    }

    @Test
    public void testNavigatorTooManyIndices() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 3, 3);

        assertThrows(IndexOutOfBoundsException.class, () -> {
            array.getAt(0).get(0).get(0);
        });
    }

    @Test
    public void testNavigatorGetValueWithoutAllIndices() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 3, 3);

        assertThrows(IllegalStateException.class, () -> {
            array.getAt(0).getValue();
        });
    }

    @Test
    public void testNavigatorSetValueWithoutAllIndices() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 3, 3);

        assertThrows(IllegalStateException.class, () -> {
            array.getAt(0).setValue(42);
        });
    }

    @Test
    public void testNavigatorToString() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 5);

        array.set(42, 0);
        assertEquals("42", array.getAt(0).toString());

        assertNull(array.get(1));
        assertEquals("null", array.getAt(1).toString());
    }

    @Test
    public void testToString1D() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 5);

        assertEquals("[null, null, null, null, null]", array.toString());

        array.set(1, 0);
        array.set(2, 2);
        array.set(3, 4);

        assertEquals("[1, null, 2, null, 3]", array.toString());
    }

    @Test
    public void testToString2D() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 2, 3);

        array.set(1, 0, 0);
        array.set(2, 0, 1);
        array.set(3, 1, 0);

        String expected = "[[1, 2, null], [3, null, null]]";
        assertEquals(expected, array.toString());
    }

    @Test
    public void testToString3D() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 2, 2, 2);

        array.set(1, 0, 0, 0);
        array.set(8, 1, 1, 1);

        String expected = "[[[1, null], [null, null]], [[null, null], [null, 8]]]";
        assertEquals(expected, array.toString());
    }

    @Test
    public void testToStringEmpty1D() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 3);

        assertEquals("[null, null, null]", array.toString());
    }

    @Test
    public void testStringArray() {
        NDynArray<String> array = new NDynArray<>(String.class, 3, 3);

        array.set("hello", 0, 0);
        array.set("world", 1, 1);

        assertEquals("hello", array.get(0, 0));
        assertEquals("world", array.get(1, 1));
    }

    @Test
    public void testDoubleArray() {
        NDynArray<Double> array = new NDynArray<>(Double.class, 5);

        array.set(3.14, 0);
        array.set(2.71, 2);

        assertEquals(3.14, array.get(0));
        assertEquals(2.71, array.get(2));
    }

    @Test
    public void testCustomObjectArray() {
        NDynArray<Person> array = new NDynArray<>(Person.class, 3);

        Person p1 = new Person("Alice", 25);
        Person p2 = new Person("Bob", 30);

        array.set(p1, 0);
        array.set(p2, 2);

        assertEquals(p1, array.get(0));
        assertEquals(p2, array.get(2));
        assertEquals("Alice", array.get(0).name);
        assertEquals(30, array.get(2).age);
    }

    @Test
    public void testLarge1DArray() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 100);

        for (int i = 0; i < 100; i++) {
            array.set(i * 10, i);
        }

        for (int i = 0; i < 100; i++) {
            assertEquals(i * 10, array.get(i));
        }
    }

    @Test
    public void testLarge2DArray() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 10, 10);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                array.set(i * 10 + j, i, j);
            }
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                assertEquals(i * 10 + j, array.get(i, j));
            }
        }
    }

    @Test
    public void testDynamicExpansion2D() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 2, 2);

        array.set(1, 0, 0);
        array.set(2, 0, 1);
        array.set(3, 1, 0);
        array.set(4, 1, 1);

        assertEquals(2, array.getDimensionSize(0));
        assertEquals(2, array.getDimensionSize(1));

        array.set(5, 3, 3);

        assertEquals(4, array.getDimensionSize(0));
        assertEquals(4, array.getDimensionSize(1));
        assertEquals(5, array.get(3, 3));
        assertEquals(1, array.get(0, 0));
    }

    @Test
    public void testDynamicExpansionSparse() {
        NDynArray<Integer> array = new NDynArray<>(Integer.class, 2, 2);

        array.set(42, 10, 10);

        assertEquals(11, array.getDimensionSize(0));
        assertEquals(11, array.getDimensionSize(1));
        assertEquals(42, array.get(10, 10));
    }

    private static class Person {
        String name;
        int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return age == person.age && name.equals(person.name);
        }

        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + "}";
        }
    }
}

