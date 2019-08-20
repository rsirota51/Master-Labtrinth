package code.critter.behaviors.chickenbehaviors;

import java.util.Random;

import code.critter.behaviors.IBehavior;
import code.critter.behaviors.chickenbehaviors.ChickenBehavior.ChickenBehaviors;
import example1.Chicken;
import example1.Critter;

//This is package private for constistency (ie, you cannot make a butterfly act like a chicken)
class ChickenWalkBehavior implements IBehavior {

	// where to walk to
	private int x, y;

	private ChickenBehavior _cb;

	public ChickenWalkBehavior(ChickenBehavior cb) {
		// TODO Auto-generated constructor stub
		_cb = cb;
	}

	@Override
	public void performBehavior(Critter c) {
		if (c.get_vx() == 0 && c.get_vy() == 0) {
			_cb.setBehavior(ChickenBehaviors.PECKING);
		} else {
			calculateVelocity(c);
		}
	}

	public void pickPoint(Chicken c) {
		// TODO Auto-generated method stub
		x = new Random().nextInt(c.getRightBound() - c.getLeftBound())
				+ c.getLeftBound();
		y = new Random().nextInt(c.getLowerBound() - c.getUpperBound())
				+ c.getUpperBound();

		calculateVelocity(c);
	}
	
	private void calculateVelocity(Critter c){
		if (c.getx() < x + 4 && c.getx() > x - 4) {
			c.set_vx(0);
		} else if (x < c.getx()) {
			c.set_vx(-2);
		} else if (x > c.getx()) {
			c.set_vx(2);
		}

		if (c.gety() < y + 4 && c.gety() > y - 4) {
			c.set_vy(0);
		} else if (y < c.gety()) {
			c.set_vy(-2);
		} else if (y > c.gety()) {
			c.set_vy(2);
		}
	}

}
