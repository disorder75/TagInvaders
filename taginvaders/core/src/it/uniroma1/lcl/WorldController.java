package it.uniroma1.lcl;

import it.uniroma1.lcl.screens.AbstractScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.reflect.ClassReflection;

/**
 * The world controller class: it manages the game mechanics
 * @author bellincampi, navigli
 *
 */
public class WorldController
{
	/**
	 * Reference to the game instance
	 */
	private TagInvaders game;

	/**
	 * Current game level
	 */
	private Level level;
	
	/**
	 * Number of the current game level
	 */
	private String levelNumber = Constants.LEVEL_NUMBER;
	
	/**
	 * The game player
	 */
	private Player player;
	
	/**
	 * Reference to the game options 
	 */
	private Preferences options = Gdx.app.getPreferences("options");
	
	/**
	 * Time in which the pause started (needed to correctly resume timers)
	 */
	private long pauseStartTime;
	
	/**
	 * Creates the world controller
	 * @param g reference to the game 
	 */
	public WorldController(TagInvaders g)
	{
		game = g;
		player = new Player();
		init();
	}
	
	/**
	 * Initializes the world controller
	 */
	private void init()
	{
		player.init();

		if (options.contains(Constants.LEVEL_NUMBER_PROP))
			levelNumber = options.getString(Constants.LEVEL_NUMBER_PROP);
	}

	/**
	 * Handles the update of the whole game!
	 * @param deltaTime the time passed since the last step
	 */
	public void update(float deltaTime)
	{
		switch (game.getState())
		{
			case LOADING:
				if (Assets.manager.update()) game.setState(GameState.MAIN_MENU);
			break;
			case MAIN_MENU:
				// new game
				if (Gdx.input.isKeyJustPressed(Input.Keys.N) || Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT))
				{
					if (isGameWithAPurpose()) game.setState(GameState.HELP);
					else startLevel();
				}
				
				// music
				if (Gdx.input.isKeyJustPressed(Input.Keys.M))
				{
					if (Constants.getOptions().getBoolean(Constants.MUSIC_OPTION,true)) Constants.getOptions().putBoolean(Constants.MUSIC_OPTION, false);
					else Constants.getOptions().putBoolean(Constants.MUSIC_OPTION, true);
					options.flush();
				}
				
				// sound effects
				if (Gdx.input.isKeyJustPressed(Input.Keys.S))
				{
					if (Constants.getOptions().getBoolean(Constants.SFX_OPTION)) Constants.getOptions().putBoolean(Constants.SFX_OPTION, false);
					else Constants.getOptions().putBoolean(Constants.SFX_OPTION, true);
					options.flush();
				}
			break;
			
			case HELP:
				if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY) || !isGameWithAPurpose())
				{
					startLevel();
				}
			break;
			
			case GAME: 
				if (Gdx.input.isKeyJustPressed(Input.Keys.P))
				{
					game.setState(GameState.PAUSED);
					pauseStartTime = System.currentTimeMillis();
					Timer.instance().stop();
				}
				else
				{
					// update the level
					level.update(deltaTime); 
					// check the victory conditions
					checkDefeatConditions();
					// check the defeat conditions
					checkVictoryConditions();
				}
				
				if (Gdx.input.isKeyJustPressed(Input.Keys.R))
				{
					level.dispose();
					game.setState(GameState.GAME_OVER);
				}
			break;
			
			case GAME_OVER:
				// play again
				if (Gdx.input.isKeyJustPressed(Input.Keys.R) || Gdx.input.isKeyJustPressed(Input.Keys.CONTROL_LEFT))
				{
					game.setState(GameState.MAIN_MENU);
					
					// reset variables
					init();
				}
			break;

			case PAUSED:
				if (Gdx.input.isKeyJustPressed(Input.Keys.P))
				{
					game.setState(GameState.RESUMED);
					Timer.instance().delay(System.currentTimeMillis()-pauseStartTime);
					Timer.instance().start();
				}
			break;
			
			case VICTORY:
				if (Gdx.input.isKeyPressed(Input.Keys.R)) // go back to main menu
				{
					game.setState(GameState.MAIN_MENU);
					
					// reset variables
					init();
				}
			break;
			
			case RESUMED:
			break;
		}
	}
	
	/**
	 * Check defeat conditions
	 */
	private void checkDefeatConditions()
	{
		if (level.isLost)
		{
			level.dispose();
			game.setState(GameState.GAME_OVER);
		}
	}
	
	/**
	 * Check victory conditions
	 */
	private void checkVictoryConditions()
	{
		if (level.isComplete())
		{
			game.setState(GameState.VICTORY);
			level.dispose();
		}
	}
	
	/**
	 * Returns the current level
	 * @return the current level
	 */
	public Level getLevel()
	{
		return level;
	}
	
	/**
	 * Returns the number of lives the player has
	 * @return the number of lives 
	 * @deprecated
	 */
	public int getLives() { return player.getLives(); }
	
	/**
	 * Increments the lives number
	 * @param l number of lives to add
	 * @deprecated
	 */
	public void addLives(int l) { player.addLives(l); }
	
	/**
	 * Returns the score
	 * @return the score
	 * @deprecated
	 */
	public int getScore() { return player.getScore(); }
	
	/**
	 * Increments the score
	 * @param s the score to be added
	 * @deprecated
	 */
	public void addScore(int s) { player.addScore(s); }

	/**
	 * Sets the score 
	 * @param s new score
	 * @deprecated
	 */
	public void setScore(int s) { player.setScore(s); }
	
	/**
	 * Returns the current player
	 * @return the current player
	 */
	public Player getPlayer() { return player; }
	
	/**
	 *  Returns the current font
	 * @return the current font
	 */
	public BitmapFont getScreenFont()
	{
		Screen s = game.getScreen();
		if (s instanceof AbstractScreen)
			return ((AbstractScreen)s).getScreenFont();
		return null;
	}
	
	/**
	 * Returns the game camera
	 * @return the game camera
	 */
	public OrthographicCamera getCamera()
	{
		return game.getCamera();
	}

	/**
	 * Starts the current level
	 */
	private void startLevel()
	{
		game.setState(GameState.GAME);

		String levelClassName = "it.uniroma1.lcl.level"+levelNumber+".Level"+levelNumber;
		
		try
		{
			// use the reflection to instantiate the level
			Class<?> levelClass = 
					ClassReflection.forName(levelClassName);
			level = (Level)levelClass.getConstructor(this.getClass()).newInstance(this);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.err.println("Livello "+levelClassName+" non disponibile, costruttore mancante o errore durante la costruzione");
			Gdx.app.exit();
		}
	}
	
	/**
	 * Returns a boolean that specifies if the game is currently with a purpose
	 */
	boolean isGameWithAPurpose()
	{
		return options.contains(Constants.GAME_WITH_A_PURPOSE) && options.getBoolean(Constants.GAME_WITH_A_PURPOSE);
	}
}
