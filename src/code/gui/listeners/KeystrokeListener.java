package code.gui.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.SwingUtilities;

import example1.BarnYard;
import example1.Butterfly;
import example1.Chicken;
import example1.Pig;


public class KeystrokeListener implements KeyListener {
	
	private int state = 0;

	@Override
	public void keyPressed(KeyEvent e) {
		char c = Character.toLowerCase(e.getKeyChar());
		switch (state) {
		case 0:
			if (c == 'g')
				state = 1;
			break;
		case 1:
			if (c == 'o')
				state = 2;
			else if (c == 'g')
				state = 1;
			else
				state = 0;
			break;
		case 2:
			if (c == 'r')
				state = 3;
			else
				state = 0;
			break;
		case 3:
			if (c == 'a')
				state = 4;
			else
				state = 0;
			break;
		case 4:
			if (c == 'n') {
				state = 5;
				SwingUtilities.invokeLater(new Minigame());
			}
			state = 0;
			break;
		default:
			System.err.println("Goran?");
		}
		
	}

	@Override public void keyTyped(KeyEvent e) {}
	@Override public void keyReleased(KeyEvent e) {}
	
	private class Minigame implements Runnable {
		
		@Override
		public void run() {
			BarnYard by;
			by = new BarnYard();
			Chicken c1;
			c1 = new Chicken();
			by.addChicken(c1);
			c1.start();
			
			Chicken c2;
			c2 = new Chicken();
			by.addChicken(c2);
			c2.start();
			
			Chicken c3;
			c3 = new Chicken();
			by.addChicken(c3);
			c3.start();
			
			Butterfly b1;
			b1 = new Butterfly();
			by.addButterfly(b1);
			b1.start();
			
			Pig p1;
			p1 = new Pig();
			by.addPig(p1);
			p1.start();
		}		
	}

}
