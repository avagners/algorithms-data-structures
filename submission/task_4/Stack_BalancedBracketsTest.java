import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Stack_BalancedBracketsTest {

	@Test
	public void testIsBalanced_emptyString() {
		assertTrue(Stack_BalancedBrackets.isBalanced(""));
	}

	@Test
	public void testIsBalanced_simpleBalanced() {
		assertTrue(Stack_BalancedBrackets.isBalanced("()"));
		assertTrue(Stack_BalancedBrackets.isBalanced("(())"));
		assertTrue(Stack_BalancedBrackets.isBalanced("()()"));
	}

	@Test
	public void testIsBalanced_complexBalanced() {
		assertTrue(Stack_BalancedBrackets.isBalanced("(()((())()))"));
		assertTrue(Stack_BalancedBrackets.isBalanced("((()()))"));
		assertTrue(Stack_BalancedBrackets.isBalanced("()()()()"));
	}

	@Test
	public void testIsBalanced_notBalanced_extraOpen() {
		assertFalse(Stack_BalancedBrackets.isBalanced("("));
		assertFalse(Stack_BalancedBrackets.isBalanced("(()"));
		assertFalse(Stack_BalancedBrackets.isBalanced("(()()(()"));
		assertFalse(Stack_BalancedBrackets.isBalanced("((())"));
	}

	@Test
	public void testIsBalanced_notBalanced_extraClose() {
		assertFalse(Stack_BalancedBrackets.isBalanced(")"));
		assertFalse(Stack_BalancedBrackets.isBalanced("())"));
		assertFalse(Stack_BalancedBrackets.isBalanced("()())"));
	}

	@Test
	public void testIsBalanced_notBalanced_wrongOrder() {
		assertFalse(Stack_BalancedBrackets.isBalanced(")("));
		assertFalse(Stack_BalancedBrackets.isBalanced("())("));
		assertFalse(Stack_BalancedBrackets.isBalanced("))(("));
	}

	@Test
	public void testIsBalancedExtended_simpleBrackets() {
		assertTrue(Stack_BalancedBrackets.isBalancedExtended("()"));
		assertTrue(Stack_BalancedBrackets.isBalancedExtended("(())"));
	}

	@Test
	public void testIsBalancedExtended_curlyBrackets() {
		assertTrue(Stack_BalancedBrackets.isBalancedExtended("{}"));
		assertTrue(Stack_BalancedBrackets.isBalancedExtended("{{}}"));
	}

	@Test
	public void testIsBalancedExtended_squareBrackets() {
		assertTrue(Stack_BalancedBrackets.isBalancedExtended("[]"));
		assertTrue(Stack_BalancedBrackets.isBalancedExtended("[[]]"));
	}

	@Test
	public void testIsBalancedExtended_mixedBrackets() {
		assertTrue(Stack_BalancedBrackets.isBalancedExtended("({[]})"));
		assertTrue(Stack_BalancedBrackets.isBalancedExtended("({[()]})"));
		assertTrue(Stack_BalancedBrackets.isBalancedExtended("[({})]"));
	}

	@Test
	public void testIsBalancedExtended_notBalanced_mixed() {
		assertFalse(Stack_BalancedBrackets.isBalancedExtended("([)]"));
		assertFalse(Stack_BalancedBrackets.isBalancedExtended("({)}"));
		assertFalse(Stack_BalancedBrackets.isBalancedExtended("((])"));
	}
}
