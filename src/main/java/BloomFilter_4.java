/**
 * 11-4 BloomFilter с алгоритмом восстановления исходного множества.
 *
 * Алгоритм восстановления:
 *
 * Задача: по содержимому фильтра Блюма (битовая маска/счётчики) восстановить
 * множество оригинальных элементов, которые были добавлены через add().
 *
 * Фундаментальное ограничение: точное восстановление невозможно, так как
 * фильтр Блюма — структура с потерями. Хэш-функции необратимы, коллизии
 * не различимы, ложноположительные срабатывания добавляют "шум".
 *
 * Реализованные подходы:
 *
 * 1. recoverFromDictionary(dictionary) — O(D*L)
 *    Если известно пространство возможных элементов (словарь), проверяем
 *    каждый кандидат через isValue(). Это наиболее практичный подход:
 *    - Для базы данных пользователей — словарь = все возможные username.
 *    - Для URL-кэша — словарь = все известные URL.
 *    Результат включает ложноположительные элементы из словаря.
 *
 * 2. recoverFromBruteForce(alphabet, maxLength) — O(A^len * L)
 *    Полный перебор всех строк из заданного алфавита. Применим только
 *    для малых длин (1-3 символа), так как A^len растёт экспоненциально.
 *    Пример: алфавит "0123456789", длина 1 — всего 10 кандидатов.
 *
 * 3. recoverFromBitmapInversion(dictionary) — O(D*L*k)
 *    Аналог словарного перебора, но с прямой проверкой хэшей против
 *    битовой маски (без вызова isValue). Результат идентичен.
 *
 * 4. recoverWithConfidence(dictionary) — O(D*L)
 *    Расширенная версия: каждому кандидату присваивается "уверенность"
 *    = sum(счётчики[h1], счётчики[h2]). Элементы с более высокими
 *    счётчиками вероятнее были реально добавлены (особенно с дубликатами).
 *    Результат отсортирован по убыванию уверенности.
 *
 * 5. recoverMostLikelySet(dictionary, minConfidence) — O(D*L + R*logR)
 *    Фильтрация кандидатов по порогу уверенности. Чем выше порог,
 *    тем меньше ложноположительных, но тем выше риск пропустить
 *    элементы с коллизировавшими хэшами.
 *
 * 6. getTopCounterPositions(k) — O(m)
 *    Возвращает k позиций с максимальными счётчиками. Эвристика:
 *    элементы, чьи хэши попадают в эти позиции, были добавлены
 *    неоднократно или не имеют коллизий.
 *
 * Практический алгоритм восстановления:
 *   Шаг 1: Сформировать словарь кандидатов (если известен домен данных).
 *   Шаг 2: Вызвать recoverWithConfidence() для ранжирования.
 *   Шаг 3: Отфильтровать по порогу уверенности или взять топ-N.
 *   Шаг 4: При необходимости — ручная верификация кандидатов.
 *
 * Пример: если фильтр использовался для кэша 10-значных числовых строк
 * ("0123456789" ... "9012345678"), словарь из 10^10 элементов слишком
 * велик для полного перебора. Но если известно, что использовались
 * только 10 конкретных строк — проверка мгновенна.
 */
public class BloomFilter_4
{
      public int filter_len;
      private byte[] counters; // массив счётчиков для поддержки удаления

      public BloomFilter_4(int f_len)
      {
        filter_len = f_len;
        counters = new byte[f_len];
      }

      // 11-4; increment; Time: O(1), Space: O(1)
      private void increment(int index)
      {
        if (counters[index] < 15) {
            counters[index]++;
        }
      }

      // 11-4; decrement; Time: O(1), Space: O(1)
      private void decrement(int index)
      {
        if (counters[index] > 0) {
            counters[index]--;
        }
      }

      // 11-4; getCount; Time: O(1), Space: O(1)
      public int getCount(int index)
      {
        return counters[index];
      }

      // 11-4; hash1; Time: O(L), Space: O(1), L - длина строки
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

      // 11-4; hash2; Time: O(L), Space: O(1), L - длина строки
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

      // 11-4; add; Time: O(L), Space: O(1), L - длина строки
      public void add(String str1)
      {
        int h1 = hash1(str1);
        int h2 = hash2(str1);
        increment(h1);
        increment(h2);
      }

      // 11-4; isValue; Time: O(L), Space: O(1), L - длина строки
      public boolean isValue(String str1)
      {
        int h1 = hash1(str1);
        int h2 = hash2(str1);
        return getCount(h1) > 0 && getCount(h2) > 0;
      }

      // 11-4; remove; Time: O(L), Space: O(1), L - длина строки
      public void remove(String str1)
      {
        int h1 = hash1(str1);
        int h2 = hash2(str1);
        decrement(h1);
        decrement(h2);
      }

      // ============================================
      // 11-4; Алгоритмы восстановления множества
      // ============================================

      /**
       * 11-4; recoverFromDictionary; Time: O(D*L), Space: O(R),
       * D — размер словаря, R — количество найденных элементов.
       *
       * Словарный перебор: проверяем каждый элемент из известного словаря
       * через isValue. Подходит, если известно пространство возможных элементов
       * (например, все 10-значные строки, все usernames из БД и т.д.).
       *
       * Недостатки: ложноположительные срабатывания добавят "лишние" элементы.
       */
      public java.util.ArrayList<String> recoverFromDictionary(
              java.util.ArrayList<String> dictionary)
      {
        java.util.ArrayList<String> result = new java.util.ArrayList<>();
        for (String candidate : dictionary) {
            if (isValue(candidate)) {
                result.add(candidate);
            }
        }
        return result;
      }

      /**
       * 11-4; recoverFromBruteForce; Time: O(A^len * L), Space: O(R),
       * A — размер алфавита, len — длина строк, R — найденные элементы.
       *
       * Полный перебор всех строк заданной длины из заданного алфавита.
       * Для m=32 это разумно при малых длинах (1-3 символа цифрового алфавита).
       *
       * Пример: алфавит "0123456789", длина 10 — это 10^10 строк,
       * что слишком много. Но для длины 1-3 — вполне выполнимо.
       */
      public java.util.ArrayList<String> recoverFromBruteForce(
              String alphabet, int maxLength)
      {
        java.util.ArrayList<String> result = new java.util.ArrayList<>();
        char[] chars = alphabet.toCharArray();
        bruteForceRecursive("", chars, maxLength, result);
        return result;
      }

      // 11-4; bruteForceRecursive; Time: O(A^len * L), Space: O(len) для стека
      private void bruteForceRecursive(
              String prefix, char[] alphabet, int maxLength,
              java.util.ArrayList<String> result)
      {
        if (prefix.length() > maxLength) {
            return;
        }
        if (prefix.length() > 0) {
            if (isValue(prefix)) {
                result.add(prefix);
            }
        }
        if (prefix.length() < maxLength) {
            for (char c : alphabet) {
                bruteForceRecursive(prefix + c, alphabet, maxLength, result);
            }
        }
      }

      /**
       * 11-4; recoverFromBitmapInversion; Time: O(A^len * L * k), Space: O(R).
       *
       * Уточнённый перебор: ищем строки, чьи хэши ТОЧНО соответствуют
       * установленным битам в фильтре. То есть h1(str) и h2(str) должны
       * указывать на биты, которые установлены. Дополнительно проверяем,
       * что счётчики в этих позициях >= 1.
       *
       * Это по сути тот же словарь/brute-force, но с фильтром на уровне
       * битовой маски — отбрасываем кандидатов, чьи хэши попадают на нули.
       */
      public java.util.ArrayList<String> recoverFromBitmapInversion(
              java.util.ArrayList<String> dictionary)
      {
        java.util.ArrayList<String> result = new java.util.ArrayList<>();
        for (String candidate : dictionary) {
            int h1 = hash1(candidate);
            int h2 = hash2(candidate);
            // Проверяем, что оба бита установлены
            if (getCount(h1) > 0 && getCount(h2) > 0) {
                result.add(candidate);
            }
        }
        return result;
      }

      /**
       * 11-4; recoverWithConfidence; Time: O(D*L), Space: O(R).
       *
       * Восстановление с оценкой "уверенности" для каждого кандидата.
       * Уверенность = сумма счётчиков по хэшам элемента.
       * Чем выше счётчики, тем больше вероятность, что элемент реально
       * был добавлен (а не является ложноположительным срабатыванием).
       *
       * Возвращает пары (элемент, уверенность), отсортированные по убыванию.
       */
      public java.util.ArrayList<RecoveryCandidate> recoverWithConfidence(
              java.util.ArrayList<String> dictionary)
      {
        java.util.ArrayList<RecoveryCandidate> result =
            new java.util.ArrayList<>();

        for (String candidate : dictionary) {
            int h1 = hash1(candidate);
            int h2 = hash2(candidate);
            int c1 = getCount(h1);
            int c2 = getCount(h2);

            if (c1 > 0 && c2 > 0) {
                int confidence = c1 + c2;
                result.add(new RecoveryCandidate(candidate, confidence));
            }
        }

        // Сортируем по убыванию уверенности
        result.sort((a, b) -> b.confidence - a.confidence);
        return result;
      }

      /**
       * 11-4; recoverMostLikelySet; Time: O(D*L + R*log(R)), Space: O(R).
       *
       * Возвращает наиболее вероятное исходное множество: элементы из словаря,
       * у которых уверенность >= порога. По умолчанию порог = 2 (оба счётчика >= 1).
       */
      public java.util.ArrayList<String> recoverMostLikelySet(
              java.util.ArrayList<String> dictionary, int minConfidence)
      {
        java.util.ArrayList<RecoveryCandidate> candidates =
            recoverWithConfidence(dictionary);

        java.util.ArrayList<String> result = new java.util.ArrayList<>();
        for (RecoveryCandidate rc : candidates) {
            if (rc.confidence >= minConfidence) {
                result.add(rc.element);
            }
        }
        return result;
      }

      /**
       * 11-4; recoverByCounterAnalysis; Time: O(m + D*L), Space: O(R).
       *
       * Анализ счётчиков: находим позиции с максимальными счётчиками
       * и пытаемся найти элементы, чьи хэши попадают именно туда.
       * Это эвристика — элементы с высокими счётчиками скорее были
       * добавлены (возможно, с дубликатами).
       *
       * Возвращает топ-K позиций с максимальными счётчиками.
       */
      public int[] getTopCounterPositions(int k)
      {
        int[] topPositions = new int[Math.min(k, filter_len)];
        int[] topValues = new int[Math.min(k, filter_len)];

        for (int i = 0; i < filter_len; i++) {
            int val = counters[i];
            for (int j = 0; j < topPositions.length; j++) {
                if (val > topValues[j]) {
                    // Сдвигаем вниз
                    for (int m = topPositions.length - 1; m > j; m--) {
                        topPositions[m] = topPositions[m-1];
                        topValues[m] = topValues[m-1];
                    }
                    topPositions[j] = i;
                    topValues[j] = val;
                    break;
                }
            }
        }
        return topPositions;
      }

      // 11-4; getNonZeroPositions; Time: O(m), Space: O(m)
      public java.util.ArrayList<Integer> getNonZeroPositions()
      {
        java.util.ArrayList<Integer> positions = new java.util.ArrayList<>();
        for (int i = 0; i < filter_len; i++) {
            if (counters[i] > 0) {
                positions.add(i);
            }
        }
        return positions;
      }

      // 11-4; countNonZero; Time: O(m), Space: O(1)
      public int countNonZero()
      {
        int count = 0;
        for (int c : counters) {
            if (c > 0) count++;
        }
        return count;
      }

      // 11-4; estimatedFalsePositiveRate; Time: O(m), Space: O(1)
      public double estimatedFalsePositiveRate()
      {
        double ratio = (double) countNonZero() / filter_len;
        return Math.pow(ratio, 2);
      }
}

// 11-4; Класс для хранения кандидата с уверенностью
class RecoveryCandidate {
    String element;
    int confidence;

    RecoveryCandidate(String element, int confidence) {
        this.element = element;
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        return element + " (conf=" + confidence + ")";
    }
}

