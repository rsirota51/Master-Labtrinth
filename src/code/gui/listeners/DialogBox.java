package code.gui.listeners;

import static code.gui.GameGUI.SQUARE_SIZE;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class DialogBox extends JComponent {

	private static final long serialVersionUID = 1298993012739449414L;

	private JDialog _frame;
	
	private JLabel _label;
	
	private JButton _button1, _button2;
	
	public DialogBox() {
		_frame = new JDialog((JFrame) null, "You landed on the next token!", true);
		_frame.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		_frame.setLayout(new GridLayout(0,1));
		
		JPanel panel1 = new JPanel();
		_frame.add(panel1);
		_label = new JLabel("<html><p><center>Would you like to pick up the token?</center></p><br /></html>");
		increaseFont(_label, 2);
		_label.setPreferredSize(new Dimension(SQUARE_SIZE*10, SQUARE_SIZE*3));
		panel1.add(_label);
		
		JPanel panel2 = new JPanel();
		_frame.add(panel2);
		_button1 = new JButton("YES");
		increaseFont(_button1, 2);
		_button1.setPreferredSize(new Dimension(SQUARE_SIZE*5, SQUARE_SIZE*3));
		_button2 = new JButton("NO");
		increaseFont(_button2, 2);
		_button2.setPreferredSize(new Dimension(SQUARE_SIZE*5, SQUARE_SIZE*3));
		panel2.add(_button1);
		panel2.add(_button2);
		
//		_frame.setPreferredSize(new Dimension(SQUARE_SIZE*10+10, SQUARE_SIZE*5+10));
		_frame.pack();
		
		_button1.addActionListener(new ActionListener(){
			@Override public void actionPerformed(ActionEvent arg0) {
				button1Pressed(arg0);
				afterAction();
			}
		});
		
		_button2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				button2Pressed(arg0);
				afterAction();				
			}
		});
		
		_frame.setVisible(true);
		
	}
	public abstract void button1Pressed(ActionEvent arg0);
	public abstract void button2Pressed(ActionEvent arg0);
	public abstract void afterAction();
	
	public void addTextToButton1(String text) {
		_button1.setText(text);
	}

	public void addTextToButton2(String text) {
		_button2.setText(text);
	}
	
	public void addTextToLabel(String text) {
		_label.setText(text);
	}
	
	public void dispose() {
		_frame.dispose();
	}
	
	private void increaseFont(JComponent c, double factor)
	{
		c.setFont(c.getFont().deriveFont((float) (c.getFont().getSize()*factor)));
	}
}
