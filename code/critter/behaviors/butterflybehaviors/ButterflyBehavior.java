package code.critter.behaviors.butterflybehaviors;

import code.critter.behaviors.IBehavior;
import code.critter.behaviors.MoveHorizontal;
import code.critter.behaviors.MoveVertical;
import example1.Critter;

public class ButterflyBehavior implements IBehavior{

	private static ButterflyBehavior _instance;
	
	private static MoveHorizontal _mh;
	private static MoveVertical _mv;
	
	private ButterflyBehavior() {
		_mh = MoveHorizontal.getInstance();
		_mv = MoveVertical.getInstance();
	}
	
	public static ButterflyBehavior getInstance(){
		return _instance == null ? _instance = new ButterflyBehavior() : _instance;
	}
	
	@Override
	public void performBehavior(Critter c) {
		// TODO Auto-generated method stub
		_mh.performBehavior(c);
		_mv.performBehavior(c);
	}

}
