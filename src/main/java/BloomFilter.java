public class BloomFilter
{
      public int filter_len;
      private int bitmap; // 32-разрядный битовый массив

      public BloomFilter(int f_len)
      {
        filter_len = f_len;
        bitmap = 0; // все биты изначально равны нулю
      }

      // 11-1; setBit; Time: O(1), Space: O(1)
      private void setBit(int index)
      {
        bitmap |= (1 << index);
      }

      // 11-1; getBit; Time: O(1), Space: O(1)
      private boolean getBit(int index)
      {
        return (bitmap & (1 << index)) != 0;
      }

      // 11-1; hash1; Time: O(L), Space: O(1), L - длина строки
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

      // 11-1; hash2; Time: O(L), Space: O(1), L - длина строки
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

      // 11-1; add; Time: O(L), Space: O(1), L - длина строки
      public void add(String str1)
      {
        int h1 = hash1(str1);
        int h2 = hash2(str1);
        setBit(h1);
        setBit(h2);
      }

      // 11-1; isValue; Time: O(L), Space: O(1), L - длина строки
      public boolean isValue(String str1)
      {
        int h1 = hash1(str1);
        int h2 = hash2(str1);
        return getBit(h1) && getBit(h2);
      }
}

