package it.uniroma1.lcl.bullets;

import it.uniroma1.lcl.Direction;
import it.uniroma1.lcl.Entity;
import it.uniroma1.lcl.Level;
import it.uniroma1.lcl.PlayerShip;
import it.uniroma1.lcl.paths.Path;

public class EnemyBullet extends Bullet
{
	/**
	 * Creates a bullet with standard speed
	 * @param level level
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param d direction
	 */
	public EnemyBullet(Level level, float x, float y, Direction d)
	{
		super(level, x, y, d);
	}

	
	/**
	 * Creates a bullet moving down with standard speed
	 * @param level level
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public EnemyBullet(Level level, float x, float y)
	{
		super(level, x, y, Direction.DOWN);
	}
	

	/**
	 * @param level level
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param speed speed
	 * @param d direction
	 */
	public EnemyBullet(Level level, float x, float y, float speed, Direction d)
	{
		super(level, x, y, speed, d);
	}
	

	/**
	 * @param level level
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param speed speed
	 * @param path path
	 */
	public EnemyBullet(Level level, float x, float y, float speed, Path path)
	{
		super(level, x, y, speed, path);
	}

	public boolean kills(Entity e) { return e instanceof PlayerShip; }
}
