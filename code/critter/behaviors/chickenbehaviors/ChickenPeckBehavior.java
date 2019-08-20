package code.critter.behaviors.chickenbehaviors;

import code.critter.behaviors.IBehavior;
import code.critter.behaviors.chickenbehaviors.ChickenBehavior.ChickenBehaviors;
import example1.Critter;

class ChickenPeckBehavior implements IBehavior{
	private ChickenBehavior _cb;
	
	public ChickenPeckBehavior(ChickenBehavior chickenBehavior) {
		// TODO Auto-generated constructor stub
		_cb = chickenBehavior;
	}

	@Override
	public void performBehavior(Critter c) {
		// TODO Auto-generated method stub
		c.set_vx(0);
		c.set_vy(0);
		
		if(!c.getAnimation().hasNext()){
			_cb.setBehavior(ChickenBehaviors.WALKING);
		}
	}

}
