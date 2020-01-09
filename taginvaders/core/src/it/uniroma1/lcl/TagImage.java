package it.uniroma1.lcl;

import it.uniroma1.lcl.decorations.Bubble;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Images to tag if the game is with a purpose
 * @author bellincampi
 *
 */
public class TagImage extends LivingEntity
{
	/**
	 * Image confidence
	 */
	public enum Type { GOOD, BAD, UNCERTAIN }
	
	/**
	 * Sprite of the image
	 */
	protected Texture standardSprite = Assets.manager.get("graphic/sprites/dante.png", Texture.class);
	
	private Type type;
	
	/**
	 * Constructor for the tag image
	 * @param l level
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param speed speed
	 * @param d direction
	 */
	public TagImage(Level l, float x, float y, float speed, Direction d)
	{
		super(l, x, y, speed, d);
		type = Type.GOOD;
	}

	/**
	 * What the bullet does when is alive
	 * @param deltaTime time passed since the last frame
	 */
	@Override
	protected void aliveUpdate(float deltaTime)
	{
		if (x >= Constants.WIDTH) 
		{
			die();
			// TODO avverti il server di ignorare l'immagine
		}
	}
	
	/**
	 * What the bullet does when is dying
	 * @param deltaTime time passed since the last frame
	 */
	@Override
	protected void dyingUpdate(float deltaTime){}
	
	@Override
	public void die()
	{
		level.remove(this);
		
		// crea bolle
		for(int i=0; i<10; i++)
		level.add (new Bubble(level, x+rand.nextInt((int) getSpriteWidth()), y, 1+rand.nextInt(3), Direction.UP));
	}

	/**
	 * Gestisce la raccolta del power up e quindi l'eventuale tag
	 */
	public void collect()
	{
		switch(type)
		{
		case GOOD:
			level.getPlayerShip().setSuperShotBonus();
			// TODO avverti il server che il giocatore avrebbe taggato bene
		break;
		
		case BAD:
			level.getPlayerShip().setWeakShotMalus();
			// TODO avverti il server che il giocatore avrebbe taggato male
		break;
		
		case UNCERTAIN:
			// TODO si premia il giocatore in ogni caso? Si valuta in base al punteggio corrente ottenuto?
			// TODO invia il tag al server
		break;
		}
		
		die();
	}
	
	@Override
	public float getSpriteWidth()
	{
		return standardSprite.getWidth();
	}

	@Override
	public float getSpriteHeight()
	{
		return standardSprite.getHeight();
	}

	@Override
	public float getHitboxWidth()
	{
		return standardSprite.getWidth();
	}

	@Override
	public float getHitboxHeight()
	{
		return standardSprite.getHeight();
	}

	@Override
	public void draw(SpriteBatch batch)
	{
		batch.draw(standardSprite, x, y);
	}
}
