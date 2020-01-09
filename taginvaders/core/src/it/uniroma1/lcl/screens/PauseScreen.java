package it.uniroma1.lcl.screens;

import it.uniroma1.lcl.Constants;
import it.uniroma1.lcl.WorldController;

/**
 *
 * Pause screen
 * @author navigli
 *
 */
public class PauseScreen extends AbstractScreen
{
	public PauseScreen(WorldController wc)
	{
		super(wc);
	}

	@Override
	protected void draw(float delta)
	{
		batch.begin();
		
		printCentered(Constants.HEIGHT/2, "PAUSE");
		
		batch.end();
	}
}
