package it.uniroma1.lcl;

import it.uniroma1.lcl.bullets.Bullet;
import it.uniroma1.lcl.decorations.Bubble;
import it.uniroma1.lcl.decorations.Decoration;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

/**
 * Abstract class representing a game level
 * @author navigli
 *
 */
public abstract class Level implements Disposable
{
	/**
	 * World controller reference
	 */
	protected WorldController worldController;
	
	/**
	 * Player
	 */
	protected Player player;
	
	/**
	 * List of the enemies in this level
	 */
	protected ArrayList<Enemy> enemies;

	/**
	 * List of the bullets in this level
	 */
	protected ArrayList<Bullet> bullets;

	/**
	 * List of the barriers in this level
	 */
	protected ArrayList<Barrier> barriers;

	/**
	 * List of the items in this level
	 */
	protected ArrayList<Item> items;

	/**
	 * List of the decorations in this level
	 */
	protected ArrayList<Decoration> decorations;

	/**
	 * List of the images to tag in this level
	 */
	protected ArrayList<TagImage> tagImages;

	/**
	 * True if the level is complete (the player won!)
	 */
	protected boolean isComplete = false;
	
	/**
	 * True if the player lost :(
	 */
	protected boolean isLost = false;
	
	/**
	 * Maximum number of enemies at the start of the level
	 */
	protected int initialEnemyNumber;
	
	/**
	 * Possible ranks for the player
	 */
	public enum Rank { D, C, B, A, S }
	
	/**
	 * How many motherships have escaped the player
	 */
	protected int aliveMotherships;
	
	/**
	 * Level data: name
	 */
	protected String name;
	
	/**
	 * Level data: description
	 */
	protected String description;

	/**
	 * Level data: developer
	 */
	protected String developer;

	/**
	 * Level data: background color
	 */
	protected Color backgroundColor;

	/**
	 * Player sprite (used to draw lives on the HUD bar)
	 */
	protected Texture playerLifeTexture = Assets.manager.get(Constants.PLAYER_TEXTURE_PATH, Texture.class);
	
	/**
	 * HUD sprite
	 */
	protected Texture hudTexture = Assets.manager.get(Constants.HUD_TEXTURE_PATH, Texture.class);

	/**
	 * Pseudorandom numbers generator
	 */
	protected RandomXS128 rand = new RandomXS128();
	
	/**
	 * Font to print data
	 */
	protected BitmapFont font = new BitmapFont();

	/**
	 * Task to be executed after every update
	 */
	private Task postUpdateTask;

		
	/**
	 * Creates an empty level with the player
	 * @param wc world controller
	 * @param name level name
	 * @param description level description
	 * @param developer level developer name
	 * @param backgroundColor level background color
	 */
	public Level(WorldController wc, String name, String description, String developer, Color backgroundColor)
	{
		worldController = wc;
		this.name = name;
		this.description = description;
		this.developer = developer;
		this.backgroundColor = backgroundColor;
		this.player = worldController.getPlayer();

		font.setColor(Color.BLACK);
		font.scale(0.5f);
		
		// initialize the entities lists
		enemies = new ArrayList<Enemy>();
		bullets = new ArrayList<Bullet>();
		items = new ArrayList<Item>();
		barriers = new ArrayList<Barrier>();
		decorations = new ArrayList<Decoration>();
		tagImages = new ArrayList<TagImage>();
		
		player.setPlayerClass(PlayerShip.class);
		newPlayer();
		
		// initializes the level
		init();
		
		// set the initial enemies number at the start of the level
		initialEnemyNumber = enemies.size();
	}

	/**
	 * Update the level and it's components
	 */
	public void update(float deltaTime)
	{
		// recreate the player in case it's dead
		if (getPlayerShip().isDead())
			newPlayer();
		
		// handle the input
		handleInput();
		
		// check thecollisions
		checkCollisions(); 

		// update all the entities logic
		getPlayerShip().update(deltaTime);
		for (Enemy e : getEnemies()) e.update(deltaTime);
		for (Bullet b : getBullets()) b.update(deltaTime);
		for (Item i : getItems()) i.update(deltaTime);
		for (Barrier b : getBarriers()) b.update(deltaTime);
		for (Decoration d : getDecorations()) d.update(deltaTime);
		for (TagImage i : getTagImages()) i.update(deltaTime);
		
		// execute the post update task (if there's one!)
		if (postUpdateTask != null)
		{
			postUpdateTask.run();
			postUpdateTask = null;
		}
		
		// decorate the level
		decorate();

		// spawn the images to be tagged
		if (worldController.isGameWithAPurpose()) generateImages();
		
		// clean the level
		removeDeadEntities(); 
		
		// victory conditions!
		checkVictoryConditions();
		
		// defeat conditions :(
		checkDefeatConditions();
	}
		
	/**
	 * Handles the player input
	 */
	protected void handleInput()
	{
		if (getPlayerShip().isAlive())
		{
			// left
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) getPlayerShip().goLeft();
			// right
			else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) getPlayerShip().goRight();
			else getPlayerShip().idle();
			// shoot
			if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT))
				getPlayerShip().shoot();
			// tagging
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && tagImages.size() > 0)
				tagImages.get(0).collect();
		}
	}
	
	/**
	 * Checks the victory conditions and in case
	 * set the level to "complete"
	 */
	protected void checkVictoryConditions()
	{
		if (enemies.size() == 0) isComplete = true;
	}
	
	/**
	 * Checks the defeat conditions and in case
	 * set the level to "lost"
	 */
	protected void checkDefeatConditions()
	{
		if (player.getLives() == 0) isLost = true;
		for (Enemy e : getEnemies()) 
			if (e.getY() < 0) isLost = true;
	}
	
	/**
	 * Initializes the level
	 * IMPORTANT: this method in the Level subclass has to add the initial enemies, barriers, items, etc.
	 */
	protected abstract void init();
	
	/**
	 * Spawns the images to be tagged
	 * @deprecated
	 */
	protected void generateImages()
	{
		if (rand.nextInt(200) == 42 && tagImages.size() == 0 && !containsMothership())
			add(new TagImage(this, -Constants.DEFAULT_TAG_IMAGE_WIDTH, Constants.HEIGHT-Constants.DEFAULT_TAG_IMAGE_HEIGHT-50, 2, Direction.RIGHT));
	}
	
	/**
	 * Creates the level decorations
	 */
	protected void decorate()
	{
		if (rand.nextInt(20) == 17)
			add(new Bubble(this, rand.nextInt(Constants.WIDTH), 0, rand.nextInt(2)+1, Direction.UP));
	}

	/**
	 * Checks collisions between all the entities
	 * (player, enemies, bullets, etc.)
	 */
	protected void checkCollisions()
	{
		PlayerShip ship = getPlayerShip();
		
		// if an enemy collides with the player, kill the player
		for (Enemy e : getEnemies())
			if (e.collideWith(ship))
			{
				ship.onCollide(e);
				e.onCollide(ship);
			}
		
		// for every bullet in game
		for (Bullet b : getBullets()) 
		{
			// if it collides with an enemy, handle the collision
			for (Enemy e : getEnemies())
			{
				if (e.isAlive() && b.collideWith(e))
				{
					e.onCollide(b);
					b.onCollide(e);
					add(new Bubble(this, b.x, b.y, 1+rand.nextInt(3), Direction.UP));
					player.addScore(e.getKillScore());  	
				}
			}
			// if it collides with the player, handle the collision
			if (b.collideWith(ship))
			{
				ship.onCollide(b);
				b.onCollide(ship);
				add(new Bubble(this, b.x, b.y, 1+rand.nextInt(3), Direction.UP));
			}
			
			//if it collides with a barrier, handle the collision
			for (Barrier bar : getBarriers())
				if (bar.collideWith(b))
				{
					bar.onCollide(b);
				}
		}

		// if an item collides with the player, pick that up!
		for (Item i : getItems()) 
			if (ship.collideWith(i))
			{
				ship.onCollide(i);
				i.onCollide(ship);
				player.addScore(i.getKillScore());
			}
	}
	
	/**
	 * Removes the useless entities from the level (bullets out of screen, etc.)
	 */
	private void removeDeadEntities()
	{
		// browse the enemies and if they're dead remove them
		for (Enemy e : getEnemies()) 
		{
			if (e.isDead())
				remove(e);
		}

		// remove the bullets that have left the screen
		for (Bullet b : getBullets()) 
			if (b.getY() > Constants.HEIGHT || b.getY() < 0) remove(b);

		// remove the items not on the screen anymore
		for (Item i : getItems()) 
			if (i.getY() < -i.getHitbox().getHeight())
				remove(i);
	}
	
	/**
	 * Draws the game interface
	 * @param batch the sprite batch on which the HUD will be drawn
	 */
	protected void drawHud(SpriteBatch batch)
	{
		// Draw the HUD background
		batch.draw(hudTexture,0,Constants.HUD_Y_COORDINATE);
		
		// Draw the player's lives
		for(int i = 0; i < player.getLives(); i++)
		{
			batch.draw(playerLifeTexture, Constants.WIDTH/2 + (i * 32) - ((player.getLives()*32)/2), 
					Constants.HUD_Y_COORDINATE, playerLifeTexture.getWidth()/2, playerLifeTexture.getHeight()/2);
		}
	}
	
	/**
	 * Instantiates a new player using the set class
	 * @return a new player
	 */
	protected void newPlayer()
	{
		player.newPlayerShip(this);
	}
	
	/**
	 * Draws the level and all the entities in it
	 */
	public void render(SpriteBatch batch)
	{
		batch.begin();
		
		for (Item i : items)
			i.draw(batch);
		for (Barrier b : barriers)
			b.draw(batch);
		for (Enemy e : enemies)
			e.draw(batch);
		for (Bullet b : bullets)
			b.draw(batch);
		for (Decoration d : decorations)
			d.draw(batch);
		for (TagImage i : tagImages)
			i.draw(batch);
		
		drawHud(batch);
		
		getPlayerShip().draw(batch);

		batch.end();	    
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.gdx.utils.Disposable#dispose()
	 * Handles the disposing of entities
	 */
	public void dispose()
	{
		for (Barrier b : barriers) b.dispose();
	}

	/**
	 * Schedule the mothership arrival
	 */
	protected void scheduleMothership()
	{
		Timer.schedule(new Task()
		{
			@Override
			public void run()
			{
				if (!containsMothership() && tagImages.size() == 0)
				{
					enemies.add(new Mothership(Level.this, -Constants.MOTHERSHIP_WIDTH, 
							Constants.HEIGHT-Constants.MOTHERSHIP_HEIGHT, Direction.RIGHT));
					aliveMotherships++;
				}
				scheduleMothership();
			}
		}, rand.nextInt(10));
	}
	
	/**
	 * True if the level contains a mothership
	 * @return true if there's a mothership in the level, false otherwise
	 */
	protected boolean containsMothership()
	{
		for (Enemy e : getEnemies())
			if (e instanceof Mothership) return true;
		
		return false;
	}
	
	
	/**
	 * Removes a mothership from the total count
	 */
	public void killMothership()
	{
		aliveMotherships--;
	}
	
	/**
	 * Removes an enemy from the level
	 * @param e the enemy to remove
	 */
	public void remove(Enemy e)
	{
		enemies.remove(e);
	}
	
	/**
	 * Removes a bullet from the level
	 * @param b the bullet to remove
	 */
	public void remove(Bullet b)
	{
		bullets.remove(b);
	}

	/**
	 * Removes an item from the level
	 * @param i the item to remove
	 */
	public void remove(Item i)
	{
		items.remove(i);
	}
	
	/**
	 * Removes a barrier from the level
	 * @param b the barrier to remove
	 */
	public void remove(Barrier b)
	{
		barriers.remove(b);
	}
	
	/**
	 * Removes a decoration from the level
	 * @param d the decoration to remove
	 */
	public void remove(Decoration d)
	{
		decorations.remove(d);
	}
	
	/**
	 * Removes an image to tag from the level
	 * @param d the image to remove
	 */
	public void remove(TagImage i)
	{
		tagImages.remove(i);
	}

	/**
	 * Adds and enemy to the level
	 * @param e the enemy to add
	 */
	public void add(Enemy e)
	{
		enemies.add(e);
	}

	/**
	 * Adds a bullet to the level
	 * @param b the bulle to add
	 */
	public void add(Bullet b)
	{
		bullets.add(b);
	}

	/**
	 * Adds an item to the level
	 * @param i the item to add
	 */
	public void add(Item i)
	{
		items.add(i);
	}

	/**
	 * Adds a barrier to the level
	 * @param b the barrier to add
	 */
	public void add(Barrier b)
	{
		barriers.add(b);
	}
	
	/**
	 * Adds a decoration to the level
	 * @param d the decoration to add
	 */
	public void add(Decoration d)
	{
		decorations.add(d);
	}
	
	/**
	 * Adds an image to tag to the level
	 * @param i the image to add
	 */
	public void add(TagImage i)
	{
		tagImages.add(i);
	}

	/**
	 * Returns a reference to the player
	 * @return a reference to the player
	 */
	public Player getPlayer()
	{
		return player;
	}
	
	/**
	 * Returns a reference to the player's ship
	 * @return a reference to the player's ship
	 */
	public PlayerShip getPlayerShip()
	{
		return player.getShip();
	}

	/**
	 * Returns the list of the enemies currently in the level
	 * @return the list of enemies in the level
	 */
	public List<Enemy> getEnemies()
	{
		return new ArrayList<Enemy>(enemies);
	}
	
	/**
	 * Returns the list of the enemies of the type in input 
	 * currently in the level
	 * @return the list of a specific type in the level
	 */
	public List<Enemy> getEnemies(Class<?> cls)
	{
		ArrayList<Enemy> temp = new ArrayList<Enemy>();
		for (Enemy e : getEnemies())
		{
			if (cls.isInstance(e))
				temp.add(e);
		}
		return temp;
	}
	
	/**
	 * Returns the list of the bullets currently in the level
	 * @return the list of the bullets in the level
	 */
	public List<Bullet> getBullets()
	{
		return new ArrayList<Bullet>(bullets);
	}

	/**
	 * Returns the list of the items currently in the level
	 * @return the list of the items in the level
	 */
	public List<Item> getItems()
	{
		return new ArrayList<Item>(items);
	}
	
	/**
	 * Returns the list of the barriers currently in the level
	 * @return the list of the barriers in the level
	 */
	public List<Barrier> getBarriers()
	{
		return new ArrayList<Barrier>(barriers);
	}
	
	/**
	 * Returns the list of the decorations currently in the level
	 * @return the list of the decorations in the level
	 */
	public ArrayList<Decoration> getDecorations()
	{
		return new ArrayList<Decoration>(decorations);
	}
	
	/**
	 * Returns the list of the images to tag currently inside the level
	 * @return the list of the images to tag in the level
	 */
	public ArrayList<TagImage> getTagImages()
	{
		return new ArrayList<TagImage>(tagImages);
	}

	/**
	 * Returns the level name
	 * @return the level name
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Returns the level description
	 * @return the level description
	 */
	public String getDescription()
	{
		return description;
	}
	
	/**
	 * Returns the developer name for this level
	 * @return the developer name
	 */
	public String getDeveloper()
	{
		return developer;
	}
	
	/**
	 * Returns the background color of the level
	 * @return the background color
	 */
	public Color getBackgroundColor()
	{
		return backgroundColor;
	}
	
	/**
	 * Sets the background color for this level
	 * @param c the new background color
	 */
	public void setBackgroundColor(Color c)
	{
		backgroundColor = c;
	}

	/**
	 * Returns true if the level has been completed (the player won!)
	 * @return true if the level is complete, false otherwise
	 */
	public boolean isComplete()
	{
		return isComplete;
	}
	
	/**
	 * Returns true if the player has lost
	 * @return true if the player has lost
	 */
	public boolean isLost()
	{
		return isLost;
	}
	
	/**
	 * Returns the level font
	 * @return the level font
	 */
	public BitmapFont getFont()
	{
		return worldController.getScreenFont();
	}
	
	/**
	 * Returns the number of enemies generated 
	 * @return the number of enemies generated
	 */
	public int getMaxEnemies()
	{
		return initialEnemyNumber;
	}
	
	/**
	 * Evaluates the rank of the player for this level and returns it as an Enumeration
	 * The rank can be D, C, B, A in increasing value.
	 * If the player has won the level in the best way possibile the awarded rank is S
	 * @return the player rank for this level
	 */
	public abstract Rank getRank();

	/**
	 * Task to be executed after every update
	 * @param the task to be executed
	 */
	public void setPostUpdateTask(Task task)
	{
		this.postUpdateTask = task;
	}
	
	/**
	 * Returns the task to be executed after every update
	 */
	public Task getPostUpdateTask()
	{
		return postUpdateTask;
	}
}
