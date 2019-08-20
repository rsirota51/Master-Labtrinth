package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import code.model.Token;
/**
 * Tests for the Token Class
 * @author Steven
 *
 */
public class TokenTests {
	/**
	 * Common test code for the toString method of the token class.
	 * @param t
	 * @param expected
	 */
	private void tokenToString(Token t, String expected) {
		String actual = t.toString();
		assertTrue(expected.equals(actual));
	}
	/**
	 * Ensures that the toString method works correctly
	 * @author
	 */
	@Test public void tokenToString00() {
		Token t = new Token(3);
		String expected = "03";
		tokenToString(t, expected);
	}
	/**
	 * Ensures that the toString method works correctly
	 * @author
	 */
	@Test public void tokenToString01() {
		Token t = new Token(10);
		String expected = "10";
		tokenToString(t, expected);
	}

}
