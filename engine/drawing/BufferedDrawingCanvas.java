package engine.drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.VolatileImage;

import javax.swing.JPanel;

public class BufferedDrawingCanvas extends JPanel implements ICanvas{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * All painting is done the _back image first. After all drawing is done, it is published
	 * to the front buffer.
	 */
	private VolatileImage _imgBuffer, _current;
	
	private int _width, _height;
	
	public BufferedDrawingCanvas(int width, int height) {
		super();
		
		setPreferredSize(new Dimension(width, height));
		
		_width = width;
		_height = height;
		
		_current = _imgBuffer = getVolatileImage(width, height);
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(_current,0,0,null);
		
		Toolkit.getDefaultToolkit().sync();
		
		g.dispose();
	}
	
	/**
	 * Creates a new compatible VolatileImage to draw to
	 * @param width
	 * @param height
	 * @return
	 */
	private VolatileImage getVolatileImage(int width, int height){
		VolatileImage tmp = null;
		
		while(tmp == null || 
				tmp.validate(GraphicsEnvironment
						.getLocalGraphicsEnvironment()
						.getDefaultScreenDevice()
						.getDefaultConfiguration()) == VolatileImage.IMAGE_INCOMPATIBLE){
			tmp = GraphicsEnvironment.getLocalGraphicsEnvironment()
					.getDefaultScreenDevice()
					.getDefaultConfiguration()
					.createCompatibleVolatileImage(width, height, VolatileImage.OPAQUE);
		}
		
		return tmp;
		
	}
	
	private Graphics2D getBufferGraphics(){
		if(_imgBuffer == null){
			_imgBuffer = getVolatileImage(_width, _height);
		}
		
		return (Graphics2D) _imgBuffer.getGraphics();
	}
	
	public boolean contentsLost(){
		if(_imgBuffer.contentsLost()){
			_imgBuffer = getVolatileImage(_width, _height);
		}
		
		return _imgBuffer.contentsLost();
	}
	
	@Override
	public void drawLine(int x, int y, int x2, int y2) {
		// TODO Auto-generated method stub
		Graphics2D g2d = getBufferGraphics();
		
		g2d.drawLine(x, y, x2, y2);
		
		g2d.dispose();
	}

	@Override
	public void drawLine(int x, int y, int x2, int y2, int thickness, Color c) {
		// TODO Auto-generated method stub
		Graphics2D g2d = getBufferGraphics();
		
		g2d.setColor(c);
		
		g2d.setStroke(new BasicStroke(thickness));
		
		g2d.dispose();
	}

	@Override
	public void fill(Color c) {
		// TODO Auto-generated method stub
		Graphics2D g2d = getBufferGraphics();
		
		g2d.setColor(c);
		
		g2d.fillRect(0, 0, _width, _height);
		
		g2d.dispose();
	}

	@Override
	public void drawRectange(int x, int y, int width, int height) {
		// TODO Auto-generated method stub
		Graphics2D g2d = getBufferGraphics();
		
		g2d.drawRect(x, y, width, height);
		
		g2d.dispose();
	}

	@Override
	public void drawRectange(int x, int y, int width, int height, Color c) {
		// TODO Auto-generated method stub
		Graphics2D g2d = getBufferGraphics();
		
		g2d.setColor(c);
		
		g2d.drawRect(x, y, width, height);
		
		g2d.dispose();
	}

	@Override
	public void drawFilledRectangle(int x, int y, int width, int height, Color c) {
		// TODO Auto-generated method stub
		Graphics2D g2d = getBufferGraphics();
		
		g2d.setColor(c);
		
		g2d.fillRect(x, y, width, height);
		
		g2d.dispose();
	}

	@Override
	public void drawCircle(int x, int y, int size, Color c) {
		// TODO Auto-generated method stub
		Graphics2D g2d = getBufferGraphics();
		
		g2d.setColor(c);
		
		g2d.drawOval(x, y, size, size);
		
		g2d.dispose();
	}

	@Override
	public void drawFilledCircle(int x, int y, int size, Color c) {
		// TODO Auto-generated method stub
		Graphics2D g2d = getBufferGraphics();
		
		g2d.setColor(c);
		
		g2d.fillOval(x, y, size, size);
		
		g2d.dispose();
	}

	@Override
	public void drawImage(Image img, int x, int y) {
		// TODO Auto-generated method stub
		Graphics2D g2d = getBufferGraphics();
		
		g2d.drawImage(img,x,y,null);
		
		g2d.dispose();
	}

	@Override
	public void setBackgroundColor(Color c) {
		// TODO Auto-generated method stub
		
		//unimplemented rightn ow
	}

	@Override
	public void scale(boolean b, double scale) {
		//unimplemented right now
	}

	@Override
	public void drawText(String s, int size, int x, int y) {
		// TODO Auto-generated method stub
		Graphics2D g2d = getBufferGraphics();
		
		g2d.dispose();
	}

	@Override
	public void drawText(String s, int size, int x, int y, Color c) {
		// TODO Auto-generated method stub
		Graphics2D g2d = getBufferGraphics();
		
		g2d.dispose();
	}
	
	public void flip() {
		// TODO Auto-generated method stub
		_current = _imgBuffer;
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

}
