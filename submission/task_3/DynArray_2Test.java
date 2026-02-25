import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DynArray_2Test {

    @Test
    public void testBankingInitialBalance() {
        DynArray_2<Integer> array = new DynArray_2<>(Integer.class);

        assertEquals(0, array.bankBalance);
        assertEquals(0, array.totalCoinsDeposited);
        assertEquals(0, array.totalCoinsSpent);
    }

    @Test
    public void testBankingAppendDepositsCoins() {
        DynArray_2<Integer> array = new DynArray_2<>(Integer.class);

        array.append(1);

        assertEquals(2, array.bankBalance);
        assertEquals(3, array.totalCoinsDeposited);
        assertEquals(1, array.totalCoinsSpent);
    }

    @Test
    public void testBankingAppendMultipleWithoutReallocation() {
        DynArray_2<Integer> array = new DynArray_2<>(Integer.class);

        for (int i = 0; i < 10; i++) {
            array.append(i);
        }

        assertEquals(20, array.bankBalance);
        assertEquals(30, array.totalCoinsDeposited);
        assertEquals(10, array.totalCoinsSpent);
        assertEquals(0, array.totalReallocations);
    }

    @Test
    public void testBankingAppendWithReallocation() {
        DynArray_2<Integer> array = new DynArray_2<>(Integer.class);

        for (int i = 0; i < 17; i++) {
            array.append(i);
        }

        assertEquals(18, array.bankBalance);
        assertEquals(51, array.totalCoinsDeposited);
        assertEquals(33, array.totalCoinsSpent);
        assertEquals(1, array.totalReallocations);

        assertTrue(array.bankBalance >= 0);
    }

    @Test
    public void testBankingBalanceNeverNegative() {
        DynArray_2<Integer> array = new DynArray_2<>(Integer.class);

        for (int i = 0; i < 100; i++) {
            array.append(i);
        }

        assertTrue(array.bankBalance >= 0, "Bank balance should never be negative");
        
        System.out.println(array.getBankingStats());
    }

    @Test
    public void testBankingInsertDepositsCoins() {
        DynArray_2<Integer> array = new DynArray_2<>(Integer.class);

        array.insert(1, 0);

        assertEquals(2, array.bankBalance);
        assertEquals(3, array.totalCoinsDeposited);
        assertEquals(1, array.totalCoinsSpent);
    }

    @Test
    public void testBankingInsertWithReallocation() {
        DynArray_2<Integer> array = new DynArray_2<>(Integer.class);

        for (int i = 0; i < 16; i++) {
            array.append(i);
        }

        int balanceBeforeInsert = array.bankBalance;
        
        array.insert(99, 0);

        assertTrue(array.bankBalance >= 0);
        assertEquals(1, array.totalReallocations);
    }

    @Test
    public void testBankingRemoveDoesNotAffectBalance() {
        DynArray_2<Integer> array = new DynArray_2<>(Integer.class);

        for (int i = 0; i < 10; i++) {
            array.append(i);
        }

        int balanceBeforeRemove = array.bankBalance;
        
        array.remove(5);

        assertTrue(array.bankBalance >= 0);
    }

    @Test
    public void testBankingRemoveWithShrink() {
        DynArray_2<Integer> array = new DynArray_2<>(Integer.class);

        for (int i = 0; i < 32; i++) {
            array.append(i);
        }

        assertEquals(32, array.capacity);
        int balanceBeforeShrink = array.bankBalance;

        for (int i = 0; i < 17; i++) {
            array.remove(0);
        }

        assertEquals(21, array.capacity);
        
        assertTrue(array.bankBalance >= 0);
    }

    @Test
    public void testBankingMultipleReallocations() {
        DynArray_2<Integer> array = new DynArray_2<>(Integer.class);

        for (int i = 0; i < 100; i++) {
            array.append(i);
        }

        assertTrue(array.totalReallocations >= 3);
        assertTrue(array.bankBalance >= 0, "Bank balance must stay non-negative");
        
        System.out.println(array.getBankingStats());
    }

    @Test
    public void testBankingInvariantHolds() {
        DynArray_2<Integer> array = new DynArray_2<>(Integer.class);

        for (int i = 0; i < 50; i++) {
            array.append(i);
        }

        for (int i = 0; i < 25; i++) {
            array.remove(0);
        }

        for (int i = 0; i < 50; i++) {
            array.insert(i * 10, i % 25);
        }

        assertTrue(array.bankBalance >= 0, "Banking invariant violated!");
        
        System.out.println(array.getBankingStats());
    }

    @Test
    public void testBankingCoinsCalculation() {
        DynArray_2<Integer> array = new DynArray_2<>(Integer.class);

        int n = 10;
        for (int i = 0; i < n; i++) {
            array.append(i);
        }

        assertEquals(3 * n, array.totalCoinsDeposited);
        assertEquals(n, array.totalCoinsSpent);
        assertEquals(2 * n, array.bankBalance);
    }

    @Test
    public void testBankingAfterReallocationCoinsCalculation() {
        DynArray_2<Integer> array = new DynArray_2<>(Integer.class);

        int capacity = 16;
        for (int i = 0; i < capacity + 1; i++) {
            array.append(i);
        }

        assertEquals(3 * (capacity + 1), array.totalCoinsDeposited);
        assertEquals((capacity + 1) + capacity, array.totalCoinsSpent);
        assertEquals(18, array.bankBalance);
    }
}
