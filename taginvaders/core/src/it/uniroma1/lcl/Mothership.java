package it.uniroma1.lcl;

import it.uniroma1.lcl.bullets.Bullet;
import it.uniroma1.lcl.decorations.Explosion;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

/**
 * Mothership
 * @author bellincampi
 *
 */
public class Mothership extends Enemy
{
	/**
	 * Spawn sound
	 */
	protected Sound spawnSound = Assets.manager.get(Constants.MOTHERSHIP_SPAWN_SOUND_PATH, Sound.class);
	
	/**
	 * Constructor for the mothershup
	 * @param l level 
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param d starting direction
	 */
	public Mothership(Level l, float x, float y, Direction d)
	{
		super(l, x, y, d);
		setSpeed(Constants.MOTHERSHIP_SPEED);
		life = Constants.MOTHERSHIP_LIFE;
		
		standardAtlas = Assets.manager.get(Constants.MOTHERSHIP_HALF_ATLAS_PATH, TextureAtlas.class);
		standardAnim = new Animation(0.025f, standardAtlas.getRegions(), Animation.PlayMode.LOOP);
		
		flashAtlas = Assets.manager.get(Constants.MOTHERSHIP_HALF_FLASH_ATLAS_PATH, TextureAtlas.class);
		flashAnim = new Animation(0.025f, flashAtlas.getRegions(), Animation.PlayMode.LOOP);
		
		deadAtlas = Assets.manager.get(Constants.EXPLOSION_ATLAS_PATH, TextureAtlas.class);
		deadAnim = new Animation(0.1f, deadAtlas.getRegions());
		
		if (Constants.getOptions().getBoolean(Constants.SFX_OPTION)) spawnSound.play();
		
		width = hitboxWidth = standardAtlas.getRegions().first().getRegionWidth();
		height = standardAtlas.getRegions().first().getRegionHeight();
		hitboxHeight = height-height/4;
	}

	protected void aliveUpdate(float deltaTime)
	{
		// if the mothership exits the screen, remove it
		if (x > Constants.WIDTH) level.remove(this);

		// shoot with a higher rate of fire than the standard enemies
		if (rand.nextInt(50) == 42) shoot();
	}

	protected void dyingUpdate(float deltaTime)
	{
		// create explosions
		level.add(new Explosion(level, x+40+rand.nextInt((int) getHitboxWidth()-120), 
				y+rand.nextInt((int) getHitboxHeight()), 0, Direction.IDLE));
		
		Timer.schedule(new Timer.Task()
		{
			@Override
			public void run()
			{
				setState(State.DEAD);
			}
		}, Constants.MOTHERSHIP_DEATH_TIME);
	}

	@Override
	public void draw(SpriteBatch batch)
	{
		// draw the mothership whit if it has been hit recently
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
		// draw the standard sprites otherwise
		else
			batch.draw(standardAnim.getKeyFrame(stateTime), x, y);
	}
	
	@Override
	public void onCollide(Bullet b)
	{
		life--;;
		level.remove(b);
		isFlashing = true;
		if (life == 0)
		{
			if (Constants.getOptions().getBoolean(Constants.SFX_OPTION)) deathSound.play();
			setDirection(Direction.UP);
			setSpeed(0.5f);
			die();
		}
	}
	
	/**
	 * Handle the mothership death
	 */
	public void die()
	{
		super.die();
		level.killMothership();
	}
}
