import java.util.ArrayList;

// 10-1,2 PowerSet & methods
public class PowerSet
{
    public ArrayList<String> store;

    public PowerSet()
    {
        store = new ArrayList<>();
    }

    // 10-1; size; Time: O(1), Space: O(1)
    public int size()
    {
        return store.size();
    }

    // 10-2; put; Time: O(n), Space: O(1)
    public void put(String value)
    {
        if (!get(value)) {
            store.add(value);
        }
    }

    // 10-2; get; Time: O(n), Space: O(1)
    public boolean get(String value)
    {
        return store.contains(value);
    }

    // 10-2; remove; Time: O(n), Space: O(1)
    public boolean remove(String value)
    {
        return store.remove(value);
    }

    // 10-2; getAll; Time: O(n), Space: O(n)
    protected ArrayList<String> getAll()
    {
        return new ArrayList<>(store);
    }

    // 10-2; intersection; Time: O(n*m), Space: O(n)
    public PowerSet intersection(PowerSet set2)
    {
        PowerSet result = new PowerSet();
        for (String el : store) {
            if (set2.get(el)) {
                result.put(el);
            }
        }
        return result;
    }

    // 10-2; union; Time: O(n+m), Space: O(n+m)
    public PowerSet union(PowerSet set2)
    {
        PowerSet result = new PowerSet();
        for (String el : store) {
            result.put(el);
        }
        for (String el : set2.store) {
            result.put(el);
        }
        return result;
    }

    // 10-2; difference; Time: O(n*m), Space: O(n)
    public PowerSet difference(PowerSet set2)
    {
        PowerSet result = new PowerSet();
        for (String el : store) {
            if (!set2.get(el)) {
                result.put(el);
            }
        }
        return result;
    }

    // 10-2; isSubset; Time: O(n*m), Space: O(1)
    public boolean isSubset(PowerSet set2)
    {
        for (String el : set2.store) {
            if (!get(el)) {
                return false;
            }
        }
        return true;
    }

    // 10-2; equals; Time: O(n*m), Space: O(1)
    public boolean equals(PowerSet set2)
    {
        if (set2.size() != this.size()) {
            return false;
        }
        for (String el : store) {
            if (!set2.get(el)) {
                return false;
            }
        }
        return true;
    }

    // 10-4; cartesianProduct; Time: O(n*m), Space: O(n*m)
    public PowerSet cartesianProduct(PowerSet set2)
    {
        PowerSet result = new PowerSet();
        for (String a : store) {
            for (String b : set2.store) {
                result.put("(" + a + "; " + b + ")");
            }
        }
        return result;
    }
}
