
package tests;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import code.model.Token;
import code.model.board.AbstractTile;
import code.model.board.ITile;
import code.model.board.LTile;
import code.model.board.TTile;


/**
 * Tests for the Tile class
 * 3/23/16: Tests 1-21 pass
 * @author Matt (for all methods)
 */
public class TileTests 
{
	/**
	 * Tests whether a non-rotated TTile is as expected
	 */
	@Test
	public void testTTile01() //1
	{
		boolean[] expected = {false, true, true, true};
		boolean[] actual = new TTile(0).getConnections();
		assertTrue("I expected the T tile to have a connection array of " + Arrays.toString(expected) + ", but it was " + Arrays.toString(actual), Arrays.equals(expected, actual));
	}
	/**
	 * Tests whether one rotation of a T tile results in the correct connections array
	 */
	@Test
	public void testOneRotationT() //2
	{
		boolean[] expected = {true, false, true, true};
		boolean[] actual = new TTile(1).getConnections();
		assertTrue("I expected the rotated connections array to be " + Arrays.toString(expected) + ", but it was " + Arrays.toString(actual), Arrays.equals(expected, actual));
	}
	/**
	 * Tests whether two rotations of a T tile results in the correct connections array
	 */
	@Test
	public void testTwoRotationsT() //3
	{
		boolean[] expected = {true, true, false, true};
		boolean[] actual = new TTile(2).getConnections();
		assertTrue("I expected the rotated connections array to be " + Arrays.toString(expected) + ", but it was " + Arrays.toString(actual), Arrays.equals(expected, actual));
	}
	/**
	 * Tests whether three rotations of a T tile results in the correct connections array
	 */
	@Test
	public void testThreeRotationsT() //4
	{
		boolean[] expected = {true, true, true, false};
		boolean[] actual = new TTile(3).getConnections();
		assertTrue("I expected the rotated connections array to be " + Arrays.toString(expected) + ", but it was " + Arrays.toString(actual), Arrays.equals(expected, actual));
	}
	/**
	 * Tests whether four rotations of a T tile results in the original tile
	 */
	@Test
	public void testFourRotationsTToOriginal() //5
	{
		boolean[] expected = new TTile(0).getConnections();
		boolean[] actual = new TTile(4).getConnections();
		assertTrue("I expected the rotated connections array to be " + Arrays.toString(expected) + ", but it was " + Arrays.toString(actual), Arrays.equals(expected, actual));
	}
	/**
	 * Tests whether a non-rotated ITile is as expected
	 */
	@Test
	public void testITile01() //6
	{
		boolean[] expected = {true, false, true, false};
		boolean[] actual = new ITile(0).getConnections();
		assertTrue("I expected the T tile to have a connection array of " + Arrays.toString(expected) + ", but it was " + Arrays.toString(actual), Arrays.equals(expected, actual));
	}
	/**
	 * Tests whether one rotation of an I tile results in the correct connections array
	 */
	@Test
	public void testOneRotationI() //7
	{
		boolean[] expected = {false, true, false, true};
		boolean[] actual = new ITile(1).getConnections();
		assertTrue("I expected the rotated connections array to be " + Arrays.toString(expected) + ", but it was " + Arrays.toString(actual), Arrays.equals(expected, actual));
	}
	/**
	 * Tests whether two rotations of an I tile results in the correct connections array
	 */
	@Test
	public void testTwoRotationsI() //8
	{
		boolean[] expected = {true, false, true, false};
		boolean[] actual = new ITile(2).getConnections();
		assertTrue("I expected the rotated connections array to be " + Arrays.toString(expected) + ", but it was " + Arrays.toString(actual), Arrays.equals(expected, actual));
	}
	/**
	 * Tests whether three rotations of an I tile results in the correct connections array
	 */
	@Test
	public void testThreeRotationsI() //9
	{
		boolean[] expected = {false, true, false, true};
		boolean[] actual = new ITile(3).getConnections();
		assertTrue("I expected the rotated connections array to be " + Arrays.toString(expected) + ", but it was " + Arrays.toString(actual), Arrays.equals(expected, actual));
	}
	/**
	 * Tests whether four rotations of an I tile results in the original tile
	 */
	@Test
	public void testFourRotationsIToOriginal() //10
	{
		boolean[] expected = new ITile(0).getConnections();
		boolean[] actual = new ITile(4).getConnections();
		assertTrue("I expected the rotated connections array to be " + Arrays.toString(expected) + ", but it was " + Arrays.toString(actual), Arrays.equals(expected, actual));
	}
	/**
	 * Tests whether two rotations of an I tile results in the original tile
	 */
	@Test
	public void testTwoRotationsIToOriginal() //11
	{
		boolean[] expected = new ITile(0).getConnections();
		boolean[] actual = new ITile(4).getConnections();
		assertTrue("I expected the rotated connections array to be " + Arrays.toString(expected) + ", but it was " + Arrays.toString(actual), Arrays.equals(expected, actual));
	}
	/**
	 * Tests whether a non-rotated LTile is as expected
	 */
	@Test
	public void testLTile01() //12
	{
		boolean[] expected = {true, true, false, false};
		boolean[] actual = new LTile(0).getConnections();
		assertTrue("I expected the T tile to have a connection array of " + Arrays.toString(expected) + ", but it was " + Arrays.toString(actual), Arrays.equals(expected, actual));
	}
	/**
	 * Tests whether one rotation of a L tile results in the correct connections array
	 */
	@Test
	public void testOneRotationL() //13
	{
		boolean[] expected = {false, true, true, false};
		boolean[] actual = new LTile(1).getConnections();
		assertTrue("I expected the rotated connections array to be " + Arrays.toString(expected) + ", but it was " + Arrays.toString(actual), Arrays.equals(expected, actual));
	}
	/**
	 * Tests whether two rotations of a L tile results in the correct connections array
	 */
	@Test
	public void testTwoRotationsL() //14
	{
		boolean[] expected = {false, false, true, true};
		boolean[] actual = new LTile(2).getConnections();
		assertTrue("I expected the rotated connections array to be " + Arrays.toString(expected) + ", but it was " + Arrays.toString(actual), Arrays.equals(expected, actual));
	}
	/**
	 * Tests whether three rotations of a L tile results in the correct connections array
	 */
	@Test
	public void testThreeRotationsL() //15
	{
		boolean[] expected = {true, false, false, true};
		boolean[] actual = new LTile(3).getConnections();
		assertTrue("I expected the rotated connections array to be " + Arrays.toString(expected) + ", but it was " + Arrays.toString(actual), Arrays.equals(expected, actual));
	}
	/**
	 * Tests whether four rotations of a T tile results in the original tile
	 */
	@Test
	public void testFourRotationsLToOriginal() //16
	{
		boolean[] expected = new LTile(0).getConnections();
		boolean[] actual = new LTile(4).getConnections();
		assertTrue("I expected the rotated connections array to be " + Arrays.toString(expected) + ", but it was " + Arrays.toString(actual), Arrays.equals(expected, actual));
	}
	/**
	 * Tests that a tile has a token associated with it once it has been added
	 */
	@Test
	public void testTokenAdd() //17
	{
		int tokenValue = (int)(Math.random() * 21);
		boolean expected = true; 
		AbstractTile t = new TTile(0);
		t.setToken(new Token(tokenValue));
		boolean actual = t.hasToken();
		assertTrue("I expected that the tile having a token was" + expected + ", but it was " + actual, expected == actual);
	}
	/**
	 * Tests that a tile is initially created with no token
	 */
	@Test
	public void testTokenNotInitiallyInTile() //18
	{
		boolean expected = false;
		AbstractTile t = new ITile(0);
		boolean actual = t.hasToken();
		assertTrue("I expected that the tile having a token was" + expected + ", but it was " + actual, expected == actual);
	}
	/**
	 * Tests that the removal of a token will result in no token associated with the tile
	 */
	@Test
	public void testTokenGoneAfterRemoval() //19
	{
		boolean expected = false;
		AbstractTile t = new LTile(0);
		t.setToken(new Token(1));
		t.removeToken();
		boolean actual = t.hasToken();	
		assertTrue("I expected that the tile having a token was" + expected + ", but it was " + actual, expected == actual);
	}
	/**
	 * Tests that a token has the same value before addition and after removal
	 */
	@Test
	public void testTokenValueMaintainance() //20
	{
		int tokenValue = (int)(Math.random() * 21);
		int expected = tokenValue;
		AbstractTile t = new TTile(0);
		t.setToken(new Token(tokenValue));
		int actual = t.removeToken().getValue();
		assertTrue("I expected the added token to have value " + expected + ", but it was " + actual, expected == actual);
	}
	
	/**
	 * Tests that the toString method returns correctly.
	 */
	@Test(timeout = 1000)
	public void testToString() //21
	{
		AbstractTile t = new ITile(0);
		String expected = "I0";
		String actual = t.toString();
		
		assertTrue("I expected the output" + expected + ", but it was " + actual, expected.equals(actual));
	}
}