package exceptions;

public class ObjectDontExistException extends Exception
{
	public ObjectDontExistException()
	{
		super("Object you are looking for does not exist");
	}
}
