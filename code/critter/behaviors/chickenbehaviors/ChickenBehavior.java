package code.critter.behaviors.chickenbehaviors;

import code.critter.behaviors.IBehavior;
import example1.Chicken;
import example1.Critter;
import example1.Chicken.ChickenAnimations;

public class ChickenBehavior implements IBehavior{
	private IBehavior _current;
	private ChickenWalkBehavior _cwb;
	private ChickenPeckBehavior _cpb;
	
	private Chicken _c;
	
	public ChickenBehavior(Chicken c) {
		// TODO Auto-generated constructor stub
		_cwb = new ChickenWalkBehavior(this);
		_cwb.pickPoint(c);
		
		_cpb = new ChickenPeckBehavior(this);
		_current = _cwb;
		_c = c;
	}
	
	@Override
	public void performBehavior(Critter c) {
		// TODO Auto-generated method stub
		_current.performBehavior(c);
	}
	
	public void setBehavior(ChickenBehaviors cb){
		switch(cb){
		case PECKING:
			_current = _cpb;
			_c.setAnimation(ChickenAnimations.PECKING);
			break;
		case WALKING:
			_current = _cwb;
			_cwb.pickPoint(_c);
			_c.setAnimation(ChickenAnimations.WALKING);
			break;
		default:
			break;
		}
	}
	public enum ChickenBehaviors{
		WALKING,PECKING;
	}
}
