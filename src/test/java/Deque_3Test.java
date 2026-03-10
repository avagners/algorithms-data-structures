import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Deque_3Test {

	@Test
	public void testIsPalindrome_emptyString() {
		assertTrue(Deque_3.isPalindrome(""));
	}

	@Test
	public void testIsPalindrome_nullString() {
		assertTrue(Deque_3.isPalindrome(null));
	}

	@Test
	public void testIsPalindrome_singleChar() {
		assertTrue(Deque_3.isPalindrome("a"));
	}

	@Test
	public void testIsPalindrome_simplePalindrome() {
		assertTrue(Deque_3.isPalindrome("aba"));
		assertTrue(Deque_3.isPalindrome("abba"));
		assertTrue(Deque_3.isPalindrome("abcba"));
	}

	@Test
	public void testIsPalindrome_notPalindrome() {
		assertFalse(Deque_3.isPalindrome("abc"));
		assertFalse(Deque_3.isPalindrome("abcd"));
	}

	@Test
	public void testIsPalindrome_caseSensitive() {
		assertTrue(Deque_3.isPalindrome("aba"));
		assertTrue(Deque_3.isPalindrome("aBa"));  // "aBa" -- палиндром
		assertFalse(Deque_3.isPalindrome("abc"));  // не палиндром
	}

	@Test
	public void testIsPalindrome_withSpaces() {
		assertTrue(Deque_3.isPalindrome("a a"));
		assertFalse(Deque_3.isPalindrome("a b"));
	}

	@Test
	public void testIsPalindromeIgnoreCase_simple() {
		assertTrue(Deque_3.isPalindromeIgnoreCase("aba"));
		assertTrue(Deque_3.isPalindromeIgnoreCase("aBa"));
		assertTrue(Deque_3.isPalindromeIgnoreCase("AbBa"));
	}

	@Test
	public void testIsPalindromeIgnoreCase_phrase() {
		assertTrue(Deque_3.isPalindromeIgnoreCase("А роза упала на лапу Азора"));
		assertTrue(Deque_3.isPalindromeIgnoreCase("A man a plan a canal Panama"));
	}

	@Test
	public void testIsPalindromeIgnoreCase_withPunctuation() {
		assertTrue(Deque_3.isPalindromeIgnoreCase("A man, a plan, a canal: Panama"));
		assertTrue(Deque_3.isPalindromeIgnoreCase("Was it a car or a cat I saw?"));
	}

	@Test
	public void testIsPalindromeIgnoreCase_numbers() {
		assertTrue(Deque_3.isPalindromeIgnoreCase("12321"));
		assertTrue(Deque_3.isPalindromeIgnoreCase("12a21"));
		assertFalse(Deque_3.isPalindromeIgnoreCase("12345"));
	}

	@Test
	public void testIsPalindrome_longPalindrome() {
		String palindrome = "abcdefghhhgfedcba";
		assertTrue(Deque_3.isPalindrome(palindrome));
	}

	@Test
	public void testIsPalindrome_longNotPalindrome() {
		String notPalindrome = "abcdefghgfedcb";
		assertFalse(Deque_3.isPalindrome(notPalindrome));
	}

	@Test
	public void testIsPalindrome_russianPalindrome() {
		assertTrue(Deque_3.isPalindrome("шалаш"));
		assertTrue(Deque_3.isPalindrome("казак"));
		assertTrue(Deque_3.isPalindrome("топот"));
	}
}

