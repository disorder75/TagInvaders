package it.uniroma1.lcl.level014;

import it.uniroma1.lcl.Assets;
import it.uniroma1.lcl.Constants;
import it.uniroma1.lcl.Enemy;
import it.uniroma1.lcl.Level;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer.Task;

/**
 * Radius Enemy
 * 
 * @author Nunzio Castelli
 *
 */
public class RadiusEnemy extends Enemy
{
	private CirclePath014 path2 = null;
	private long prevTime = TimeUtils.millis();
	private boolean isExpansion = true;
	private static float STEP_RADIUS = 0.5f;
	private float starting_radius = 0.0f;
	private long update_frequency = 500;
	private final static long MAX_FREQUENCY = 5;
	private final static long STEP_FREQUENCY = 10;
	private int idSquad = 0;
	
	/**
	 * Constructor for the enemy
	 * @param l level
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param 
	 */

	public RadiusEnemy(Level l, float x, float y, float speed, CirclePath014 path, int idSquad)
	{
		super(l, x, y, speed, path);
		width = hitboxWidth = standardAtlas.getRegions().first().getRegionWidth();
		height = hitboxHeight = standardAtlas.getRegions().first().getRegionHeight();
		this.path2 = path;
		this.idSquad = idSquad;
		this.starting_radius = this.path2.getRadius();
		chooseSprite(this.starting_radius);
	}

	/**
	 * Handle the enemy position on the battlefield calculating a new
	 * position and rotation
	 * when is dying increase its own squadron behavior
	 * when is out of range or the minimum radius is reached do a path inverse
	 * 
	 */
	public void update(float deltaTime)
	{
		super.update(deltaTime);
		if (this.isDying()) {
			/* when an enemy dies, increase a speed of its own squad */
			/* spawning a thread */
			if (level.getPostUpdateTask() == null)
				level.setPostUpdateTask(new Task() {
					@Override
					public void run() {
						RadiusEnemy.this.incSpeedSquad(RadiusEnemy.this.getIdSquad());
					}
				});
		} else if (this.isOutOfField() || 
				   ((this.isExpansion == false) && 
					(this.path2.getRadius() < this.path2.getMinimRadius()))) {
					/* Reverse path squadron */
					if (level.getPostUpdateTask() == null)
						level.setPostUpdateTask(new Task() {
						@Override
						public void run() {
							RadiusEnemy.this.reverseSquadPath(RadiusEnemy.this.getIdSquad());
						}
					});			
			} else if (TimeUtils.timeSinceMillis(prevTime) > this.getFrequency()) {
			prevTime = TimeUtils.millis();
			/* perform the action */
			this.doBreath();
		}
		/* calculate new position */
		this.doRotation();				
	}

	/**
	 * Increase / decrease the angle on the path and calculate new
	 * coordinates
	 * 
	 */	
	public void doRotation() {
		CirclePath014 cp2 = path2;
		if (cp2.isRight_orientation())
			cp2.decreaseAngle();
		else
			cp2.increaseAngle();
		cp2.CalculatePosition(cp2.getAngle(), cp2.getRadius());
	}

	public long getFrequency() {
		return this.update_frequency;
	}
	
	public long getRefreshMaxFrequency() {
		return RadiusEnemy.MAX_FREQUENCY;
	}
	
	public void setFrequency(long frequency) {
		this.update_frequency = frequency;
		return;
	}
	
	/**
	 * Increase / decrease the radius action
	 * 
	 */
	public void doBreath() {
		CirclePath014 cp2 = ((RadiusEnemy) this).path2;
		if (this.isExpansion)
			cp2.setRadius(cp2.getRadius() + STEP_RADIUS);
		else
			cp2.setRadius(cp2.getRadius() - STEP_RADIUS);
	}

	/**
	 * Check if current enemy is into the field range
	 */
	private boolean isOutOfField() {
		float xMin = level.getPlayerShip().getSpriteWidth();
		float xMax = Constants.WIDTH - level.getPlayerShip().getSpriteWidth();
		float yMin = ((Level014)level).getHudMaxY() + level.getPlayerShip().getSpriteHeight();
		float yMax = Constants.HEIGHT;

		if ((this.getX() <= xMin) || (this.getY() <= yMin) || 
			(this.getX() >= xMax) || (this.getY() >= yMax) || 
			this.isOutOfScreen())
			return true;
		
		return false;
	}
	
	public int getIdSquad() {
		return this.idSquad;
	}
	
	/**
	 * Chooses the sprite to use
	 */
	protected void chooseSprite(float radius) {
		if (radius <= 40) {
			standardAtlas = Assets.manager.get(Constants.ENEMY3_ATLAS_PATH, TextureAtlas.class);
			flashAtlas = Assets.manager.get(Constants.ENEMY3_FLASH_ATLAS_PATH, TextureAtlas.class);
		} else if (radius > 40 && radius <= 80)	{
			standardAtlas = Assets.manager.get(Constants.ENEMY2_ATLAS_PATH, TextureAtlas.class);
			flashAtlas = Assets.manager.get(Constants.ENEMY2_FLASH_ATLAS_PATH, TextureAtlas.class);
		} else {
			standardAtlas = Assets.manager.get(Constants.ENEMY1_ATLAS_PATH, TextureAtlas.class);
			flashAtlas = Assets.manager.get(Constants.ENEMY1_FLASH_ATLAS_PATH, TextureAtlas.class);
		}
		standardAnim = new Animation(0.04f+rand.nextFloat()/20, standardAtlas.getRegions(), Animation.PlayMode.LOOP);
		flashAnim = new Animation(standardAnim.getFrameDuration() , flashAtlas.getRegions(), Animation.PlayMode.LOOP);
	}

	/**
	 *  Increase the squadron speed increasing the frequency and 
	 *  increase / decrease the radius for squadron field action
	 */		
	public void incSpeedSquad(int idSquad) {
		for (Enemy e : level.getEnemies()) {
			if (e instanceof RadiusEnemy) {
				RadiusEnemy re = (RadiusEnemy) e;
				if (re.getIdSquad() == idSquad) {
					if (re.getFrequency() > MAX_FREQUENCY) {
						re.setFrequency(re.update_frequency -= STEP_FREQUENCY);
					}
					if (!re.isOutOfField())
						re.path2.setRadius(re.path2.getRadius() + STEP_RADIUS);
					//re.setSpeed(re.getSpeed() - 0.1f);
					//re.path2.setSpeed(re.getSpeed() - 0.1f);
				}
			}
		}
	}
	
	/**
	 *  Reverse the Squadron direction
	 */		
	public void reverseSquadPath(int idSquad) {
		for (Enemy e : level.getEnemies()) {
			if (e instanceof RadiusEnemy) {
				RadiusEnemy re = (RadiusEnemy) e;
				if (re.getIdSquad() == idSquad) {
					re.isExpansion = !re.isExpansion;
				}
			}
		}
	}
	
	/**
	 *  True if all enemies of a specific squadron are destroyed
	 */	
	public boolean isSquadDestoyed(int idSquad) {
		for (Enemy e : level.getEnemies()) {
			if (e instanceof RadiusEnemy) {
				RadiusEnemy re = (RadiusEnemy) e;
				if (re.getIdSquad() == idSquad) {
					return false;
				}
			}
		}
		return true;
	}
}
