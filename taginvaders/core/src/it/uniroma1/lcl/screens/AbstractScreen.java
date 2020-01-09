package it.uniroma1.lcl.screens;

import it.uniroma1.lcl.Constants;
import it.uniroma1.lcl.WorldController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * The abstact Screen class
 * @author navigli
 *
 */
abstract public class AbstractScreen implements Screen
{
	/**
	 * The screen SpriteBatch
	 */
	protected SpriteBatch batch;
	
	/**
	 * The screen ShapeRenderer
	 */
	protected ShapeRenderer shapeRenderer;
	
	/**
	 * The screen font
	 */
	protected BitmapFont font;

	/**
	 * The screen camera
	 */
	protected OrthographicCamera camera;
	
	/**
	 * The screen viewport
	 */
	protected Viewport vp;
	
	/**
	 * Reference to the world controller
	 */
	protected WorldController worldController;
	
	/**
	 * @param wc world controller
	 */
	public AbstractScreen(WorldController wc)
	{
		worldController = wc;
		init();
	}
	
	/**
	 * Initialize the Screen
	 */
	public void init()
	{
		// create the sprite batch
		batch = new SpriteBatch();
		
		// create the shape renderer
		shapeRenderer = new ShapeRenderer();

		// create a font
		font = new BitmapFont();
		font.setColor(Color.BLACK);
		font.scale(0.5f);
		
		// create the camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Constants.WIDTH, Constants.HEIGHT);
		
		// create a view on the camera that "fits" the screen
		vp = new FitViewport(Constants.WIDTH, Constants.HEIGHT, camera);
	}

	@Override
	public void render(float delta)
	{
		Gdx.gl.glClearColor(Constants.YELLOW.r,Constants.YELLOW.g,Constants.YELLOW.b,Constants.YELLOW.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		camera.update();
		
		// batch begin and end have to be handled in level!
		draw(delta);
	}
	
	/**
	 * Print some strings centered
	 * @param font
	 * @param y
	 * @param strings
	 */
	protected void printCentered(BitmapFont font, float y, String... strings)
	{
		float x = 0;
		for (String s : strings)
			x = Math.max(x, font.getBounds(s).width);

		float height = font.getBounds(strings[0]).height*2;
		
		for (String s : strings)
		{
			font.draw(batch, s, (Constants.WIDTH-x)/2, Constants.HEIGHT-y);
			y += height;
		}
	}

	protected void printCentered(float y, String... strings)
	{
		printCentered(font, y, strings);
	}

	/**
	 * Draw thing on screen!
	 * @param delta time passed since the last frame
	 */
	abstract protected void draw(float delta);

	@Override
	public void resize(int width, int height)
	{
		vp.update(width, height);
	}

	@Override
	public void show()
	{
	}

	@Override
	public void hide()
	{
	}

	@Override
	public void pause()
	{
	}

	@Override
	public void resume()
	{
	}

	@Override
	public void dispose()
	{
		batch.dispose();
		font.dispose();
	}
	
	/**
	 * Returns the font
	 * @return the font
	 */
	public BitmapFont getScreenFont() { return font; }
	
	/**
	 * Returns the game camera
	 * @return the game camera
	 */
	public OrthographicCamera getCamera()
	{
		return camera;
	}
}
