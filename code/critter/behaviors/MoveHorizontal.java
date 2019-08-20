package code.critter.behaviors;

import example1.Critter;

public class MoveHorizontal implements IBehavior{
	private static MoveHorizontal _instance;
	
	private MoveHorizontal() {
		// TODO Auto-generated constructor stub
	}
	
	public static MoveHorizontal getInstance(){
		return _instance == null ? _instance = new MoveHorizontal() : _instance;
	}
	
	@Override
	public void performBehavior(Critter c) {
		// TODO Auto-generated method stub
		if(c.getx() + c.get_vx() <= c.getLeftBound() || c.getx() + c.get_vx() >= c.getRightBound()){
			c.set_vx(-c.get_vx());
		}
	}

}
