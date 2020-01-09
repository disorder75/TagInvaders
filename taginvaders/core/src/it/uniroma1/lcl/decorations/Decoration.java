package it.uniroma1.lcl.decorations;

import it.uniroma1.lcl.Assets;
import it.uniroma1.lcl.Constants;
import it.uniroma1.lcl.Direction;
import it.uniroma1.lcl.Level;
import it.uniroma1.lcl.LivingEntity;
import it.uniroma1.lcl.paths.Path;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Abstract generic decoration
 * @author bellincampi
 *
 */
public abstract class Decoration extends LivingEntity
{
	/**
	 * Decoration width
	 */
	protected float width;
	
	/**
	 * Decoration height
	 */
	protected float height;
	
	/**
	 * Decoration hitbox width
	 */
	protected float hitboxWidth;
	
	/**
	 * DEcoration hitbox height
	 */
	protected float hitboxHeight;	

	/**
	 * Image scale, where 1 = original dimensions
	 */
	protected float scale;
	
	/**
	 * TextureAtlas for the standard animation
	 */
	protected TextureAtlas standardAtlas = Assets.manager.get(Constants.BUBBLE_ATLAS_PATH, TextureAtlas.class);
	
	/**
	 * Stadard animation
	 */
	protected Animation standardAnim = new Animation(0.1f, standardAtlas.getRegions(), Animation.PlayMode.LOOP);

	/**
	 * Hidden costructor
	 * @param l level
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param speed speed
	 */
	protected Decoration (Level l, float x, float y, float speed)
	{
		super(l, x, y, speed);
		width = hitboxWidth = standardAtlas.getRegions().first().getRegionWidth();
		height = hitboxHeight = standardAtlas.getRegions().first().getRegionHeight();
		scale = 0.3f+rand.nextFloat();
	}
	
	/**
	 * Creates a decoration
	 * @param l level
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param speed speed
	 * @param d direction
	 */
	public Decoration (Level l, float x, float y, float speed, Direction d)
	{
		this(l, x, y, speed);
		setDirection(d);
	}
	
	/**
	 * Creates a decoration
	 * @param l level
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param speed speed
	 * @param spline path
	 */
	public Decoration (Level l, float x, float y, float speed, Path p)
	{
		this(l, x, y, speed);
		setPath(p);
	}
	
	/**
	 * What the decoration does when is alive
	 * @param deltaTime time passed since the last frame
	 */
	@Override
	protected void aliveUpdate(float deltaTime)
	{
		if (isOutOfScreen()) level.remove(this);
	}
	
	/**
	 * What the decoration does when is dying
	 * @param deltaTime time passed since the last frame
	 */
	@Override
	protected void dyingUpdate(float deltaTime){}

	@Override
	public float getSpriteWidth()
	{
		return width;
	}
	
	@Override
	public float getSpriteHeight()
	{
		return height;
	}
	
	@Override
	public float getHitboxWidth()
	{
		return hitboxWidth;
	}
	
	@Override
	public float getHitboxHeight()
	{
		return hitboxHeight;
	}

	@Override
	public void draw(SpriteBatch batch)
	{
		batch.draw(standardAnim.getKeyFrame(stateTime), x, y, standardAnim.getKeyFrame(stateTime).getRegionWidth()*scale, 
				standardAnim.getKeyFrame(stateTime).getRegionHeight()*scale);
	}
}
