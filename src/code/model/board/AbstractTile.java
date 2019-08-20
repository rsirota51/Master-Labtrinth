package code.model.board;

import java.util.Arrays;

import code.model.Token;

/**
 * A representation of one square of the Master Labyrinth board. The tiles contains a boolean array which 
 * represents the tile's connections with the adjacent tiles on the board, as well as a container for a token
 * object contained on the tile.
 * 
 * @author Matthew, Blake  
 */
public abstract class AbstractTile 
{
	/**
	 * Indicates whether tile is reachable given the current player and board state
	 */
	private boolean _reachable;
	/**
	 * Constant that holds the default connections of the tile
	 */
	protected final boolean[] DEFAULT = initializeConnections();
	/**
	 * Holds the connections of a tile, in order 0 = NORTH, 1 = EAST, 2 = SOUTH, 3 = WEST
	 */
	protected boolean[] _connections;
	/**
	 * Holds the token object found on this tile
	 */
	private Token _token;
	/**
	 * The constructor for the Tile object
	 * @param connections the connections of the tile, one of the default static variables of this class
	 * @param rotation the orientation of this tile, representing number of 90-degree clockwise rotations from default
	 * @author Blake, Matt
	 */
	public AbstractTile() 
	{
		this(0);
	}
	/**
	 * The constructor for the Tile object
	 * @param connections the connections of the tile, one of the default static variables of this class
	 * @param rotation the orientation of this tile, representing number of 90-degree clockwise rotations from default
	 * @author Blake, Matt
	 */
	public AbstractTile(int rotation) 
	{
		_connections = initializeConnections();
		rotate(rotation);
		setToken(null);
	}
	/**
	 * Initializes the connections array of the tile
	 */
	public abstract boolean[] initializeConnections();
	/**
	 * Rotates the tile, specifically the positions of the booleans in the connections array 
	 * @param rotation number of 90-degree clockwise rotations
	 * @author Matt
	 */
	public void rotate(int rotation)
	{
		for (int i=0; i<rotation; i++)
		{
			boolean temp = _connections[0];
			_connections[0] = _connections[3];
			_connections[3] = _connections[2];
			_connections[2] = _connections[1];
			_connections[1] = temp;
		}
	}
	/**
	 * Returns the connections array associated with this tile
	 * @return connections array
	 * @author Blake
	 */
	public boolean[] getConnections()
	{
		return _connections;
	}
	/**
	 * Removes the token from the tile and returns its reference
	 * @return a reference to the token contained on the tile
	 * @author Blake
	 */
	public Token removeToken() {
		Token output = _token;
		_token = null;
		return output;
	}
	/**
	 * Sets the token object contained on the tile
	 * @param token the token object contained on the tile
	 * @author Blake, Matt
	 */
	public void setToken(Token token) 
	{
		_token = token;
	}
	/**
	 * Resets the reachable parameter for this tile, determining its state
	 */
	public void setReachable(boolean reachable)
	{
		_reachable = reachable;
	}
	/**
	 *Accessor for the reachable parameter
	 *@return whether the tile is reachable by the current player
	 */
	public boolean isReachable()
	{
		return _reachable;
	}
	/**
	 * Gets the token without removing it
	 * @return Token
	 * @author Steven
	 */
	public Token getToken(){
		return _token;
	}
	/**
	 * Returns whether the given tile has a token
	 * @return if the given tile has a token
	 * @author Blake, Matt
	 */
	public boolean hasToken()
	{
		return _token != null;
	}	
	/**
	 * Returns a two character string representing the tile.
	 *  "T0" represents a 'T' Tile in its default rotation.
	 *  "I1" represents a 'I" Tile rotated once clockwise.
	 *  "L2" represents an 'L' Tile rotated twice clockwise.
	 *  @author Joe
	 */
	@Override public String toString()
	{
		AbstractTile t = null;
		try {
			t = this.getClass().newInstance();
		} catch (Exception e) {System.out.println(e);	} 
		
		int rotations = 0;
		while (!equals(t))
		{			t.rotate(1);
			rotations++;
		}

		return "" + getClass().getSimpleName().charAt(0) + rotations + (hasToken() ? "." + _token.toString() : "");		
	}
	/**
	 * TODO
	 * @param t
	 * @return
	 * @author Blake, Matt
	 */
	@Override public boolean equals(Object o)
	{
		if (!(o instanceof AbstractTile))
			return false;
		else
		{
			AbstractTile t = (AbstractTile) o;
			return Arrays.equals(getConnections(), t.getConnections());
		}
	}
}