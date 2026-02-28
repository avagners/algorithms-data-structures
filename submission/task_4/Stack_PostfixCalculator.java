import java.util.Stack;

public class Stack_PostfixCalculator {

	// 4-8; evaluate; Time: O(n), Space: O(n)
	public static int evaluate(String[] tokens) {
		Stack<String> s1 = new Stack<>();
		Stack<Integer> s2 = new Stack<>();

		for (int i = tokens.length - 1; i >= 0; i--) {
			s1.push(tokens[i]);
		}

		while (!s1.isEmpty()) {
			String token = s1.pop();

			if (isBinaryOperator(token)) {
				if (s2.size() < 2) {
					throw new IllegalArgumentException("Недостаточно операндов для операции: " + token);
				}
				int b = s2.pop();
				int a = s2.pop();
				int result = applyOperator(token, a, b);
				s2.push(result);
			} else if ("=".equals(token)) {
				if (s2.isEmpty()) {
					throw new IllegalArgumentException("Пустой стек для операции =");
				}
				return s2.pop();
			} else {
				s2.push(Integer.parseInt(token));
			}
		}

		if (s2.isEmpty()) {
			throw new IllegalArgumentException("Пустое выражение");
		}
		return s2.peek();
	}

	// 4-8; isBinaryOperator; Time: O(1), Space: O(1)
	private static boolean isBinaryOperator(String token) {
		return "+".equals(token) || "-".equals(token) ||
			   "*".equals(token) || "/".equals(token);
	}

	// 4-8; applyOperator; Time: O(1), Space: O(1)
	private static int applyOperator(String op, int a, int b) {
		switch (op) {
			case "+": return a + b;
			case "-": return a - b;
			case "*": return a * b;
			case "/":
				if (b == 0) {
					throw new ArithmeticException("Деление на ноль");
				}
				return a / b;
			case "=": return b;
			default:
				throw new IllegalArgumentException("Неизвестная операция: " + op);
		}
	}

	// 4-8; evaluateExpression; Time: O(n), Space: O(n)
	public static int evaluateExpression(String expression) {
		String[] tokens = expression.trim().split("\\s+");
		return evaluate(tokens);
	}
}

