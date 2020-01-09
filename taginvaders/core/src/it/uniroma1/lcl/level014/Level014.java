package it.uniroma1.lcl.level014;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer.Task;

import it.uniroma1.lcl.Constants;
import it.uniroma1.lcl.Level;
import it.uniroma1.lcl.WorldController;
import it.uniroma1.lcl.bullets.Bullet;


/**
 * @author Nunzio Castelli
 * 
 * Destroy enemies squadron that runs in a circular path.
 * When an enemy is killed, it's own squadron will increase the range of action
 * until the maximum range (radius) is reached and after that, the squadron will 
 * go back to the center and restart again it's path. 
 * 
 * If the enemies are not killed, they slowly increase their radius action...
 * 
 * Special features
 *   Extended HUD with player coordinate, enemies counters.
 *   Is not possible to overlap HUD by player / enemies 
 *   Mothership arrival is called when 50% is destroyed.
 *   Maximum rank is earn only when all enemies are killed without losing any live
 *
 */
public class Level014 extends Level {

	public static final long MAX_FREQUENCY = 10;
	public static final long START_FREQUENCY = 150;
	private static final long ENEMIES_30PERCENT = 30;
	private static final long ENEMIES_50PERCENT = 50;
	private static final long ENEMIES_70PERCENT = 70;
	private int initialPlayerNumber;
	private boolean scheduledMothership = false;
	public Level014(WorldController wc) {
		super(wc, "Nunzio Castelli", "Flashback to Galaga - Namco 1981", 
			  "Nunzio Castelli", Color.BLUE);
	}

	/**
	 * Create enemies squadron
	 * */
	@Override
	protected void init() {
		final float STARTING_RADIUS = 10f;
		final int MIN_ENEMIES_SQUAD_ENTRY = 3;
		final int MAX_ENEMIES_SQUADRON = 7;
		final int RADIUS_OFFSET = 35;

		boolean orientation = true;
		float radius = STARTING_RADIUS;
		int baseEnemy = MIN_ENEMIES_SQUAD_ENTRY;
		
		for (int i = baseEnemy; i <= MAX_ENEMIES_SQUADRON; i++, 
			 orientation = !orientation, radius += RADIUS_OFFSET) {
			addEnemiesSquad(MIN_ENEMIES_SQUAD_ENTRY*i, 0, radius, orientation);
		}
		this.initialEnemyNumber = this.getEnemies().size();
		this.initialPlayerNumber = this.getPlayer().getLives();

	}

	/**
	 * Create enemy squadron
	 * 
	 * @param n number of enemies
	 * @param angle degree orientation
	 * @param radius action radius of the squadron 
	 * @param righ_orientation 
	 * 
	 * */
	private void addEnemiesSquad(int n, float angle, float radius, boolean righ_orientation) {

		float angle_step = 360 / n;

		CirclePath014 path = new CirclePath014(Constants.WIDTH/2, 
	    		   						   	   Constants.HEIGHT/2,
	    		   						   	   angle, 
	    		   						   	   radius, 
	    		   						   	   righ_orientation);
		for (int i=1; i <= n; i++) {

			/* create an enemy for the level */
			RadiusEnemy re = new RadiusEnemy(this, 
											 path.getX(), 
											 path.getY(), 
											 Constants.ENEMY_SPEED,   
											 path,
											 n);			
			
			/* push enemy into the level */
			this.add(re);

			/* create new path for the next enemy */
			angle = path.getAngle() + angle_step;
			path = new CirclePath014(Constants.WIDTH/2, 
		    		   			   Constants.HEIGHT/2,
								   angle, 
								   radius, 
								   righ_orientation);
			path.CalculatePosition(path.getAngle(), path.getRadius());
		}
	}
	
	/**
	 * Control the playership position, stops the enemies fire
	 * when the player dies and schedule mothership while the 
	 * half of the enemies are destroyed
	 * 
	 * */
	public void update(float deltaTime) {	
		
		super.update(deltaTime);
		
		if (getPlayerShip().isDying()) {
			if (this.getPostUpdateTask() == null)
				this.setPostUpdateTask(new Task() {
					@Override
					public void run() {
						Level014.this.stopFire(Level014.this);
					}
				});
		}
		
		if (getEnemies().size() < (this.getMaxEnemies() / 2)) {
			this.scheduledMothership = true;
			scheduleMothership();		
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) { 
			/* do not overlap HUD area */
			if (getPlayerShip().getY() <= getHudMaxY())
				getPlayerShip().idle();
		}
	}

	/**
	 * Add to playership the feature to go up or down and deny to overlap
	 * the HUD area 
	 * 
	 * */
	@Override
	protected void handleInput() {
		
		super.handleInput();
		
		if (getPlayerShip().isAlive()) {
			if (Gdx.input.isKeyPressed(Input.Keys.UP)) 
				getPlayerShip().goUp();
			else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
				/* do not overlap HUD area */
				if (getPlayerShip().getY() > (Constants.HUD_Y_COORDINATE+hudTexture.getHeight()))
					getPlayerShip().goDown();
			}
		}
	}
	
	/**
	 * Calculate the ranking at the end of the game.
	 * Maximum ranking is gained when all enemies are killed without losing
	 * any live otherwise is calculated starting from the ratio of enemies
	 * killed. 100% of enemies killed will return A.
	 * 
	 * */
	@Override
	public Rank getRank() {
		
		int nEnemies = getEnemies().size();
		if ((nEnemies > 0) && this.scheduledMothership == true)
			nEnemies--;
		
		float ratioStillActive = (float)nEnemies / (float)this.getMaxEnemies() * 100f;

		if (ratioStillActive == 0 && 
			this.initialPlayerNumber == this.getPlayer().getLives()) {
			return Rank.S;
		} else if (ratioStillActive <= ENEMIES_30PERCENT) {
			return Rank.A;
		} else if ((ratioStillActive > ENEMIES_30PERCENT) && 
				   (ratioStillActive <= ENEMIES_50PERCENT)) {
			return Rank.B;
		} else if ((ratioStillActive > ENEMIES_50PERCENT) && 
				   (ratioStillActive <= ENEMIES_70PERCENT)) {
			return Rank.C;
		} else
			return Rank.D;
	}

	/**
	 * Draws the game interface
	 * @param batch the sprite batch on which the HUD will be drawn
	 */
	@Override
	protected void drawHud(SpriteBatch batch) {
		StringBuilder sb = new StringBuilder();
		String sHud;
		float xHudScore = 0;

		super.drawHud(batch);

		sb.append(" ship ");
		sb.append(String.format("%.1f", player.getShip().getX()));
		sb.append(" ");
		sb.append(String.format("%.1f", player.getShip().getY()));
		sHud = sb.toString();
		
		float x = font.getScaleX();
		float y = font.getScaleY();
		font.setScale(0.9f, 0.9f);
		
		/* ship position */
		font.draw(batch, sHud, 0, Constants.HUD_Y_COORDINATE + 20);

		/* enemies counters */
		sb = new StringBuilder();
		sb.append("Left " + this.getEnemies().size());
		sb.append(" killed " + (this.getMaxEnemies() - this.getEnemies().size()));
		sHud = sb.toString();
		xHudScore = Constants.WIDTH/2 + ((player.getLives()+1) * 32) - ((player.getLives()*32)/2);
		font.draw(batch, 
				  sHud, 
				  xHudScore, 
				  Constants.HUD_Y_COORDINATE + 20);
		font.setScale(x, y);
	}
	
	/**
	 * Remove all enemies bullet on the screen 
	 * 
	 */
	public void stopFire(Level l) {
		for (Bullet b : l.getBullets()) {
			l.remove(b);
		}
	}
	
	public int getHudMaxY() {
		return (int) (Constants.HUD_Y_COORDINATE + hudTexture.getHeight());
	}	
}
