import java.util.Stack;

public class Stack_BalancedBrackets {

	// 4-4; isBalanced; Time: O(n), Space: O(n)
	public static boolean isBalanced(String s) {
		Stack<Character> stack = new Stack<>();

		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);

			if (ch == '(') {
				stack.push(ch);
			} else if (ch == ')') {
				if (stack.isEmpty()) {
					return false;
				}
				stack.pop();
			}
		}

		return stack.isEmpty();
	}

	// 4-5; isBalancedExtended; Time: O(n), Space: O(n)
	public static boolean isBalancedExtended(String s) {
		Stack<Character> stack = new Stack<>();

		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);

			if (ch == '(' || ch == '{' || ch == '[') {
				stack.push(ch);
			} else if (ch == ')' || ch == '}' || ch == ']') {
				if (stack.isEmpty()) {
					return false;
				}
				char open = stack.pop();
				if (!isMatchingPair(open, ch)) {
					return false;
				}
			}
		}

		return stack.isEmpty();
	}

	// 4-5; isMatchingPair; Time: O(1), Space: O(1)
	private static boolean isMatchingPair(char open, char close) {
		return (open == '(' && close == ')') ||
			   (open == '{' && close == '}') ||
			   (open == '[' && close == ']');
	}
}
