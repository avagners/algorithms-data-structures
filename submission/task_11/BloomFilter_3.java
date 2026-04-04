public class BloomFilter_3
{
      public int filter_len;
      private byte[] counters; // массив 4-битных счётчиков (используем byte)

      public BloomFilter_3(int f_len)
      {
        filter_len = f_len;
        counters = new byte[f_len]; // все счётчики изначально равны нулю
      }

      // 11-3; increment; Time: O(1), Space: O(1)
      private void increment(int index)
      {
        if (counters[index] < 15) { // max для 4-битного счётчика
            counters[index]++;
        }
      }

      // 11-3; decrement; Time: O(1), Space: O(1)
      private void decrement(int index)
      {
        if (counters[index] > 0) {
            counters[index]--;
        }
      }

      // 11-3; getCount; Time: O(1), Space: O(1)
      private int getCount(int index)
      {
        return counters[index];
      }

      // 11-3; getCounter; Time: O(1), Space: O(1)
      public byte[] getCounters()
      {
        return counters.clone();
      }

      // 11-3; hash1; Time: O(L), Space: O(1), L - длина строки
      public int hash1(String str1)
      {
        int result = 0;
        for(int i=0; i<str1.length(); i++)
        {
            int code = (int)str1.charAt(i);
            result = (result * 17 + code) % filter_len;
        }
        return result;
      }

      // 11-3; hash2; Time: O(L), Space: O(1), L - длина строки
      public int hash2(String str1)
      {
        int result = 0;
        for(int i=0; i<str1.length(); i++)
        {
            int code = (int)str1.charAt(i);
            result = (result * 223 + code) % filter_len;
        }
        return result;
      }

      // 11-3; add; Time: O(L), Space: O(1), L - длина строки
      public void add(String str1)
      {
        int h1 = hash1(str1);
        int h2 = hash2(str1);
        increment(h1);
        increment(h2);
      }

      // 11-3; isValue; Time: O(L), Space: O(1), L - длина строки
      // Элемент считается присутствующим, если оба счётчика > 0
      public boolean isValue(String str1)
      {
        int h1 = hash1(str1);
        int h2 = hash2(str1);
        return getCount(h1) > 0 && getCount(h2) > 0;
      }

      // 11-3; remove; Time: O(L), Space: O(1), L - длина строки
      // Удаляет элемент, декрементируя счётчики по хэшам.
      // ВАЖНО: если элемента нет (ложноположительное срабатывание),
      // удаление нарушит структуру — счётчики уменьшатся некорректно,
      // и другие элементы могут стать ненайденными.
      public void remove(String str1)
      {
        // Можно удалять только те элементы, которые "находятся" в фильтре.
        // Но даже если isValue возвращает true, это может быть ложноположительное
        // срабатывание — тогда декремент повредит другим элементам.
        int h1 = hash1(str1);
        int h2 = hash2(str1);
        decrement(h1);
        decrement(h2);
      }

      // 11-3; countNonZero; Time: O(m), Space: O(1), m — размер фильтра
      // Количество ненулевых счётчиков (оценка заполненности)
      public int countNonZero()
      {
        int count = 0;
        for (int i = 0; i < filter_len; i++) {
            if (counters[i] > 0) count++;
        }
        return count;
      }

      // 11-3; estimatedFalsePositiveRate; Time: O(m), Space: O(1)
      // Оценка вероятности ложного срабатывания через заполненность
      public double estimatedFalsePositiveRate()
      {
        double ratio = (double) countNonZero() / filter_len;
        return Math.pow(ratio, 2); // k = 2
      }

      // 11-3; totalElements; Time: O(m), Space: O(1)
      // Приблизительная оценка количества добавленных элементов
      // (сумма всех счётчиков, делённая на k=2, так как каждый элемент
      // увеличивает два счётчика)
      public double estimatedElementCount()
      {
        long sum = 0;
        for (int c : counters) sum += c;
        return sum / 2.0;
      }
}

