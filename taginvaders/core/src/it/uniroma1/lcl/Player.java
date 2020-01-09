package it.uniroma1.lcl;

/**
 * The player's class
 * @author navigli
 *
 */
public class Player
{
	/**
	 * The player's ship
	 */
	protected PlayerShip ship;
	
	/**
	 * The player's score
	 */
	private int score;
	
	/**
	 * Number of lives left
	 */
	private int lives;
	
	/**
	 * Class of the player to be created when dead
	 */
	protected Class<? extends PlayerShip> playerClass;
	
	public Player()
	{
		init();
	}
	
	/**
	 * Initializes the player's information
	 */
	public void init()
	{
		lives = Constants.PLAYER_LIVES;
		if (Constants.getOptions().contains(Constants.PLAYER_LIVES_PROP))
			lives = Constants.getOptions().getInteger(Constants.PLAYER_LIVES_PROP);
	}
	
	/**
	 * Returns the current player's score
	 */
	public int getScore() { return score; }
	
	/**
	 * Returns the current player's number of lives
	 */
	public int getLives() { return lives; }

	/**
	 * Increments the lives number
	 * @param l number of lives to add
	 */
	public void addLives(int l) { lives += l; }
	
	/**
	 * Increments the score
	 * @param s the score to be added
	 */
	public void addScore(int s) { score += s; }

	/**
	 * Sets the score 
	 * @param s new score
	 */
	public void setScore(int s) { score = s; }
	
	/**
	 * Creates a new ship
	 * @param playerClass the {@link PlayerShip} class for the new ship
	 */
	public void newPlayerShip(Level level)
	{
		try
		{
			ship = playerClass.getConstructor(Level.class).newInstance(level);
		}
		catch(Exception e)
		{
			// stato fatale del gioco...
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets theplayer class
	 * @param playerClass the player class to use everytime it dies
	 */
	public void setPlayerClass(Class<? extends PlayerShip> playerClass)
	{
		this.playerClass = playerClass;
	}
	
	/**
	 * Returns the current player's ship
	 * @return
	 */
	public PlayerShip getShip() { return ship; }
}
