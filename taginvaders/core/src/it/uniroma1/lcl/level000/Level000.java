package it.uniroma1.lcl.level000;

import it.uniroma1.lcl.Constants;
import it.uniroma1.lcl.Direction;
import it.uniroma1.lcl.Enemy;
import it.uniroma1.lcl.Level;
import it.uniroma1.lcl.SpaceInvadersEnemy;
import it.uniroma1.lcl.WorldController;
import it.uniroma1.lcl.paths.DrawableSplinePath;
import it.uniroma1.lcl.paths.LinePath;

/**
 * Level 000: a minimal level
 * @author navigli
 *
 */
public class Level000 extends Level
{
	public Level000(WorldController wc)
	{
		super(wc, "Level000", "Try level", "Roberto Navigli", Constants.YELLOW);
	}
	
	@Override
	protected void init()
	{
		for (int k = 100; k <= 350; k += 50)
			add(new SpaceInvadersEnemy(this, k, 200, Direction.LEFT));
		
		// set a supershoot player
		getPlayer().setPlayerClass(PlayerWithSuperShot.class);
		newPlayer();
	}
	
	public Rank getRank()
	{
		return Rank.A;
	}
}
