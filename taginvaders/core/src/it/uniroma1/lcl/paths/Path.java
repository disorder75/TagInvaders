package it.uniroma1.lcl.paths;

import it.uniroma1.lcl.Direction;

import com.badlogic.gdx.math.Vector2;


/**
 * Abstract class to define a path for an entity
 * @author bellincampi
 *
 */
public abstract class Path
{
	/**
	 * The end action for the path
	 */
	public enum Loop
	{
		NONE,
		RESTART,
		FLIP,
		CONTINUE;
		
		public boolean isNone() { return this == NONE; }
	}
	
	/**
	 * X coordinate of the next position of the entity over the path
	 */
	protected float x;
	
	/**
	 * YX coordinate of the next position of the entity over the path
	 */
	protected float y;
	
	/**
	 * Type of path loop
	 */
	protected Loop loop;
	
	/**
	 * X coordinate of the starting position of the entity over the path
	 */
	protected float startX;
	
	/**
	 * X coordinate of the starting position of the entity over the path
	 */
	protected float startY;
	
	/**
	 * X coordinate of the prev position of the entity over the path
	 */
	protected float prevX;
	
	/**
	 * X coordinate of the prev position of the entity over the path
	 */
	protected float prevY;

	/**
	 * Entity speed over the path
	 */
	protected float speed;
	
	/**
	 * Position between 0 and 1 of the entity over the path
	 */
	protected float position;
	
	/**
	 * Creates a path
	 * @param x x starting coordinate for the path
	 * @param y y starting coordinate for the path
	 * @param speed speed over the path
	 */
	public Path(float x, float y, float speed)
	{
		this(x, y, speed, Loop.NONE);
	}
	
	/**
	 * Creates a path
	 * @param x x starting coordinate for the path
	 * @param y y starting coordinate for the path
	 * @param speed speed over the path
	 * @param l loop type
	 */
	public Path(float x, float y, float speed, Loop l)
	{
		this.x = x;
		this.y = y;
		this.startX = x;
		this.startY = y;
		this.speed = speed;
		this.loop = l;
	}
	
	/**
	 * Update the path logic and variables
	 */
	protected abstract void updateImpl(float deltaTime);

	public void update(float deltaTime)
	{
		prevX = x;
		prevY = y;
		updateImpl(deltaTime);
	}

	/**
	 * Returns the x coordinate that the entity on the path must be in 
	 * in the next step
	 * @return the next x coordinate for the entity on the path
	 */
	public float getX()
	{
		return x;
	}
	
	/**
	 * Returns the y coordinate that the entity on the path must be in 
	 * in the next step
	 * @return the next y coordinate for the entity on the path
	 */
	public float getY()
	{
		return y;
	}
	
	/**
	 * Returns the speed over the path
	 * @return the speed over the path
	 */
	public float getSpeed()
	{
		return speed;
	}
	
	/**
	 * Set the speed over the path
	 * @param speed the new speed
	 */
	public void setSpeed(float speed)
	{
		this.speed = speed;
	}
	
	/**
	 * Returns the current path direction
	 * @return path direction as a vector
	 */
	public Direction getDirection()
	{
		Vector2 v = new Vector2(x-prevX, y-prevY);
		float len = v.len();
		return new Direction(v.x/len, v.y/len);
	}
	
	/**
	 * Returns the loop type
	 * @return the loop type
	 */
	public Loop getLoopType()
	{
		return loop;
	}
	
	/**
	 * Sets the loop type
	 * @param l the loop type
	 */
	public void setLoopType(Loop l)
	{
		loop = l;
	}
	
	/**
	 * Returns the x starting coordinate
	 * @return the x starting coordinate
	 */
	public float getStartX()
	{
		return startX;
	}
	
	/**
	 * Returns the y starting coordinate
	 * @return the y starting coordinate
	 */
	public float getStartY()
	{
		return startY;
	}
	
	/**
	 * Sets the x starting coordinate
	 * @param x the x starting coordinate
	 */
	public void setStartX(float x)
	{
		startX = x;
	}
	
	/**
	 * Sets the y starting coordinate
	 * @param y the y starting coordinate
	 */
	public void setStartY(float y)
	{
		startX = y;
	}
	
	public abstract boolean isComplete();
}
