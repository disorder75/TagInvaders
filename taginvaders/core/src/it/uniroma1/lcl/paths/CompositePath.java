package it.uniroma1.lcl.paths;

import it.uniroma1.lcl.paths.SplinePath.Start;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

/**
 * Path wrapper to support multiple paths. Handles the different paths and redirects
 * the method calls to the actual path the entity is currently on.
 * @author bellincampi
 *
 */
public class CompositePath extends Path
{
	/**
	 * The path on which the entity is currently on
	 */
	protected SplinePath currentPath;
	
	/**
	 * Path list iterator
	 */
	protected ListIterator<SplinePath> iterator;
	
	/**
	 * List of all the paths
	 */
	protected List<SplinePath> paths = new ArrayList<SplinePath>();
	
	/**
	 * True if the path is complete
	 */
	private boolean complete;
	
	/**
	 * Traveling direction
	 */
	protected Start start;
	
	/**
	 * Creates a CompositePath starting from an already existing path list
	 * @param paths the path list
	 */
	public CompositePath(Start start, Loop l, List<SplinePath> paths)
	{
		super(paths.get(0).x, paths.get(0).y, paths.get(0).getSpeed(), l);
		this.start = start;
		this.paths = paths;
		for (SplinePath p : paths)
		{
			if (!p.getLoopType().isNone()) throw new PathLoopNotAllowedException("You can only have Loop.NONE as loop type in a composite path");
			p.setStart(start);
		}
		this.iterator = paths.listIterator();
		currentPath = iterator.next();
	}
	
	/**
	 * Creates a CompositePath starting from the Paths in input
	 * @param paths the paths which will be in the CompositPath
	 */
	public CompositePath(Start start, Loop l, SplinePath... paths)
	{
		this(start, l, Arrays.asList(paths));
	}

	@Override
	public void updateImpl(float deltaTime)
	{
		currentPath.updateImpl(deltaTime);
		
		if (complete) complete = false;
		
		if (currentPath.isComplete()) 
		{
			if (!hasNextPath())
			{
				complete = true;
				
				switch (loop)
				{
					case NONE: 
						position = isBeginToEnd() ? 1 : 0; break;
					
					case RESTART: 
						iterator = paths.listIterator();
						currentPath = iterator.next();
					break;
					
					case FLIP: 
						reverseStart(); 
						for (SplinePath p : paths)
						{
							p.setStart(start);
							p.reset();
						}
						getNextPath();
						break;
					
				case CONTINUE: 
					
					for (SplinePath p : paths)
					{
						for (int i=0; i< p.controlPoints.length; i++)
						{
							p.controlPoints[i].x += getX() - startX;
							p.controlPoints[i].y += getY() - startY;
						}
						
						p.startX = p.controlPoints[0].x;
						p.startY = p.controlPoints[0].y;
						
						p.spline.set(p.controlPoints, p.getSpline().continuous);
						p.reset(); 
					}
						
					iterator = paths.listIterator();
					currentPath = iterator.next();
					
					startX = getX();
					startY = getY();
					break;
				}
			}
			else
			{
				currentPath.reset();
				currentPath = getNextPath();
			}	
		}
	}
	
	/**
	 * Returns the next path on the list
	 * @return the next path on the list
	 */
	protected SplinePath getNextPath()
	{
		if (isBeginToEnd())
		{
			if (!iterator.hasNext())
				iterator = paths.listIterator();
			return iterator.next();
		}
		else
		{
			if (!iterator.hasPrevious())
				iterator = paths.listIterator();
			return iterator.previous();
		}
	}
	
	protected boolean hasNextPath()
	{
		if (isBeginToEnd()) return iterator.hasNext();
		else return iterator.hasPrevious();
	}
	
	@Override
	public float getX()
	{
		return currentPath.x;
	}
	
	@Override
	public float getY()
	{
		return currentPath.y;
	}

	/**
	 * Sets the traveling direction to begin-to-end or end-to-begin
	 * @param s the traveling direction
	 */
	public void setStart(Start s)
	{
		start = s;
	}
	
	/**
	 * Reverse the traveling direction
	 */
	public void reverseStart()
	{
		if (start == Start.BEGIN) start = Start.END;
		else start = Start.BEGIN;
	}
	
	/**
	 * Returns true if the traveling direction is begin-to-end, false if it is end-to-begin
	 * @return true if the traveling direction is begin-to-end
	 */
	public boolean isBeginToEnd()
	{
		if (start == Start.BEGIN) return true;
		else return false;
	}
	
	/**
	 * Returns true if the path has been completed
	 * @return true if the path has been completed
	 */
	@Override
	public boolean isComplete()
	{
		return complete;
		//return !iterator.hasNext();
	}
}
