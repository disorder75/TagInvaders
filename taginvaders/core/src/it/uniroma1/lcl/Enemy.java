package it.uniroma1.lcl;

import it.uniroma1.lcl.bullets.Bullet;
import it.uniroma1.lcl.bullets.EnemyBullet;
import it.uniroma1.lcl.paths.Path;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

/**
 * Game default enemy
 * @author navigli
 *
 */
public class Enemy extends LivingEntity
{
	/**
	 * TextureAtlas for the standard animation
	 */
	protected TextureAtlas standardAtlas = Assets.manager.get(Constants.ENEMY1_ATLAS_PATH, TextureAtlas.class);
	
	/**
	 * Standard Animation
	 */
	protected Animation standardAnim = new Animation(0.1f, standardAtlas.getRegions(), Animation.PlayMode.LOOP);
	
	/**
	 * TextureAtlas for the flashing animation
	 */
	protected TextureAtlas flashAtlas = Assets.manager.get(Constants.ENEMY1_FLASH_ATLAS_PATH, TextureAtlas.class);
	
	/**
	 * Flashing animation
	 */
	protected Animation flashAnim = new Animation(0.1f, standardAtlas.getRegions(), Animation.PlayMode.LOOP);
	
	/**
	 * TextureAtlas for the death animation
	 */
	protected TextureAtlas deadAtlas = Assets.manager.get(Constants.EXPLOSION_ATLAS_PATH, TextureAtlas.class);
	
	/**
	 * Death animation
	 */
	protected Animation deadAnim = new Animation(0.05f, deadAtlas.getRegions());
	 
	/**
	 * Death sound
	 */
	protected Sound deathSound = Assets.manager.get(Constants.ENEMY_DEATH_SOUND_PATH, Sound.class);
	
	/**
	 * Shoot sound
	 */
	protected Sound shootSound = Assets.manager.get(Constants.ENEMY_BULLET_SOUND_PATH, Sound.class);
	
	/**
	 * Enemy width
	 */
	protected float width = standardAtlas.getRegions().first().getRegionWidth();
	
	/**
	 * Enemy height
	 */
	protected float height = standardAtlas.getRegions().first().getRegionHeight();
	
	/**
	 * Enemy hitbox width
	 */
	protected float hitboxWidth = width;
	
	/**
	 * Enemy hitbox height
	 */
	protected float hitboxHeight = height;
	
	/**
	 * Enemy's life
	 */
	protected int life = 1;
	
	/**
	 * True if the enemy has been hit
	 */
	protected boolean isFlashing;
	
	/**
	 * Creates an enemy
	 * @param l the level which the enemy belong to
	 * @param x x starting coordinate
	 * @param y y starting coordinate
	 * @param speed speed
	 * @param d direction
	 */
	public Enemy(Level l, float x, float y, float speed, Direction d)
	{
		super (l, x, y, speed, d);
	}
	
	/**
	 * Creates an enemy
	 * @param l the level which the enemy belong to
	 * @param x x starting coordinate
	 * @param y y starting coordinate
	 * @param speed speed
	 * @param path path
	 */
	public Enemy(Level l, float x, float y, float speed, Path path)
	{
		super (l, x, y, speed, path);
	}
	
	/**
	 * Creates an enemy
	 * @param l the level which the enemy belong to
	 * @param x x starting coordinate
	 * @param y y starting coordinate
	 * @param d direction
	 */
	public Enemy(Level l, float x, float y, Direction d)
	{
		super(l, x, y, Constants.ENEMY_SPEED, d);
	}
	
	/**
	 * Creates an enemy
	 * @param l the level which the enemy belong to
	 * @param x x starting coordinate
	 * @param y y starting coordinate
	 * @param path path
	 */
	public Enemy(Level l, float x, float y, Path path)
	{
		super(l, x, y, Constants.ENEMY_SPEED, path);
	}

	/**
	 * Draw the enemy
	 * @param batch
	 */
	public void draw(SpriteBatch batch)
	{
		// if the enemy has been hit we have to draw it white
		if (isFlashing)
		{
			batch.draw(flashAnim.getKeyFrame(stateTime), x, y);
			Timer.schedule(new Task()
			{
				@Override
				public void run()
				{
					isFlashing = false;
				}
			}, Constants.ENEMY_FLASH_TIME);
		}
		// else we draw the standard sprites
		else
		{
			if (isDying()) batch.draw(deadAnim.getKeyFrame(stateTime), x-getSpriteWidth()/2, y-getSpriteHeight()/2);
			else batch.draw(standardAnim.getKeyFrame(stateTime), x, y);
		}
	}
	
	/**
	 * Shoots a bullet
	 */
	public void shoot()
	{
		if (Constants.getOptions().getBoolean(Constants.SFX_OPTION)) shootSound.play();
		level.add(new EnemyBullet(level, x+getSpriteWidth()/2-2, y));
	}

	/**
	 * Handle the collision with the player ship
	 * @param s the ship the enemy is colliding with
	 */
	public void onCollide(PlayerShip s)
	{
	}
	
	/**
	 * Handle the collision with a bullet
	 * @param b the colliding bullet
	 */
	public void onCollide(Bullet b)
	{
		life--;
		isFlashing = true;
		if (life == 0)
		{
			if (Constants.getOptions().getBoolean(Constants.SFX_OPTION)) deathSound.play();
			die();
		}
	}
	
	/**
	 * Returns the score the enemy awards for its killing
	 * @return punteggio dato dal nemico
	 */
	public int getKillScore()
	{
		return Constants.KILL_SCORE;
	}
	
	protected void aliveUpdate(float deltaTime)
	{
		// shoot at random intervals
		if (rand.nextInt(500) < 1+Constants.ENEMY_SHOOT_MODIFIER*(2/level.getEnemies().size())) shoot();
	}

	protected void dyingUpdate(float deltaTime)
	{
		Timer.schedule(new Timer.Task()
		{
			@Override
			public void run()
			{
				setState(State.DEAD);
			}
		}, Constants.ENEMY_DEATH_TIME);
	}
	
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
}
