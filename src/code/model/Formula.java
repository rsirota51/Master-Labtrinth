package code.model;

/**
 * An enumeration of an object representing the formula card which is held by each of the player. This holds
 * all of the possible cards in the game, as well as the methods to access the concents of the formula, as
 * well as to pass a reference to the formula card to a player. 
 * 
 * @author Matthew, Robert
 */
public enum Formula {

	CARD1(8, 19, 5, 1),
	CARD2(18, 11, 19, 2),
	CARD3(3, 18, 1, 3),
	CARD4(1, 10, 13, 4),
	CARD5(10, 12, 16, 5),
	CARD6(6, 14, 8, 6),
	CARD7(20, 17, 3, 7),
	CARD8(5, 25, 18, 8),
	CARD9(9, 20, 11, 9),
	CARD10(4, 15, 20, 10),
	CARD11(12, 1, 9, 11),
	CARD12(14, 4, 10, 12),
	CARD13(19, 7, 15, 13),
	CARD14(25, 16, 2, 14),
	CARD15(13, 15, 12, 15),
	CARD16(11, 3, 14, 16),
	CARD17(2, 8, 17, 17),
	CARD18(16, 9, 7, 18),
	CARD19(7, 6, 25, 19),
	CARD20(17, 5, 6, 20),
	CARD21(15, 2, 4, 21);
	
	/**
	 * Variables representing the token values that are a part of the formula
	 */
	private final int _t1, _t2, _t3;
	/**
	 * The sequential, numerical code which references this Formula
	 */
	private final int _code;
	/**
	 * Whether this formula card is currently in use by a player
	 */
	private boolean _inUse;
	
	/**
	 * The constructor for the Formula object
	 * @param t1 The value of the token which is the first ingredient in the formula
	 * @param t2 The value of the token which is the second ingredient in the formula
	 * @param t3 The value of the token which is the third ingredient in the formula
	 * @param code The numerical code associated with this formula
	 * @author Matt, Robert
	 */
	private Formula(int t1, int t2, int t3, int code)
	{
		_t1 = t1;
		_t2 = t2;
		_t3 = t3;
		_code = code;
		_inUse = false;
	}
	/**
	 * Determines whether the given token value is contained within this formula
	 * @param value The value of the the token
	 * @return whether the value is in the formula
	 * @author 
	 */
	public boolean contains(int value)
	{
		return false; //TODO
	}
	/**
	 * Returns a random, not currently in use, formula object
	 * @return formula object
	 * @author 
	 */
	public static Formula getFormula()
	{
		return null; //TODO
	}
}
