package engine.collision;

import java.awt.Rectangle;

public interface CollisionListener {
	
	public void collision(CollisionListener cl);
	
	public Rectangle getRect();

	public boolean hasMoved();
	
	/**
	 * This method is used to better identify 
	 * @return a string representing something about the object or null if unimplemented
	 */
	public String getID();
}
