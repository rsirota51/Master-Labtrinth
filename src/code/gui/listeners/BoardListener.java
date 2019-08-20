package code.gui.listeners;

import static code.gui.GameGUI.SQUARE_SIZE;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import code.gui.GameGUI;
import code.model.Game;

/**
 * Listener associated with the panel containing the board
 * Relies on mouse drags for board rotations and mouse clicks for moving players
 * @author Matt, Joe
 *
 */
public class BoardListener implements MouseListener {

	/**
	 * Represents the underlying model
	 */
	private Game _game;
	/**
	 * Represents the parent component of the GUI
	 */
	private GameGUI _gui;
	/**
	 * Represents the beginning of the mouse drag event
	 */
	private Point _startLoc;

	/**
	 * The constructor for the listener which initializes the underlying model and parent GUI component
	 * @param game - underlying model
	 * @param gui - parent GUI component
	 */
	public BoardListener(Game game, GameGUI gui) {
		_game = game;
		_gui = gui;
	}

	/**
	 * The action associated with moving a player along a valid path, triggered when one of the squares on the board is clicked
	 * @author Matt
	 */
	@SuppressWarnings("serial")
	@Override
	public void mouseClicked(MouseEvent e) {

		if (_game.getCurrentPlayer().getPhase() != 2) //only allows a player to move their pawn if the board has already been rotated
			return;
		Point moveLoc = new Point(e.getY() / (3 * SQUARE_SIZE), e.getX() / (3 * SQUARE_SIZE)); //the location the player is trying to move to
		if (!_game.getBoard().tileAt(moveLoc).isReachable()) //if the player cannot get to the tile, the move is not executed
			return;

		if (_game.movePlayer(moveLoc)) 
		{
			//TODO: Player picking up Token 
			

//			_gui.setFocus(false);
			

			new DialogBox() {

				@Override
				public void button1Pressed(ActionEvent arg0) {
					_game.removeToken();					
				}

				@Override
				public void button2Pressed(ActionEvent arg0) {}

				@Override
				public void afterAction() {
					dispose();
					_gui.setFocus(true);
					_game.advanceTurn(); //turns control of the game over to the next player
					_gui.repaint(); //updates the GUI to reflect the movement 					
				}};

		} else {
			_game.advanceTurn(); //turns control of the game over to the next player
			_gui.repaint(); //updates the GUI to reflect the movement 	
		}
	}

	/**
	 * Represents the first half of the rotate maze mouse drag event
	 * Stores the square that the mouse started the drag in the _startLoc instance variable
	 * @author Matt, Joe
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		if (_game.getCurrentPlayer().getPhase() != 1) //only allows rotating the board during the first part of a player's turn
			return;
		_startLoc = new Point(e.getX() / (3 * SQUARE_SIZE), e.getY() / (3 * SQUARE_SIZE));
	}

	/**
	 * Represents the second half of the rotate maze mouse drag event
	 * Determines the square that the mouse ended the drag and calls the proper rotate method on the underlying model
	 * @author Matt, Joe
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		if (_game.getCurrentPlayer().getPhase() != 1)  //only allows rotating the board during the first part of a player's turn
			return;

		Point endLoc = new Point(e.getX() / (3 * SQUARE_SIZE), e.getY() / (3 * SQUARE_SIZE));
		Point diff = new Point(endLoc.x - _startLoc.x, endLoc.y - _startLoc.y); //represents the displacement of the mouse drag
		if (new Point().equals(diff))  //if drag ends in same square, no move is made
			return;
		if ((diff.x == 0 && _game.getBoard().rotateColumn(endLoc.x, diff.y > 0))  //performs rotation if mouse drag is contained in one column
		 || (diff.y == 0 && _game.getBoard().rotateRow(endLoc.y, diff.x > 0))) //performs rotation if mouse drag is contained in one row
		{
			_game.afterBoardShift(); //puts any pieces that fell off the board in their proper location, advances turn phase for current player
			_gui.repaint(); //updates the GUI to represent board shifting
		}
	}

	@Override public void mouseEntered(MouseEvent e) {}
	@Override public void mouseExited(MouseEvent e) {}
}
