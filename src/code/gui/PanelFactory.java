package code.gui;

import static code.gui.GameGUI.SQUARE_SIZE;
import static java.awt.Color.BLACK;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import code.model.Token;
/**
 * @author Matt
 */
public class PanelFactory {
	private PanelFactory() {} //This class is non-instantiable.
	public static JPanel getPanel()
	{
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
		panel.setVisible(true);
		
		return panel;
	}
	/**
	 * Creates and returns a new JPanel of the given size.
	 * @author Matt
	 * 
	 * @param width
	 * @param height
	 * @return panel
	 */
	public static JPanel getPanel(int width, int height)
	{
		JPanel panel = getPanel();
		panel.setPreferredSize(new Dimension(width * SQUARE_SIZE, height * SQUARE_SIZE));
			
		return panel;
	}
	public static JPanel getPanel(Color background)
	{
		JPanel panel = getPanel();
		panel.setBackground(background);
		
		return panel;
	}
	/**
	 * Creates and returns a new JPanel of the given size and color.
	 * @author Matt
	 * 
	 * @param width
	 * @param height
	 * @param background
	 * @return panel
	 */
	public static JPanel getPanel(int width, int height, Color background)
	{
		JPanel panel = getPanel(width, height);
		panel.setBackground(background);
		
		return panel;
	}
	/**
	 * 
	 */
	@SuppressWarnings("serial")
	public static JPanel getPanel(Color background, Token token)
	{
		if (token == null)
			return getPanel(background);
		JPanel panel = new JPanel() {
			@Override public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(new Color(255,255,100));
				g.fillOval(SQUARE_SIZE/10, SQUARE_SIZE/10, SQUARE_SIZE * 4/5, SQUARE_SIZE * 4/5);
				g.setColor(BLACK);
				g.drawOval(SQUARE_SIZE/10, SQUARE_SIZE/10, SQUARE_SIZE * 4/5, SQUARE_SIZE * 4/5);
				Graphics2D g2 = (Graphics2D) g;
				g2.drawString(String.valueOf(token.getValue()), SQUARE_SIZE * 2/7 + ((token.getValue() >= 10) ? 0 : SQUARE_SIZE * 2/15), SQUARE_SIZE * 2/3);
			}
		};
		
		panel.setPreferredSize(new Dimension(SQUARE_SIZE, SQUARE_SIZE));
		panel.setBackground(background);
		panel.setVisible(true);
		
		return panel;
	}
	
	@SuppressWarnings("serial")
	public static JPanel getPanel(int width, int height, Color background, String text)
	{
		if (text == null || text == "")
			return getPanel(background);
		JPanel panel = new JPanel() {
			@Override public void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.setColor(Color.BLACK);
				Graphics2D g2 = (Graphics2D)g;
				g2.setFont(g2.getFont().deriveFont(Font.BOLD, g2.getFont().getSize()*8));
				g2.drawString(text, (int)(SQUARE_SIZE*2.75), SQUARE_SIZE*4);
			}
		};
		
		panel.setPreferredSize(new Dimension(width * SQUARE_SIZE, height * SQUARE_SIZE));
		panel.setBackground(background);
		panel.setVisible(true);
		
		return panel;
	}
	

}
