package it.uniroma1.lcl.level000;

import it.uniroma1.lcl.Direction;
import it.uniroma1.lcl.Level;
import it.uniroma1.lcl.PlayerShip;
import it.uniroma1.lcl.bullets.PlayerBullet;

/**
 * Esempio di giocatore con supersparo
 * @author navigli
 *
 */
public class PlayerWithSuperShot extends PlayerShip
{
	public PlayerWithSuperShot(Level l)
	{
		super(l);
		shotReloadTime = 0.1f;
	}

	@Override
	protected void createShots()
	{
		level.add(new PlayerBullet(level, x+getSpriteWidth()/2-16, y+getSpriteHeight()-2, 7f, new Direction(-0.3f, 1)));
		level.add(new PlayerBullet(level, x+getSpriteWidth()/2-12, y+getSpriteHeight()-2, 7f, new Direction(-0.2f, 1)));
		level.add(new PlayerBullet(level, x+getSpriteWidth()/2+4, y+getSpriteHeight()-2, 7f, Direction.UP));
		level.add(new PlayerBullet(level, x+getSpriteWidth()/2-2, y+getSpriteHeight()-2, 7f, Direction.UP));
		level.add(new PlayerBullet(level, x+getSpriteWidth()/2-8, y+getSpriteHeight()-2, 7f, Direction.UP));
		level.add(new PlayerBullet(level, x+getSpriteWidth()/2+8, y+getSpriteHeight()-2, 7f, new Direction(0.2f, 1)));
		level.add(new PlayerBullet(level, x+getSpriteWidth()/2+12, y+getSpriteHeight()-2, 7f, new Direction(0.3f, 1)));
	}
}
