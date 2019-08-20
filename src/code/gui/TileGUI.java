package code.gui;

import static code.gui.PanelFactory.getPanel;
import static java.awt.Color.BLACK;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JPanel;

import code.model.Player;
import code.model.board.AbstractTile;

/**
 * @author Robert, Steven, Matt
 */
public class TileGUI extends JPanel {

	private static final long serialVersionUID = -487545373571038865L;
	/**
	 * References the Tile represented by this GUI
	 */
	private AbstractTile _tile;
	/**
	 * 
	 */
	private Color _pathColor; 
	
	private ArrayList<Player> _players = new ArrayList<Player>(4);
	/**
	 * Sets each TileGUI to visible and 3x3 GridLayout
	 * @param tile
	 * @param _map 
	 */
	public TileGUI(AbstractTile tile, HashMap<Player, AbstractTile> _map) {
		this(tile);
		
		for (Player p : _map.keySet())
			if (_map.get(p) == tile)
				_players.add(p);
	}
	
	public TileGUI(AbstractTile tile) {
		_tile = tile;
		setVisible(true);
		setLayout(new GridLayout(3,3));
		_pathColor = (_tile.isReachable())? Color.WHITE: Color.GRAY;
	}
	/**
	 * Repaints the TileGUI
	 */
	@Override
	public void repaint() {
		if (_tile == null)
			return;
		
		Iterator<Player> it = _players.iterator();
		
		add(getPanel((it.hasNext()) ? GameGUI.PLAYER_COLORS[it.next().getIndex()] : BLACK));
		add(getPanel((_tile.getConnections()[0]) ? _pathColor : BLACK));
		add(getPanel((it.hasNext()) ? GameGUI.PLAYER_COLORS[it.next().getIndex()] : BLACK));
		add(getPanel((_tile.getConnections()[3]) ? _pathColor : BLACK));
		add(getPanel(_pathColor, _tile.getToken()));
		add(getPanel((_tile.getConnections()[1]) ? _pathColor : BLACK));
		add(getPanel((it.hasNext()) ? GameGUI.PLAYER_COLORS[it.next().getIndex()] : BLACK));
		add(getPanel((_tile.getConnections()[2]) ? _pathColor : BLACK));
		add(getPanel((it.hasNext()) ? GameGUI.PLAYER_COLORS[it.next().getIndex()] : BLACK));
		
		super.repaint();
	}
}
