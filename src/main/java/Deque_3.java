public class Deque_3
{
	// 6-4; isPalindrome; Time: O(n), Space: O(n)
	public static boolean isPalindrome(String text)
	{
		if (text == null || text.isEmpty()) {
			return true;
		}

		Deque<Character> deque = new Deque<>();

		for (int i = 0; i < text.length(); i++) {
			deque.addTail(text.charAt(i));
		}

		while (deque.size() > 1) {
			Character front = deque.removeFront();
			Character back = deque.removeTail();

			if (!front.equals(back)) {
				return false;
			}
		}

		return true;
	}

	// 6-4; isPalindromeIgnoreCase; Time: O(n), Space: O(n)
	public static boolean isPalindromeIgnoreCase(String text)
	{
		if (text == null || text.isEmpty()) {
			return true;
		}

		Deque<Character> deque = new Deque<>();

		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			if (Character.isLetterOrDigit(ch)) {
				deque.addTail(Character.toLowerCase(ch));
			}
		}

		while (deque.size() > 1) {
			Character front = deque.removeFront();
			Character back = deque.removeTail();

			if (!front.equals(back)) {
				return false;
			}
		}

		return true;
	}
}

