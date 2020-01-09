package it.uniroma1.lcl.screens;

import it.uniroma1.lcl.Constants;
import it.uniroma1.lcl.WorldController;

/**
 * Main menu screen
 * @author navigli
 *
 */
public class MainMenuScreen extends AbstractScreen
{	
	public MainMenuScreen(WorldController wc)
	{
		super(wc);
	}
	
	@Override
	protected void draw(float delta)
	{
		batch.begin();
		
		font.setScale(3f);
		
		printCentered(80, "TAG INVADERS");
		font.setScale(1.5f);
		printCentered(300, 
				"Press fire [LEFT_CTRL] or [N] to start!", "", 
				"[M]usic: "+(Constants.getOptions().getBoolean(Constants.MUSIC_OPTION) ? "on" : "off"), 
				"[S]fx: "+(Constants.getOptions().getBoolean(Constants.SFX_OPTION) ? "on" : "off"));
		
		batch.end();
	}
}
