package it.uniroma1.lcl.paths;

import it.uniroma1.lcl.Constants;

import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;

/**
 * Rectangle Path
 * @author bellincampi
 *
 */
/**
 *
 * @author bellincampi
 *
 */
public class RectanglePath extends SplinePath
{
	/**
	 * Rectangle base
	 */
	protected float base;

	/**
	 * Rectangle height
	 */
	protected float height;

	/**
	 * @param x x starting coordinate for the path
	 * @param y y starting coordinate for the path
	 * @param speed speed over the path
	 * @param offset point between 0 and 1 in which the entity will start
	 * @param clockwise traveling direction
	 * @param bs rectangle base
	 * @param h rectangle height
	 */
	public RectanglePath(float x, float y, float speed, float offset, Start start, float bs, float h)
	{
		this(x, y, speed, offset, start, Loop.NONE, bs, h);
	}
	
	/**
	 * @param x x starting coordinate for the path
	 * @param y y starting coordinate for the path
	 * @param speed speed over the path
	 * @param offset point between 0 and 1 in which the entity will start
	 * @param clockwise traveling direction
	 * @param bs rectangle base
	 * @param h rectangle height
	 */
	public RectanglePath(float x, float y, float bs, float h)
	{
		this(x, y, Constants.ENTITY_PATH_SPEED, 0, Start.BEGIN, Loop.NONE, bs, h);
	}
	
	/**
	 * @param x x starting coordinate for the path
	 * @param y y starting coordinate for the path
	 * @param speed speed over the path
	 * @param offset point between 0 and 1 in which the entity will start
	 * @param clockwise traveling direction
	 * @param loop loop type
	 * @param bs rectangle base
	 * @param h rectangle height
	 */
	public RectanglePath(float x, float y, float speed, float offset, Start start, Loop loop, float bs, float h)
	{
		super(x, y, speed, offset, start, loop);
		if (loop == Loop.CONTINUE) throw new PathLoopNotAllowedException("You can't have Loop.CONTINUE as loop type in a RectanglePath");
		this.base = bs;
		this.height = h;
		
		controlPoints = new Vector2[]
				{
				new Vector2(x,y), new Vector2(x+base,y), new Vector2(x+base,y-height), new Vector2(x,y-height)
			    };
		
		spline = new CatmullRomSpline<Vector2>(controlPoints, true);
	}
}
