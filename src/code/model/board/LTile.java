package code.model.board;

public class LTile extends AbstractTile 
{
	/**
	 * {@inheritDoc}
	 */
	public LTile()
	{
		super();
	}
	/**
	 * {@inheritDoc}
	 * @param rotations
	 */
	public LTile(int rotations)
	{
		super(rotations);
	}
	/**
	 * Initializes the connections array for the "L" Tile
	 */
	@Override
	public boolean[] initializeConnections() 
	{
		return new boolean[] {true, true, false, false};
	}
}
