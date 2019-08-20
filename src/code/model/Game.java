package code.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import code.model.board.AbstractTile;
import code.model.board.Board;

/**
 * The parent object of the project, containing the board and a list of the players.
 * Record keeping for turn numbers, tokens, and turn control
 * @author  Matt
 */ 
public class Game {

	/**
	 * The number of each type of Tile in the game, listed in the order L, I, T.
	 */
	public static final int[] NUMBER_OF_TILES = {15, 13, 6};
	/**
	 * The board object associated with this game
	 */
	private Board _board;
	/**
	 * A list of the players who are playing the game, specified by the command line arguments
	 */
	private HashMap<Player, AbstractTile> _playerMap = new HashMap<Player, AbstractTile>(); 
	/**
	 * The current turn number
	 */
	private int _turn = 0;
	/**
	 * The value of the next token to be picked up.
	 */
	private int _nextToken = 1;
	/**
	 * The constructor for the game class, instantiates the board and the players
	 * @param args The list of player names
	 */
	public Game(String[] args)
	{
		if (args.length < 2 || args.length > 4)
			throw new IllegalArgumentException("The game must have 2-4 players.");
		
		_board = new Board();
		
		for (int i=0; i<args.length; i++)
			_playerMap.put(new Player(args[i], i), _board.tileAt(i/2*2+2, i%2*2+2));
		
		getCurrentPlayer().advanceTurn();
		_board.resetReachable(_playerMap.get(getCurrentPlayer()));
	}
	public Game(String board){
		_board = new Board(board);
	}
	/**
	 * Returns the Board associated with this instance of the Game.
	 * @return Board
	 */
	public Board getBoard()
	{
		return _board;
	}
	public int getNextToken()
	{
		return _nextToken;
	}
	/**
	 * Returns the player who is currently taking their turn
	 * @return current player
	 */
	public Player getCurrentPlayer()
	{
		for (Player p: _playerMap.keySet())
			if (p.getIndex() == (_turn)%_playerMap.keySet().size())
				return p;
		return null;
	}
	/**
	 * Returns all the players in the game
	 * @return all players
	 */
	public List<Player> getPlayers()
	{
		ArrayList<Player> players = new ArrayList<Player>(4);
		
		for (int i=0; i<_playerMap.size(); i++)	
			for (Player p : _playerMap.keySet())
				if (p.getIndex() == i)
					players.add(p);
		
		return players;
	}
	/**
	 * Moves any players or tokens that were pushed off the board to their proper location,
	 * then advances the turn sequence.
	 * @author Matt, Blake
	 */
	public void afterBoardShift()
	{
		_board.getLastPlaced().setToken(_board.getUnused().removeToken());
		for (Player p : _playerMap.keySet())//_players)
			if (_playerMap.get(p) == _board.getUnused())
				_playerMap.put(p, _board.getLastPlaced());
		advanceTurn();
	}
	/**
	 * Sets the location of the player to the tile they wish to move to.
	 * @param to - the location to move to.
	 * @return Whether they land on the tile containing the next token to be picked up.
	 * @author Blake, Matt
	 */
	public boolean movePlayer(Point to)
	{
		if (_playerMap.get(getCurrentPlayer()) == getBoard().tileAt(to))
			return false;
		_playerMap.put(getCurrentPlayer(), _board.tileAt(to));
		
		return _board.tileAt(to).hasToken() && _board.tileAt(to).getToken().getValue() == _nextToken;
	}
	/**
	 * Advances the turn of the turn of the current player and advances control to the next player when a 
	 * turn is finished
	 * @author Matt
	 */
	public void advanceTurn()
	{
		getCurrentPlayer().advanceTurn();
		if (getCurrentPlayer().getPhase() == 0)
		{
			_turn = (_turn + 1)%_playerMap.keySet().size();
			getCurrentPlayer().advanceTurn();
		}

		_board.resetReachable(_playerMap.get(getCurrentPlayer()));
	}
	/**
	 * Moves the lowest indexed token from the tile to the control of the current player
	 * Updates the next token to be the numerically next one
	 */
	public void removeToken()
	{
		getCurrentPlayer().takeToken(getCurrentLoc().removeToken());
		if (_nextToken == 25)
			endGame();
		_nextToken++;
		if (_nextToken == 21)
			_nextToken = 25;
	}
	/**
	 * Returns the location of the current player
	 * @return current player location
	 */
	public AbstractTile getCurrentLoc()
	{
		return _playerMap.get(getCurrentPlayer());
	}
	/**
	 * Returns the map which maps the players to their locations
	 * @return
	 */
	public HashMap<Player, AbstractTile> getMap()
	{
		return _playerMap;
	}
	/**
	 * Returns a String representation of this game
	 */
	@Override
	public String toString()
	{
		String players = "";
		Point loc;
		for (Player p : _playerMap.keySet())
		{
			loc = new Point(); //TODO
			players += p.getName() + " " + loc.x + "," + loc.y + System.lineSeparator();
		}
		return _board.toString() + System.lineSeparator() + players;
	}
	
	public void endGame()
	{
		System.out.println("Game Over!");
		int topScore = 0;
		Player winningPlayer = null;
		for (Player p : _playerMap.keySet())
			if (p.getScore() > topScore) {
				topScore = p.getScore();
				winningPlayer = p;
			}
		System.out.printf("Winning player: %s%sHigh Score: %s", winningPlayer, System.lineSeparator(), topScore);
		System.exit(0);
	}
}
