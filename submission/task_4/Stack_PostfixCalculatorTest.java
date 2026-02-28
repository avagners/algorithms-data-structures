import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Stack_PostfixCalculatorTest {

	@Test
	public void testEvaluate_simpleNumber() {
		assertEquals(5, Stack_PostfixCalculator.evaluate(new String[]{"5", "="}));
	}

	@Test
	public void testEvaluate_simpleAddition() {
		assertEquals(7, Stack_PostfixCalculator.evaluate(new String[]{"3", "4", "+", "="}));
	}

	@Test
	public void testEvaluate_simpleMultiplication() {
		assertEquals(12, Stack_PostfixCalculator.evaluate(new String[]{"3", "4", "*", "="}));
	}

	@Test
	public void testEvaluate_fromTask_example1() {
		// (1 + 2) * 3 = 9
		assertEquals(9, Stack_PostfixCalculator.evaluate(new String[]{"1", "2", "+", "3", "*", "="}));
	}

	@Test
	public void testEvaluate_fromTask_example2() {
		// 8 2 + 5 * 9 + =  =>  (8 + 2) * 5 + 9 = 59
		assertEquals(59, Stack_PostfixCalculator.evaluate(new String[]{"8", "2", "+", "5", "*", "9", "+", "="}));
	}

	@Test
	public void testEvaluate_subtraction() {
		assertEquals(8, Stack_PostfixCalculator.evaluate(new String[]{"10", "2", "-", "="}));
	}

	@Test
	public void testEvaluate_division() {
		assertEquals(5, Stack_PostfixCalculator.evaluate(new String[]{"10", "2", "/", "="}));
	}

	@Test
	public void testEvaluate_complexExpression1() {
		// 2 3 4 + * =  =>  2 * (3 + 4) = 14
		assertEquals(14, Stack_PostfixCalculator.evaluate(new String[]{"2", "3", "4", "+", "*", "="}));
	}

	@Test
	public void testEvaluate_complexExpression2() {
		// 5 1 2 + 4 * + 3 - =  =>  5 + (1 + 2) * 4 - 3 = 14
		assertEquals(14, Stack_PostfixCalculator.evaluate(new String[]{"5", "1", "2", "+", "4", "*", "+", "3", "-", "="}));
	}

	@Test
	public void testEvaluate_multipleOperations() {
		// 2 3 + 4 5 + * =  =>  (2 + 3) * (4 + 5) = 45
		assertEquals(45, Stack_PostfixCalculator.evaluate(new String[]{"2", "3", "+", "4", "5", "+", "*", "="}));
	}

	@Test
	public void testEvaluate_withoutEquals() {
		// Без операции = возвращает верхний элемент S2
		assertEquals(7, Stack_PostfixCalculator.evaluate(new String[]{"3", "4", "+"}));
	}

	@Test
	public void testEvaluateExpression_method() {
		assertEquals(59, Stack_PostfixCalculator.evaluateExpression("8 2 + 5 * 9 + ="));
	}

	@Test
	public void testEvaluate_divisionByZero() {
		assertThrows(ArithmeticException.class, () -> {
			Stack_PostfixCalculator.evaluate(new String[]{"10", "0", "/", "="});
		});
	}

	@Test
	public void testEvaluate_insufficientOperands() {
		assertThrows(IllegalArgumentException.class, () -> {
			Stack_PostfixCalculator.evaluate(new String[]{"5", "+", "="});
		});
	}
}
