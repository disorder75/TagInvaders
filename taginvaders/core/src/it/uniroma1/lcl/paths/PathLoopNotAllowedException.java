package it.uniroma1.lcl.paths;

public class PathLoopNotAllowedException extends RuntimeException
{
	public PathLoopNotAllowedException(String message)
	{
		 super(message);
	}
}
