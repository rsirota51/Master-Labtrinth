package code.critter.behaviors;

import example1.Critter;

public class MoveVertical implements IBehavior{
	private static MoveVertical _instance;

	private MoveVertical() {
	}

	public static MoveVertical getInstance(){
		return (_instance == null) ? _instance = new MoveVertical() : _instance;
	}

	@Override
	public void performBehavior(Critter c) {
		// TODO Auto-generated method stub
		if(c.gety() + c.get_vy() < c.getUpperBound() || c.gety() + c.get_vy() > c.getLowerBound()){
			c.set_vy(-c.get_vy());
		}
	}

	
}
