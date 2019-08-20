package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import code.model.board.AbstractTile;
import code.model.board.Board;
import code.model.board.FileIO;
/**
 * Gives tests for the Board class
 * @author Matt, Steven, Blake
 *
 */
public class BoardTest 
{
	/**
	 * Test to see that GIVEN_TILES is correct size.
	 * @author Steven
	 */
	@Test
	public void testGivenTilesIsCorrectSize() //1
	{
		ArrayList<AbstractTile> tiles = new ArrayList<AbstractTile>(Board.GIVEN_TILES);
		int expected = 16;
		int actual = tiles.size();
		assertTrue("I expected the size of GIVEN_TILES to be " + expected + " but the actual size is " + actual, actual == expected);
	}
	
	/**
	 * Tests to check that each tile given on the board is what we expect it to be.
	 * @author Matt
	 */
	@Test
	public void testGivenTiles() //2
	{
		ArrayList<String> expected = new ArrayList<String>(Arrays.asList(new String[]{"L1", "T0", "T0", "L2", "T3", "T3", "T0", "T1", "T3", "T2", "T1", "T1", "L0", "T2", "T2", "L3"}));
		for (int i=0; i<16; i++)
		{
			if (!Board.GIVEN_TILES.get(i).toString().equals(expected.get(i)))
				fail("Given tile at (" + i/4*2 + "," + i%4*2 + ") should be " + expected.get(i) + ", but it is " + Board.GIVEN_TILES.get(i).toString());
		}
		assertTrue(true); 
	}
	/**
	 * Checks to make sure the generated board has the given tiles in the correct places
	 * @author Blake, Matt
	 */
	@Test
	public void testRandomBoardForGivenTiles() //3
	{
		Board board = new Board();
		for (int i=0; i<16; i++)
		{
			if (!board.tileAt(i/4*2, i%4*2).equals(Board.GIVEN_TILES.get(i)))
				fail("Incorrect tile at ("+i/4*2+","+i%4*2+"). "+Board.GIVEN_TILES+"\n"+ board.toString());
		}
		assertTrue(true);
	}
	
	/**
	 * Checks to make sure Board from file is properly formed.
	 * @author Blake
	 */
	@Test
	public void testBoardFormation() //4
	{
		String expected = FileIO.load("sampleboard.mlb");
		String actual = new Board(expected).toString();
		assertEquals(expected, actual);
	}
	
	/**
	 * Checks that a board is restored identical to how it was saved
	 * @author Matt
	 */
	@Test
	public void testProperBoardRestorationFromFile() //5
	{
		Board board = new Board();
		String expected = board.toString();
		FileIO.save("restoretest.mlb", board.toString());
		String actual = new Board(FileIO.load("restoretest.mlb")).toString();
		assertEquals(expected, actual);
	}
	
	/**
	 * Checks that the correct number of T tiles are placed in the board
	 * @author Matt
	 */
	@Test
	public void testProperNumberOfTs() //6
	{
		int expected = 18;
		String s = new Board().toString();
		int actual = 0;
		for (int i=0; i<s.length(); i++)
			if (s.charAt(i) == 'T')
				actual++;
		assertEquals(expected, actual);
	}
	
	/**
	 * Checks that the correct number of I tiles are placed in the board
	 * @author Matt
	 */
	@Test
	public void testProperNumberOfIs() //7
	{
		int expected = 13;
		String s = new Board().toString();
		int actual = 0;
		for (int i=0; i<s.length(); i++)
			if (s.charAt(i) == 'I')
				actual++;
		assertEquals(expected, actual);
	}
	
	/**
	 * Checks that the correct number of L tiles are placed in the board
	 * @author Matt
	 */
	@Test
	public void testProperNumberOfLs() //8
	{
		int expected = 19;
		String s = new Board().toString();
		int actual = 0;
		for (int i=0; i<s.length(); i++)
			if (s.charAt(i) == 'L')
				actual++;
		assertEquals(expected, actual);
	}
	/**
	 * 	Ensures that the tiles on the board are in the proper locations after the unused
	 *  tile pushed the tiles in the first row to the right
	 *  @author Matt, Blake
	 */
	@Test
	public void testRightShiftR1() //9
	{
		Board board = new Board();
		String expected = board.getUnused() + (board.tileAt(1,6).hasToken() ? "." + board.tileAt(1,6).getToken() : "");
		for (int i=0; i<6; i++)
			expected += board.tileAt(1,i);

		board.rotateRow(1, true);
		String actual = "";
		for (int i=0; i<7; i++)
			actual += board.tileAt(1,i);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * 	Ensures that the unused tile is the former rightmost tile in row 1 after 
	 *  the rightward rotation of the first row
	 *  @author Matt, Blake
	 */
	@Test
	public void testRightShiftR1Unused() //10
	{
		Board board = new Board();
		AbstractTile expected = board.tileAt(1,6);
		
		board.rotateRow(1, true);
		AbstractTile actual = board.getUnused();
				
		assertTrue(expected == actual);
	}
	
	/**
	 * 	Ensures that the last placed tile is the former unused after 
	 *  the rightward rotation of the first row
	 *  @author Matt, Blake
	 */
	@Test
	public void testRightShiftR1LastPlaced() //11
	{
		Board board = new Board();
		AbstractTile expected = board.getUnused();
		
		board.rotateRow(1, true);
		AbstractTile actual = board.getLastPlaced();
				
		assertTrue(expected == actual);
	}
	
	/**
	 * Ensures that the second row cannot be rotated to the right, as there are fixed tiles
	 * @author Matt
	 */
	@Test
	public void testRightShiftR0Illegal() //81
	{
		Board board = new Board();
		boolean expected = false;
		boolean actual = board.rotateRow(0, true);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Ensures that the second row cannot be rotated to the right, as there are fixed tiles
	 * @author Matt
	 */
	@Test
	public void testRightShiftR2Illegal() //12
	{
		Board board = new Board();
		boolean expected = false;
		boolean actual = board.rotateRow(2, true);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Ensures that the second row cannot be rotated to the right, as there are fixed tiles
	 * @author Matt
	 */
	@Test
	public void testRightShiftR4Illegal() //82
	{
		Board board = new Board();
		boolean expected = false;
		boolean actual = board.rotateRow(4, true);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Ensures that the second row cannot be rotated to the right, as there are fixed tiles
	 * @author Matt
	 */
	@Test
	public void testRightShiftR6Illegal() //83
	{
		Board board = new Board();
		boolean expected = false;
		boolean actual = board.rotateRow(6, true);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * 	Ensures that the tiles on the board are in the proper locations after the unused
	 *  tile pushed the tiles in the third row to the right
	 *  @author Matt, Blake
	 */
	@Test
	public void testRightShiftR3() //13
	{
		Board board = new Board();
		String expected = board.getUnused() + (board.tileAt(3,6).hasToken() ? "." + board.tileAt(3,6).getToken() : "");
		
		for (int i=0; i<6; i++)
			expected += board.tileAt(3,i);

		board.rotateRow(3, true);
		String actual = "";
		for (int i=0; i<7; i++)
			actual += board.tileAt(3,i);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * 	Ensures that the unused tile is the former rightmost tile in row 1 after 
	 *  the rightward rotation of the third row
	 *  @author Matt, Blake
	 */
	@Test
	public void testRightShiftR3Unused() //14
	{
		Board board = new Board();
		AbstractTile expected = board.tileAt(3,6);
		
		board.rotateRow(3, true);
		AbstractTile actual = board.getUnused();
				
		assertTrue(expected == actual);
	}
	
	/**
	 * 	Ensures that the last placed tile is the former unused after 
	 *  the rightward rotation of the third row
	 *  @author Matt, Blake
	 */
	@Test
	public void testRightShiftR3LastPlaced() //15
	{
		Board board = new Board();
		AbstractTile expected = board.getUnused();
		
		board.rotateRow(3, true);
		AbstractTile actual = board.getLastPlaced();

		assertTrue(expected == actual);
	}
	
	/**
	 * 	Ensures that the tiles on the board are in the proper locations after the unused
	 *  tile pushed the tiles in the fifth row to the right
	 *  @author Matt, Blake
	 */
	@Test
	public void testRightShiftR5() //16
	{
		Board board = new Board();
		String expected = board.getUnused() + (board.tileAt(5,6).hasToken() ? "." + board.tileAt(5,6).getToken() : "");
		
		for (int i=0; i<6; i++)
			expected += board.tileAt(5,i);
		
		board.rotateRow(5, true);
		String actual = "";
		for (int i=0; i<7; i++)
			actual += board.tileAt(5,i);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * 	Ensures that the unused tile is the former rightmost tile in row 1 after 
	 *  the rightward rotation of the fifth row
	 *  @author Matt, Blake
	 */
	@Test
	public void testRightShiftR5Unused() //17
	{
		Board board = new Board();
		AbstractTile expected = board.tileAt(5,6);
		
		board.rotateRow(5, true);
		AbstractTile actual = board.getUnused();

		assertTrue(expected == actual);
	}
	
	/**
	 * 	Ensures that the last placed tile is the former unused after 
	 *  the rightward rotation of the fifth row
	 *  @author Matt, Blake
	 */
	@Test
	public void testRightShiftR5LastPlaced() //18
	{
		Board board = new Board();
		AbstractTile expected = board.getUnused();
		
		board.rotateRow(5, true);
		AbstractTile actual = board.getLastPlaced();

		assertTrue(expected == actual);
	}
	
	/**
	 *  Ensures that the tiles on the board are in the proper locations after the unused
	 *  tile pushed the tiles in the first row to the left
	 *  @author Matt, Blake
	 */
	@Test
	public void testLeftShiftR1() //19
	{
		Board board = new Board();
		String expected = "";
		for (int i=1; i<7; i++)
			expected += board.tileAt(1,i);
		
		expected += board.getUnused() + (board.tileAt(1,0).hasToken() ? "." + board.tileAt(1,0).getToken() : "");
		
		board.rotateRow(1, false);
		String actual = "";
		for (int i=0; i<7; i++)
			actual += board.tileAt(1,i);
		
		assertEquals(expected, actual);
	}
	
	/**
	 *  Ensures that the unused tile is the former leftmost tile in row 1 after 
	 *  the leftward rotation of the first row
	 *  @author Matt, Blake
	 */
	@Test
	public void testLeftShiftR1Unused() //20
	{
		Board board = new Board();
		AbstractTile expected = board.tileAt(1,0);
		
		board.rotateRow(1, false);
		AbstractTile actual = board.getUnused();

		assertTrue(expected == actual);
	}
	
	/**
	 * 	Ensures that the last placed tile is the former unused after 
	 *  the leftward rotation of the first row
	 *  @author Matt, Blake
	 */
	@Test
	public void testLeftShiftR1LastPlaced() //21
	{
		Board board = new Board();
		AbstractTile expected = board.getUnused();
		
		board.rotateRow(1, false);
		AbstractTile actual = board.getLastPlaced();
				
		assertTrue(expected == actual);
	}
	
	/**
	 * Ensures that the second row cannot be rotated to the left, as there are fixed tiles
	 * @author Matt
	 */
	@Test
	public void testLeftShiftR2Illegal() //22
	{
		Board board = new Board();
		boolean expected = false;
		boolean actual = board.rotateRow(2, false);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * 	Ensures that the tiles on the board are in the proper locations after the unused
	 *  tile pushed the tiles in the third row to the left
	 *  @author Matt, Blake
	 */
	@Test
	public void testLeftShiftR3() //23
	{
		Board board = new Board();
		String expected = "";
		for (int i=1; i<7; i++)
			expected += board.tileAt(3,i);

		expected += board.getUnused() + (board.tileAt(3,0).hasToken() ? "." + board.tileAt(3,0).getToken() : "");
		
		board.rotateRow(3, false);
		String actual = "";
		for (int i=0; i<7; i++)
			actual += board.tileAt(3,i);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * 	Ensures that the unused tile is the former leftmost tile in row 1 after 
	 *  the leftward rotation of the third row
	 *  @author Matt, Blake
	 */
	@Test
	public void testLeftShiftR3Unused() //24
	{
		Board board = new Board();
		AbstractTile expected = board.tileAt(3,0);
		
		board.rotateRow(3, false);
		AbstractTile actual = board.getUnused();

		assertTrue(expected == actual);
	}
	
	/**
	 * 	Ensures that the last placed tile is the former unused after 
	 *  the leftward rotation of the third row
	 *  @author Matt, Blake
	 */
	@Test
	public void testLeftShiftR3LastPlaced() //25
	{
		Board board = new Board();
		AbstractTile expected = board.getUnused();
		
		board.rotateRow(3, false);
		AbstractTile actual = board.getLastPlaced();
				
		assertEquals(expected, actual);
	}
	
	/**
	 * 	Ensures that the tiles on the board are in the proper locations after the unused
	 *  tile pushed the tiles in the fifth row to the left
	 *  @author Matt, Blake
	 */
	@Test
	public void testLeftShiftR5() //26
	{
		Board board = new Board();
		String expected = "";
		for (int i=1; i<7; i++)
			expected += board.tileAt(5,i);

		expected += board.getUnused() + (board.tileAt(5,0).hasToken() ? "." + board.tileAt(5,0).getToken() : "");		
		
		board.rotateRow(5, false);
		String actual = "";
		for (int i=0; i<7; i++)
			actual += board.tileAt(5,i);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * 	Ensures that the unused tile is the former leftmost tile in row 1 after 
	 *  the leftward rotation of the fifth row
	 *  @author Matt, Blake
	 */
	@Test
	public void testLeftShiftR5Unused() //27
	{
		Board board = new Board();
		AbstractTile expected = board.tileAt(5,0);
		
		board.rotateRow(5, false);
		AbstractTile actual = board.getUnused();
				
		assertTrue(expected == actual);
	}
	
	/**
	 * 	Ensures that the last placed tile is the former unused after 
	 *  the leftward rotation of the fifth row
	 *  @author Matt, Blake
	 */
	@Test
	public void testLeftShiftR5LastPlaced() //28
	{
		Board board = new Board();
		AbstractTile expected = board.getUnused();
		
		board.rotateRow(5, false);
		AbstractTile actual = board.getLastPlaced();
				
		assertEquals(expected, actual);
	}
	
	/** 
	 *  Ensures that move backtracking is not allowed for right then left rotation of the row 1
	 *  @author Matt
	 */
	@Test
	public void testR1RotateRLBacktrackingFalse() //29
	{
		Board board = new Board();
		board.rotateRow(1, true);
		assertFalse(board.rotateRow(1, false));
	}
	
	/** 
	 *  Ensures that move backtracking is not allowed for right then left rotation of the row 3
	 *  @author Matt
	 */
	@Test
	public void testR3RotateRLBacktrackingFalse() //30
	{
		Board board = new Board();
		board.rotateRow(3, true);
		assertFalse(board.rotateRow(3, false));
	}
	
	/** 
	 *  Ensures that move backtracking is not allowed for right then left rotation of the row 5
	 *  @author Matt
	 */
	@Test
	public void testR5RotateRLBacktrackingFalse() //31
	{
		Board board = new Board();
		board.rotateRow(5, true);
		assertFalse(board.rotateRow(5, false));
	}
	
	/** 
	 *  Ensures that move backtracking is not allowed for left then right rotation of the row 1
	 *  @author Matt
	 */
	@Test
	public void testR1RotateLRBacktrackingFalse() //32
	{
		Board board = new Board();
		board.rotateRow(1, false);
		assertFalse(board.rotateRow(1, true));
	}
	
	/** 
	 *  Ensures that move backtracking is not allowed for left then right rotation of the row 3
	 *  @author Matt
	 */
	@Test
	public void testR3RotateLRBacktrackingFalse() //33
	{
		Board board = new Board();
		board.rotateRow(3, false);
		assertFalse(board.rotateRow(3, true));
	}
	
	/** 
	 *  Ensures that move backtracking is not allowed for left then right rotation of the row 5
	 *  @author Matt
	 */
	@Test
	public void testR5RotateLRBacktrackingFalse() //34
	{
		Board board = new Board();
		board.rotateRow(5, false);
		assertFalse(board.rotateRow(5, true));
	}
	
	/**
	 * 	Ensures that the tiles on the board are in the proper locations after the unused
	 *  tile pushed the tiles in the first column downward
	 *  @author Matt, Blake
	 */
	@Test
	public void testDownShiftC1() //35
	{
		Board board = new Board();
		String expected = board.getUnused() + (board.tileAt(6,1).hasToken() ? "." + board.tileAt(6,1).getToken() : "");
		for (int i=0; i<6; i++)
			expected += board.tileAt(i,1);
		
		board.rotateColumn(1, true);
		String actual = "";
		for (int i=0; i<7; i++)
			actual += board.tileAt(i,1);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * 	Ensures that the unused tile is the former bottom most tile in column 1 after 
	 *  the downward rotation of the first column
	 *  @author Matt, Blake
	 */
	@Test
	public void testDownShiftC1Unused() //36
	{
		Board board = new Board();
		AbstractTile expected = board.tileAt(6,1);
		
		board.rotateColumn(1, true);
		AbstractTile actual = board.getUnused();

		assertTrue(expected == actual);
	}
	
	/**
	 * 	Ensures that the last placed tile is the former unused after 
	 *  the downward rotation of the first column
	 *  @author Matt
	 */
	@Test
	public void testDownShiftC1LastPlaced() //37
	{
		Board board = new Board();
		AbstractTile expected = board.getUnused();
		
		board.rotateColumn(1, true);
		AbstractTile actual = board.getLastPlaced();

		assertTrue(expected == actual);
	}
	
	/**
	 * Ensures that the second column cannot be rotated downward, as there are fixed tiles
	 * @author Matt
	 */
	@Test
	public void testDownShiftC0Illegal() //84
	{
		Board board = new Board();
		boolean expected = false;
		boolean actual = board.rotateColumn(0, true);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Ensures that the second column cannot be rotated downward, as there are fixed tiles
	 * @author Matt
	 */
	@Test
	public void testDownShiftC2Illegal() //38
	{
		Board board = new Board();
		boolean expected = false;
		boolean actual = board.rotateColumn(2, true);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Ensures that the second column cannot be rotated downward, as there are fixed tiles
	 * @author Matt
	 */
	@Test
	public void testDownShiftC4Illegal() //85
	{
		Board board = new Board();
		boolean expected = false;
		boolean actual = board.rotateColumn(4, true);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Ensures that the second column cannot be rotated downward, as there are fixed tiles
	 * @author Matt
	 */
	@Test
	public void testDownShiftC6Illegal() //86
	{
		Board board = new Board();
		boolean expected = false;
		boolean actual = board.rotateColumn(6, true);
		
		assertEquals(expected, actual);
	}

	
	/**
	 * 	Ensures that the tiles on the board are in the proper locations after the unused
	 *  tile pushed the tiles in the third column downward
	 *  @author Matt, Blake
	 */
	@Test
	public void testDownShiftC3() //39
	{
		Board board = new Board();
		String expected = board.getUnused() + (board.tileAt(6,3).hasToken() ? "." + board.tileAt(6,3).getToken() : "");
		for (int i=0; i<6; i++)
			expected += board.tileAt(i,3);
		
		board.rotateColumn(3, true);
		String actual = "";
		for (int i=0; i<7; i++)
			actual += board.tileAt(i,3);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * 	Ensures that the unused tile is the former bottom most tile in column 1 after 
	 *  the downward rotation of the third column
	 *  @author Matt, Blake
	 */
	@Test
	public void testDownShiftC3Unused() //40
	{
		Board board = new Board();
		AbstractTile expected = board.tileAt(6,3);
		
		board.rotateColumn(3, true);
		AbstractTile actual = board.getUnused();

		assertTrue(expected == actual);
	}
	
	/**
	 * 	Ensures that the last placed tile is the former unused after 
	 *  the downward rotation of the third column
	 *  @author Matt, Blake
	 */
	@Test
	public void testDownShiftC3LastPlaced() //41
	{
		Board board = new Board();
		AbstractTile expected = board.getUnused();
		
		board.rotateColumn(3, true);
		AbstractTile actual = board.getLastPlaced();
				
		assertTrue(expected == actual);
	}
	
	/**
	 * 	Ensures that the tiles on the board are in the proper locations after the unused
	 *  tile pushed the tiles in the fifth column downward
	 *  @author Matt, Blake
	 */
	@Test
	public void testDownShiftC5() //42
	{
		Board board = new Board();
		String expected = board.getUnused() + (board.tileAt(6,5).hasToken() ? "." + board.tileAt(6,5).getToken() : "");
		for (int i=0; i<6; i++)
			expected += board.tileAt(i,5);		
		
		board.rotateColumn(5, true);
		String actual = "";
		for (int i=0; i<7; i++)
			actual += board.tileAt(i,5);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * 	Ensures that the unused tile is the former bottom most tile in column 1 after 
	 *  the downward rotation of the fifth column
	 *  @author Matt, Blake
	 */
	@Test
	public void testDownShiftC5Unused() //43
	{ 
		Board board = new Board();
		AbstractTile expected = board.tileAt(6,5);
		
		board.rotateColumn(5, true);
		AbstractTile actual = board.getUnused();

		assertTrue(expected == actual);
	}
	
	/**
	 * 	Ensures that the last placed tile is the former unused after 
	 *  the downward rotation of the fifth column
	 *  @author Matt
	 */
	@Test
	public void testDownShiftC5LastPlaced() //44
	{
		Board board = new Board();
		AbstractTile expected = board.getUnused();
		
		board.rotateColumn(5, true);
		AbstractTile actual = board.getLastPlaced();

		assertTrue(expected == actual);
	}
	
	/**
	 *  Ensures that the tiles on the board are in the proper locations after the unused
	 *  tile pushed the tiles in the first column upward
	 *  @author Matt, Blake
	 */
	@Test
	public void testUpShiftC1() //45
	{
		Board board = new Board();
		String expected = "";
		for (int i=1; i<7; i++)
			expected += board.tileAt(i,1);

		expected += board.getUnused() + (board.tileAt(0,1).hasToken() ? "." + board.tileAt(0,1).getToken() : "");
		
		board.rotateColumn(1, false);
		String actual = "";
		for (int i=0; i<7; i++)
			actual += board.tileAt(i,1);
		
		assertEquals(expected, actual);
	}
	
	/**
	 *  Ensures that the unused tile is the former topmost tile in column 1 after 
	 *  the upward rotation of the first column
	 *  @author Matt, Blake
	 */
	@Test
	public void testUpShiftC1Unused() //46
	{
		Board board = new Board();
		AbstractTile expected = board.tileAt(0,1);
		
		board.rotateColumn(1, false);
		AbstractTile actual = board.getUnused();

		assertTrue(expected == actual);
	}
	
	/**
	 * 	Ensures that the last placed tile is the former unused after 
	 *  the upward rotation of the first column
	 *  @author Matt, Blake
	 */
	@Test
	public void testUpShiftC1LastPlaced() //47
	{
		Board board = new Board();
		AbstractTile expected = board.getUnused();
		
		board.rotateColumn(1, false);
		AbstractTile actual = board.getLastPlaced();
				
		assertTrue(expected == actual);
	}
	
	/**
	 * Ensures that the second column cannot be rotated upward, as there are fixed tiles
	 * @author Matt
	 */
	@Test
	public void testUpShiftC2Illegal() //48
	{
		Board board = new Board();
		boolean expected = false;
		boolean actual = board.rotateColumn(2, false);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * 	Ensures that the tiles on the board are in the proper locations after the unused
	 *  tile pushed the tiles in the third column upward
	 *  @author Matt, Blake
	 */
	@Test
	public void testUpShiftC3() //49
	{
		Board board = new Board();
		String expected = "";
		for (int i=1; i<7; i++)
			expected += board.tileAt(i,3);

		expected += board.getUnused() + (board.tileAt(0,3).hasToken() ? "." + board.tileAt(0,3).getToken() : "");
		
		board.rotateColumn(3, false);
		String actual = "";
		for (int i=0; i<7; i++)
			actual += board.tileAt(i,3);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * 	Ensures that the unused tile is the former topmost tile in column 1 after 
	 *  the upward rotation of the third column
	 *  @author Matt
	 */
	@Test
	public void testUpShiftC3Unused() //50
	{
		Board board = new Board();
		AbstractTile expected = board.tileAt(0,3);
		
		board.rotateColumn(3, false);
		AbstractTile actual = board.getUnused();
				
		assertTrue(expected == actual);
	}
	
	/**
	 * 	Ensures that the last placed tile is the former unused after 
	 *  the upward rotation of the third column
	 *  @author Matt
	 */
	@Test
	public void testUpShiftC3LastPlaced() //51
	{
		Board board = new Board();
		String expected = board.getUnused().toString();
		
		board.rotateColumn(3, false);
		String actual = board.getLastPlaced().toString();
				
		assertEquals(expected, actual);
	}
	
	/**
	 * 	Ensures that the tiles on the board are in the proper locations after the unused
	 *  tile pushed the tiles in the fifth column upward
	 *  @author Matt, Blake
	 */
	@Test
	public void testUpShiftC5() //52
	{
		Board board = new Board();
		String expected = "";
		for (int i=1; i<7; i++)
			expected += board.tileAt(i,5);

		expected += board.getUnused() + (board.tileAt(0,5).hasToken() ? "." + board.tileAt(0,5).getToken() : "");
		
		board.rotateColumn(5, false);
		String actual = "";
		for (int i=0; i<7; i++)
			actual += board.tileAt(i,5);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * 	Ensures that the unused tile is the former most tile in column 1 after 
	 *  the upward rotation of the fifth column
	 *  @author Matt
	 */
	@Test
	public void testUpShiftC5Unused() //53
	{
		Board board = new Board();
		AbstractTile expected = board.tileAt(0,5);
		
		board.rotateColumn(5, false);
		AbstractTile actual = board.getUnused();
				
		assertTrue(expected == actual);
	}
	
	/**
	 * 	Ensures that the last placed tile is the former unused after 
	 *  the upward rotation of the fifth column
	 *  @author Matt
	 */
	@Test
	public void testUpShiftC5LastPlaced() //54
	{
		Board board = new Board();
		String expected = board.getUnused().toString();
		
		board.rotateColumn(5, false);
		String actual = board.getLastPlaced().toString();
				
		assertEquals(expected, actual);
	}
	
	/** 
	 *  Ensures that move backtracking is not allowed for downward then upward rotation of the column 1
	 *  @author Matt
	 */
	@Test
	public void testC1RotateDUBacktrackingFalse() //55
	{
		Board board = new Board();
		board.rotateColumn(1, true);
		assertFalse(board.rotateColumn(1, false));
	}
	
	/** 
	 *  Ensures that move backtracking is not allowed for downward then upward rotation of the column 3
	 *  @author Matt
	 */
	@Test
	public void testC3RotateDUBacktrackingFalse() //56
	{
		Board board = new Board();
		board.rotateColumn(3, true);
		assertFalse(board.rotateColumn(3, false));
	}
	
	/** 
	 *  Ensures that move backtracking is not allowed for downward then upward rotation of the column 5
	 *  @author Matt
	 */
	@Test
	public void testC5RotateDUBacktrackingFalse() //57
	{
		Board board = new Board();
		board.rotateColumn(5, true);
		assertFalse(board.rotateColumn(5, false));
	}
	
	/** 
	 *  Ensures that move backtracking is not allowed for upward then downward rotation of the column 1
	 *  @author Matt
	 */
	@Test
	public void testC1RotateUDBacktrackingFalse() //58
	{
		Board board = new Board();
		board.rotateColumn(1, false);
		assertFalse(board.rotateColumn(1, true));
	}
	
	/** 
	 *  Ensures that move backtracking is not allowed for upward then downward rotation of the column 3
	 *  @author Matt
	 */
	@Test
	public void testC3RotateUDBacktrackingFalse() //59
	{
		Board board = new Board();
		board.rotateColumn(3, false);
		assertFalse(board.rotateColumn(3, true));
	}
	
	/** 
	 *  Ensures that move backtracking is not allowed for upward then downward rotation of the column 5
	 *  @author Matt
	 */
	@Test
	public void testC5RotateUDBacktrackingFalse() //60
	{
		Board board = new Board();
		board.rotateColumn(5, false);
		assertFalse(board.rotateColumn(5, true));
	}
	
	/**
	 * Uses the preconstructed board testboard1.mlb to test whether there is a valid path between
	 * tiles (5,2) and (5,3) (adjacent non-connected tiles)
	 * @author Matt
	 */
	@Test
	public void testBoard1Path01() //61
	{
		Board board = new Board(FileIO.load("testboard1.mlb"));
		AbstractTile t1 = board.tileAt(5,2);
		AbstractTile t2 = board.tileAt(5,3);
		boolean expected = false;
		boolean actual = board.getConnected(t1, null).contains(t2);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Uses the preconstructed board testboard1.mlb to test whether there is a valid path between
	 * tiles (0,0) and (0,1) (adjacent connected tiles)
	 * @author Matt
	 */
	@Test
	public void testBoard1Path02() //62
	{
		Board board = new Board(FileIO.load("testboard1.mlb"));
		AbstractTile t1 = board.tileAt(0,0);
		AbstractTile t2 = board.tileAt(0,1);
		boolean expected = true;
		boolean actual = board.getConnected(t1, null).contains(t2);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Uses the preconstructed board testboard1.mlb to test whether there is a valid path between
	 * tiles (0,0) and (0,5) (linearly-connected tiles)
	 * @author Matt
	 */
	@Test
	public void testBoard1Path03() //63
	{
		Board board = new Board(FileIO.load("testboard1.mlb"));
		AbstractTile t1 = board.tileAt(0,0);
		AbstractTile t2 = board.tileAt(0,5);
		boolean expected = true;
		boolean actual = board.getConnected(t1, null).contains(t2);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Uses the preconstructed board testboard1.mlb to test whether there is a valid path between
	 * tiles (0,0) and (1,0) (non-linear connected tiles)
	 * @author Matt
	 */
	@Test
	public void testBoard1Path04() //64
	{
		Board board = new Board(FileIO.load("testboard1.mlb"));
		AbstractTile t1 = board.tileAt(0,0);
		AbstractTile t2 = board.tileAt(1,0);
		boolean expected = true;
		boolean actual = board.getConnected(t1, null).contains(t2);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Uses the preconstructed board testboard1.mlb to test whether there is a valid path between
	 * tiles (5,0) and (2,6) (tiles with long, windy connection path)
	 * @author Matt
	 */
	@Test
	public void testBoard1Path05() //65
	{
		Board board = new Board(FileIO.load("testboard1.mlb"));
		AbstractTile t1 = board.tileAt(5,0);
		AbstractTile t2 = board.tileAt(1,6);
		boolean expected = true;
		boolean actual = board.getConnected(t1, null).contains(t2);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Uses the preconstructed board testboard1.mlb to test whether there is a valid path between
	 * tiles (1,1) and (4, 4) (tiles that aren't close to being connected)
	 * @author Matt
	 */
	@Test
	public void testBoard1Path06() //66
	{
		Board board = new Board(FileIO.load("testboard1.mlb"));
		AbstractTile t1 = board.tileAt(1,1);
		AbstractTile t2 = board.tileAt(4,4);
		boolean expected = false;
		boolean actual = board.getConnected(t1, null).contains(t2);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Uses the preconstructed board testboard2.mlb to test whether there is a valid path between
	 * tiles (0,1) and (1,1) (adjacent non-connected tiles)
	 * @author Matt
	 */
	@Test
	public void testBoard2Path01() //67
	{
		Board board = new Board(FileIO.load("testboard2.mlb"));
		AbstractTile t1 = board.tileAt(0,1);
		AbstractTile t2 = board.tileAt(1,1);
		boolean expected = false;
		boolean actual = board.getConnected(t1, null).contains(t2);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Uses the preconstructed board testboard2.mlb to test whether there is a valid path between
	 * tiles (0,0) and (0,1) (adjacent connected tiles)
	 * @author Matt
	 */
	@Test
	public void testBoard2Path02() //68
	{
		Board board = new Board(FileIO.load("testboard2.mlb"));
		AbstractTile t1 = board.tileAt(0,0);
		AbstractTile t2 = board.tileAt(0,1);
		boolean expected = true;
		boolean actual = board.getConnected(t1, null).contains(t2);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Uses the preconstructed board testboard2.mlb to test whether there is a valid path between
	 * tiles (0,0) and (0,2) (linearly-connected tiles)
	 * @author Matt
	 */
	@Test
	public void testBoard2Path03() //69
	{
		Board board = new Board(FileIO.load("testboard2.mlb"));
		AbstractTile t1 = board.tileAt(0,0);
		AbstractTile t2 = board.tileAt(0,2);
		boolean expected = true;
		boolean actual = board.getConnected(t1, null).contains(t2);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Uses the preconstructed board testboard2.mlb to test whether there is a valid path between
	 * tiles (1,2) and (4,5) (non-linear connected tiles)
	 * @author Matt
	 */
	@Test
	public void testBoard2Path04() //70
	{
		Board board = new Board(FileIO.load("testboard2.mlb"));
		AbstractTile t1 = board.tileAt(1,2);
		AbstractTile t2 = board.tileAt(4,5);
		boolean expected = true;
		boolean actual = board.getConnected(t1, null).contains(t2);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Uses the preconstructed board testboard2.mlb to ensure that a valid path can loop back onto itself
	 * using tile (1,5) as a reference (while this is a valid path, the movePlayer method will not allow for 
	 * a tile to be picked up after it
	 * @author Matt
	 */
	@Test
	public void testBoard2Path05() //71
	{
		Board board = new Board(FileIO.load("testboard2.mlb"));
		AbstractTile t1 = board.tileAt(1,5);
		AbstractTile t2 = board.tileAt(1,5);
		boolean expected = true;
		boolean actual = board.getConnected(t1, null).contains(t2);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Uses the preconstructed board testboard2.mlb to test whether there is a valid path between
	 * tiles (2,2) and (4, 4) (tiles that aren't close to being connected)
	 * @author Matt
	 */
	@Test
	public void testBoard2Path06() //72
	{
		Board board = new Board(FileIO.load("testboard2.mlb"));
		AbstractTile t1 = board.tileAt(2,2);
		AbstractTile t2 = board.tileAt(4,4);
		boolean expected = false;
		boolean actual = board.getConnected(t1, null).contains(t2);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Uses the preconstructed board testboard3.mlb to test whether there is a valid path between
	 * tiles (0,0) and (0,1) (adjacent non-connected tiles)
	 * @author Matt
	 */
	@Test
	public void testBoard3Path01() //73
	{
		Board board = new Board(FileIO.load("testboard3.mlb"));
		AbstractTile t1 = board.tileAt(0,0);
		AbstractTile t2 = board.tileAt(0,1);
		boolean expected = false;
		boolean actual = board.getConnected(t1, null).contains(t2);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Uses the preconstructed board testboard3.mlb to test whether there is a valid path between
	 * tiles (0,0) and (1,0) (adjacent connected tiles)
	 * @author Matt
	 */
	@Test
	public void testBoard3Path02() //74
	{
		Board board = new Board(FileIO.load("testboard3.mlb"));
		AbstractTile t1 = board.tileAt(0,0);
		AbstractTile t2 = board.tileAt(1,0);
		boolean expected = true;
		boolean actual = board.getConnected(t1, null).contains(t2);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Uses the preconstructed board testboard3.mlb to test whether there is a valid path between
	 * tiles (0,0) and (5,0) (linearly-connected tiles)
	 * @author Matt
	 */
	@Test
	public void testBoard3Path03() //75
	{
		Board board = new Board(FileIO.load("testboard3.mlb"));
		AbstractTile t1 = board.tileAt(0,0);
		AbstractTile t2 = board.tileAt(5,0);
		boolean expected = true;
		boolean actual = board.getConnected(t1, null).contains(t2);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Uses the preconstructed board testboard3.mlb to test whether there is a valid path between
	 * tiles (0,1) and (0,2) (non-linear connected tiles)
	 * @author Matt
	 */
	@Test
	public void testBoard3Path04() //76
	{
		Board board = new Board(FileIO.load("testboard3.mlb"));
		AbstractTile t1 = board.tileAt(0,1);
		AbstractTile t2 = board.tileAt(0,2);
		boolean expected = true;
		boolean actual = board.getConnected(t1, null).contains(t2);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * Uses the preconstructed board testboard3.mlb to test whether there is a valid path between
	 * tiles (2,3) and (0,6) (tiles with long, windy connection path)
	 * @author Matt
	 */
	@Test
	public void testBoard3Path05() //77
	{
		Board board = new Board(FileIO.load("testboard3.mlb"));
		AbstractTile t1 = board.tileAt(2,3);
		AbstractTile t2 = board.tileAt(0,6);
		boolean expected = true;
		boolean actual = board.getConnected(t1, null).contains(t2);
		assertEquals(expected, actual);
	}
	
	/**
	 * Uses the preconstructed board testboard3.mlb to test whether there is a valid path between
	 * tiles (5,3) and (2,4) (tiles that aren't close to being connected)
	 * @author Matt
	 */
	@Test
	public void testBoard3Path06() //78
	{
		Board board = new Board(FileIO.load("testboard3.mlb"));
		AbstractTile t1 = board.tileAt(5,3);
		AbstractTile t2 = board.tileAt(2,4);
		boolean expected = false;
		boolean actual = board.getConnected(t1, null).contains(t2);
		
		assertEquals(expected, actual);
	}
	
	/**
	 * This method tests whether the same result is returned by the isValidPathBetween method
	 * regardless of the order of the inputs, allows for many calls by one JUnit test
	 * @return whether results are the same
	 * @author Matt
	 */
	private boolean sameResultWithDifferentOrder()
	{
		Board board = new Board();
		int x1 = (int)(Math.random() * 7);
		int y1 = (int)(Math.random() * 7);
		int x2 = (int)(Math.random() * 7);
		int y2 = (int)(Math.random() * 7);
		boolean b1 = board.getConnected(board.tileAt(x1,y1), null).contains(board.tileAt(x2,y2));
		boolean b2 = board.getConnected(board.tileAt(x2,y2), null).contains(board.tileAt(x1,y1));
		return !(b1^b2);
	}
	
	/**
	 * Ensures that isValidPath works the same, regardless of the order of the inputs
	 * Repeated 100 times to reduce chance of random correct result
	 * @author Matt
	 */
	@Test
	public void testOrderUnimportant() //79
	{
		boolean b = true;
		for (int i=0; i<100; i++)
			b &= sameResultWithDifferentOrder();
		assertTrue(b);
	}
	
	/**
	 *	This method returns whether a randomly selected tile from a random board has 
	 *	a valid path to itself, allows for many calls by one JUnit test
	 *	@return whether path to itself is valid
	 *  @author Matt
	 */
	private boolean validPathToItself()
	{
		Board board = new Board();
		int x = (int)(Math.random() * 7);
		int y = (int)(Math.random() * 7);
		return board.getConnected(board.tileAt(x,y), null).contains(board.tileAt(x,y));
	}
	
	/**
	 * Ensures that isValidPath always returns true for a path starting and ending on the same tile 
	 * Repeated 100 times to reduce chance of random correct result
	 * @author Matt
	 */
	@Test
	public void testPathToSelfOkay() //80
	{
		boolean b = true;
		for (int i=0; i<100; i++)
			b &= validPathToItself();
		assertTrue(b);
	}
}