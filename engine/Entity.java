package engine;

import engine.drawing.ICanvas;

public interface Entity {
	public void draw(ICanvas dc);
	
	public void update();
	
	public int getx();
	
	public int gety();
}
