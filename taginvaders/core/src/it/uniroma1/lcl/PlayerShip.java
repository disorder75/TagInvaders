package it.uniroma1.lcl;

import it.uniroma1.lcl.bullets.Bullet;
import it.uniroma1.lcl.bullets.PlayerBullet;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Timer;

/**
 * The player object
 * @author navigli
 *
 */
public class PlayerShip extends LivingEntity
{
	/**
	 * True if the player can shoot
	 */
	protected boolean shotReady = true;
	
	/**
	 * Time, in seconds, it takes to reload the shoot
	 */
	protected float shotReloadTime = Constants.SHOT_RELOAD_TIME;
	
	/**
	 * Texture for the standard sprite
	 */
	protected Texture standardSprite = Assets.manager.get(Constants.PLAYER_TEXTURE_PATH, Texture.class);
	
	/**
	 * TextureAtlas for the right turning animation
	 */
	protected TextureAtlas rightAtlas = Assets.manager.get(Constants.PLAYER_DX_ATLAS_PATH, TextureAtlas.class);
	
	/**
	 * Right turning animation
	 */
	protected Animation rightAnim = new Animation(0.05f, rightAtlas.getRegions()/*, Animation.PlayMode.LOOP*/);
	
	/**
	 * TextureAtlas for the left turning animation
	 */
	protected TextureAtlas leftAtlas = Assets.manager.get(Constants.PLAYER_SX_ATLAS_PATH, TextureAtlas.class);
	
	/**
	 * Left turning animation
	 */
	protected Animation leftAnim = new Animation(0.05f, leftAtlas.getRegions()/*, Animation.PlayMode.LOOP*/);

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
	protected Sound deathSound = Assets.manager.get(Constants.PLAYER_DEATH_SOUND_PATH, Sound.class);
	
	/**
	 * Shoot sound
	 */
	protected Sound shootSound = Assets.manager.get(Constants.PLAYER_BULLET_SOUND_PATH, Sound.class);
	
	/**
	 * Player hitbox width
	 */
	protected float hitboxWidth = standardSprite.getWidth()-20;
	
	/**
	 * Player hitbox height
	 */
	protected float hitboxHeight = standardSprite.getHeight()-4;
	
	/**
	 * Maximum speed reachable by the player
	 */
	protected float maxSpeed = Constants.PLAYER_SPEED;
	
	/**
	 * Constructor with specific position for the player
	 * @param l level
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param speed speed
	 */
	public PlayerShip(Level l, int x, int y, float speed)
	{
		super(l, x, y, speed, Direction.IDLE);
	}
	
	/**
	 * Creates a Player in the standard position
	 */
	public PlayerShip(Level l)
	{
		super(l, 0, 45, Constants.PLAYER_SPEED, Direction.IDLE);
		x = (Constants.WIDTH-getSpriteWidth())/2;
	}
	
	@Override
	protected boolean usesPath()
	{
		return false;
	}

	/**
	 * What the player ship does when is alive
	 * @param deltaTime time passed since the last frame
	 */
	@Override
	protected void aliveUpdate(float deltaTime)
	{
		Direction direction = getDirection();

		if ((direction.getX() < 0) && (x >= 0)) 
			x -= speed*deltaTime*Constants.IDEAL_FPS;
		if (direction.getX() > 0 && x < Constants.WIDTH-getSpriteWidth()) 
			x += speed*deltaTime*Constants.IDEAL_FPS;
		if ((direction.getY() < 0) && (y >= 0)) 
			y -= speed*deltaTime*Constants.IDEAL_FPS;
		if (direction.getY() > 0 && y < Constants.HEIGHT-getSpriteHeight()) 
			y += speed*deltaTime*Constants.IDEAL_FPS;
	}
	
	/**
	 * What the player ship does when is dying
	 * @param deltaTime time passed since the last frame
	 */
	@Override
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

	/**
	 * Draw the player
	 * @param batch the sprite batch on which the player will be drawn
	 */
	public void draw(SpriteBatch batch)
	{
		switch(getState())
		{
			case ALIVE: 
				if (getDirection() == Direction.LEFT)	{batch.draw(leftAnim.getKeyFrame(stateTime), x, y);}
				else if (getDirection() == Direction.RIGHT)	batch.draw(rightAnim.getKeyFrame(stateTime), x, y); 
				else batch.draw(standardSprite, x, y); 
			break;
			case DYING: batch.draw(deadAnim.getKeyFrame(stateTime), x, y); break;
			case DEAD: break;
		}
	}

	@Override
	public float getSpriteWidth()
	{
		return standardSprite.getWidth();
	}
	
	@Override
	public float getSpriteHeight()
	{
		return standardSprite.getHeight();
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
	
	public void goLeft() { speed = maxSpeed; setDirection(Direction.LEFT); }
	public void goRight() { speed = maxSpeed; setDirection(Direction.RIGHT); }
	public void goUp() { speed = maxSpeed; setDirection(Direction.UP); }
	public void goDown() { speed = maxSpeed; setDirection(Direction.DOWN); }
	public void idle() 
	{ 
		// handle inertia
		if (speed > Constants.PLAYER_SPEED) speed = Constants.PLAYER_SPEED;
		if (speed > 0.01f)
		{
			speed -= 0.6f;
		}
		else 
		{
			speed = maxSpeed;
			setDirection(Direction.IDLE); 
		}
		
		// reset the stateTime so that the animations will be player from the first frame
		stateTime = 0;
	}
	
	/**
	 * Shoots a bullet if the weapon is loaded
	 */
	public void shoot()
	{
		if (shotReady)
		{
			shotReady = false;
			
			if (Constants.getOptions().getBoolean(Constants.SFX_OPTION)) shootSound.play();
			
			createShots();
			startShotReload();
		}
	}

	/**
	 * Creates and adds bullets to the level
	 */
	protected void createShots()
	{
		level.add(new PlayerBullet(level, x+getSpriteWidth()/2-5, y+getSpriteHeight()-25));
	}
	
	/**
	 * Starts reloading the weapon
	 */
	protected void startShotReload()
	{
		Timer.schedule(new Timer.Task()
		{
			@Override
			public void run()
			{
				shotReady = true;
			}
		}, shotReloadTime);
	}
	
	public void die()
	{
		if (isAlive())
		{
			setState(State.DYING);
			level.player.addLives(-1);
			if (Constants.getOptions().getBoolean(Constants.SFX_OPTION)) deathSound.play();
		}
	}


	/**
	 *  Increases the rate of fire for a given time
	 */
	public void setSuperShotBonus()
	{
		shotReloadTime -= Constants.TAG_BONUS_AMOUNT;
		
		Timer.schedule(new Timer.Task()
		{
			@Override
			public void run()
			{
				shotReloadTime += Constants.TAG_BONUS_AMOUNT;
			}
		}, Constants.BONUS_EXPIRATION_TIME);
	}
	
	/**
	 *  Decreases the rate of time for a given time
	 */
	public void setWeakShotMalus()
	{
		shotReloadTime += Constants.TAG_BONUS_AMOUNT;
		
		Timer.schedule(new Timer.Task()
		{
			@Override
			public void run()
			{
				shotReloadTime -= Constants.TAG_BONUS_AMOUNT;
			}
		}, Constants.BONUS_EXPIRATION_TIME);
	}
	
	/**
	 * Handles the collision between the player and an enemy
	 * @param e the enemy the player is colliding with
	 */
	public void onCollide(Enemy e)
	{
		die();
	}

	/**
	 * Handles the collision between the player and a bullet
	 * @param b the bullet the player is colliding with
	 */
	public void onCollide(Bullet b)
	{
		die();
	}
	
	/**
	 * Handles the collision between the player and an item
	 * @param b the item the player is colliding with
	 */
	public void onCollide(Item i)
	{
	}

	/**
	 * Checks the collision of the player with another entity
	 */
	public boolean collideWith(Entity e)
	{
		if (!(e instanceof PlayerShip)) return e.collideWith(this);
		return false;
	}
	
	/**
	 * Sets the maximums speed
	 * @param s the new maximum speed
	 */
	public void setSpeed(float s)
	{
		maxSpeed = s;
	}
	
	/**
	 * Returns the maximum speed
	 * @return the maximum speed
	 */
	public float getSpeed()
	{
		return maxSpeed;
	}
}
