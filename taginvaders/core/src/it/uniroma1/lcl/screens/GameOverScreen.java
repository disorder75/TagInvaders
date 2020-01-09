package it.uniroma1.lcl.screens;

import it.uniroma1.lcl.Constants;
import it.uniroma1.lcl.WorldController;

/**
 * The game over screen
 * @author navigli
 *
 */
public class GameOverScreen extends AbstractScreen
{
	public GameOverScreen(WorldController wc)
	{
		super(wc);
	}

	@Override
	protected void draw(float delta)
	{
		batch.begin();
		
		printCentered(Constants.HEIGHT/2-120, "GAME OVER");
		printCentered(Constants.HEIGHT/2+80, "Your rank: "+worldController.getLevel().getRank());
		printCentered(Constants.HEIGHT/2+120, "Press fire [LEFT_CTRL] or [R] to restart");
		
		batch.end();
	}
}
