package it.uniroma1.lcl;

import it.uniroma1.lcl.screens.AbstractScreen;
import it.uniroma1.lcl.screens.GameOverScreen;
import it.uniroma1.lcl.screens.GameScreen;
import it.uniroma1.lcl.screens.HelpScreen;
import it.uniroma1.lcl.screens.LoadingScreen;
import it.uniroma1.lcl.screens.MainMenuScreen;
import it.uniroma1.lcl.screens.PauseScreen;
import it.uniroma1.lcl.screens.VictoryScreen;

import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Main class for the game
 * @author bellincampi, navigli
 */
public class TagInvaders extends Game 
{
	/**
	 * World controller
	 */
	private WorldController worldController;
	
	/**
	 * Game options reference
	 */
	private Preferences options;
	
	/**
	 * Currente game state
	 */
	private GameState gameState;
	
	/**
	 * Pause screen
	 */
	private Screen pausedScreen;
	
	/**
	 * True if the game is active (has focus, etc.)
	 */
	private boolean gameIsActive = true;
	
	/**
	 * Method called by LibGDX at the beginning of execution
	 */
	@Override
	public void create()
	{
		// sets the game options
		options = Constants.getOptions();
		options.putBoolean(Constants.MUSIC_OPTION, false);
		options.putBoolean(Constants.SFX_OPTION, false);
		options.flush();

		// loads additional properties from the configuration file
		FileHandle gameProps = Gdx.files.internal(Constants.GAME_PROPERTIES_FILENAME);
		try
		{
			ResourceBundle b = new PropertyResourceBundle(gameProps.read());
			for (String s : b.keySet())
				options.putString(s, b.getString(s));
		}
		catch(IOException e)
		{
			e.printStackTrace();
			Gdx.app.exit();
		}
		
		// load the assests
		Assets.load();
		
		// initialize the world controller
		worldController = new WorldController(this);

		setState(GameState.LOADING);
	}

	/**
	 * Updates the game. Called every "instant" by LibGDX
	 */
	@Override
	public void render()
	{
		// updates the logic
		if (gameIsActive) worldController.update(Gdx.graphics.getDeltaTime());
		
		// updates the graphics
		super.render();
	}

	/**
	 * Changes the game state and set the correct Screen
	 * @param state the new state of the game
	 */
	public void setState(GameState state)
	{
		switch(state)
		{
			case MAIN_MENU: setScreen(new MainMenuScreen(worldController)); break;
			case HELP: setScreen(new HelpScreen(worldController)); break;
			case GAME: setScreen(new GameScreen(worldController)); break;
			case GAME_OVER: setScreen(new GameOverScreen(worldController)); break;
			case VICTORY: setScreen(new VictoryScreen(worldController)); break;
			case PAUSED: 
				pausedScreen = getScreen();
				setScreen(new PauseScreen(worldController)); 
			break;
			case RESUMED:
				setScreen(pausedScreen);
				state = GameState.GAME;
				pausedScreen = null;
			break;
			case LOADING:
				setScreen(new LoadingScreen(worldController));
			break;
		}
		
		gameState = state;
	}

	/**
	 * Frees the allocated resouces
	 */
	@Override
	public void dispose()
	{
		Assets.manager.dispose();
	}

	/**
	 * Handles the game window resize. Called by LibGDX when needed
	 */
	public void resize(int width, int height)
	{
		getScreen().resize(width, height);
	}
	
	/**
	 * Returns the current state of the game
	 * @return the curernt game state
	 */
	public GameState getState()
	{
		 return gameState;
	}
	
	@Override
	public void pause()
	{
		gameIsActive = false;
	}
	
	@Override
	public void resume()
	{
		gameIsActive = true;
	}
	
	/**
	 * Returns the game camera
	 * @return the game camera
	 */
	public OrthographicCamera getCamera()
	{
		return ((AbstractScreen) getScreen()).getCamera();
	}
}
