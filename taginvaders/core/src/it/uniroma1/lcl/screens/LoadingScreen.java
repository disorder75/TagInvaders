package it.uniroma1.lcl.screens;

import it.uniroma1.lcl.Assets;
import it.uniroma1.lcl.Constants;
import it.uniroma1.lcl.WorldController;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

/**
 * Loading screen
 * @author bellincampi
 *
 */
public class LoadingScreen extends AbstractScreen
{
	protected TextureAtlas loadingBarAtlas = Assets.manager.get("graphic/sprites/loading_bar.txt", TextureAtlas.class);
	
	public LoadingScreen(WorldController wc)
	{
		super(wc);
	}
	
	/*
	 * Draw the loading bar
	 */
	@Override
	protected void draw(float delta)
	{

		float percent = (Constants.WIDTH-40 - (53 + loadingBarAtlas.getRegions().get(0).getRegionWidth())) * Assets.manager.getProgress();
		
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(0.55f, 0.55f, 0.55f, 0.1f);
		shapeRenderer.rect(50 + loadingBarAtlas.getRegions().get(0).getRegionWidth(), 451, 
				Constants.WIDTH-50 - (50 + loadingBarAtlas.getRegions().get(0).getRegionWidth()), 18);
		
		
		shapeRenderer.setColor(0.14f, 0.14f, 0.14f, 0.1f);
		shapeRenderer.rect(50 + loadingBarAtlas.getRegions().get(0).getRegionWidth()-3, 453, percent, 14);
		
		shapeRenderer.end();
		
		batch.begin();
		
		font.setScale(3f);
		
		printCentered(80, "TAG INVADERS");
		font.setScale(1.5f);
		printCentered(300, "LOADING");
		
		batch.draw(loadingBarAtlas.getRegions().get(0), 50, 451);
		batch.draw(loadingBarAtlas.getRegions().get(2), 53, 453);
		batch.draw(loadingBarAtlas.getRegions().get(1), Constants.WIDTH-50, 451);
		batch.draw(loadingBarAtlas.getRegions().get(3), percent+63, 453);

		batch.end();
	}
}
