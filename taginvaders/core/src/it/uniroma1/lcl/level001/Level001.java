package it.uniroma1.lcl.level001;

import it.uniroma1.lcl.Barrier;
import it.uniroma1.lcl.Constants;
import it.uniroma1.lcl.Direction;
import it.uniroma1.lcl.Enemy;
import it.uniroma1.lcl.Level;
import it.uniroma1.lcl.SpaceInvadersEnemy;
import it.uniroma1.lcl.WorldController;

/**
 * Level 001: Classic Space Invaders
 * 
 * The classic space invaders!
 * 
 * @author navigli
 */
public class Level001 extends Level
{
	static public final int BORDER = 250;
	static public final int STRIPE_DISTANCE = 50;
	static public final int BARRIER_DISTANCE = 130;
	static public final int BARRIER_STRIPE_DISTANCE = 80;
	static public final int BARRIER_Y = 100;
	
	/**
	 * @param wc: worldController
	 */
	public Level001(WorldController wc)
	{
		super(wc, "Level 001", "Classic Invaders", "Roberto Navigli", Constants.YELLOW);
	}
	
	@Override
	protected void init()
	{
		// for every vertical layer
		for (int y = BORDER; y < Constants.HEIGHT-BORDER; y += STRIPE_DISTANCE)
		{
			// 8 enemies per row
			for (int k = STRIPE_DISTANCE; k < Constants.WIDTH-STRIPE_DISTANCE; k += STRIPE_DISTANCE)
			{
				Enemy e = new SpaceInvadersEnemy(this, k, y, Direction.LEFT);
				add(e);
			}
		}

		for (int x = BARRIER_STRIPE_DISTANCE; x < Constants.WIDTH-BARRIER_STRIPE_DISTANCE; x += BARRIER_DISTANCE)
			add(new Barrier(this, x, BARRIER_Y));

		// schedule the mothership arrival
		scheduleMothership();
	}
	
	/* (non-Javadoc)
	 * @see it.uniroma1.lcl.Level#calculateRank()
	 * Awards a rank between D and A based on the number of enemies still alive at the end of the level.
	 * If the played destroyed all the spawned mothership and all the enemies awards an S.
	 */
	public Rank getRank()
	{
		if (initialEnemyNumber - getEnemies().size() <= initialEnemyNumber/4) return Rank.D;
		if (initialEnemyNumber - getEnemies().size() > initialEnemyNumber/4 && initialEnemyNumber - getEnemies().size() <= initialEnemyNumber/4*2) return Rank.C;
		if (initialEnemyNumber - getEnemies().size() > initialEnemyNumber/4*2 && initialEnemyNumber - getEnemies().size() <= initialEnemyNumber/4*3) return Rank.B;
		if (initialEnemyNumber - getEnemies().size() > initialEnemyNumber/4*3 && aliveMotherships == 0) return Rank.S;
		else if (initialEnemyNumber - getEnemies().size() > initialEnemyNumber/4*3 && aliveMotherships > 0) return Rank.A;

		return Rank.D;
	}
}
