package engine.tools.animationtester;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import engine.Animation;
import engine.Game;

public class GUI {
	private Game _g;

	private AnimationHolder _ah;

	private String _lastDirectory;

	private JFrame jf;

	private JLabel currentFrame;

	public GUI() {
		_lastDirectory = null;

		jf = new JFrame("Animation Tester");

		jf.setLayout(new GridLayout(0, 1));

		JButton load;
		load = new JButton("Load");

		load.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				loadAnimation();
			}
		});

		jf.add(load);

		JPanel playSettings = new JPanel();
		playSettings.setLayout(new GridLayout(1, 0));

		JButton back, play, forward;
		back = new JButton("<<");
		play = new JButton(">");
		forward = new JButton(">>");

		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				back();
			}
		});

		play.addActionListener(new ActionListener() {
			private boolean depressed = false;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(_ah == null){
					return;
				}
				
				if (depressed) {
					((JButton) e.getSource()).setText(">");
					pause();
				} else {
					((JButton) e.getSource()).setText("||");
					play();
				}

				depressed = !depressed;
			}
		});

		forward.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				forward();
			}
		});

		playSettings.add(back);
		playSettings.add(play);
		playSettings.add(forward);

		jf.add(playSettings);

		currentFrame = new JLabel("Current Frame: N/A");
		jf.add(currentFrame);

		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.pack();
		jf.setVisible(true);

	}

	private void loadAnimation() {
		JFileChooser chooser = new JFileChooser(_lastDirectory);

		int returnval = chooser.showOpenDialog(jf);

		if (returnval == JFileChooser.APPROVE_OPTION) {
			String across = (String) JOptionPane.showInputDialog(jf,
					"How many frames across?");
			String numDown = (String) JOptionPane.showInputDialog(jf,
					"How many frames down?");
			String maxFrames = (String) JOptionPane.showInputDialog(jf,
					"How many frames total?");
			
			String FPS = (String) JOptionPane.showInputDialog(jf,"How many Frames Per Second (FPS)?");
			

			// System.out.println(across + " " + numDown + " " + maxFrames + " "
			// + chooser.getSelectedFile().getAbsolutePath());
			Animation a = null;

			a = new Animation(chooser.getSelectedFile().getAbsolutePath(),
					Integer.parseInt(across), Integer.parseInt(numDown))
					.setMaxFrames(Integer.parseInt(maxFrames));

			_lastDirectory = chooser.getSelectedFile().getPath();

			_ah = new AnimationHolder(a,currentFrame);
			
			if(_g != null){
				_g.quit();
			}

			_g = new Game(a.getWidth(), a.getHeight(), Integer.parseInt(FPS));
			
			_g.addEntity(_ah);

			currentFrame.setText("Current Frame: " + _ah.getCurrentFrame());
		}
	}

	private void forward() {
		if (_ah == null) {
			return;
		}

		_ah.nextAnimation();
		currentFrame.setText("Current Frame: " + _ah.getCurrentFrame());
	}

	private void back() {
		if (_ah == null) {
			return;
		}

		_ah.previousAnimation();
		currentFrame.setText("Current Frame: " + _ah.getCurrentFrame());
	}

	private void play() {
		if (_ah != null) {
			_ah.play();
		}
	}

	private void pause() {
		if (_ah != null) {
			_ah.pause();
		}
	}
	
	public static void main(String[] args) {
		new GUI();
	}

}
