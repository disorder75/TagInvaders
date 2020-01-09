package it.uniroma1.lcl.bullets;

import it.uniroma1.lcl.Assets;
import it.uniroma1.lcl.Barrier;
import it.uniroma1.lcl.Constants;
import it.uniroma1.lcl.Direction;
import it.uniroma1.lcl.Enemy;
import it.uniroma1.lcl.Entity;
import it.uniroma1.lcl.Level;
import it.uniroma1.lcl.LivingEntity;
import it.uniroma1.lcl.PlayerShip;
import it.uniroma1.lcl.paths.Path;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Abstract bullet class
 * @author bellincampi
 *
 */
public abstract class Bullet extends LivingEntity
{
	/**
	 * TextureAlas for the standard animation
	 */
	protected TextureAtlas standardAtlas = Assets.manager.get(Constants.ENEMY_BULLET_ATLAS_PATH, TextureAtlas.class);
	
	/**
	 * Standard Animation
	 */
	protected Animation standardAnim = new Animation(0.03f, standardAtlas.getRegions(), Animation.PlayMode.LOOP);
	
	/**
	 * Bullet hitbox width
	 */
	protected float hitboxWidth = standardAtlas.getRegions().first().getRegionWidth();
	
	/**
	 * Bullet hitbox height
	 */
	protected float hitboxHeight = standardAtlas.getRegions().first().getRegionHeight();

	/**
	 * Constructor for the bullet
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param speed speed
	 */
	protected Bullet(Level level, float x, float y, float speed)
	{
		super(level, x, y, speed);
	}
	
	/**
	 * Constructor for the bullet with standard speed
	 * @param x x coordinate
	 * @param y y coordinate
	 * @direction d direction
	 */
	public Bullet(Level level, float x, float y, Direction d)
	{
		this(level, x, y, Constants.DEFAULT_BULLET_SPEED, d);
	}

	/**
	 * Constructor for the bullet
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param speed speed
	 * @direction d direction
	 */
	public Bullet(Level level, float x, float y, float speed, Direction d)
	{
		super(level, x, y, speed, d);
	}
	
	/**
	 * Constructor for the bullet
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param speed speed
	 * @direction path path
	 */
	public Bullet(Level level, float x, float y, float speed, Path path)
	{
		super(level, x, y, speed, path);
	}
	
	@Override
	public float getSpriteWidth() { return standardAtlas.getRegions().first().getRegionWidth(); }
	
	@Override
	public float getSpriteHeight() { return standardAtlas.getRegions().first().getRegionHeight(); }
	
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

	/**
	 * Checks the collision with another entity 
	 */
	public boolean collideWith(Entity e)
	{
		if (kills(e))
			return e.getHitbox().overlaps(getHitbox());
		return false;
	}
	
	/**
	 * True if the input entity can be destroyed by the bullet
	 * @param e entity to check
	 * @return true if the bullet can destroy the entity
	 */
	abstract public boolean kills(Entity e);
	
	/**
	 * Draw the bullet
	 * @param batch the sprite batch on which the bullet will be drawn
	 */
	public void draw(SpriteBatch batch)
	{
		batch.draw(standardAnim.getKeyFrame(stateTime), x, y);
	}
	
	/**
	 * Handle the collision with an enemy
	 * @param e enemy
	 */
	public void onCollide(Enemy e)
	{
		if (kills(e)) level.remove(this);
	}

	/**
	 * Handle the collision with a player ship
	 * @param s the player ship
	 */
	public void onCollide(PlayerShip s)
	{
		if (kills(s)) level.remove(this);
	}
	
	/**
	 * Handles the collision with a barrier
	 * @param b the barrier the bullet collides with
	 */
	public void onCollide(Barrier b)
	{
		level.remove(this);
	}
	
	/**
	 * What the bullet does when is alive
	 * @param deltaTime time passed since the last frame
	 */
	@Override
	protected void aliveUpdate(float deltaTime){}
	
	/**
	 * What the bullet does when is dying
	 * @param deltaTime time passed since the last frame
	 */
	@Override
	protected void dyingUpdate(float deltaTime){}
}
