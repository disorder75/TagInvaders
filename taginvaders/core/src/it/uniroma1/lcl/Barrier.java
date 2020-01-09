package it.uniroma1.lcl;

import it.uniroma1.lcl.bullets.Bullet;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Classic Space Invaders barrier
 * @author navigli
 *
 */
public class Barrier extends Entity
{
	
	/**
	 * Barrier width
	 */
	static public final int WIDTH = 60;
	
	/**
	 * Barrier height
	 */
	static public final int HEIGHT = 20;
	
	/**
	 * Barrier hitbox width
	 */
	protected float hitboxWidth = WIDTH;
	
	/**
	 * Barrier hitbox height
	 */
	protected float hitboxHeight = HEIGHT;

	/**
	 * arrier pixel map
	 */
	protected Pixmap pixmap;
	
	/**
	 * Texture linked to the pixel map
	 */
	protected Texture pixmapTexture;

	/**
	 * Barrier constructor
	 * @param x x coordinate 
	 * @param y y coordinate
	 */
	public Barrier(Level l, int x, int y)
	{
		super(l, x, y);
		
		pixmap = new Pixmap(WIDTH, HEIGHT, Format.RGBA8888);
		pixmap.setColor(level.getBackgroundColor());
		pixmap.fill();
		pixmap.setColor(Color.BLACK);
		pixmap.fillCircle(WIDTH/2, HEIGHT, WIDTH/2);
	}
	
	/**
	 * Draw the barrier
	 * @param batch
	 */
	public void draw(SpriteBatch batch)
	{
		if (pixmapTexture != null) pixmapTexture.dispose();
		pixmapTexture = new Texture(pixmap, Format.RGB888, false);
		
		batch.draw(pixmapTexture, x, y);
	}

	/**
	 * Check the collision with another entity
	 * @return true if it's colliding, false otherwise
	 */
	public boolean collideWith(Bullet b)
	{
		// if the hitbox of the bullet overlaps the barrier one
		return getHitbox().overlaps(b.getHitbox());
	}
	
	/**
	 * Update the barrier
	 */
	public void update(float deltaTime)
	{
		// does nothing unless redefined in a subclass
	}

	public void dispose()
	{
		if (pixmapTexture != null) pixmapTexture.dispose();
		if (pixmap != null) pixmap.dispose();
	}
	
	/**
	 * Handles the collision with a bullet
	 * @param b the bullet the barrier collides with
	 */
	public void onCollide(Bullet b)
	{
		int pixX = (int)(b.x-x);
		int delta = b.getDirection() == Direction.DOWN ? (int)b.getSpriteHeight()-1 : 0;
		int pixY = (int)(y+getSpriteHeight()-b.y);
		
		int count = 0;
		for (int k = pixX; k < pixX+b.getSpriteWidth(); k++)
			if (pixmap.getPixel(k, pixY) == Color.rgba8888(Color.BLACK)) count++;
		
		// at least one third of the barrier hit by the bullet must be "full"
		if (count > b.getSpriteWidth()/3f)
		{
			// remove that part of barrier
			pixmap.setColor(level.getBackgroundColor());
			pixmap.fillRectangle(pixX, pixY-delta, 
								(int)b.getSpriteWidth(), (int)b.getSpriteHeight());
			b.onCollide(this);
		}
	}

	@Override
	public float getSpriteWidth()
	{
		return pixmap.getWidth();
	}

	@Override
	public float getSpriteHeight()
	{
		return pixmap.getHeight();
	}
	
	@Override
	public float getHitboxWidth()
	{
		return hitboxWidth;
	}
	
	@Override
	public float getHitboxHeight()
	{
		return hitboxHeight;
	}
}
