package it.uniroma1.lcl.level014;

import it.uniroma1.lcl.Constants;
import it.uniroma1.lcl.paths.Path;

/**
 * Circular path using trigonometric function
 * @author Nunzio Castelli
 *
 */
public class CirclePath014 extends Path
{
	private float x_0; 
	private float y_0; 
	private float angle; 
	private float radius;
	static private final float STEP_ANGLE = 0.500f;
	static private final float MAX_ANGLE = 360f;
	static private final float MIN_RADIUS = 0.1f;
	RadiusEnemy renemy = null;
	private boolean right_orientation;
	
	/**
	 * @param x_0 x coordinate for the circle center
	 * @param y_0 y coordinate for the circle center
	 * @param angle degree orientation
	 * @param radius circle radius
	 * false if they are the coordinates of the first point
	 */
	public CirclePath014(float x_0, float y_0, float angle, float radius, boolean right_orientation)
	{
		super(x_0, y_0, Constants.ENEMY_SPEED, Loop.NONE);
		this.x_0 = x_0;
		this.y_0 = y_0;
		this.setAngle(angle);
		this.setRadius(radius);
		this.setRight_orientation(right_orientation);
	}

	/**
	 * Draw the enemy on the circumference starting from specified angle
	 * follow the trigonometric formula:
	 * 	x= x0 + r*cos(a) / y = y0 + r*sen(a)
	 *  
	 * @param x_0 x coordinate for the circle center
	 * @param y_0 y coordinate for the circle center
	 * @param angle degree orientation
	 * @param radius circle radius
	 * false if they are the coordinates of the first point
	 */
	public void CalculatePosition(float angle, float radius)
	{
		this.x = this.x_0 + (float)Math.cos(Math.toRadians(angle))*radius;
		this.y = this.y_0 + (float)Math.sin(Math.toRadians(angle))*radius;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public float getAngle() {
		return angle;
	}

	public float getMinimRadius() {
		return MIN_RADIUS;
	}
	
	public void setAngle(float angle) {
		this.angle = angle;
	}

	public void increaseAngle() {
		if (this.angle >= MAX_ANGLE) 
			this.angle = 0;
		else 
			this.angle += STEP_ANGLE;
	}

	public void decreaseAngle() {
		if (this.angle <= 0) 
			this.angle = MAX_ANGLE;
		else 
			this.angle -= STEP_ANGLE;
	}
	
	@Override
	protected void updateImpl(float deltaTime) {
		//this.CalculatePosition(this.angle, this.radius);
	}

	@Override
	public boolean isComplete() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRight_orientation() {
		return right_orientation;
	}

	public void setRight_orientation(boolean right_orientation) {
		this.right_orientation = right_orientation;
	}
	
}
