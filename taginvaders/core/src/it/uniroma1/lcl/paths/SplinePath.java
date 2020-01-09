package it.uniroma1.lcl.paths;

import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;

/**
 * Path created using splines
 * @author bellincampi
 *
 */
public abstract class SplinePath extends Path
{
	
	/**
	 * Speed modifier to adjust the speed of the entity over the path
	 */
	private final static float SPEED_MODIFIER = 50f;
	
	/**
	 * The journey direction: begin-to-end or end-to-begin
	 */
	public enum Start
	{
		BEGIN,
		END;
	}
	
	/**
	 * Type of path loop
	 */
	protected Start start;
	
	/**
	 * Vector containing the control points for the spline
	 */
	protected Vector2[] controlPoints;
	
	/**
	 * Output vector for the spline derivative
	 */
	protected Vector2 out;
	
	/**
	 * Point between 0 and 1 in which the entity will start pathing
	 */
	protected float offset;

	/**
	 * The spline representing the path
	 */
	protected CatmullRomSpline<Vector2> spline;
	
	/**
	 * @param x x starting coordinate for the path
	 * @param y y starting coordinate for the path
	 * @param speed speed over the path
	 * @param offset point between 0 and 1 in which the entity will start
	 * @param start traveling direction
	 */
	public SplinePath(float x, float y, float speed, float offset, Start start)
	{
		this(x, y, speed, offset, start, Loop.NONE);
	}
	
	/**
	 * @param x x starting coordinate for the path
	 * @param y y starting coordinate for the path
	 * @param speed speed over the path
	 * @param offset point between 0 and 1 in which the entity will start
	 * @param start traveling direction
	 */
	public SplinePath(float x, float y, float speed, float offset, Start start, Loop loop)
	{
		super(x, y, speed, loop);
		this.offset = offset;
		this.start = start;
		
		if (isBeginToEnd()) this.position = offset;
		else this.position = 1-offset;
	}
	
	@Override
	public void updateImpl(float deltaTime)
	{
		// output vector for the derivative
		out = new Vector2(); 
		spline.derivativeAt(out, position);

		if (isBeginToEnd())
			position += (deltaTime*speed*SPEED_MODIFIER) / out.len();
		else 
			position -= (deltaTime*speed*SPEED_MODIFIER) / out.len();
		
		x = getSpline().valueAt(out, position).x;
		y = getSpline().valueAt(out, position).y;
		
		if (isComplete()) 
		{
			switch (loop)
			{
				case NONE: position = isBeginToEnd() ? 1 : 0; break;
				
				case RESTART: reset(); break;
				
				case FLIP: reverseStart();  reset(); break;
				
				case CONTINUE: 
					for (int i=0; i< controlPoints.length; i++)
					{
						controlPoints[i].x += x - startX;
						controlPoints[i].y += y - startY;
					}
					
					startX = controlPoints[0].x;
					startY = controlPoints[0].y;
					
					spline.set(controlPoints, spline.continuous);
					reset(); 
				break;
			}
		}
	}

	
	/**
	 * Returns true if the path has been completed
	 * @return true if the path has been completed
	 */
	public boolean isComplete()
	{
		if (isBeginToEnd())
			return position >= 1;
		else 
			return position <= 0;
	}

	
	/**
	 * Resets the path
	 */
	public void reset()
	{
		if (isBeginToEnd()) position = 0;
		else position = 1;
		
		x = getSpline().valueAt(new Vector2(), position).x;
		y = getSpline().valueAt(new Vector2(), position).y;
	}

	/**
	 * Sets the traveling direction to begin-to-end or end-to-begin
	 * @param s the traveling direction
	 */
	public void setStart(Start s)
	{
		start = s;
	}
	
	/**
	 * Reverse the traveling direction
	 */
	public void reverseStart()
	{
		if (start == Start.BEGIN) start = Start.END;
		else start = Start.BEGIN;
	}
	
	/**
	 * Returns true if the traveling direction is begin-to-end, false if it is end-to-begin
	 * @return true if the traveling direction is begin-to-end
	 */
	public boolean isBeginToEnd()
	{
		if (start == Start.BEGIN) return true;
		else return false;
	}
	
	/**
	 * Returns the spline representing the path
	 * @return the spline representing the path
	 */
	public CatmullRomSpline<Vector2> getSpline()
	{
		return spline;
	}
	
	/**
	 * Returns the current position over the path
	 * @return the current position over the path
	 */
	public float getPosition()
	{
		return position;
	}
	
	/**
	 * Sets the position between 0 and 1 over the path
	 * @param p the position between 0 and 1 over the path
	 */
	public void setPosition(float p)
	{
		position = p;
	}
	
	public Vector2[] getControlPoints()
	{
		return controlPoints;
	}
	
	public void setControlPoints(Vector2[] cp)
	{
		controlPoints = cp;

		startX = controlPoints[0].x;
		startY = controlPoints[0].y;
		
		spline.set(controlPoints, getSpline().continuous);
		reset(); 
	}
}
