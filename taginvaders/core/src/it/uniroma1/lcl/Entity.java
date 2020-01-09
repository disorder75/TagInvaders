package it.uniroma1.lcl;

import it.uniroma1.lcl.paths.Path;
import it.uniroma1.lcl.paths.SimplePath;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Rectangle;

/**
 * Basic modeling of any entity in the game
 * @author navigli
 *
 */

abstract public class Entity
{
	/**
	 * Pseudorandom numbers generator
	 */
	protected RandomXS128 rand = new RandomXS128();
	
	/**
	 * Game level in which the entity is right now
	 */
	final protected Level level;
	
	/**
	 * Entity's x coordinate
	 */
	protected float x;
	
	/**
	 * Entity's y coordinate
	 */
	protected float y;
	
	/**
	 * Speed
	 */
	protected float speed;
	
	/**
	 * Path the entity has been assigned to
	 */
	protected Path path;
	
	/**
	 * Time passed from the beginning of the game
	 */
	protected float stateTime;
	
	/**
	 * Entity constructor
	 * @param x x starting coordinate
	 * @param y y starting coordinate
	 * @param speed starting speed
	 */
	protected Entity(Level l, float x, float y, float speed)
	{
		this.level = l;
		this.x = x;
		this.y = y;
		this.speed = speed;
	}
	
	/**
	 * Entity constructor
	 * @param x x starting coordinate
	 * @param y y starting coordinate
	 * @param speed starting speed
	 * @param d direction
	 */
	public Entity(Level l, float x, float y, float speed, Direction d)
	{
		this(l, x, y, speed);
		setDirection(d);
	}
	
	/**
	 * Entity constructor
	 * @param x x starting coordinate
	 * @param y y starting coordinate
	 * @param speed starting speed
	 * @param path path
	 */
	public Entity(Level l, float x, float y, float speed, Path path)
	{
		this(l, x, y, speed);
		setPath(path);
	}
	
	/**
	 * Entity constructor
	 * @param x x starting coordinate
	 * @param y y starting coordinate
	 */
	public Entity(Level l, float x, float y)
	{
		this(l, x, y, Constants.DEFAULT_ENTITY_SPEED, Direction.IDLE);
	}
	
	/**
	 * Updates the entity
	 * It's mandatory to call super.update(deltaTime) in the subclasses for the animation to work
	 * correcty.
	 * @param deltaTime time passed since the last frame
	 */
	public void update(float deltaTime)
	{
		stateTime += deltaTime;
		updatePath(deltaTime);
	}
	
	/**
	 * Updates the corordinates for the entity
	 * @param deltaTime time passed since the last frame
	 */
	protected void updatePath(float deltaTime)
	{
		if (usesPath())
		{
			path.update(deltaTime);
		
			// update the x,y position
			x = path.getX();
			y = path.getY();
		}
	}
	
	/**
	 * Returns true if the entity uses a path
	 * @return true if the entity uses a path
	 */
	protected boolean usesPath() { return true; }
	
	/**
	 * Returns the x coordinate of the entity
	 * @return x coordinate
	 */
	public float getX() { return x; }

	/**
	 * Returns the y coordinate of the entity
	 * @return y coordinate
	 */
	public float getY() { return y; }

	/**
	 * Returns the width of the sprite of the entity
	 * @return the width of the sprite of the entity
	 */
	abstract public float getSpriteWidth();

	/**
	 * Returns the height of the sprite of the entity
	 * @return the height of the sprite of the entity
	 */
	abstract public float getSpriteHeight();
	
	/**
	 * Returns the width of the sprite of the hitbox
	 * @return the width of the sprite of the hitbox
	 */
	abstract public float getHitboxWidth();

	/**
	 * Returns the height of the sprite of the hitbox
	 * @return the height of the sprite of the hitbox
	 */
	abstract public float getHitboxHeight();

	/**
	 * Draws the entity on the sprite batch
	 * @param batch the batch on which the entity will be drawn
	 */
	abstract public void draw(SpriteBatch batch);

	/**
	 * Sets the movement direction of the enemy
	 * @param d new direction
	 */
	public void setDirection(Direction d)
	{
		setPath(new SimplePath(x, y, speed, d));
	}
	
	/**
	 * Returns the current movement direction
	 * @return
	 */
	public Direction getDirection()
	{
		return path.getDirection();
	}
	
	/**
	 * Returns the hitbox (x, y, hitbox_width, hitbox_height)
	 * @return the hitbox
	 */
	public Rectangle getHitbox()
	{
		float widthSpan = getSpriteWidth()-getHitboxWidth();
		float heightSpan = getSpriteHeight()-getHitboxHeight();
		return new Rectangle(x+widthSpan/2, y+heightSpan/2, getHitboxWidth(), getHitboxHeight());
	}

	/**
	 * Checks the collision with another entity
	 */
	public boolean collideWith(Entity e)
	{
		return e.getHitbox().overlaps(getHitbox());
	}
	
	/**
	 * Sets the speed
	 * @param s new speed
	 */
	public void setSpeed(float s)
	{
		speed = s;
		path.setSpeed(s);
	}
	
	/**
	 * Returns the speed
	 * @return speed of the entity
	 */
	public float getSpeed()
	{
		return speed;
	}
	
	/**
	 * Sets a path for the entity
	 * @param p the path to set
	 */
	public void setPath(Path p)
	{
		path = p;
	}
	
	/**
	 * Returns true if the entity is out of the playble area, false otherwise
	 * @return true if the entity is out of the playble area
	 */
	public boolean isOutOfScreen()
	{
		if (x > Constants.WIDTH || x < 0 || y > Constants.HEIGHT || y < 0) return true;
		else return false;
	}
}
