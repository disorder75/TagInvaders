package it.uniroma1.lcl;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Timer.Task;

/**
 * Classic Space Invaders enemy
 * @author navigli
 *
 */
public class SpaceInvadersEnemy extends Enemy
{

	/**
	 * How much the enemy drops every time the direction is inverted
	 */
	static final float DESCEND_AMOUNT = 6;
	
	/**
	 * When the level has this number of enemies they move the slowest
	 */
	static final float ENEMIES_MAX_NUMBER = 80;
	
	/**
	 * Top border for the different enemies placement
	 */
	static final float ENEMY_PLACEMENT_TOP_BORDER = 420;
	
	/**
	 * Bottom border for the different enemies placement
	 */
	static final float ENEMY_PLACEMENT_BOTTOM_BORDER = 300;
	
	/**
	 * Constructor for the enemy
	 * @param l level
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param d direction
	 */
	public SpaceInvadersEnemy(Level l, float x, float y, Direction d)
	{
		super(l, x, y, d);
		width = hitboxWidth = standardAtlas.getRegions().first().getRegionWidth();
		height = hitboxHeight = standardAtlas.getRegions().first().getRegionHeight();
		chooseSprite();
	}
	
	/**
	 * Constructor for the enemy
	 * @param l level
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param speed speed
	 * @param d direction
	 */
	public SpaceInvadersEnemy(Level l, float x, float y, float speed, Direction d)
	{
		this(l, x, y, d);
		setSpeed(speed);
		chooseSprite();
	}

	public void update(float deltaTime)
	{
		super.update(deltaTime);
		handleSpeed();

		// handle the movement (when an enemy reaches the screen border ALL the enemies have to reverse direction)
		if ((getX() >= Constants.WIDTH-getHitboxWidth() && getDirection() == Direction.RIGHT)
			||
			(getX() <= 0 && getDirection() == Direction.LEFT))
		{
			if (level.getPostUpdateTask() == null)
				level.setPostUpdateTask(new Task()
				{
					@Override
					public void run()
					{
						for (Enemy f : level.getEnemies())
							if (f instanceof SpaceInvadersEnemy) ((SpaceInvadersEnemy) f).reverseDirection();
					}
				});
		}
	}
	
	/**
	 * Chooses the sprite to use
	 */
	protected void chooseSprite()
	{
		if (y > ENEMY_PLACEMENT_TOP_BORDER)
		{
			standardAtlas = Assets.manager.get(Constants.ENEMY3_ATLAS_PATH, TextureAtlas.class);
			flashAtlas = Assets.manager.get(Constants.ENEMY3_FLASH_ATLAS_PATH, TextureAtlas.class);
		}
		else if (y <= ENEMY_PLACEMENT_TOP_BORDER && y > ENEMY_PLACEMENT_BOTTOM_BORDER)
		{
			standardAtlas = Assets.manager.get(Constants.ENEMY2_ATLAS_PATH, TextureAtlas.class);
			flashAtlas = Assets.manager.get(Constants.ENEMY2_FLASH_ATLAS_PATH, TextureAtlas.class);
		}
		else 
		{
			standardAtlas = Assets.manager.get(Constants.ENEMY1_ATLAS_PATH, TextureAtlas.class);
			flashAtlas = Assets.manager.get(Constants.ENEMY1_FLASH_ATLAS_PATH, TextureAtlas.class);
		}
		
		standardAnim = new Animation(0.04f+rand.nextFloat()/20, standardAtlas.getRegions(), Animation.PlayMode.LOOP);
		flashAnim = new Animation(standardAnim.getFrameDuration() , flashAtlas.getRegions(), Animation.PlayMode.LOOP);
	}

	/**
	 * Increases the speed at the decrease of enemies number
	 */
	protected void handleSpeed()
	{		
		float enemyPercent = ((float) level.getEnemies(SpaceInvadersEnemy.class).size()) / ((float) ENEMIES_MAX_NUMBER);
		setSpeed(Constants.ENEMY_SPEED/enemyPercent);
	}

	/**
	 * Reverts the movement direction and make the enemy descend
	 */
	public void reverseDirection()
	{
		y -= DESCEND_AMOUNT;
		setDirection(getDirection().reversed());
	}
}
