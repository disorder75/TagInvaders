package it.uniroma1.lcl.paths;

import it.uniroma1.lcl.Constants;

import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;

/**
 * Line path
 * @author bellincampi
 *
 */
public class LinePath extends SplinePath
{
	/**
	 * Line slope
	 */
	protected float angle;
	
	/**
	 * Line length
	 */
	protected float length;
	
	/**
	 * @param x x starting coordinate for the path
	 * @param y y starting coordinate for the path
	 * @param speed speed over the path
	 * @param offset point between 0 and 1 in which the entity will start
	 * @param clockwise traveling direction
	 * @param loop loop type
	 * @param a line slope
	 * @param l line length
	 */
	public LinePath(float x, float y, float speed, float offset, Start start, Loop loop, float angle, float length)
	{
		super(x, y, speed, offset, start, loop);
		this.angle = angle;
		this.length = length;
		
		controlPoints = new Vector2[]
				{
					new Vector2(x,y),  
					new Vector2(x,y), 
					new Vector2( (float) (x+Math.cos(Math.toRadians(angle))*length),(float) (y+Math.sin(Math.toRadians(angle))*length)),
					new Vector2( (float) (x+Math.cos(Math.toRadians(angle))*length),(float) (y+Math.sin(Math.toRadians(angle))*length)),
			    };
		
		spline = new CatmullRomSpline<Vector2>(controlPoints, false);
	}
	
	/**
	 * @param x x starting coordinate for the path
	 * @param y y starting coordinate for the path
	 * @param speed speed over the path
	 * @param offset point between 0 and 1 in which the entity will start
	 * @param clockwise traveling direction
	 * @param a line slope
	 * @param l line length
	 */
	public LinePath(float x, float y, float speed, float offset, Start start, float angle, float length)
	{
		this(x, y, speed, offset, start, Loop.NONE, angle, length);
	}
	
	/**
	 * @param x x starting coordinate for the path
	 * @param y y starting coordinate for the path
	 * @param speed speed over the path
	 * @param offset point between 0 and 1 in which the entity will start
	 * @param clockwise traveling direction
	 * @param a line slope
	 * @param l line length
	 */
	public LinePath(float x, float y, float angle, float length)
	{
		this(x, y, Constants.ENTITY_PATH_SPEED, 0, Start.BEGIN, Loop.NONE, angle, length);
	}
}
