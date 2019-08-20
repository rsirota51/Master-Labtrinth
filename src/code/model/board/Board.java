package code.model.board;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import code.model.Game;
import code.model.Token;

/**
 * Represents a grid of Tiles
 * 
 * @author Matthew, Blake, Robert
 */
public class Board
{
	/**
	 * Constant list containing the preset Tiles of the board
	 */
	public static final List<AbstractTile> GIVEN_TILES = givenTiles();

	/**
	 * Random object used for randomizing the board
	 */
	private static final Random RANDOM = new Random();

	/**
	 * Stores all the Tiles in the game.
	 */
	private AbstractTile[][] _board = new AbstractTile[7][7];

	/**
	 * Holds the key value of the Tile which is currently not in the board
	 */
	private AbstractTile _unusedTile;

	/**
	 * Holds the key value of the Tiles which was the last one to enter the
	 * board (the previous _unusedTile)
	 */
	private AbstractTile _lastPlaced;
	/**
	 * Constructs a new Board
	 * 
	 * @author Blake, Matt, Steven
	 */
	public Board()
	{
		ArrayList<Token> tokens = new ArrayList<Token>();
		for(int i = 1; i <= 20; i++)
			tokens.add(new Token(i));
		tokens.add(new Token(25));
		
		ArrayList<AbstractTile> remainingTiles = populateRemainingTiles();
		
		for (int i = 0; i < 7; i++)
		{
			for (int j = 0; j < 7; j++)
			{
				if (i % 2 == 0 && j % 2 == 0)
					_board[i][j] = GIVEN_TILES.get(4 * (i / 2) + j / 2);
				else
				{
					_board[i][j] = remainingTiles.remove(RANDOM.nextInt(remainingTiles.size()));
				}
				if (i != 0 && j != 0 && i != 6 && j != 6){
					if(i%2 != 0){
						_board[i][j].setToken(tokens.remove(RANDOM.nextInt(tokens.size())));
					}
					else if(i%2 == 0 && j%2 != 0) {
						_board[i][j].setToken(tokens.remove(RANDOM.nextInt(tokens.size())));
					}
				}
			}
		}
		_unusedTile = remainingTiles.remove(0);
		
	}

	/**
	 * Constructs a Board from a String
	 * 
	 * @param input
	 *            the String representing the Board
	 * @author Blake, Steven
	 */
	public Board(String input)
	{
		ArrayList<String> split = new ArrayList<String>(
				Arrays.asList(input.split("( |" + System.lineSeparator() + ")+")));

		String u = split.remove(split.size()-1);
		switch (u.charAt(0))
		{
		case ('L'):
			_unusedTile = new LTile(u.charAt(1) - '0');
			break;
		case ('I'):
			_unusedTile = new ITile(u.charAt(1) - '0');
			break;
		case ('T'):
			_unusedTile = new TTile(u.charAt(1) - '0');
		}

		int i = 0, j = 0;
		String[] tileSplit;
		for (String s : split)
		{
			tileSplit = s.split("\\.");
			switch (s.charAt(0))
			{
			case ('L'):
				_board[i][j] = new LTile(s.charAt(1) - '0');
				break;
			case ('I'):
				_board[i][j] = new ITile(s.charAt(1) - '0');
				break;
			case ('T'):
				_board[i][j] = new TTile(s.charAt(1) - '0');
			}
			if(tileSplit.length >= 2){
				_board[i][j].setToken(new Token(Integer.valueOf(tileSplit[1])));
			}
			j = (j + 1) % 7;
			if (j == 0)
				i++;
		}
	}

	/**
	 * Populates the ArrayList containing the Tiles that are not preset in the
	 * board remaining Tiles ArrayList
	 * 
	 * @return ArrayList
	 * @author Blake, Matt
	 */
	private ArrayList<AbstractTile> populateRemainingTiles()
	{
		ArrayList<AbstractTile> remainingTiles = new ArrayList<AbstractTile>(35);
		for (int i = 0; i < Game.NUMBER_OF_TILES[0]; i++)
			remainingTiles.add(new LTile(RANDOM.nextInt(4)));
		for (int i = 0; i < Game.NUMBER_OF_TILES[1]; i++)
			remainingTiles.add(new ITile(RANDOM.nextInt(2)));
		for (int i = 0; i < Game.NUMBER_OF_TILES[2]; i++)
			remainingTiles.add(new TTile(RANDOM.nextInt(4)));
		return remainingTiles;
	}

	/**
	 * Slides the unusedTile into the board and removes the Tile that was slid
	 * off the board
	 * 
	 * @param index,
	 *            index of the row to rotate
	 * @param reverse,
	 *            boolean telling which side of the row the Tile is being added
	 * @return boolean, boolean telling whether it is a valid Tile placement or
	 *         not. Left to Right Normal Down to Up Normal
	 * @author Robert
	 */
	public boolean rotateRow(int index, boolean normal)
	{
		if (index % 2 == 0)
			return false;

		if (normal)
		{
			if (_board[index][6] == _lastPlaced)
				return false;

			_lastPlaced = _unusedTile;
			_unusedTile = _board[index][6];
			for (int x = 6; x > 0; x--)
			{
				_board[index][x] = _board[index][x - 1];
			}
			_board[index][0] = _lastPlaced;
		} else
		{
			if (_board[index][0] == _lastPlaced)
				return false;

			_lastPlaced = _unusedTile;
			_unusedTile = _board[index][0];
			for (int x = 0; x < 6; x++)
			{
				_board[index][x] = _board[index][x + 1];
			}
			_board[index][6] = _lastPlaced;
		}
		return true;
	}

	/**
	 * Slides the unusedTile into the board and removes the Tile that was slid
	 * off the board
	 * 
	 * @param index,
	 *            index of the column to rotate
	 * @param reverse,
	 *            boolean telling which side of the column the Tile is being
	 *            added.
	 * @return boolean, boolean telling whether it is a valid Tile placement or
	 *         not Left to Right Normal Down to Up Normal
	 * @author Robert
	 */
	public boolean rotateColumn(int index, boolean normal)
	{
		if (index % 2 == 0)
			return false;

		if (normal)
		{
			if (_board[6][index] == _lastPlaced)
				return false;

			_lastPlaced = _unusedTile;
			_unusedTile = _board[6][index];
			for (int x = 6; x > 0; x--)
			{
				_board[x][index] = _board[x - 1][index];
			}
			_board[0][index] = _lastPlaced;
		} else
		{
			if (_board[0][index] == _lastPlaced)
				return false;

			_lastPlaced = _unusedTile;
			_unusedTile = _board[0][index];
			for (int x = 0; x < 6; x++)
			{
				_board[x][index] = _board[x + 1][index];
			}
			_board[6][index] = _lastPlaced;
		}
		return true;
	}
	
	/**
	 * Resets the state (reachability) of each of the tiles on the board, using 
	 * the recursive pathfinding algorithm with thhe passed in tile representing 
	 * the location of the current player
	 * @author Matt
	 * @param from - The location on which the current player resides
	 */
	public void resetReachable(AbstractTile from)
	{
		for (AbstractTile[] e: _board)
			for (AbstractTile f: e)
				f.setReachable(false);
		for (AbstractTile g: getConnected(from, null))
			g.setReachable(true);
		_unusedTile.setReachable(false);
	}
	
	/**
	 * Returns a HashSet which contains all of the tiles connected to a passed
	 * in tile, including itself
	 * 
	 * @param from - the tile from which to build the connections array
	 * @param group - the tiles already found to be connected to the tile
	 * @return the set of connected tiles
	 * @author Blake, Matt
	 */
	public HashSet<AbstractTile> getConnected(AbstractTile from, HashSet<AbstractTile> group)
	{
		if (group == null)
		{
			group = new HashSet<AbstractTile>();
			group.add(from);
		}

		Point loc = loc(from);
		ArrayList<AbstractTile> toCheck = new ArrayList<AbstractTile>(4);
		toCheck.add(tileAt(loc.x - 1, loc.y));
		toCheck.add(tileAt(loc.x + 1, loc.y));
		toCheck.add(tileAt(loc.x, loc.y - 1));
		toCheck.add(tileAt(loc.x, loc.y + 1));

		for (AbstractTile t : toCheck)
			if (t != null && connects(from, t) && group.add(t))
				group = getConnected(t, group);
		
		return group;
	}

	/**
	 * Gets the location of a Tile by reference
	 * 
	 * @param t
	 *            the Tile to be found
	 * @return the location of the Tile
	 */
	public Point loc(AbstractTile t)
	{
		for (int i = 0; i < 7; i++)
			for (int j = 0; j < 7; j++)
				if (t == _board[i][j])
					return new Point(i, j);
		return null;
	}

	/**
	 * Gets the Tile given a location
	 * 
	 * @param x
	 *            the x-coordinate of the location
	 * @param y
	 *            the y-coordinate of the location
	 * @return the Tile at the given location
	 */
	public AbstractTile tileAt(int x, int y)
	{
		if (x < 0 || x > 6 || y < 0 || y > 6)
			return null;
		return _board[x][y];
	}

	/**
	 * Gets the Tile given an index
	 * 
	 * @return the Tile at the given index
	 */
	public AbstractTile tileAt(Point p)
	{
		return tileAt(p.x, p.y);
	}

	/**
	 * Checks whether two Tiles are directly connected
	 * 
	 * @param t1
	 *            the first Tile
	 * @param t2
	 *            the second Tile
	 * @return a boolean that tells whether the two Tiles are connected
	 */
	public boolean connects(AbstractTile t1, AbstractTile t2)
	{
		Point p1 = loc(t1);
		Point p2 = loc(t2);
		Point diff = new Point(p2.x - p1.x, p2.y - p1.y);
		if (Math.abs(diff.x) + Math.abs(diff.y) != 1)
			return false;
		if (diff.x == -1)
			return t1.getConnections()[0] && t2.getConnections()[2];	// t2 is north of t1
		else if (diff.y == 1)
			return t1.getConnections()[1] && t2.getConnections()[3];	// t2 is east of t1
		else if (diff.x == 1)
			return t1.getConnections()[2] && t2.getConnections()[0];	// t2 is south of t1
		else
			return t1.getConnections()[3] && t2.getConnections()[1];	// t2 is west of t1
	}

	/**
	 * Overrides toString method and formats our board in the String
	 * representation that we want.
	 * 
	 * @return String representation of the board
	 * @author Blake
	 */
	@Override
	public String toString()
	{
		String output = "";
		for (AbstractTile[] tiles : _board)
		{
			for (AbstractTile tile : tiles)
				output += tile.toString() + " ";
			output += System.lineSeparator();
		}
		return output + _unusedTile.toString();
	}

	/**
	 * Builds the array of pre-set Tiles
	 * 
	 * @return the list of pre-set Tiles, ordered from top-left to bottom-right
	 * @author Matt
	 */
	public static List<AbstractTile> givenTiles()
	{
		List<AbstractTile> output = new ArrayList<AbstractTile>(16);
		output.add(new LTile(1));
		output.add(new TTile(0));
		output.add(new TTile(0));
		output.add(new LTile(2));
		output.add(new TTile(3));
		output.add(new TTile(3));
		output.add(new TTile(0));
		output.add(new TTile(1));
		output.add(new TTile(3));
		output.add(new TTile(2));
		output.add(new TTile(1));
		output.add(new TTile(1));
		output.add(new LTile(0));
		output.add(new TTile(2));
		output.add(new TTile(2));
		output.add(new LTile(3));
		return output;
	}

	/**
	 * Returns the Tile which is not currently in the board
	 * 
	 * @return unused Tile
	 */
	public AbstractTile getUnused()
	{
		return _unusedTile;
	}

	/**
	 * Returns the Tile which was the last to be placed in the board
	 * 
	 * @return last-placed Tile
	 */
	public AbstractTile getLastPlaced()
	{
		return _lastPlaced;
	}
}
