package engine;

import javax.swing.JFrame;

import engine.drawing.DrawingCanvas;

@Deprecated
public class Display {
	private JFrame _window;
	
	public Display(){
		/*_t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				_window = new JFrame();
				_window.setResizable(false);
				_window.setVisible(true);
				_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		});
		_t.start();*/
		
		_window = new JFrame();
		_window.setResizable(false);
		_window.setVisible(true);
		_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void addCanvas(DrawingCanvas c){
		_window.setContentPane(c);
	}
	
	public void pack(){
		_window.pack();
	}
	
	public void addKeyListener(InputManager i){
		_window.addKeyListener(i);
	}
	
	public void addMouseListener(InputManager i){
		_window.addMouseListener(i);
	}
	
	public void close(){
		_window.dispose();
	}
}
