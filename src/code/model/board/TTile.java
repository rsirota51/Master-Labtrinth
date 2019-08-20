package code.model.board;

public class TTile extends AbstractTile 
{
	/**
	 * {@inheritDoc}
	 */
	public TTile()
	{
		super();
	}
	/**
	 * {@inheritDoc}
	 * @param rotations
	 */
	public TTile(int rotations)
	{
		super(rotations);
	}
	/**
	 * Initializes the connections array for the "T" Tile
	 */
	@Override
	public boolean[] initializeConnections() 
	{
		return new boolean[] {false, true, true, true};
	}
}
