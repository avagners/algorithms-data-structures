import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для представления элемента с частотой в мульти-множестве.
 */
public class BagElement
{
    public final String value;
    public final int count;

    public BagElement(String value, int count)
    {
        this.value = value;
        this.count = count;
    }

    @Override
    public String toString()
    {
        return value + " (x" + count + ")";
    }
}
