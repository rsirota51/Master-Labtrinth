package example1;

import java.util.Random;

import code.Properties;
import code.critter.behaviors.MoveHorizontal;
import engine.Animation;
import engine.drawing.ICanvas;
import engine.util.ImageTransformations;

public class Pig extends Critter {
	public static final String PIG_ANIMATION = "../code/critter/images/pig_walk_right.png";

	private Animation _animation;

	public Pig() {
		super(MoveHorizontal.getInstance());
		set_x(new Random().nextInt(Properties.SCREEN_WIDTH - 40));
		set_y(Properties.SKY_HEIGHT - 40);

		set_vx(2);

		setLeftBound(0);
		setRightBound(Properties.SCREEN_WIDTH - 40);

		_animation = new Animation(getClass().getResource(PIG_ANIMATION), 8, 3);
	}

	@Override
	public void draw(ICanvas dc) {
		if (!isStarted()) {
			if (get_vx() < 0) {
				dc.drawImage(ImageTransformations
						.flipHorizontal(_animation.getCurrentAnimation()),
						getx(), gety());
			} else {
				dc.drawImage(_animation.getCurrentAnimation(), getx(),
						gety());
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
