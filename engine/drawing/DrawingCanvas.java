package engine.drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;

import javax.swing.JPanel;

public class DrawingCanvas extends JPanel implements ICanvas{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * _back and _front are the graphics that will be drawn. All drawing done to
	 * the canvas is done to the back image. The two are identical in every way.
	 */
	// private VolatileImage _back, _front;

	private BufferedImage _back, _front;

	// does the image need to be flipped?
	private boolean _dirty;
	
	private Color _background;

	private boolean _scale;

	private double _scaleValue;

	public DrawingCanvas(int width, int height) {
		super();

		super.setPreferredSize(new Dimension(width, height));

		_back = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice().getDefaultConfiguration()
				.createCompatibleImage(width, height, VolatileImage.OPAQUE);

		_front = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice().getDefaultConfiguration()
				.createCompatibleImage(width, height, VolatileImage.OPAQUE);
	
		
		_back = toCompatibleImage(_back);
		_front = toCompatibleImage(_front);
		

		setDoubleBuffered(true);

		// _back = createImage(width, height, false);
		// _front = createImage(width, height, false);

		// _front.setAccelerationPriority(1.0f);
		// _back.setAccelerationPriority(1.0f);

		clearFront();
		clearBack();

		_dirty = true;

		_background = Color.BLACK;

		_scale = false;
	}

	public void setListen(boolean b) {
		super.setFocusable(b);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		if (_dirty) {
			BufferedImage tmp = _front;
			_front = _back;
			_back = tmp;

			if (_scale) {
				((Graphics2D) g).scale(_scaleValue, _scaleValue);
				g.drawImage(_front, 0, 0, null);
			} else {
				g.drawImage(_front, 0, 0, null);
			}

			_dirty = false;

			clearBack();
		}

		g.dispose();
	}

	public void flip() {
		_dirty = true;
		repaint();
	}

	private Graphics2D getBackGraphics() {
		return (Graphics2D) _back.getGraphics();
	}

	public void drawLine(int x, int y, int x2, int y2) {
		Graphics2D g = getBackGraphics();

		g.drawLine(x, y, x2, y2);

		g.dispose();
	}

	public void drawLine(int x, int y, int x2, int y2, int thickness, Color c) {
		Graphics2D g = getBackGraphics();

		g.setStroke(new BasicStroke(thickness));
		g.setColor(c);

		g.drawLine(x, y, x2, y2);

		g.dispose();

	}

	public void clearBack() {
		Graphics2D g = getBackGraphics();
		g.setColor(_background);
		g.fillRect(0, 0, _back.getWidth(), _back.getHeight());

		g.dispose();
	}

	public void clearFront() {
		Graphics g = _front.getGraphics();
		g.setColor(_background);
		g.fillRect(0, 0, _front.getWidth(), _front.getHeight());

		g.dispose();
	}

	public void fill(Color c) {
		Graphics2D g = getBackGraphics();
		g.fillRect(0, 0, _back.getWidth(), _back.getHeight());

		g.dispose();
	}

	public void drawRectange(int x, int y, int width, int height) {
		Graphics2D g = getBackGraphics();
		g.drawRect(x, y, width, height);

		g.dispose();
	}

	public void drawRectange(int x, int y, int width, int height, Color c) {
		Graphics2D g = getBackGraphics();
		g.setColor(c);

		g.drawRect(x, y, width, height);

		g.dispose();
	}

	public void drawFilledRectangle(int x, int y, int width, int height, Color c) {
		Graphics2D g = getBackGraphics();
		g.setColor(c);

		g.fillRect(x, y, width, height);

		g.dispose();
	}

	public void drawCircle(int x, int y, int size, Color c) {
		Graphics2D g = getBackGraphics();
		g.setColor(c);

		g.drawOval(x, y, size, size);

		g.dispose();
	}

	public void drawFilledCircle(int x, int y, int size, Color c) {
		Graphics2D g = getBackGraphics();
		g.setColor(c);

		g.fillOval(x, y, size, size);

		g.dispose();
	}

	public void drawImage(Image img, int x, int y) {
		Graphics2D g = getBackGraphics();

		g.drawImage(img, x, y, null);

		g.dispose();
	}

	public void setBackgroundColor(Color c) {
		_background = c;
	}

	public void resize(int width, int height) {
		_back = null;
		_front = null;

		_back = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice().getDefaultConfiguration()
				.createCompatibleImage(width, height, VolatileImage.OPAQUE);
		_front = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice().getDefaultConfiguration()
				.createCompatibleImage(width, height, VolatileImage.OPAQUE);
	}

	public void scale(boolean b, double scale) {
		_scale = b;
		_scaleValue = scale;
	}

	public void drawText(String s, int size, int x, int y) {
		Graphics2D g = getBackGraphics();

		g.setFont(new Font("TimesRoman", Font.PLAIN, size));

		g.drawString(s, x, y);

		g.dispose();
	}

	private BufferedImage toCompatibleImage(BufferedImage image) {
		// obtain the current system graphical settings
		GraphicsConfiguration gfx_config = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();

		/*
		 * if image is already compatible and optimized for current system
		 * settings, simply return it
		 */
		if (image.getColorModel().equals(gfx_config.getColorModel()))
			return image;

		// image is not optimized, so create a new image that is
		BufferedImage new_image = gfx_config.createCompatibleImage(
				image.getWidth(), image.getHeight(), image.getTransparency());

		// get the graphics context of the new image to draw the old image on
		Graphics2D g2d = (Graphics2D) new_image.getGraphics();

		// actually draw the image and dispose of context no longer needed
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();

		// return the new optimized image
		return new_image;
	}

	public static BufferedImage createImage(final int width, final int height,
			final boolean shouldTransparencyBeAllowed) {
		// get the GraphicsConfiguration
		GraphicsConfiguration graphicsConfiguration = GraphicsEnvironment
				.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();

		// this enables/disables transparency and makes the Image more likely to
		// be managed
		return graphicsConfiguration.createCompatibleImage(width, height,
				shouldTransparencyBeAllowed ? Transparency.BITMASK
						: Transparency.OPAQUE);
	}

	@Override
	public void drawText(String s, int size, int x, int y, Color c) {
		// TODO Auto-generated method stub
		Graphics2D g = getBackGraphics();

		g.setColor(c);
		
		g.setFont(new Font("TimesRoman", Font.PLAIN, size));

		g.drawString(s, x, y);

		g.dispose();
	}
}
