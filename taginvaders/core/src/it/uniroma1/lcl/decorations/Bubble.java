package it.uniroma1.lcl.decorations;

import it.uniroma1.lcl.Assets;
import it.uniroma1.lcl.Constants;
import it.uniroma1.lcl.Direction;
import it.uniroma1.lcl.Level;
import it.uniroma1.lcl.paths.Path;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Phial bubbles
 * @author bellincampi
 *
 */
public class Bubble extends Decoration
{
	/**
	 * Hidden bubble
	 * @param l level
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param speed speed
	 */
	protected Bubble (Level l, float x, float y, float speed)
	{
		super(l, x, y, speed);
		standardAtlas = Assets.manager.get(Constants.BUBBLE_ATLAS_PATH, TextureAtlas.class);
		standardAnim = new Animation(0.1f, standardAtlas.getRegions(), Animation.PlayMode.LOOP);
		width = hitboxWidth = standardAtlas.getRegions().first().getRegionWidth();
		height = hitboxHeight = standardAtlas.getRegions().first().getRegionHeight();
	}
	
	/**
	 * Creates a bubble
	 * @param l level
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param speed speed
	 * @param d direction
	 */
	public Bubble (Level l, float x, float y, float speed, Direction d)
	{
		this(l, x, y, speed);
		setDirection(d);
	}
	
	/**
	 * Creates a bubble
	 * @param l level
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param speed speed
	 * @param path path
	 */
	public Bubble (Level l, float x, float y, float speed, Path p)
	{
		this(l, x, y, speed);
		setPath(p);
	}
	
	public void update(float deltaTime)
	{
		super.update(deltaTime);

		if (y > Constants.HEIGHT) level.remove(this);
	}

}
