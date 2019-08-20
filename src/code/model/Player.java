package code.model;

import java.util.ArrayList;

/**
 * Represents a player in the game, including a list of tokens collected, a formula card, a number of wands,
 * and the current location on the board 
 * 
 * @author Steven, Blake
 */
public class Player
{
	/**
	 * Holds all the tokens the player has
	 */
	private ArrayList<Token> _tokens;
	/**
	 * Holds the Formula card of the player
	 */
	private Formula _formula;
	/**
	 * Holds the number of wands the player has
	 */
	private int _numberOfWands = 3;
	/**
	 * Holds the player's name
	 */
	private final String _name;
	/**
	 * Holds the phase of the player's turn: 0-other player's turn, 1-shifting time, 2-moving time
	 */
	private int _phase;
	/**
	 * Holds the player's index in the Game
	 */
	private int _index;
	/**
	 * Constructor for the Player class, sets player name, starting score and Formula card.
	 * @param name
	 * @author Blake
	 */
	public Player(String name, int index)
	{
		_name = name;
		_index = index;
		_formula = Formula.getFormula();
		_tokens = new ArrayList<Token>();
		_phase = 0;
	}
	
	/**
	 * Adds specified token to the ArrayList of Tokens
	 * @param token
	 * @author Steven
	 */
	public void takeToken(Token token)
	{
		_tokens.add(token);
	}
	/**
	 * Gets the players name
	 * @author Steven
	 */
	public String getName(){
		return _name;
	}
	/**
	 *TODO
	 */
	public void advanceTurn()
	{
		_phase = (_phase + 1)%3;
	}
	public int getPhase()
	{
		return _phase;
	}
	@Override
	public String toString()
	{
		return getName(); //TODO: Add score, formula card, etc.
	}

	public int getIndex() 
	{
		return _index;
	}
	
	public int getScore()
	{
		int score = 0;
		for (Token t : _tokens)
			score += t.getValue();
		return score;
	}
	
}