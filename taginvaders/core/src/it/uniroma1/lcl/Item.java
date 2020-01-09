package it.uniroma1.lcl;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * An item (typically to be avoided or picked up)
 * @author navigli
 *
 */
public abstract class Item extends Entity
{
	protected Texture standardSprite = Assets.manager.get(Constants.POWER_UP_TEXTURE_PATH, Texture.class);
	
	/**
	 * Constructor for the Item
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public Item(Level l, float x, float y)
	{
		super(l, x, y);
	}
	
	/**
	 * Updates the item
	 */
	public abstract void update(float deltaTime);

	public void draw(SpriteBatch batch)
	{
		batch.draw(standardSprite, x, y);
	}
	
	/**
	 * Returns the score awarded to the player when the item is picked up
	 * @return score awarded by the item
	 */
	public int getKillScore()
	{
		return Constants.ITEM_SCORE;
	}
	
	/**
	 * Handles the collision between the item and a player ship
	 * @param ship the player the item is colliding with
	 */
	public void onCollide(PlayerShip ship)
	{
		level.remove(this);
	}
}
