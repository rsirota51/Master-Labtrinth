package example1;

import code.critter.behaviors.IBehavior;
import engine.Animation;
import engine.Entity;

public abstract class Critter implements Entity {
	private int _x;
	private int _y;
	private int _vx;
	private int _vy;
	private int leftBound,rightBound,upperBound,lowerBound;

	public int getLeftBound() {
		return leftBound;
	}

	public void setLeftBound(int leftBound) {
		this.leftBound = leftBound;
	}

	public int getRightBound() {
		return rightBound;
	}

	public void setRightBound(int rightBound) {
		this.rightBound = rightBound;
	}

	public int getUpperBound() {
		if (this instanceof Butterfly)
			return upperBound - 100;
		return upperBound;
	}

	public void setUpperBound(int upperBound) {
		this.upperBound = upperBound;
	}

	public int getLowerBound() {
		return lowerBound;
	}

	public void setLowerBound(int lowerBound) {
		this.lowerBound = lowerBound;
	}
	
	abstract public Animation getAnimation();

	private IBehavior _behavior;

	private boolean started;

	public Critter(IBehavior ib) {
		started = false;
		_behavior = ib;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if (_behavior != null && started) {
			_behavior.performBehavior(this);
			
			_x += _vx;
			_y += _vy;
		}

	}
	
	public void setBehavior(IBehavior ib){
		_behavior = ib;
	}

	public void stop() {
		started = false;
	}

	public void start() {
		started = true;
	}

	@Override
	public int getx() {
		return _x;
	}

	public void set_x(int _x) {
		this._x = _x;
	}

	@Override
	public int gety() {
		return _y;
	}

	public void set_y(int _y) {
		this._y = _y;
	}

	public int get_vx() {
		return _vx;
	}

	public void set_vx(int _vx) {
		this._vx = _vx;
	}

	public int get_vy() {
		return _vy;
	}

	public void set_vy(int _vy) {
		this._vy = _vy;
	}
	
	public boolean isStarted(){
		return started;
	}

}
