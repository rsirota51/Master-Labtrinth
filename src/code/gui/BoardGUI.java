package code.gui;

import static code.gui.GameGUI.COMP_BACKGROUND;
import static code.gui.GameGUI.SQUARE_SIZE;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.HashMap;

import javax.swing.JPanel;

import code.gui.listeners.BoardListener;
import code.model.Player;
import code.model.board.AbstractTile;
import code.model.board.Board;
/**
 * @author Robert, Steven
 */
public class BoardGUI extends JPanel
{
	private static final long serialVersionUID = -7538653053363019635L;
	
	/**
	 * References the Board represented by this GUI
	 */
	private Board _board;
	/**
	 * Represents the child JPanel containing the Board
	 */
	private JPanel _boardPanel;

	private HashMap<Player, AbstractTile> _map;
 	/**
	 * The constructor for the BoardGUI.
	 * Initializes the Panels for the GUI.
	 * @param game - the underlying model for this GUI
	 * @param gameGUI - the parent GameGUI for this instance of the BoardGUI
	 */
	public BoardGUI(Board board, HashMap<Player, AbstractTile> map, BoardListener boardListener) {
		super();
		_board = board;
		_map = map;
		
		setPreferredSize(new Dimension(23 * SQUARE_SIZE, 23 * SQUARE_SIZE));
		setLayout(new FlowLayout(0,0,0));
		setVisible(true);
		
		add(PanelFactory.getPanel(23, 1, COMP_BACKGROUND));	//Top border
		add(PanelFactory.getPanel(1, 21, COMP_BACKGROUND)); //Left border
		
		_boardPanel = PanelFactory.getPanel(21, 21);
		_boardPanel.setLayout(new GridLayout(7,7,2,2));
		_boardPanel.addMouseListener(boardListener);
		add(_boardPanel);
		
		add(PanelFactory.getPanel(1, 21, COMP_BACKGROUND)); //Right border
		add(PanelFactory.getPanel(23, 1, COMP_BACKGROUND)); //Bottom border
	}
	/**
	 * Repaints the game board and each of its children.
	 * @author Robert
	 */
	@Override
	public void repaint()
	{
		if (_boardPanel == null)
			return;
		_boardPanel.removeAll();
		AbstractTile tile;
		for (int i=0; i<7; i++)
			for (int j=0; j<7; j++) {
				tile = _board.tileAt(i,j);
				if (_map.values().contains(tile))
					_boardPanel.add(new TileGUI(tile, _map));
				else
					_boardPanel.add(new TileGUI(tile));	
			}
		revalidate();
	}
}
