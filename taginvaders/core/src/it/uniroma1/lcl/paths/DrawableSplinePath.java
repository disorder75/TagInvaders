package it.uniroma1.lcl.paths;

import it.uniroma1.lcl.Assets;
import it.uniroma1.lcl.Constants;
import it.uniroma1.lcl.paths.SplinePath.Start;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;

/**
 * Pattern decorator for the SplinePaths. Adds the possibility to draw a SplinePath through the draw() method
 * @author bellincampi
 *
 */
public class DrawableSplinePath extends Path
{
	/**
	 * The path on which the entity is currently on
	 */
	protected SplinePath actualPath;
	
	/**
	 * Traveling direction
	 */
	protected Start start;
	
	/**
	 * Texure utilizzata per disegnare il percorso in fase di debug
	 */
	protected Texture dot = Assets.manager.get(Constants.DOT_TEXTURE_PATH, Texture.class);

	/**
	 * Creates a DrawableSplinePath wrapping a SplinePath
	 * @param path the actual path to wrap
	 */
	public DrawableSplinePath(SplinePath path)
	{
		super(path.x, path.y, path.getSpeed());
		this.actualPath = path;
	}
	
	/**
	 * Draws the path (for debug reasons!)
	 * @param batch the sprite batch on which the path will be drawn
	 */
	public void draw(SpriteBatch batch)
	{
		for (float i = 0; i < 1; i += 0.01)
			batch.draw(dot, actualPath.getSpline().valueAt(new Vector2(), i).x, actualPath.getSpline().valueAt(new Vector2(), i).y);
	}

	@Override
	public void updateImpl(float deltaTime)
	{
		actualPath.updateImpl(deltaTime);
	}
	
	
	@Override
	public float getX()
	{
		return actualPath.getX();
	}
	
	@Override
	public float getY()
	{
		return actualPath.getY();
	}
	
	/**
	 * Returns true if the path has been completed
	 * @return true if the path has been completed
	 */
	public boolean isComplete()
	{
		return actualPath.isComplete();
	}
	
	/**
	 * Resets the path
	 */
	public void reset()
	{
		actualPath.reset();
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
	public CatmullRomSpline<Vector2> getPath()
	{
		return actualPath.spline;
	}
	
	/**
	 * Returns the current position over the path
	 * @return the current position over the path
	 */
	public float getPosition()
	{
		return actualPath.getPosition();
	}
	
	/**
	 * Sets the position between 0 and 1 over the path
	 * @param p the position between 0 and 1 over the path
	 */
	public void setPosition(float p)
	{
		actualPath.setPosition(p);
	}

}
