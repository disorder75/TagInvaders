package it.uniroma1.lcl.screens;

import it.uniroma1.lcl.Constants;
import it.uniroma1.lcl.WorldController;

/**
 * Victory screen
 * @author navigli
 *
 */
public class VictoryScreen extends AbstractScreen
{
	public VictoryScreen(WorldController wc)
	{
		super(wc);
	}
	
	@Override
	protected void draw(float delta)
	{
		batch.begin();
		
		printCentered(Constants.HEIGHT/2-80, "YOU WIN!");
		printCentered(Constants.HEIGHT/2+80, "Your rank: "+worldController.getLevel().getRank());
		printCentered(Constants.HEIGHT/2+120, "Press [R] to restart");
		
		batch.end();
	}
}
