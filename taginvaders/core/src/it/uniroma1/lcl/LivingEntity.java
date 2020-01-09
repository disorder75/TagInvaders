package it.uniroma1.lcl;

import it.uniroma1.lcl.decorations.Bubble;
import it.uniroma1.lcl.paths.Path;

/**
 * A living entity in the game
 * @author navigli
 *
 */
abstract public class LivingEntity extends Entity
{
	/**
	 * Possibile states for the entity
	 */
	public enum State { ALIVE, DYING, DEAD }

	/**
	 * Current state of the entity
	 */
	protected State state = State.ALIVE;

	/**
	 * Constructor for the entity
	 * @param l level
 	 * @param x x coordinate
	 * @param y y coordinate
	 * @param speed speed
	 */
	protected LivingEntity(Level l, float x, float y, float speed)
	{
		super(l, x, y, speed);
	}

	/**
	 * Constructor for the entity
	 * @param l level
 	 * @param x x coordinate
	 * @param y y coordinate
	 * @param speed speed
	 * @param d direction
	 */
	public LivingEntity(Level l, float x, float y, float speed, Direction d)
	{
		super(l, x, y, speed, d);
	}
	
	/**
	 * Constructor for the entity
	 * @param l level
 	 * @param x x coordinate
	 * @param y y coordinate
	 * @param speed speed
	 * @param path path
	 */
	public LivingEntity(Level l, float x, float y, float speed, Path path)
	{
		super(l, x, y, speed, path);
	}
	
	/**
	 * Handles the death of the living entity
	 */
	public void die()
	{
		if (state != State.DEAD) 
			setState(State.DYING);
		
		// spawn bubbles
		for(int i=0; i<6; i++)
		level.add (new Bubble(level, x+rand.nextInt((int) getSpriteWidth()), y, 1+rand.nextInt(3), Direction.UP));
	}

	public void update(float deltaTime)
	{
		super.update(deltaTime);
		switch (getState())
		{
		case ALIVE:
			aliveUpdate(deltaTime);
			break;
		case DYING:
			dyingUpdate(deltaTime);
			break;
		case DEAD:
			deadUpdate(deltaTime);
			break;
		}
	}
	
	@Override
	protected boolean usesPath()
	{
		return isAlive();
	}

	/**
	 * What the enemy does when is dead
	 * @param deltaTime time passed since the last frame
	 */
	protected void deadUpdate(float deltaTime)
	{
	}
	
	/**
	 * What the enemy does when is dying
	 * @param deltaTime time passed since the last frame
	 */
	protected abstract void aliveUpdate(float deltaTime);

	/**
	 * What the enemy does when is dying
	 * @param deltaTime time passed since the last frame
	 */
	protected abstract void dyingUpdate(float deltaTime);
	
	/**
	 * Returns true if the entity is dead
	 * @return true if the entity is dead
	 */
	public boolean isDead() { return state == State.DEAD; }

	/**
	 * True if the entity is alive
	 * @return true if the entity is alive
	 */
	public boolean isAlive() { return state == State.ALIVE; }
	
	/**
	 * Returns true if the entity is dying
	 * @return true if the entity is dying
	 */
	public boolean isDying() { return state == State.DYING; }
	
	/**
	 * Returns the current state of the entity
	 * @return the current state of the entity
	 */
	public State getState() { return state; }

	/**
	 * Sets a new state for the entity
	 * @param the new state
	 */
	protected void setState(State newState)
	{
		state = newState;
		stateTime = 0;
	}
}
