package code.barnyard;

import java.net.URL;

import engine.Entity;
import engine.ImagePool;
import engine.drawing.ICanvas;

public class Background implements Entity{
	/**
	 * Background image for our BarnYard.
	 * Taken from Google Maps.
	 */
	public static final String BACKGROUND_IMAGE = "terrain.png";
	
	private ImagePool _ip;
	private URL key;
	
	public Background(){
		_ip = ImagePool.getInstace();
		key = getClass().getResource(BACKGROUND_IMAGE);
		_ip.registerImage(key);
	}

	@Override
	public void draw(ICanvas arg0) {
		// TODO Auto-generated method stub
		arg0.drawImage(_ip.getImage(key), 0, 0);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		//do nothing
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
