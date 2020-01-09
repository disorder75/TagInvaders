package it.uniroma1.lcl.paths;

import it.uniroma1.lcl.Constants;

import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;

/**
 * Mobius strip path
 * @author bellincampi
 *
 */
public class MobiusPath extends SplinePath
{
	/**
	 * The two circles radius
	 */
	protected float radius;
	
	/**
	 * The strip orientation
	 */
	protected float angle;
	
	/**
	 * @param x x starting coordinate for the path
	 * @param y y starting coordinate for the path
	 * @param speed speed over the path
	 * @param offset point between 0 and 1 in which the entity will start
	 * @param clockwise traveling direction
	 * @param radius circles radius
	 * @param angle strip oriantation
	 */
	public MobiusPath(float x, float y, float speed, float offset, Start start, float radius, float angle)
	{
		this (x, y, speed, offset, start, Loop.NONE, radius, angle);
	}
	
	/**
	 * @param x x starting coordinate for the path
	 * @param y y starting coordinate for the path
	 * @param speed speed over the path
	 * @param offset point between 0 and 1 in which the entity will start
	 * @param clockwise traveling direction
	 * @param radius circles radius
	 * @param angle strip oriantation
	 */
	public MobiusPath(float x, float y, float radius, float angle)
	{
		this (x, y, Constants.ENTITY_PATH_SPEED, 0, Start.BEGIN, Loop.NONE, radius, angle);
	}
	
	/**
	 * @param x x starting coordinate for the path
	 * @param y y starting coordinate for the path
	 * @param speed speed over the path
	 * @param offset point between 0 and 1 in which the entity will start
	 * @param clockwise traveling direction
	 * @param loop loop type
	 * @param radius circles radius
	 * @param angle strip oriantation
	 */
	public MobiusPath(float x, float y, float speed, float offset, Start start, Loop loop, float radius, float angle)
	{
		super(x, y, speed, offset, start, loop);
		if (loop == Loop.CONTINUE) throw new PathLoopNotAllowedException("You can't have Loop.CONTINUE as loop type in a MobiusPath");
		this.radius = radius;
		this.angle = angle;
		
		// change the starting coordinates to be the center of the strip
		x -= Math.cos(Math.toRadians(angle))*radius;
		y -= Math.sin(Math.toRadians(angle))*radius;
		
		controlPoints = new Vector2[16];
		
		for (int i=0;i<8;i++)
			controlPoints[i] = new Vector2( (float) (x+Math.cos(Math.toRadians(angle-i*45))*radius),(float) (y+Math.sin(Math.toRadians(angle-i*45))*radius));
	
		
		for (int i=8;i<16;i++)
			controlPoints[i] = new Vector2( (float) (x+(Math.cos(Math.toRadians(angle))*radius*2)+Math.cos(Math.toRadians(angle+(i-4)*45))*radius),
								 (float) (y+(Math.sin(Math.toRadians(angle))*radius*2)+Math.sin(Math.toRadians(angle+(i-4)*45))*radius));
			
		spline = new CatmullRomSpline<Vector2>(controlPoints, true);
	}

}
