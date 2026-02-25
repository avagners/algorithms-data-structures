import java.util.Arrays;

public class NDynArray<T> {

    private final DynArray<Object> storage;
    private final int[] currentDimensions;
    private final int rank;
    private final Class<T> elementType;

    public static class ArrayNavigator<T> {
        private final NDynArray<T> array;
        private final int[] indices;
        private int currentPosition;
        
        public ArrayNavigator(NDynArray<T> array, int firstIndex) {
            this.array = array;
            this.indices = new int[array.getRank()];
            this.indices[0] = firstIndex;
            this.currentPosition = 0;
        }
        
        private ArrayNavigator(NDynArray<T> array, int[] indices, int position) {
            this.array = array;
            this.indices = indices;
            this.currentPosition = position;
        }
        
        public ArrayNavigator<T> get(int index) {
            if (currentPosition + 1 >= array.getRank()) {
                throw new IndexOutOfBoundsException(
                    "Слишком много индексов. Максимум: " + array.getRank());
            }
            
            indices[currentPosition + 1] = index;
            return new ArrayNavigator<>(array, indices, currentPosition + 1);
        }
        
        public T getValue() {
            if (currentPosition != array.getRank() - 1) {
                throw new IllegalStateException(
                    "Нужно указать все индексы. Указано: " + (currentPosition + 1) + 
                    ", нужно: " + array.getRank());
            }
            return array.get(indices);
        }
        
        public void setValue(T value) {
            if (currentPosition != array.getRank() - 1) {
                throw new IllegalStateException(
                    "Нужно указать все индексы. Указано: " + (currentPosition + 1) + 
                    ", нужно: " + array.getRank());
            }
            array.set(value, indices);
        }
        
        @Override
        public String toString() {
            T value = getValue();
            return value == null ? "null" : value.toString();
        }
    }
    
    public NDynArray(Class<T> elementType, int... initialSizes) {
        this(elementType, 0, initialSizes, initialSizes.length);
    }

    private NDynArray(Class<T> elementType, int level, int[] dimensions, int rank) {
        if (dimensions == null || dimensions.length == 0) {
            throw new IllegalArgumentException("Должно быть хотя бы одно измерение");
        }
        for (int size : dimensions) {
            if (size <= 0) {
                throw new IllegalArgumentException("Размеры должны быть положительными: " + size);
            }
        }

        this.elementType = elementType;
        this.rank = rank;
        this.currentDimensions = Arrays.copyOf(dimensions, rank);
        this.storage = buildRecursive(level);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private DynArray<Object> buildRecursive(int currentLevel) {
        if (currentLevel == rank - 1) {

            DynArray<T> lastLevel = new DynArray<>(elementType);
            for (int i = 0; i < currentDimensions[currentLevel]; i++) {
                lastLevel.append(null);
            }
            return (DynArray<Object>) (DynArray) lastLevel;
        } else {

            DynArray<NDynArray<T>> intermediate = new DynArray(NDynArray.class);

            for (int i = 0; i < currentDimensions[currentLevel]; i++) {
                NDynArray<T> nextLevel = new NDynArray<>(elementType, currentLevel + 1, currentDimensions, rank);
                intermediate.append(nextLevel);
            }
            return (DynArray<Object>) (DynArray) intermediate;
        }
    }
    
    public T get(int... indices) {
        if (indices.length != rank) {
            throw new IllegalArgumentException(
                "Ожидалось " + rank + " индексов, получено: " + indices.length);
        }
        
        return getRecursive(storage, indices, 0);
    }
    
    @SuppressWarnings("unchecked")
    private T getRecursive(DynArray<Object> currentStorage, int[] indices, int position) {
        int currentIndex = indices[position];
        
        if (currentIndex < 0) {
            throw new IndexOutOfBoundsException("Индекс не может быть отрицательным: " + currentIndex);
        }
        
        if (currentIndex >= currentStorage.count) {
            return null;
        }
        
        if (position == rank - 1) {

            return (T) currentStorage.array[currentIndex];
        } else {

            Object next = currentStorage.array[currentIndex];
            if (next == null) {
                return null;
            }

            return getRecursive(((NDynArray<T>) next).storage, indices, position + 1);
        }
    }

    public void set(T value, int... indices) {
        if (indices.length != rank) {
            throw new IllegalArgumentException(
                "Ожидалось " + rank + " индексов, получено: " + indices.length);
        }
        
        for (int idx : indices) {
            if (idx < 0) {
                throw new IndexOutOfBoundsException("Индекс не может быть отрицательным: " + idx);
            }
        }
        
        for (int i = 0; i < indices.length; i++) {
            if (indices[i] >= currentDimensions[i]) {
                currentDimensions[i] = indices[i] + 1;
            }
        }
        
        setRecursive(storage, value, indices, 0);
    }

    @SuppressWarnings({"unchecked"})
    private void setRecursive(DynArray<Object> currentStorage, T value, int[] indices, int position) {
        int currentIndex = indices[position];

        if (position == rank - 1) {

            while (currentIndex >= currentStorage.count) {
                currentStorage.append(null);
            }

            currentStorage.array[currentIndex] = value;
        } else {

            while (currentIndex >= currentStorage.count) {

                NDynArray<T> newSubArray = new NDynArray<>(elementType, position + 1, currentDimensions, rank);
                currentStorage.append(newSubArray);
            }

            NDynArray<T> nextLevel = (NDynArray<T>) currentStorage.array[currentIndex];
            if (nextLevel == null) {
                nextLevel = new NDynArray<>(elementType, position + 1, currentDimensions, rank);
                currentStorage.array[currentIndex] = nextLevel;
            }

            nextLevel.setRecursive(nextLevel.storage, value, indices, position + 1);
        }
    }
    
    public ArrayNavigator<T> getAt(int index) {
        return new ArrayNavigator<>(this, index);
    }
    
    public int getDimensionSize(int dimension) {
        if (dimension < 0 || dimension >= rank) {
            throw new IndexOutOfBoundsException("Измерение: " + dimension);
        }
        return currentDimensions[dimension];
    }
    
    public int getRank() {
        return rank;
    }
    
    public boolean contains(int... indices) {
        return get(indices) != null;
    }
    
    public void remove(int... indices) {
        set(null, indices);
    }
    
    @Override
    public String toString() {
        return toStringRecursive(storage, 0);
    }
    
    @SuppressWarnings("unchecked")
    private String toStringRecursive(DynArray<Object> currentStorage, int currentLevel) {
        if (currentLevel == rank - 1) {

            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < currentStorage.count; i++) {
                if (i > 0) sb.append(", ");
                sb.append(currentStorage.array[i]);
            }
            sb.append("]");
            return sb.toString();
        } else {

            StringBuilder sb = new StringBuilder("[");
            for (int i = 0; i < currentStorage.count; i++) {
                if (i > 0) sb.append(", ");
                NDynArray<T> subArray = (NDynArray<T>) currentStorage.array[i];
                if (subArray == null) {
                    sb.append("null");
                } else {
                    sb.append(subArray.toStringRecursive(subArray.storage, currentLevel + 1));
                }
            }
            sb.append("]");
            return sb.toString();
        }
    }
}

