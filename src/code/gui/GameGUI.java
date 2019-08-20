package code.gui;

import static code.gui.PanelFactory.getPanel;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JFrame;

import code.gui.listeners.BoardListener;
import code.model.Game;

/**
 * @author Blake, Matt
 */
public class GameGUI implements Runnable
{
	/**
	 * Represents the width of a path in the maze.
	 * All other sizes of the GUI are built as a multiple of this int.
	 * This int depends on the size of the screen on which the application is run.
	 */
	public static final int SQUARE_SIZE = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height / 35;
	/**
	 * Represents the background color of the GameGUI.
	 */
	public static final Color BACKGROUND = new Color(224, 156, 74);
	/**
	 * Represents the background of the child components of the GameGUI.
	 */
	public static final Color COMP_BACKGROUND = new Color(102, 62, 14);
	/**
	 * Represents the Colors of each Player, in order.
	 */
	public static final Color[] PLAYER_COLORS = new Color[] {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN};
	public static final String[] PLAYER_COLORS_AS_STRINGS = new String[] {"red", "blue", "yellow", "green"};
	/**
	 * References the underlying model represented by this GUI.
	 */
	private Game _game;
	private JFrame _frame;
	/**
	 * References the child BoardGUI of this GameGUI.
	 */
	private BoardGUI _board;
	/**
	 * References the child SideBarGUI of this GameGUI.
	 */
	private SideBarGUI _sidebar;
	/**
	 * The constructor for this class.
	 * @param game - the underlying model represented by this GUI.
	 */
	public GameGUI(Game game) { _game = game; }
	/**
	 * Create The Frame And Lays Out The Basic Design Of The Board
	 */
	public void run()
	{
		_frame = new JFrame("Master Labyrinth - Brought to you by Danimals(R)");
		_frame.setSize(38*SQUARE_SIZE+6, 30*SQUARE_SIZE+31);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.setResizable(false);
		_frame.setVisible(true);
		_frame.setLayout(new FlowLayout(0,0,0));																			try {_frame.addKeyListener(new code.gui.listeners.KeystrokeListener()); _frame.requestFocus();} catch (Throwable e) {}
		
		_frame.add(getPanel(38, 5, BACKGROUND, "MASTER LABYRINTH"));
		_frame.add(getPanel(2, 23, BACKGROUND));
		
		_board = new BoardGUI(_game.getBoard(), _game.getMap(), new BoardListener(_game, this));
		_frame.add(_board);
		
		_frame.add(getPanel(2, 23, BACKGROUND));		
		
		_sidebar = new SideBarGUI(_game);
		_frame.add(_sidebar);
		
		_frame.add(getPanel(2, 23, BACKGROUND));
		_frame.add(getPanel(38, 2, BACKGROUND));
	}
	/**
	 * Calls the repaint methods in the children of this GameGUI.
	 */
	public void repaint()
	{
		_board.repaint();
		_sidebar.repaint();
	}
	public void setFocus(boolean b)
	{
		_frame.setFocusable(b);
	}

}
