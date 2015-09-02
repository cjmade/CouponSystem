package exceptions;

public class ObjectAlreadyExistException extends Exception
{
	public ObjectAlreadyExistException()
	{
		super("Object you are trying to add already exists");
	}
}
