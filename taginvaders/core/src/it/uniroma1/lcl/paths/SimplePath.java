package it.uniroma1.lcl.paths;

import it.uniroma1.lcl.Constants;
import it.uniroma1.lcl.Direction;

/**
 * Linear path defined through a Direction
 * @author bellincampi
 *
 */
public class SimplePath extends Path
{	
	/**
	 * Line path direction
	 */
	protected Direction direction;
	
	/**
	 * Length of the path
	 */
	protected float length;
	
	/**
	 * Creates a path starting from a Direction
	 * @param x x starting coordinate for the path
	 * @param y y starting coordinate for the path
	 * @param speed speed over the path
	 * @param direction direction
	 */
	public SimplePath(float x, float y, float speed, Direction direction)
	{
		super(x,y,speed);
		this.direction = direction;

	}
	
	@Override
	public void updateImpl(float deltaTime)
	{

		x += speed*deltaTime*Constants.IDEAL_FPS*direction.getX();
		y += speed*deltaTime*Constants.IDEAL_FPS*direction.getY();

	}

	/**
	 * Returns the direction of the SimplePath
	 * @return the direction of the SimplePath
	 */
	public Direction getDirection()
	{
		return direction;
	}
	
	@Override
	public boolean isComplete()
	{
		return false;
	}
}
