package code.model.board;

public class ITile extends AbstractTile 
{
	/**
	 * {@inheritDoc}
	 */
	public ITile()
	{
		super();
	}
	/**
	 * {@inheritDoc}
	 * @param rotations
	 */
	public ITile(int rotations)
	{
		super(rotations);
	}
	/**
	 * Initializes the connections array for the "I" Tile
	 */
	@Override
	public boolean[] initializeConnections() 
	{
		return new boolean[] {true, false, true, false};
	}
}
