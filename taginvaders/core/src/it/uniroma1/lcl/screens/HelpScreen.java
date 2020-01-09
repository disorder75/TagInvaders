package it.uniroma1.lcl.screens;

import it.uniroma1.lcl.Constants;
import it.uniroma1.lcl.WorldController;

/**
 * Help screen shown before entering the actual level
 * @author bellincampi
 *
 */
public class HelpScreen extends AbstractScreen
{
	public HelpScreen(WorldController wc)
	{
		super(wc);
	}

	@Override
	protected void draw(float delta)
	{
		batch.begin();
		
		printCentered(Constants.HEIGHT/2-120, "DIVINA COMMEDIA");
		printCentered(Constants.HEIGHT/2-80, "Poema di Dante Alighieri scritto in terzine");
		printCentered(Constants.HEIGHT/2-50, "incatenate di versi endecasillabi");
		printCentered(Constants.HEIGHT/2+120, "Premi SPAZIO se le immagini che scorrono");
		printCentered(Constants.HEIGHT/2+150, " in alto sono inerenti al contesto: ");
		printCentered(Constants.HEIGHT/2+180, "otterrai un super sparo!");
		
		batch.end();
	}
}
