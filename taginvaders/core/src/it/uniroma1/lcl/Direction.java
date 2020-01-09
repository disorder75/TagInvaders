package it.uniroma1.lcl;

import com.badlogic.gdx.math.Vector2;

/**
 * Class that represent every simple direction in the game
 * @author navigli
 *
 */
public class Direction
{	
	public static final Direction LEFT = new Direction(-1, 0)
	{
		public Direction reversed() { return Direction.RIGHT; }
	};
	public static final Direction RIGHT = new Direction(1, 0)
	{
		public Direction reversed() { return Direction.LEFT; }
	};

	public static final Direction UP = new Direction(0, 1)
	{
		public Direction reversed() { return Direction.DOWN; }
	};

	public static final Direction DOWN = new Direction(0, -1)
	{
		public Direction reversed() { return Direction.UP; }
	};

	public static final Direction IDLE = new Direction(0, 0)
	{
		public Direction reversed() { return Direction.IDLE; }
	};

	/**
	 * Bidimensional vector representing the direction
	 */
	private Vector2 vector;
	
	/**
	 * Direction constructor
	 * @param x dimensione x
	 * @param y dimensione y
	 */
	public Direction(float x, float y)
	{
		vector = new Vector2(x, y);
	}

	public float getX() { return vector.x; }
	public float getY() { return vector.y; }

	/**
	 * Returns the reversed direction
	 * @return
	 */
	public Direction reversed()
	{
		return new Direction(-vector.x, -vector.y);
	}
	
	@Override
	public String toString()
	{
		return "("+vector.x+","+vector.y+")";
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o == null || !(o instanceof Direction)) return false;
		Direction d = (Direction)o;
		return vector.equals(d.vector);
	}
	
	@Override
	public int hashCode()
	{
		return vector.hashCode();
	}
}
