package example1;

import java.util.Random;

import code.Properties;
import code.critter.behaviors.butterflybehaviors.ButterflyBehavior;
import engine.Animation;
import engine.drawing.ICanvas;
import engine.util.ImageTransformations;

public class Butterfly extends Critter {
	public static final String BUTTERFLY_ANIMATION = "../code/critter/images/butterfly_right.png";
	public static final String CACHE_KEY = "butterfly";

	private Animation _animation;

	public Butterfly() {
		super(ButterflyBehavior.getInstance());

		set_x(new Random().nextInt(Properties.SCREEN_WIDTH - 40) + 20);
		set_y(new Random().nextInt(Properties.SKY_HEIGHT - 120) + 20);

		set_vx(-2);
		set_vy(1);

		_animation = new Animation(getClass().getResource(BUTTERFLY_ANIMATION),
				14, 6);

		setUpperBound(gety() - 10);
		setLowerBound(gety() + 10);

		setLeftBound(0);
		setRightBound(Properties.SCREEN_WIDTH - 20);
	}

	@Override
	public void draw(ICanvas dc) {
		if (!isStarted()) {
			if (get_vx() < 0) {
				dc.drawImage(ImageTransformations.flipHorizontal(_animation
						.getCurrentAnimation()), getx(), gety());
			} else {
				dc.drawImage(_animation.getCurrentAnimation(), getx(), gety());
			}
		} else if (get_vx() < 0) {
			dc.drawImage(ImageTransformations.flipHorizontal(_animation
					.getNextAnimation()), getx(), gety());
		} else {
			dc.drawImage(_animation.getNextAnimation(), getx(), gety());
		}
	}

	@Override
	public Animation getAnimation() {
		// TODO Auto-generated method stub
		return _animation;
	}
}
