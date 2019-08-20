package example1;

import java.util.Random;

import code.Properties;
import code.critter.behaviors.chickenbehaviors.ChickenBehavior;
import engine.Animation;
import engine.drawing.ICanvas;
import engine.util.ImageTransformations;

public class Chicken extends Critter {
	public static final String WALKING_ANIMATION = "../code/critter/images/chicken_walk_right.png";
	public static final String PECKING_ANIMATION = "../code/critter/images/chicken_pecking_right.png";

	private Animation _walking, _pecking;

	private Animation _currentAnimation;

	public Chicken() {
		super(null);

		set_x(new Random().nextInt(Properties.SCREEN_WIDTH));
		set_y(new Random().nextInt(Properties.SCREEN_HEIGHT
				- Properties.SKY_HEIGHT)
				+ (Properties.SKY_HEIGHT - 40));

		set_vx(2);
		set_vy(2);

		_walking = new Animation(getClass().getResource(WALKING_ANIMATION), 6,
				4);
		_pecking = new Animation(getClass().getResource(PECKING_ANIMATION), 6,
				7);
		_pecking.setMaxFrames(40);
		_pecking.setLoop(false);

		_currentAnimation = _walking;

		setLeftBound(0);
		setRightBound(Properties.SCREEN_WIDTH - 40);
		setUpperBound(400);
		setLowerBound(Properties.SCREEN_HEIGHT - 80);

		setBehavior(new ChickenBehavior(this));
	}

	@Override
	public void draw(ICanvas dc) {
		// TODO Auto-generated method stub
		if (!isStarted()) {
			if (get_vx() < 0) {
				dc.drawImage(
						ImageTransformations.flipHorizontal(_currentAnimation
								.getCurrentAnimation()), getx(), gety());
			} else {
				dc.drawImage(_currentAnimation.getCurrentAnimation(), getx(),
						gety());
			}
		} else if (get_vx() < 0) {
			dc.drawImage(ImageTransformations.flipHorizontal(_currentAnimation
					.getNextAnimation()), getx(), gety());
		} else {
			dc.drawImage(_currentAnimation.getNextAnimation(), getx(), gety());
		}
	}

	@Override
	public Animation getAnimation() {
		// TODO Auto-generated method stub
		return _currentAnimation;
	}

	public void setAnimation(ChickenAnimations ca) {
		switch (ca) {
		case PECKING:
			_currentAnimation = _pecking;
			_pecking.setAnimationFrame(0);
			break;
		case WALKING:
			_currentAnimation = _walking;
			_walking.setAnimationFrame(0);
			break;
		default:
			break;
		}
	}

	public enum ChickenAnimations {
		WALKING, PECKING;
	}
}
