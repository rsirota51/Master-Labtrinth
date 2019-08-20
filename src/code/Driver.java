package code;

import javax.swing.SwingUtilities;

import code.gui.GameGUI;
import code.model.Game;

public class Driver {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new GameGUI(new Game(args)));
	}

}
