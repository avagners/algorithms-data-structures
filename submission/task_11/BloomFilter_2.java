/**
 * 11-2 BloomFilter с поддержкой слияния (merge) фильтров.
 *
 * Вопрос: как изменится вероятность ложного срабатывания для итогового фильтра?
 *
 * Ответ: вероятность ложного срабатывания ВОЗРАСТАЕТ.
 *
 * Объяснение:
 * Слияние фильтров Блюма выполняется побитовым ИЛИ (OR) их битовых масок.
 * В результате в объединённом фильтре установлены все биты, которые были
 * установлены хотя бы в одном из исходных фильтров. Это означает, что
 * «заполненность» (доля единичных битов) итогового фильтра не меньше,
 * чем заполненность каждого из исходных, и обычно - строго больше.
 */
public class BloomFilter_2
{
      public int filter_len;
      private int bitmap; // 32-разрядный битовый массив

      public BloomFilter_2(int f_len)
      {
        filter_len = f_len;
        bitmap = 0; // все биты изначально равны нулю
      }

      // 11-2; setBit; Time: O(1), Space: O(1)
      private void setBit(int index)
      {
        bitmap |= (1 << index);
      }

      // 11-2; getBit; Time: O(1), Space: O(1)
      private boolean getBit(int index)
      {
        return (bitmap & (1 << index)) != 0;
      }

      // 11-2; getBitmap; Time: O(1), Space: O(1)
      public int getBitmap()
      {
        return bitmap;
      }

      // 11-2; setBitmap; Time: O(1), Space: O(1)
      public void setBitmap(int bm)
      {
        bitmap = bm;
      }

      // 11-2; hash1; Time: O(L), Space: O(1), L - длина строки
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

      // 11-2; hash2; Time: O(L), Space: O(1), L - длина строки
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

      // 11-2; add; Time: O(L), Space: O(1), L - длина строки
      public void add(String str1)
      {
        int h1 = hash1(str1);
        int h2 = hash2(str1);
        setBit(h1);
        setBit(h2);
      }

      // 11-2; isValue; Time: O(L), Space: O(1), L - длина строки
      public boolean isValue(String str1)
      {
        int h1 = hash1(str1);
        int h2 = hash2(str1);
        return getBit(h1) && getBit(h2);
      }

      // 11-2; merge; Time: O(1), Space: O(1)
      public void merge(BloomFilter_2 other)
      {
        if (this.filter_len != other.filter_len) {
            throw new IllegalArgumentException(
                "Фильтры должны быть одинакового размера");
        }
        this.bitmap = this.bitmap | other.bitmap;
      }

      // 11-2; mergeMany; Time: O(N), Space: O(1), N — количество фильтров
      public static BloomFilter_2 mergeMany(BloomFilter_2[] filters)
      {
        if (filters == null || filters.length == 0) {
            throw new IllegalArgumentException("Массив фильтров не может быть пустым");
        }

        BloomFilter_2 result = new BloomFilter_2(filters[0].filter_len);
        for (BloomFilter_2 f : filters) {
            if (f.filter_len != result.filter_len) {
                throw new IllegalArgumentException(
                    "Все фильтры должны быть одинакового размера");
            }
            result.bitmap |= f.bitmap;
        }
        return result;
      }

      // 11-2; countSetBits; Time: O(1), Space: O(1)
      public int countSetBits()
      {
        return Integer.bitCount(bitmap);
      }

      // 11-2; estimatedFalsePositiveRate; Time: O(1), Space: O(1)
      // Оценка вероятности ложного срабатывания на основе текущей заполненности.
      // P ≈ (m_set_bits / m) ^ k, где k = 2 (количество хэш-функций)
      public double estimatedFalsePositiveRate()
      {
        double ratio = (double) countSetBits() / filter_len;
        return Math.pow(ratio, 2); // k = 2
      }
}

