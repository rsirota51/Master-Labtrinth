package code.model;

/**
 * This class represents the numerical token object which is placed on one of the tiles. The token has
 * a value, as well as a name (useful later for user-interface)
 * 
 * @author Blake
 */
public class Token {
	
	/**
	 * Value of the token
	 */
	private final int _value;
	/**
	 * The constructor for the token object 
	 * @param value the value contained by the token
	 * @param name the name of the token
	 * @author Blake
	 */
	public Token(int value)
	{
		_value = value;
	}
	/**
	 * Returns the value stored in the token
	 * @return the value stored in the token
	 * @author Blake
	 */
	public int getValue()
	{
		return _value;
	}
	/**
	 * Returns the value of the tile as a String of length 2
	 * @return the String representation of the Token
	 * @author Blake
	 */
	@Override public String toString()
	{
		return String.format("%02d", _value);
	}

	

}
