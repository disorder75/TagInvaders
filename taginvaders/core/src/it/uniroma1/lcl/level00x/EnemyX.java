package it.uniroma1.lcl.level00x;

import it.uniroma1.lcl.Constants;
import it.uniroma1.lcl.Direction;
import it.uniroma1.lcl.Enemy;
import it.uniroma1.lcl.Level;

/**
 * Enemy that can go out of the screen
 * @author navigli
 *
 */
public class EnemyX extends Enemy
{
	public EnemyX(Level l, float x, float y, Direction d)
	{
		super(l, x, y, d);
	}
	
	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);
		
		// remove the enemy if it's really out of the level
		if (x > Constants.WIDTH*2 || x < -Constants.WIDTH)
			level.remove(this);
	}
}
