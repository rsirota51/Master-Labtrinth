package code.gui;

import static code.gui.GameGUI.BACKGROUND;
import static code.gui.GameGUI.COMP_BACKGROUND;
import static code.gui.GameGUI.PLAYER_COLORS_AS_STRINGS;
import static code.gui.GameGUI.SQUARE_SIZE;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import code.model.Game;
import code.model.Player;
/**
 * @author Robert, Steve, Matt
 */
public class SideBarGUI extends JPanel {

	private static final long serialVersionUID = -4825451571074331678L;
	/**
	 * Represents SideBarGUI Class
	 */
	private Game _game;
	/**
	 * Panel That Displays the Unused Tile
	 */
	private JPanel _unusedTilePanel;
	/**
	 * Panel That Displays The Players Scores
	 */
	private JPanel _score;
	/**
	 * Rotate Button, Rotates Left 
	 */
	private JButton _b1;
	/**
	 * Rotate Button, Rotates Right
	 */
	private JButton _b2;
	private JLabel _scoreText;
	/**
	 * Builds The Side Bar
	 * @param game
	 */
	public SideBarGUI(Game game) {
		_game = game;
		setPreferredSize(new Dimension(9*GameGUI.SQUARE_SIZE,23*GameGUI.SQUARE_SIZE));
		setVisible(true);
		setLayout(new FlowLayout(0,0,0));
		
		add(PanelFactory.getPanel(1, 23, COMP_BACKGROUND));
		
		JPanel p2 = PanelFactory.getPanel(7, 23);
		p2.setLayout(new FlowLayout(0,0,0));
		
			p2.add(PanelFactory.getPanel(7, 1, COMP_BACKGROUND));
			
			_b1 = new JButton();
			ImageIcon icon1 = new ImageIcon("src/assets/rotatecounterclockwise.png"); 
			Image Img1 = icon1.getImage();
			Image newImg1 = Img1.getScaledInstance(SQUARE_SIZE, 3 * SQUARE_SIZE, Image.SCALE_SMOOTH);
			ImageIcon newIcon1 = new ImageIcon(newImg1);
			_b1.setPreferredSize(new Dimension(GameGUI.SQUARE_SIZE,3*GameGUI.SQUARE_SIZE));
			_b1.setBackground(BACKGROUND);
			_b1.setIcon(newIcon1);
			_b1.addActionListener(new ActionListener() {
				/*
				 * ActionListener For Rotate Left Button, Rotates Unused Tile Left 90 Degree 
				 */
				@Override public void actionPerformed(ActionEvent e)
				{	_game.getBoard().getUnused().rotate(3);
					repaint();	}
			});
			_b1.setVisible(true);
			p2.add(_b1);
			
			p2.add(PanelFactory.getPanel(1, 3, COMP_BACKGROUND));
			
			_unusedTilePanel = PanelFactory.getPanel(3, 3);
			_unusedTilePanel.setLayout(new GridLayout(1,1,0,0));
			p2.add(_unusedTilePanel);
			
			p2.add(PanelFactory.getPanel(1, 3, COMP_BACKGROUND));
			
			_b2 = new JButton();
			ImageIcon icon2 = new ImageIcon("src/assets/rotateclockwise.png"); 
			Image Img2 = icon2.getImage();
			Image newImg2 = Img2.getScaledInstance(SQUARE_SIZE, 3 * SQUARE_SIZE, Image.SCALE_SMOOTH);
			ImageIcon newIcon2 = new ImageIcon(newImg2);
			_b2.setPreferredSize(new Dimension(GameGUI.SQUARE_SIZE,3*GameGUI.SQUARE_SIZE));
			_b2.setBackground(BACKGROUND);
			_b2.setIcon(newIcon2);
			_b2.addActionListener(new ActionListener() {
				/*
				 * ActionListner For Rotate Right Button, Rotates Unused Tile Right 90 Degree
				 */
				@Override public void actionPerformed(ActionEvent e)
				{	_game.getBoard().getUnused().rotate(1);
					repaint();	}
			});
			_b2.setVisible(true);
			p2.add(_b2);
		
			p2.add(PanelFactory.getPanel(7, 1, COMP_BACKGROUND));
			
			_score = PanelFactory.getPanel(7, 5, Color.WHITE); 
			_scoreText = new JLabel();
			updateScoreText();
			_score.add(_scoreText);
			p2.add(_score);
			
			p2.add(PanelFactory.getPanel(7, 1, COMP_BACKGROUND));
			
			JLabel instructions = new JLabel();
			instructions.setPreferredSize(new Dimension(7*SQUARE_SIZE,11*SQUARE_SIZE));
			instructions.setBackground(Color.WHITE);
			instructions.setVisible(true);
			instructions.setOpaque(true);
			instructions.setText("<html><div style='text-align: center;'><font size=6>Instructions</font><br><br>"
					+ "<font size=4 name='serif'>Use the above buttons to rotate the unused tile.<br><br>"
					+ "Swipe across a row or column in the maze to shift.<br><br>"
					+ "Double click on a tile to move there, or the tile you are standing on to stay put.<br><br>"
					+ "If possible, you will be given the option to pick up the next token.<br><br>"
					+ "</font></html>");
			
			
	
			p2.add(instructions); //TODO INSTR PANEL
			
			p2.add(PanelFactory.getPanel(7, 1, COMP_BACKGROUND));
		add(p2);
		
		add(PanelFactory.getPanel(1, 23, COMP_BACKGROUND));
	}
	/**
	 * Returns The Side Bar
	 * @return _game
	 */
	public Game getGame()
	{
		return _game;
	}
	/**
	 * Repaints The Unused Tile Panel In The Side Bar
	 */
	@Override
	public void repaint()
	{
		super.repaint();
		if (_unusedTilePanel != null)
		{
			_unusedTilePanel.removeAll();
			_unusedTilePanel.add(new TileGUI(_game.getBoard().getUnused()));
			_unusedTilePanel.revalidate();
			updateScoreText();
		}
	}
	private void updateScoreText()
	{
		String text = "<html><font size=6 name='serif'>Score:</font><br />";
		
		Player p;
		for (int i=0; i<_game.getPlayers().size(); i++)
		{
			p = _game.getPlayers().get(i);
			if (p == _game.getCurrentPlayer())
				text += "<b><font size=5 color="+PLAYER_COLORS_AS_STRINGS[i]+">" + p.getName() + ": " + p.getScore() + "</font></b><br><font size=1 /><br>";
			else
				text += "<font size=3 color="+PLAYER_COLORS_AS_STRINGS[i]+">" + p.getName() + ": " + p.getScore() + "</font><br><font size=1 /><br>";
		}
		
		_scoreText.setText(text);
		
		
		
		
		//_scoreText.setText(String.format("<html><h2>Current Player: %s</h2><h3>Score: %s</h3></html>", _game.getCurrentPlayer().getName(), _game.getCurrentPlayer().getScore()));
		
	}

}
