package it.uniroma1.lcl.level00x;

import it.uniroma1.lcl.Constants;
import it.uniroma1.lcl.Direction;
import it.uniroma1.lcl.Enemy;
import it.uniroma1.lcl.Level;
import it.uniroma1.lcl.WorldController;

/**
 * Level00x - Secret level with differente wavers
 * @author navigli
 *
 */
public class Level00x extends Level
{
	public Level00x(WorldController wc)
	{
		super(wc, "level00x", "Secret LEvel!", "Roberto Navigli", Constants.YELLOW);
	}
	
	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);
		
		// spawn the next wave
		if (rand.nextInt(100) == 42) newEnemyWave();
	}
	
	@Override
	protected void init()
	{
		newEnemyWave();
	}
	
	/**
	 * Spawn the next enemy wave
	 */
	private void newEnemyWave()
	{
		Direction d;
		if (rand.nextBoolean()) d = Direction.LEFT;
		else d = Direction.RIGHT;
		
		float y = rand.nextInt(400)+100;
		float speed = rand.nextInt(100)/10.0f+1;
		
		addEnemyWave(rand.nextInt(5)+1, y, speed, d);
	}
	
	/**
	 * Adds an enemy wave
	 * @param enemyNumber enemies number
	 * @param y y coordinate
	 * @param speed speed
	 * @param d direction
	 */
	private void addEnemyWave(int enemyNumber, float y, float speed, Direction d)
	{
		float x;
		float sign = -1;
		
		if (d == Direction.RIGHT) x = -200;
		else { x = Constants.WIDTH+200; sign = +1; }
		
		for (int k = 0; k < enemyNumber; k++)
		{
			Enemy e = new Enemy(this, x+sign*k*50, y, d);
			e.setSpeed(speed);
			add(e);
		}
	}

	public Rank getRank()
	{
		return Rank.A;
	}
}
