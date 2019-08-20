package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import code.model.board.AbstractTile;
import code.model.board.Board;
import code.model.board.FileIO;
/**
 * Gives additional tests for the Board class
 * @author Blake
 *
 */
public class BoardTestsStage2 
{
	/**
	 * Tests to ensure that rotating a row 7 times returns the Board to its original position
	 * @author Blake
	 */
	@Test
	public void test7RowRotations()
	{
		Board b = new Board();
		AbstractTile[] expected = new AbstractTile[7];
		for (int i=0; i<7; i++)
			expected[i] = b.tileAt(i,3);
		for (int i=0; i<=7; i++)
			if (!b.rotateRow(3, true))
				assertTrue("Valid row rotation prevented.", false);
		AbstractTile[] actual = new AbstractTile[7];
		for (int i=0; i<7; i++)
			actual[i] = b.tileAt(i,3);
		assertTrue("Board not returned to original position.", Arrays.equals(expected, actual));
	}
	/**
	 * Tests to ensure that rotating a column 7 times returns the Board to its original position.
	 * @author Blake
	 */
	@Test
	public void test7ColRotations()
	{
		Board b = new Board();
		AbstractTile[] expected = new AbstractTile[7];
		for (int i=0; i<7; i++)
			expected[i] = b.tileAt(i,5);
		for (int i=0; i<=7; i++)
			if (!b.rotateColumn(5, false))
				assertTrue("Valid row rotation prevented.", false);
		AbstractTile[] actual = new AbstractTile[7];
		for (int i=0; i<7; i++)
			actual[i] = b.tileAt(i,5);
		assertTrue("Board not returned to its original position. \n"+expected+"\n"+actual, Arrays.equals(actual, expected));
	}
	/**
	 * Tests to ensure the String contained in a file is the same as the string returned by the board's toString.
	 * @author Blake
	 */
	@Test
	public void testBoardLoadedFromStringCorrectly()
	{
		String expected = FileIO.load("sampleboard");
		String actual = new Board(expected).toString();
		assertEquals(expected, actual);
	}
	/**
	 * Tests to ensure exactly one token of value 1-21 is placed on the Board.
	 * @author Blake
	 */
	@Test
	public void testAllTokensPlacedOnce()
	{
		Board board = new Board();
		HashSet<Integer> expected = new HashSet<Integer>(21);
		for (int i=0; i<7; i++) 
			for (int j=0; j<7; j++) 
				if (board.tileAt(i,j).hasToken()) {
					int key = board.tileAt(i,j).getToken().getValue();
					if (!expected.add(key))
						assertTrue("Board has multiple tokens of value "+key+".", false);						
				}
		assertTrue("Board does not contain all keys.", expected.size() == 21);	
	}
	/**
	 * Tests to ensure tokens are placed on the correct tiles from startup
	 * @author Blake
	 */
	@Test
	public void testTokensOnCorrectTiles()
	{
		Board board = new Board();
		for (int i=0; i<7; i++)
			for (int j=0; j<7; j++)
				if (board.tileAt(i,j).hasToken() ^ !(i==0 || i==6 || j==0 || j==6 || (i%2==0 && j%2==0)))
					fail();
		assertTrue(true);
	}
	
}