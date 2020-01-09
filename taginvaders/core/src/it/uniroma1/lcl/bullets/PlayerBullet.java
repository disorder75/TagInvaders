package it.uniroma1.lcl.bullets;

import it.uniroma1.lcl.Assets;
import it.uniroma1.lcl.Constants;
import it.uniroma1.lcl.Direction;
import it.uniroma1.lcl.Enemy;
import it.uniroma1.lcl.Entity;
import it.uniroma1.lcl.Level;
import it.uniroma1.lcl.paths.Path;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;


public class PlayerBullet extends Bullet
{	
	/**
	 * Hidden constructor
	 * @param level level
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param speed speed
	 */
	protected PlayerBullet(Level level, float x, float y, float speed)
	{
		super(level, x, y, speed);
		standardAtlas = Assets.manager.get("graphic/sprites/player_bullet.txt", TextureAtlas.class);
		standardAnim = new Animation(0.1f, standardAtlas.getRegions(), Animation.PlayMode.NORMAL);
	}

	/**
	 * @param level level
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param speed speed 
	 * @param d direction
	 */
	public PlayerBullet(Level level, float x, float y, float speed, Direction d)
	{
		this(level, x, y, speed);
		setDirection(d);

	}
	
	/**
	 * @param level level
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param speed speed
	 * @param path path
	 */
	public PlayerBullet(Level level, float x, float y, float speed, Path path)
	{
		this(level, x, y, speed);
		setPath(path);
	}
	
	/**
	 * Creates a bullet with standard speed
	 * @param level level
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param d direction
	 */
	public PlayerBullet(Level level, float x, float y, Direction d)
	{
		this(level, x, y, Constants.DEFAULT_BULLET_SPEED);
	}
	
	/**
	 * Creates a bullet going up with standard speed
	 * @param level level
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public PlayerBullet(Level level, float x, float y)
	{
		this(level, x, y, Constants.DEFAULT_BULLET_SPEED,  Direction.UP);
	}

	public boolean kills(Entity e) { return e instanceof Enemy; }
}
