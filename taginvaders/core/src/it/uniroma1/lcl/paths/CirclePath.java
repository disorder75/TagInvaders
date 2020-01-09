package it.uniroma1.lcl.paths;

import it.uniroma1.lcl.Constants;

import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;


/**
 * Circular path
 * @author bellincampi
 *
 */
public class CirclePath extends SplinePath
{
	/**
	 * Circle radius
	 */
	protected float radius;
	
	/**
	 * @param x x starting coordinate for the path
	 * @param y y starting coordinate for the path
	 * @param speed speed over the path
	 * @param offset point between 0 and 1 in which the entity will start
	 * @param clockwise traveling direction
	 * @param radius circle radius
	 * @param centered true if the x, y coordinates are the center of the circle
	 * false if they are the cordinates of the first point
	 */
	public CirclePath(float centerX, float centerY, float speed, float offset, Start start, float radius, boolean centered)
	{
		this(centerX, centerY, speed, offset, start, Loop.NONE, radius, centered);
	}
	
	/**
	 * @param x x starting coordinate for the path
	 * @param y y starting coordinate for the path
	 * @param speed speed over the path
	 * @param offset point between 0 and 1 in which the entity will start
	 * @param clockwise traveling direction
	 * @param radius circle radius
	 * @param centered true if the x, y coordinates are the center of the circle
	 * false if they are the cordinates of the first point
	 */
	public CirclePath(float centerX, float centerY, float radius, boolean centered)
	{
		this(centerX, centerY, Constants.ENTITY_PATH_SPEED, 0, Start.BEGIN, Loop.NONE, radius, centered);
	}
	
	/**
	 * @param x x starting coordinate for the path
	 * @param y y starting coordinate for the path
	 * @param speed speed over the path
	 * @param offset point between 0 and 1 in which the entity will start
	 * @param clockwise traveling direction
	 * @param loop loop type
	 * @param radius circle radius
	 * @param centered true if the x, y coordinates are the center of the circle
	 * false if they are the cordinates of the first point
	 */
	public CirclePath(float centerX, float centerY, float speed, float offset, Start start, Loop loop, float radius, boolean centered)
	{
		super(centerX, centerY, speed, offset, start, loop);
		if (loop == Loop.CONTINUE) throw new PathLoopNotAllowedException("You can't have Loop.CONTINUE as loop type in a CirclePath");
		this.radius = radius;
		if (!centered) x -= radius;
		controlPoints = new Vector2[8];
		
		for (int i=0;i<8;i++)
			controlPoints[i] = new Vector2((float) (x+Math.cos(Math.toRadians(-i*45))*radius),(float) (y+Math.sin(Math.toRadians(-i*45))*radius));
		
		spline = new CatmullRomSpline<Vector2>(controlPoints, true);
	}
	
	/**
	 * Returns the circle path radius
	 * @return the circle path radius
	 */
	public float getRadius()
	{
		return radius;
	}
}
