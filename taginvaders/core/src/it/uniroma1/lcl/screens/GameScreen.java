package it.uniroma1.lcl.screens;

import it.uniroma1.lcl.Assets;
import it.uniroma1.lcl.Constants;
import it.uniroma1.lcl.WorldController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.utils.Timer;

/**
 * Schermo di gioco di default
 * @author navigli
 *
 */
public class GameScreen extends AbstractScreen
{
	public class GameTextScreen
	{
		/**
		 * Pseudorandom number generator
		 */
		private RandomXS128 rand = new RandomXS128();

		/**
		 * Font used to draw the level name
		 */
		private BitmapFont levelHeaderFont = new BitmapFont();

		/**
		 * Number of character of the description to show 
		 */
		private int descriptionCharacters = 0;
		
		/**
		 * is the text curson visible?
		 */
		private boolean cursor = true;
		
		/**
		 * Text cursor texture
		 */
		protected Texture cursorTexture =  Assets.manager.get(Constants.TEXT_CURSOR_TEXTURE_PATH, Texture.class);

		/**
		 * Text cursor blinking delay
		 */
		static public final float CURSOR_BLINKING_DELAY = 0.4f;

		/**
		 * Text cursor blinking delay when is not showed
		 */
		static public final float CURSOR_NON_BLINKING_DELAY = 0.2f;
		
		/**
		 * Transition between name and description delay
		 */
		static public final float LEVEL_NAME_DELAY = 1f;
		
		/**
		 * Transition between description and play delay
		 */
		static public final float LEVEL_DESCRIPTION_DELAY = 3.5f;

		/**
		 * Level name font minimum dimension
		 */
		static public final float MIN_LEVEL_NAME_SIZE = 2f;

		private GameTextScreen()
		{
			levelHeaderFont.setColor(Color.RED);
			levelHeaderFont.setScale(100f);
		}

		public void draw(float delta)
		{
			batch.begin();
			
			switch(state)
			{
				case SHOWING_LEVEL_DESCRIPTION:
					drawLevelDescription(delta);
	
				case SHOWING_LEVEL_NAME:
					drawLevelLabel(delta);
				break;
				
				case PLAY:
				break;
			}
			
			batch.end();
		}
		
		private void typeNextCharacter()
		{
			// type the next character
			descriptionCharacters++;
			final String fullDescription = worldController.getLevel().getDescription();

			if (descriptionCharacters == fullDescription.length())
			{
				Timer.schedule(new Timer.Task()
				{
					@Override
					public void run()
					{
						state = TextState.PLAY;
					}
					
				}, LEVEL_DESCRIPTION_DELAY);
			}
			else
				Timer.schedule(new Timer.Task()
				{
					@Override
					public void run()
					{
						typeNextCharacter();
					}
					
				}, 1f/(7f+rand.nextInt(5)));
		}

		/**
		 * Draw the label containing level name and description
		 */
		protected void drawLevelLabel(float delta)
		{
			if (state == TextState.SHOWING_LEVEL_NAME 
					&& levelHeaderFont.getScaleX() > MIN_LEVEL_NAME_SIZE)
			{
				levelHeaderFont.scale(-1f*delta*Constants.IDEAL_FPS);
				if (levelHeaderFont.getScaleX() <= MIN_LEVEL_NAME_SIZE)
				{
					Timer.schedule(new Timer.Task()
					{
						@Override
						public void run()
						{
							state = TextState.SHOWING_LEVEL_DESCRIPTION;

							blink();
							typeNextCharacter();
						}
					}, LEVEL_NAME_DELAY);
					levelHeaderFont.setScale(MIN_LEVEL_NAME_SIZE, MIN_LEVEL_NAME_SIZE);
				}
			}
			
			printCentered(levelHeaderFont, Constants.HEIGHT/6, worldController.getLevel().getName());
		}
		
		protected void drawLevelDescription(float delta)
		{
			final String fullDescription = worldController.getLevel().getDescription();
			String descr = fullDescription.substring(0, descriptionCharacters);
			
			TextBounds tb = font.getBounds(fullDescription);
			float x = (Constants.WIDTH-tb.width)/2;
			float y = Constants.HEIGHT/6f*5f - tb.height*2;
			font.draw(batch, descr, x, y);

			if (cursor)
				batch.draw(cursorTexture, x+font.getBounds(descr).width, y-tb.height-7, tb.height, tb.height+8);
		}
		
		public void blink()
		{
			Timer.schedule(new Timer.Task()
			{
				@Override
				public void run()
				{
					cursor ^= true;
					blink();
				}
			}, cursor ? CURSOR_BLINKING_DELAY : CURSOR_NON_BLINKING_DELAY);
		}
	}

	enum TextState { SHOWING_LEVEL_NAME, SHOWING_LEVEL_DESCRIPTION, PLAY }
	
	/**
	 * Screen state
	 */
	private TextState state = TextState.SHOWING_LEVEL_NAME;

	private GameTextScreen textUtil = new GameTextScreen();
	
	public GameScreen(WorldController wc)
	{
		super(wc);
	}
	
	@Override
	protected void draw(float delta)
	{	
		// pain the background with the color stored in the level
		Gdx.gl.glClearColor(worldController.getLevel().getBackgroundColor().r,worldController.getLevel().getBackgroundColor().g,
				worldController.getLevel().getBackgroundColor().b,worldController.getLevel().getBackgroundColor().a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		batch.begin();
		
		//font.draw(batch, "Score: "+worldController.getScore(), 10, 	Constants.HEIGHT-10);
		//font.draw(batch, "Lives: "+worldController.getLives(), Constants.WIDTH/2-20, Constants.HEIGHT-10);
		//font.draw(batch, "FPS: "+Gdx.graphics.getFramesPerSecond(), Constants.WIDTH-100, Constants.HEIGHT-10);
		
		batch.end();
		
		worldController.getLevel().render(batch);
		
		textUtil.draw(delta);
	}
}
