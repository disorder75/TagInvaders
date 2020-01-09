package it.uniroma1.lcl.decorations;

import it.uniroma1.lcl.Assets;
import it.uniroma1.lcl.Constants;
import it.uniroma1.lcl.Direction;
import it.uniroma1.lcl.Level;
import it.uniroma1.lcl.paths.Path;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Esplosioni
 * @author bellincampi
 *
 */
public class Explosion extends Decoration
{
	public static final int BUBBLES_NUMBER = 6;
	
	/**
	 * Creates an explosion
	 * @param l level
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param speed speed
	 */
	public Explosion(Level l, float x, float y, float speed)
	{
		super(l, x, y, speed);
		standardAtlas = Assets.manager.get(Constants.EXPLOSION_ATLAS_PATH, TextureAtlas.class);
		standardAnim = new Animation(0.05f, standardAtlas.getRegions());
		
		// spawn bubbles
		for(int i=0; i<BUBBLES_NUMBER; i++)
		level.add (new Bubble(level, x, y, 1+rand.nextInt(3), Direction.UP));
	}
	
	/**
	 * Creates an explosion
	 * @param l level
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param speed speed
	 * @param d direction
	 */
	public Explosion (Level l, float x, float y, float speed, Direction d)
	{
		this(l, x, y, speed);
		setDirection(d);
	}
	
	/**
	 * Creates an explosion
	 * @param l level
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param speed speed
	 * @param path path
	 */
	public Explosion (Level l, float x, float y, float speed, Path p)
	{
		this(l, x, y, speed);
		setPath(p);
	}
	public void update(float deltaTime)
	{
		super.update(deltaTime);
		
		if (standardAnim.getKeyFrames().length < (int)(stateTime / standardAnim.getFrameDuration())) level.remove(this);
		
	}

}
