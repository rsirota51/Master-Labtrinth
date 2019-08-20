package engine.tools.animationtester;

import java.awt.Color;

import javax.swing.JLabel;

import engine.Animation;
import engine.Entity;
import engine.drawing.ICanvas;

public class AnimationHolder implements Entity{
	private Animation _a;
	
	private boolean _playing;
	
	private JLabel jl;
	
	public AnimationHolder(Animation a, JLabel jl) {
		// TODO Auto-generated constructor stub
		_a = a;	
		_playing = false;
		this.jl = jl;
	}
	
	@Override
	public void draw(ICanvas dc) {
		// TODO Auto-generated method stub
		dc.drawFilledRectangle(0, 0, _a.getWidth(), _a.getHeight(), Color.WHITE);
		
		if(_playing && _a.hasNext()){
			dc.drawImage(_a.getNextAnimation(), 0, 0);
			jl.setText("Current Frame: " + _a.getCurrentFrame());
		}else{
			dc.drawImage(_a.getCurrentAnimation(), 0, 0);
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(_playing && !_a.hasNext()){
			_playing = false;
		}
	}
	
	public void nextAnimation(){
		_a.skipFrame();
	}

	public void previousAnimation() {
		// TODO Auto-generated method stub
		_a.previousFrame();
	}
	
	public int getCurrentFrame(){
		return _a.getCurrentFrame() + 1;
	}

	public void play(){
		_playing = true;
	}
	
	public void pause(){
		_playing = false;
	}

	@Override
	public int getx() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int gety() {
		// TODO Auto-generated method stub
		return 0;
	}
}
